package cc.mrbird.febs.out.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.FebsResponse;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.common.utils.ExportExcelUtils;
import cc.mrbird.febs.out.entity.OutBInfoExtend;
import cc.mrbird.febs.out.service.IOutBInfoService;
import cc.mrbird.febs.out.entity.OutBInfo;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.google.common.collect.Lists;
import com.wuwenze.poi.ExcelKit;
import com.wuwenze.poi.handler.ExcelReadHandler;
import com.wuwenze.poi.pojo.ExcelErrorField;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author viki
 * @since 2020-12-09
 */
@Slf4j
@Validated
@RestController
@RequestMapping("outBInfo")

public class OutBInfoController extends BaseController {

    private String message;
    @Autowired
    public IOutBInfoService iOutBInfoService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'接口投票','/dca/OutBInfo/OutBInfo','dca/OutBInfo/OutBInfo','outBInfo:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'接口投票新增','outBInfo:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'接口投票编辑','outBInfo:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'接口投票删除','outBInfo:delete',1,1,NOW())
 */


    /**
     * 分页查询数据
     *
     * @param request  分页信息
     * @param outBInfo 查询条件
     * @return
     */
    @GetMapping
    @RequiresPermissions("outBInfo:view")
    public Map<String, Object> List(QueryRequest request, OutBInfo outBInfo) {
        return getDataTable(this.iOutBInfoService.findOutBInfos(request, outBInfo));
    }

    /**
     * 添加
     *
     * @param outBInfo
     * @return
     */
    @Log("新增/按钮")
    @PostMapping
    @RequiresPermissions("outBInfo:add")
    public void addOutBInfo(@Valid OutBInfo outBInfo) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            outBInfo.setCreateUserId(currentUser.getUserId());
            this.iOutBInfoService.createOutBInfo(outBInfo);
        } catch (Exception e) {
            message = "新增/按钮失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改
     *
     * @param outBInfo
     * @return
     */
    @Log("修改")
    @PutMapping
    @RequiresPermissions("outBInfo:update")
    public void updateOutBInfo(@Valid OutBInfo outBInfo) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            outBInfo.setModifyUserId(currentUser.getUserId());
            this.iOutBInfoService.updateOutBInfo(outBInfo);
        } catch (Exception e) {
            message = "修改失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @Log("删除")
    @DeleteMapping("/{ids}")
    @RequiresPermissions("outBInfo:delete")
    public void deleteOutBInfos(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            this.iOutBInfoService.deleteOutBInfos(arr_ids);
        } catch (Exception e) {
            message = "删除失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel")
    @RequiresPermissions("outBInfo:export")
    public void export(QueryRequest request, OutBInfo dcaBSciencepublish,String dataJson,HttpServletResponse response)throws FebsException{
        try{
            request.setPageNum(1);
            request.setPageSize(100000);
            User currentUser = FebsUtil.getCurrentUser();
            List<OutBInfo> dcaBSciencepublishList=  this.iOutBInfoService.findOutBInfos(request, dcaBSciencepublish).getRecords();
            //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
            ExportExcelUtils.exportCustomExcel_han(response, dcaBSciencepublishList,dataJson,"");
        }catch(Exception e){
            message="导出Excel失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    @PostMapping("importTp")
    public FebsResponse importTp(@RequestParam MultipartFile file)
            throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<OutBInfoExtend> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();

        ExcelKit.$Import(OutBInfoExtend.class)
                .readXlsx(file.getInputStream(), new ExcelReadHandler<OutBInfoExtend>() {

                    @Override
                    public void onSuccess(int sheetIndex, int rowIndex, OutBInfoExtend entity) {
                        successList.add(entity); // 单行读取成功，加入入库队列。
                    }

                    @Override
                    public void onError(int sheetIndex, int rowIndex,
                                        List<ExcelErrorField> errorFields) {
                        // 读取数据失败，记录了当前行所有失败的数据
                        errorList.add(MapUtil.of(//
                                "sheetIndex", sheetIndex
                        ));
                        errorList.add(MapUtil.of(//
                                "rowIndex", rowIndex));
                        errorList.add(MapUtil.of(//
                                "errorFields", errorFields));
                    }
                });

        // TODO: 执行successList的入库操作。
        Map<String, Object> result = new HashMap<>();
        result.put("data", successList);
        result.put("haveError", !CollectionUtil.isEmpty(errorList));
        result.put("error", errorList);
        result.put("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L);

        if (CollectionUtil.isEmpty(errorList)) {
            for (OutBInfoExtend d : successList
            ) {
                OutBInfo outBInfo=new OutBInfo();
                outBInfo.setDcayear(d.getDcayear());
                outBInfo.setZgbm(d.getZgbm());
                outBInfo.setZbbz(d.getZbbz());
                outBInfo.setXuhao(d.getXuhao());
                outBInfo.setXkznum(d.getXkznum());
                outBInfo.setTpzb(d.getTpzb());
                outBInfo.setTpbt(d.getTpbt());
                outBInfo.setTotalnum(d.getTotalnum());
                outBInfo.setSqlb(d.getSqlb());
                outBInfo.setSfzzb(d.getSfzzb());
                outBInfo.setHospitalnum(d.getHospitalnum());
                outBInfo.setSbzc(d.getSbzc());
                outBInfo.setName(d.getName());
                outBInfo.setKs(d.getKs());
                outBInfo.setDyzc(d.getDyzc());
                this.iOutBInfoService.save(outBInfo);
            }
        }

        return new FebsResponse().data(errorList);
    }

    @GetMapping("/{id}")
    public OutBInfo detail(@NotBlank(message = "{required}") @PathVariable String id) {
        OutBInfo outBInfo = this.iOutBInfoService.getById(id);
        return outBInfo;
    }
}