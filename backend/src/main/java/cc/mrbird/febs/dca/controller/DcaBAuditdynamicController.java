package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.common.utils.ExportExcelUtils;
import cc.mrbird.febs.dca.entity.DcaBWorknum;
import cc.mrbird.febs.dca.service.IDcaBAuditdynamicService;
import cc.mrbird.febs.dca.entity.DcaBAuditdynamic;

import cc.mrbird.febs.dca.service.IDcaBWorknumService;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyAuditdynamic;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import cn.hutool.core.date.DateUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author viki
 * @since 2020-10-27
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBAuditdynamic")

public class DcaBAuditdynamicController extends BaseController {

    private String message;
    @Autowired
    public IDcaBAuditdynamicService iDcaBAuditdynamicService;

    @Autowired
    public IDcaBWorknumService iDcaBWorknumService;


    /**
     * 分页查询数据
     *
     * @param request          分页信息
     * @param dcaBAuditdynamic 查询条件
     * @return
     */
    @GetMapping
    public Map<String, Object> List(QueryRequest request, DcaBAuditdynamic dcaBAuditdynamic) {
        return getDataTable(this.iDcaBAuditdynamicService.findDcaBAuditdynamics(request, dcaBAuditdynamic));
    }

    @GetMapping("userAccount")
    public List<DcaBAuditdynamic> List2(String userAccount) {
        return this.iDcaBAuditdynamicService.findAllAuditdynamics(userAccount);
    }

    @GetMapping("custom")
    public Map<String, Object> ListCustom(QueryRequest request, DcaBAuditdynamic dcaBAuditdynamic) {
        User currentUser = FebsUtil.getCurrentUser();
        dcaBAuditdynamic.setUserAccount(currentUser.getUsername());
        dcaBAuditdynamic.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBAuditdynamicService.findDcaBAuditdynamics(request, dcaBAuditdynamic));
    }



    @Log("新增/按钮")
    @PostMapping("addNew")
    public void addDcaBAuditdynamicCustom(@Valid String jsonStr, String year,String userAccount2) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            List<DcaBAuditdynamic> list = JSON.parseObject(jsonStr, new TypeReference<List<DcaBAuditdynamic>>() {
            });
            int countid = 0;
            int i_year_qu= Convert.toInt(year) - 1;
            int i_year_qa= Convert.toInt(year) - 2;
            int i_year_dqa= Convert.toInt(year) - 3;
            /**
             * 先删除数据，然后再添加
             */
            // this.iDcaBAuditdynamicService.deleteByuseraccount(currentUser.getUsername());
            //  int display=this.iDcaBAuditdynamicService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
            DcaBWorknum dcaBWorknum2019=new DcaBWorknum();
            dcaBWorknum2019.setUserAccount(userAccount2);
            dcaBWorknum2019.setYear(i_year_dqa);
            DcaBWorknum dcaBWorknum2020=new DcaBWorknum();
            dcaBWorknum2020.setUserAccount(userAccount2);
            dcaBWorknum2020.setYear(i_year_qa);
            DcaBWorknum dcaBWorknum2021=new DcaBWorknum();
            dcaBWorknum2021.setUserAccount(userAccount2);
            dcaBWorknum2021.setYear(i_year_qu);

            for (DcaBAuditdynamic dcaBAuditdynamic : list
            ) {
                dcaBAuditdynamic.setCreateUserId(currentUser.getUserId());
                // dcaBAuditdynamic.setUserAccount(currentUser.getUsername());
                //  dcaBAuditdynamic.setUserAccountName(currentUser.getRealname());
                this.iDcaBAuditdynamicService.createDcaBAuditdynamic(dcaBAuditdynamic);
              //  if(dcaBAuditdynamic.getAuditTitletype().equals(""))
                ConvertAuditResult(dcaBAuditdynamic.getAuditTitletype(),dcaBAuditdynamic.getAuditResult(),dcaBWorknum2019, dcaBWorknum2020,dcaBWorknum2021);

            }
            InsertAuditResult(dcaBWorknum2019);
            InsertAuditResult(dcaBWorknum2020);
            InsertAuditResult(dcaBWorknum2021);
        } catch (Exception e) {
            message = "审核失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
    private void  InsertAuditResult(DcaBWorknum dcaBWorknum){
        LambdaQueryWrapper<DcaBWorknum> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBWorknum::getIsDeletemark, 1);//1是未删 0是已删
        queryWrapper.eq(DcaBWorknum::getUserAccount,dcaBWorknum.getUserAccount());
        queryWrapper.eq(DcaBWorknum::getYear,dcaBWorknum.getYear());
        List<DcaBWorknum> list=this.iDcaBWorknumService.list(queryWrapper);
        if(list.size()>0){
            dcaBWorknum.setId(list.get(0).getId());
            this.iDcaBWorknumService.updateDcaBWorknum(dcaBWorknum);
        }
        else{
            dcaBWorknum.setState(3);
            dcaBWorknum.setIsDeletemark(1);
            this.iDcaBWorknumService.createDcaBWorknum(dcaBWorknum);
        }
    }
    private void ConvertAuditResult(String titleType,String auditResult,DcaBWorknum dcaBWorknum2019,DcaBWorknum dcaBWorknum2020,DcaBWorknum dcaBWorknum2021){
        BigDecimal value= new BigDecimal(0);
        if(!StrUtil.isBlank(auditResult)){
            value= Convert.toBigDecimal(auditResult);
        }
        //region 病人工作量
        if(titleType.equals("ddqnmzgzl"))
        {
            dcaBWorknum2019.setMzbrl(value);
            //  return  "ddqnmzgzl";
        }
        if(titleType.equals("dqnmzgzl"))
        {
            dcaBWorknum2020.setMzbrl(value);
          //  return  "ddqnmzgzl";
        }
        if(titleType.equals("qnmzgzl"))
        {
            dcaBWorknum2021.setMzbrl(value);
        }
        //endregion
        //region 管理住院病人量
        if(titleType.equals("ddqnglzybrl"))
        {
            dcaBWorknum2019.setGlzybrl(value);
        }
        if(titleType.equals("dqnglzybrl"))
        {
            dcaBWorknum2020.setGlzybrl(value);
        }
        if(titleType.equals("qnglzybrl"))
        {
            dcaBWorknum2021.setGlzybrl(value);
        }
        //endregion
        //region 手术病人量(总)
        if(titleType.equals("ddqsybrl"))
        {
            dcaBWorknum2019.setSsbrl(value);
        }
        if(titleType.equals("dqnsybrl"))
        {
            dcaBWorknum2020.setSsbrl(value);
        }
        if(titleType.equals("qnsybrl"))
        {
            dcaBWorknum2021.setSsbrl(value);
        }
        //endregion

        //region手术量（1）
        if(titleType.equals("dqnssbrl1"))
        {
            dcaBWorknum2019.setSsbrl1(value);
        }
        if(titleType.equals("qnbrlss1"))
        {
            dcaBWorknum2020.setSsbrl1(value);
        }
        if(titleType.equals("qnbrssl1"))
        {
            dcaBWorknum2021.setSsbrl1(value);
        }
        //endregion
        //region手术量（2）
        if(titleType.equals("dqnssbrl2"))
        {
            dcaBWorknum2019.setSsbrl2(value);
        }
        if(titleType.equals("qnbrlss2"))
        {
            dcaBWorknum2020.setSsbrl2(value);
        }
        if(titleType.equals("qnbrssl2"))
        {
            dcaBWorknum2021.setSsbrl2(value);
        }
        //endregion
        //region手术量（3）
        if(titleType.equals("dqnssbrl3"))
        {
            dcaBWorknum2019.setSsbrl3(value);
        }
        if(titleType.equals("qnbrlss3"))
        {
            dcaBWorknum2020.setSsbrl3(value);
        }
        if(titleType.equals("qnbrssl3"))
        {
            dcaBWorknum2021.setSsbrl3(value);
        }
        //endregion
        //region手术量（4）
        if(titleType.equals("dqnssbrl4"))
        {
            dcaBWorknum2019.setSsbrl4(value);
        }
        if(titleType.equals("qnbrlss4"))
        {
            dcaBWorknum2020.setSsbrl4(value);
        }
        if(titleType.equals("qnbrssl4"))
        {
            dcaBWorknum2021.setSsbrl4(value);
        }
        //endregion
    }
    @Log("审核/按钮")
    @PostMapping("updateNew")
    public void updateNewDcaBAuditdynamic(@Valid String jsonStr) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            DcaBAuditdynamic dcaBAuditdynamic = JSON.parseObject(jsonStr, new TypeReference<DcaBAuditdynamic>() {
            });
            this.iDcaBAuditdynamicService.updateDcaBAuditdynamic(dcaBAuditdynamic);

        } catch (Exception e) {
            message = "审核/按钮失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 添加
     *
     * @param dcaBAuditdynamic
     * @return
     */
    @Log("新增/按钮")
    @PostMapping
    public void addDcaBAuditdynamic(@Valid DcaBAuditdynamic dcaBAuditdynamic) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            dcaBAuditdynamic.setCreateUserId(currentUser.getUserId());
            dcaBAuditdynamic.setUserAccount(currentUser.getUsername());
            this.iDcaBAuditdynamicService.deleteByuseraccount(currentUser.getUsername());
            this.iDcaBAuditdynamicService.createDcaBAuditdynamic(dcaBAuditdynamic);
        } catch (Exception e) {
            message = "新增/按钮失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改
     *
     * @param dcaBAuditdynamic
     * @return
     */
    @Log("修改")
    @PutMapping
    @RequiresPermissions("dcaBAuditdynamic:update")
    public void updateDcaBAuditdynamic(@Valid DcaBAuditdynamic dcaBAuditdynamic) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            dcaBAuditdynamic.setModifyUserId(currentUser.getUserId());
            this.iDcaBAuditdynamicService.updateDcaBAuditdynamic(dcaBAuditdynamic);
        } catch (Exception e) {
            message = "修改失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @Log("删除")
    @DeleteMapping("/{ids}")
    @RequiresPermissions("dcaBAuditdynamic:delete")
    public void deleteDcaBAuditdynamics(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            this.iDcaBAuditdynamicService.deleteDcaBAuditdynamics(arr_ids);
        } catch (Exception e) {
            message = "删除失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @GetMapping("/{id}")
    public DcaBAuditdynamic detail(@NotBlank(message = "{required}") @PathVariable String id) {
        DcaBAuditdynamic dcaBAuditdynamic = this.iDcaBAuditdynamicService.getById(id);
        return dcaBAuditdynamic;
    }
}