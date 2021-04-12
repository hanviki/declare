package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.FebsResponse;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.common.utils.ExportExcelUtils;
import cc.mrbird.febs.dca.app.Common;
import cc.mrbird.febs.dca.entity.*;
import cc.mrbird.febs.dca.service.*;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.google.common.collect.Lists;
import com.wuwenze.poi.ExcelKit;
import com.wuwenze.poi.handler.ExcelReadHandler;
import com.wuwenze.poi.pojo.ExcelErrorField;
import lombok.extern.slf4j.Slf4j;
import cn.hutool.core.date.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author viki
 * @since 2020-10-23
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaUserAudit")

public class DcaUserAuditController extends BaseController {

    private String message;
    @Autowired
    public IDcaUserAuditService iDcaUserAuditService;
    @Autowired
    public IDcaBUserService iDcaBUserService;
    @Autowired
    public IDcaBAuditdynamicService iDcaBAuditdynamicService;
    @Autowired
    public IDcaBReportService iDcaBReportService;
    @Autowired
    public IDcaBUserapplyService iDcaBUserapplyService;

    @Autowired
    private  Common common;

    /**
     * 分页查询数据
     *
     * @param request      分页信息
     * @param dcaUserAudit 查询条件
     * @return
     */
    @GetMapping
    @RequiresPermissions("dcaUserAudit:view")
    public Map<String, Object> List(QueryRequest request, DcaUserAudit dcaUserAudit) {
        return getDataTable(this.iDcaUserAuditService.findDcaUserAudits(request, dcaUserAudit));
    }


    /**
     * 添加
     *
     * @param dcaUserAudit
     * @return
     */
    @Log("新增/按钮")
    @PostMapping
    public void addDcaUserAudit(@Valid DcaUserAudit dcaUserAudit) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();

            this.iDcaUserAuditService.deleteByuseraccount(currentUser.getUsername());
            this.iDcaUserAuditService.createDcaUserAudit(dcaUserAudit);
        } catch (Exception e) {
            message = "新增/按钮失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改
     *
     * @param dcaUserAudit
     * @return
     */
    @Log("修改")
    @PutMapping
    @RequiresPermissions("dcaUserAudit:update")
    public void updateDcaUserAudit(@Valid DcaUserAudit dcaUserAudit) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();

            this.iDcaUserAuditService.updateDcaUserAudit(dcaUserAudit);
        } catch (Exception e) {
            message = "修改失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @Log("删除")
    @DeleteMapping("/{ids}")
    @RequiresPermissions("dcaUserAudit:delete")
    public void deleteDcaUserAudits(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            this.iDcaUserAuditService.deleteDcaUserAudits(arr_ids);
        } catch (Exception e) {
            message = "删除失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel")
    @RequiresPermissions("dcaUserAudit:export")
    public void export(QueryRequest request, DcaUserAudit dcaUserAudit, HttpServletResponse response) throws FebsException {
        try {
            List<DcaUserAudit> dcaUserAudits = this.iDcaUserAuditService.findDcaUserAudits(request, dcaUserAudit).getRecords();
            ExcelKit.$Export(DcaUserAudit.class, response).downXlsx(dcaUserAudits, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
    @PostMapping("excel2")
    public void export(QueryRequest request, DcaBUser dcaBUser,String dataJson,int state,HttpServletResponse response)throws FebsException{
        try{
            request.setPageNum(1);
            request.setPageSize(10000);
            User currentUser = FebsUtil.getCurrentUser();
            dcaBUser.setCreateUserId(currentUser.getUserId());
            if(StringUtils.isNotBlank(currentUser.getCode())) {//设置部门筛选
                dcaBUser.setDoctorDesc(currentUser.getCode());
            }
            List<DcaBUser> dcaBAuditdynamics=this.iDcaBUserService.findDcaBUsersAuditCustom(request, dcaBUser,state).getRecords();
          //  LambdaQueryWrapper<DcaBAuditdynamic> queryWrapperDynamic = new LambdaQueryWrapper<>();

            List<DcaBAuditdynamic> listDynamic= this.iDcaBAuditdynamicService.list();
            //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
            ExportExcelUtils.exportCustomExcelCutome(response, dcaBAuditdynamics,dataJson,listDynamic,"");
        }catch(Exception e){
            message="导出Excel失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }
    @GetMapping("/{id}")
    public DcaUserAudit detail(@NotBlank(message = "{required}") @PathVariable String id) {
        DcaUserAudit dcaUserAudit = this.iDcaUserAuditService.getById(id);
        return dcaUserAudit;
    }
    @PostMapping("importAudit")
    public FebsResponse importUser(@RequestParam MultipartFile file,String dataJson)
            throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<DcaBAuditdynamic> successList = Lists.newArrayList();
        List<String> accounts = new ArrayList<>();
        List<String> dataIndexList = new ArrayList<>();
        List<Map<String, Object>> errorList = Lists.newArrayList();

        List<ExportAfferentCustomExcel> exportList = new ArrayList<>();
        if (dataJson != null && !dataJson.equals("")) {
            exportList = JSON.parseObject(dataJson, new TypeReference<List<ExportAfferentCustomExcel>>() {
            });
        }
       // Common common =new Common();
        String[] workNumList= new String[]{
                "ddqnmzgzl","dqnmzgzl","qnmzgzl","ddqnglzybrl","dqnglzybrl","qnglzybrl","dqnssbrl2","qnbrlss2","qnbrssl2",
                "dqnssbrl3","qnbrlss3","qnbrssl3","dqnssbrl4","qnbrlss4","qnbrssl4",
        };
        List<ExportAfferentCustomExcel> listWork=
     exportList.stream().filter(p-> Arrays.asList(workNumList).contains(p.getDataIndex())).collect(Collectors.toList());
        ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
        List<Map<String,Object>> readAll = reader.readAll();
        int flag=0;
        for (Map<String,Object> map: readAll
             ) {
            accounts.add(map.get("发薪号").toString());
            //region 工作量 插入数据
            String year =map.get("申报年度").toString();
            String userAccount2 =map.get("发薪号").toString();
            String userAccountName =map.get("姓名").toString();
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
            dcaBWorknum2019.setUserAccountName(userAccountName);
            dcaBWorknum2019.setYear(i_year_dqa);
            DcaBWorknum dcaBWorknum2020=new DcaBWorknum();
            dcaBWorknum2020.setUserAccount(userAccount2);
            dcaBWorknum2020.setUserAccountName(userAccountName);
            dcaBWorknum2020.setYear(i_year_qa);
            DcaBWorknum dcaBWorknum2021=new DcaBWorknum();
            dcaBWorknum2021.setUserAccount(userAccount2);
            dcaBWorknum2021.setUserAccountName(userAccountName);
            dcaBWorknum2021.setYear(i_year_qu);
            //endregion
            for (ExportAfferentCustomExcel export:exportList
                 ) {

                if(!(export.getDataIndex().equals("userAccount") || export.getDataIndex().equals("userAccountName")|| export.getDataIndex().equals("dcaYear"))) {
                    if(flag==0){
                        dataIndexList.add(export.getDataIndex());
                    }
                    DcaBAuditdynamic auditdynamic = new DcaBAuditdynamic();
                    String auditResult = map.get(export.getTitle())==null?"":map.get(export.getTitle()).toString();
                    auditdynamic.setAuditResult(auditResult);
                    auditdynamic.setAuditTitletype(export.getDataIndex());
                    auditdynamic.setAuditTitle(export.getTitle());
                    auditdynamic.setIsDeletemark(1);
                    auditdynamic.setState(1);
                    auditdynamic.setUserAccount(map.get("发薪号").toString());
                    auditdynamic.setUserAccountName(map.get("姓名").toString());
                    successList.add(auditdynamic);

                    common.ConvertAuditResult(export.getDataIndex(),auditResult,dcaBWorknum2019, dcaBWorknum2020,dcaBWorknum2021);
                }

               // iDcaBAuditdynamicService.createDcaBAuditdynamic(auditdynamic);
            }
            flag=1;
            if(listWork.size()>0) {
                //region 插入数据工作量
                common.InsertAuditResult(dcaBWorknum2019);
                common.InsertAuditResult(dcaBWorknum2020);
                common.InsertAuditResult(dcaBWorknum2021);
                //endregion
            }

        }
        iDcaBAuditdynamicService.deleteBy(accounts,dataIndexList);
        for (DcaBAuditdynamic dcaBAuditdynamic: successList
             ) {
            iDcaBAuditdynamicService.createDcaBAuditdynamic(dcaBAuditdynamic);
        }
        return new FebsResponse().data(errorList);
    }


    @GetMapping("mudules/{userId}")
    public List<String> getRoleMenus(@NotBlank(message = "{required}") @PathVariable String userId) {
        LambdaQueryWrapper<DcaUserAudit> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaUserAudit::getUserId, Integer.parseInt(userId));//
        List<DcaUserAudit> list = this.iDcaUserAuditService.list(queryWrapper);
        return list.stream().map(area -> String.valueOf(area.getAuditId())).collect(Collectors.toList());
    }

    @GetMapping("user")
    public Map<String, Object> List(QueryRequest request, DcaBUser dcaUserAudit, int state) {
        User currentUser = FebsUtil.getCurrentUser();
        dcaUserAudit.setCreateUserId(currentUser.getUserId());
        if(StringUtils.isNotBlank(currentUser.getCode())) {//设置部门筛选
            log.info(currentUser.getCode());
            dcaUserAudit.setDoctorDesc(currentUser.getCode());
        }

        return getDataTable(this.iDcaBUserService.findDcaBUsersAudit(request, dcaUserAudit, state));
    }
    @GetMapping("userList")
    public Map<String, Object> ListUser(QueryRequest request, DcaBUser dcaUserAudit) {
        User currentUser = FebsUtil.getCurrentUser();
       // dcaUserAudit.setCreateUserId(currentUser.getUserId());
        return getDataTable(this.iDcaBUserService.findDcaBUsersAll(request, dcaUserAudit));
    }

    @GetMapping("userAuditResult2")
    public Map<String, Object> List(QueryRequest request, DcaBUser dcaUserAudit) {
        User currentUser = FebsUtil.getCurrentUser();
        dcaUserAudit.setCreateUserId(currentUser.getUserId());
        return getDataTable(this.iDcaBUserService.findDcaBUsersAuditResult(request, dcaUserAudit));
    }

    /**
    @GetMapping("userAuditResultUser")
    public Map<String, Object> List232(QueryRequest request, DcaBUser dcaUserAudit) {
        User currentUser = FebsUtil.getCurrentUser();
        dcaUserAudit.setCreateUserId(currentUser.getUserId());
        return getDataTable(this.iDcaBUserService.findDcaBUsersAuditResult(request, dcaUserAudit));
    }*/
    @GetMapping("userAuditResultUser")
    public Map<String, Object> ListReport(QueryRequest request, DcaBUser dcaUserAudit) {
        User currentUser = FebsUtil.getCurrentUser();
        dcaUserAudit.setCreateUserId(currentUser.getUserId());
        return getDataTable(this.iDcaBUserService.findDcaBUsersAuditReport(request, dcaUserAudit));
    }
    @GetMapping("userAuditReport")
    public Map<String, Object> ListReport(QueryRequest request, DcaBUserapply dcaUserAudit) {
        User currentUser = FebsUtil.getCurrentUser();
        dcaUserAudit.setCreateUserId(currentUser.getUserId());
        return getDataTable(this.iDcaBUserapplyService.findDcaBUsersAuditResult(request, dcaUserAudit));
    }

    /**
     * 年度申报把报表
     * @param request
     * @param dcaBUser
     * @param dataJson
     * @param response
     * @throws FebsException
     */
    @PostMapping("excel3")
    public void export3(QueryRequest request, DcaBUserapply dcaBUser,String dataJson,HttpServletResponse response)throws FebsException{
        try{
            request.setPageNum(1);
            request.setPageSize(10000);
            User currentUser = FebsUtil.getCurrentUser();
            dcaBUser.setCreateUserId(currentUser.getUserId());
            List<DcaBUserapply> dcaBAuditdynamics=this.iDcaBUserapplyService.findDcaBUsersAuditResult(request, dcaBUser).getRecords();
            //  LambdaQueryWrapper<DcaBAuditdynamic> queryWrapperDynamic = new LambdaQueryWrapper<>();
            List<String> listDynamic2 = dcaBAuditdynamics.stream().map(p -> p.getUserAccount()).collect(Collectors.toList());
            List<DcaBAuditdynamic> listDynamic= this.iDcaBUserService.getAllInfo(listDynamic2);
            //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
            ExportExcelUtils.exportCustomExcelCutome2(response, dcaBAuditdynamics,dataJson,listDynamic,"");
        }catch(Exception e){
            message="导出Excel失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 年度申报把报表
     * @param request
     * @param dcaBUser
     * @param dataJson
     * @param response
     * @throws FebsException
     */
    @PostMapping("excelBigTable")
    public void export4(QueryRequest request, DcaBUser dcaBUser,DcaBReport dcaBReport,String dataJson,int excelIndex,HttpServletResponse response)throws FebsException{
        try{
            request.setPageNum(1);
            request.setPageSize(10000);
            User currentUser = FebsUtil.getCurrentUser();
            dcaBUser.setCreateUserId(currentUser.getUserId());
            List<DcaBReport> dcaBAuditdynamics= new ArrayList<>();
            if(dcaBUser.getState()==null){
             dcaBAuditdynamics=this.iDcaBUserService.findDcaBUsersAuditReport(request, dcaBUser).getRecords();}
            else {
                dcaBAuditdynamics= this.iDcaBReportService.findDcaBReports(request, dcaBReport).getRecords();
            }
            String url="D:/big.xlsx";
            if(excelIndex==1){
                url="D:/big1.xlsx";
            }
            if(excelIndex==2){
                url="D:/big2.xlsx";
            }
           //String url= ResourceUtils.getURL("classpath:").getPath()+"/uploadFile/big.xlsx";
            ExportExcelUtils.exportCustomExcelCutome3(response, dcaBAuditdynamics,dataJson,url,6);
        }catch(Exception e){
            message="导出Excel失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }
}