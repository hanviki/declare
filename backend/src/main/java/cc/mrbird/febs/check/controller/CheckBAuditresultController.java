package cc.mrbird.febs.check.controller;

import cc.mrbird.febs.check.entity.CheckBUser;
import cc.mrbird.febs.check.entity.CheckDTitle;
import cc.mrbird.febs.check.service.ICheckBSettingService;
import cc.mrbird.febs.check.service.ICheckBUserService;
import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.FebsResponse;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.check.service.ICheckBAuditresultService;
import cc.mrbird.febs.check.entity.CheckBAuditresult;

import cc.mrbird.febs.common.utils.ExportExcelUtils;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.dca.entity.ExportAfferentCustomExcel;
import cc.mrbird.febs.system.domain.User;
import cn.hutool.Hutool;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.google.common.collect.Lists;
import com.wuwenze.poi.ExcelKit;
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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author viki
 * @since 2021-01-22
 */
@Slf4j
@Validated
@RestController
@RequestMapping("checkBAuditresult")

public class CheckBAuditresultController extends BaseController {

    private String message;
    @Autowired
    public ICheckBAuditresultService iCheckBAuditresultService;
    @Autowired
    public ICheckBSettingService iCheckBSettingService;
    @Autowired
    public ICheckBUserService iCheckBUserService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'指标结果表','/dca/CheckBAuditresult/CheckBAuditresult','dca/CheckBAuditresult/CheckBAuditresult','checkBAuditresult:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'指标结果表新增','checkBAuditresult:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'指标结果表编辑','checkBAuditresult:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'指标结果表删除','checkBAuditresult:delete',1,1,NOW())
 */


    /**
     * 分页查询数据
     *
     * @param request           分页信息
     * @param checkBAuditresult 查询条件
     * @return
     */
    @GetMapping
    @RequiresPermissions("checkBAuditresult:view")
    public Map<String, Object> List(QueryRequest request, CheckBAuditresult checkBAuditresult) {
        return getDataTable(this.iCheckBAuditresultService.findCheckBAuditresults(request, checkBAuditresult));
    }

    /**
     * 添加
     *
     * @param checkBAuditresult
     * @return
     */
    @Log("新增/按钮")
    @PostMapping
    @RequiresPermissions("checkBAuditresult:add")
    public void addCheckBAuditresult(@Valid CheckBAuditresult checkBAuditresult) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            checkBAuditresult.setCreateUserId(currentUser.getUserId());
            this.iCheckBAuditresultService.createCheckBAuditresult(checkBAuditresult);
        } catch (Exception e) {
            message = "新增/按钮失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改
     *
     * @param checkBAuditresult
     * @return
     */
    @Log("修改")
    @PutMapping
    @RequiresPermissions("checkBAuditresult:update")
    public void updateCheckBAuditresult(@Valid CheckBAuditresult checkBAuditresult) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            checkBAuditresult.setModifyUserId(currentUser.getUserId());
            this.iCheckBAuditresultService.updateCheckBAuditresult(checkBAuditresult);
        } catch (Exception e) {
            message = "修改失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("新增/按钮")
    @PostMapping("addNew")
    public void addCheckBAuditresult(@Valid String jsonStr, String year, String userAccount2, String userAccountName) throws FebsException {
        try {
            // Common common =new Common();
            User currentUser = FebsUtil.getCurrentUser();
            List<CheckBAuditresult> list = JSON.parseObject(jsonStr, new TypeReference<List<CheckBAuditresult>>() {
            });
            for (CheckBAuditresult dcaBAuditdynamic : list
            ) {
                dcaBAuditdynamic.setCreateUserId(currentUser.getUserId());
                dcaBAuditdynamic.setCheckUserId(currentUser.getUsername());
                // dcaBAuditdynamic.setUserAccount(currentUser.getUsername());
                //  dcaBAuditdynamic.setUserAccountName(currentUser.getRealname());
                if(dcaBAuditdynamic.getShowType()) {
                    this.iCheckBAuditresultService.createCheckBAuditresult(dcaBAuditdynamic);
                }
                //  if(dcaBAuditdynamic.getAuditTitletype().equals(""))
            }
        } catch (Exception e) {
            message = "审核失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("删除")
    @DeleteMapping("/{ids}")
    @RequiresPermissions("checkBAuditresult:delete")
    public void deleteCheckBAuditresults(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            this.iCheckBAuditresultService.deleteCheckBAuditresults(arr_ids);
        } catch (Exception e) {
            message = "删除失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel")
    @RequiresPermissions("checkBAuditresult:export")
    public void export(QueryRequest request, CheckBAuditresult checkBAuditresult, HttpServletResponse response) throws FebsException {
        try {
            List<CheckBAuditresult> checkBAuditresults = this.iCheckBAuditresultService.findCheckBAuditresults(request, checkBAuditresult).getRecords();
            ExcelKit.$Export(CheckBAuditresult.class, response).downXlsx(checkBAuditresults, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @GetMapping("/{id}")
    public CheckBAuditresult detail(@NotBlank(message = "{required}") @PathVariable String id) {
        CheckBAuditresult checkBAuditresult = this.iCheckBAuditresultService.getById(id);
        return checkBAuditresult;
    }

    @PostMapping("importAudit")
    public FebsResponse importUser(@RequestParam MultipartFile file, String dataJson)
            throws IOException {
        long beginMillis = System.currentTimeMillis();
        User currentUser = FebsUtil.getCurrentUser();
        List<CheckBAuditresult> successList = Lists.newArrayList();
        List<String> accounts = new ArrayList<>();
        List<String> dataIndexList = new ArrayList<>();
        List<Map<String, Object>> errorList = Lists.newArrayList();

        List<ExportAfferentCustomExcel> exportList = new ArrayList<>();
        if (dataJson != null && !dataJson.equals("")) {
            exportList = JSON.parseObject(dataJson, new TypeReference<List<ExportAfferentCustomExcel>>() {
            });
        }
        dataIndexList=exportList.stream().map(p->p.getDataIndex()).collect(Collectors.toList());
        // Common common =new Common();


        ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
        List<Map<String, Object>> readAll = reader.readAll();
        int flag = 0;
        List<CheckDTitle> checkDTitleList = this.iCheckBSettingService.getTitleByUserAccount(currentUser.getUsername());
        List<CheckBUser> checkBUserList = this.iCheckBUserService.list();
        for (Map<String, Object> map : readAll
        ) {
            accounts.add(map.get("发薪号").toString());
            //region 工作量 插入数据
            String year = map.get("考核年度").toString();
            String userAccount2 = map.get("发薪号").toString();
            String userAccountName = map.get("姓名").toString();
            iCheckBAuditresultService.deleteBy(accounts, dataIndexList, currentUser.getUsername(), year);
            List<CheckBUser> checkBUsers = checkBUserList.stream().filter(p -> p.getUserAccount().equals(userAccount2) && p.getDcaYear().equals(year)).collect(Collectors.toList());
            //endregion
            for (ExportAfferentCustomExcel export : exportList
            ) {

                if (!ExportExcelUtils.isDiable(export, checkDTitleList, checkBUsers.get(0), currentUser.getUsername())) {
                    continue;
                }
                List<CheckDTitle> selectList = checkDTitleList.stream().filter(p -> p.getFiledName().equals(export.getDataIndex())).collect(Collectors.toList());
                if (export.getDataIndex()!=null && !(export.getDataIndex().equals("userAccount") || export.getDataIndex().equals("userAccountName") || export.getDataIndex().equals("dcaYear"))) {

                    CheckBAuditresult auditdynamic = new CheckBAuditresult();
                    String auditResult = map.get(export.getTitle()) == null ? "" : map.get(export.getTitle()).toString();
                    if (selectList.get(0).getShowType().equals(1)) {
                        if (!StrUtil.hasBlank(auditResult)) {
                            float f_result = Convert.toFloat(auditResult);

                            float f2 = Convert.toFloat(selectList.get(0).getRange() == null ? 0 : selectList.get(0).getRange());
                           // log.info(String.valueOf(f_result)+"_"+String.valueOf(f2));
                            if(NumberUtil.compare(f_result,f2)>0){
                                auditResult= String.valueOf(selectList.get(0).getRange());
                            }
                        }
                    }
                    auditdynamic.setAuditResult(auditResult);
                    auditdynamic.setAuditTitletype(export.getDataIndex());
                    auditdynamic.setCheckUserId(currentUser.getUsername());
                    auditdynamic.setAuditTitle(export.getTitle());
                    auditdynamic.setIsOria(selectList.get(0).getIsOria());
                    // auditdynamic.setKs()
                    auditdynamic.setDcaYear(year);
                    auditdynamic.setIsDeletemark(1);
                    auditdynamic.setState(1);
                    auditdynamic.setUserAccount(map.get("发薪号").toString());
                    auditdynamic.setUserAccountName(map.get("姓名").toString());
                    successList.add(auditdynamic);

                }

                // iDcaBAuditdynamicService.createDcaBAuditdynamic(auditdynamic);
            }
          //  flag = 1;


        }

        for (CheckBAuditresult dcaBAuditdynamic : successList
        ) {
            iCheckBAuditresultService.createCheckBAuditresult(dcaBAuditdynamic);
        }
        return new FebsResponse().data(errorList);
    }
}