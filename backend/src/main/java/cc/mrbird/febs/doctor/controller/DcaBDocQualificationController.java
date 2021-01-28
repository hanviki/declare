package cc.mrbird.febs.doctor.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.ExportExcelUtils;
import cc.mrbird.febs.doctor.service.IDcaBDocQualificationService;
import cc.mrbird.febs.doctor.entity.DcaBDocQualification;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author viki
 * @since 2021-01-13
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBDocQualification")

public class DcaBDocQualificationController extends BaseController{

private String message;
@Autowired
public IDcaBDocQualificationService iDcaBDocQualificationService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBDocQualification 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, DcaBDocQualification dcaBDocQualification){
        return getDataTable(this.iDcaBDocQualificationService.findDcaBDocQualifications(request, dcaBDocQualification));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBDocQualification dcaBDocQualification){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocQualification.setUserAccount(currentUser.getUsername());
    dcaBDocQualification.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocQualificationService.findDcaBDocQualifications(request, dcaBDocQualification));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBDocQualification dcaBDocQualification){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocQualification.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocQualificationService.findDcaBDocQualifications(request, dcaBDocQualification));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBDocQualificationCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBDocQualification> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBDocQualification>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBDocQualificationService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBDocQualificationService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBDocQualification dcaBDocQualification:list
        ){
        if(dcaBDocQualification.getState()!=null&&dcaBDocQualification.getState().equals(3)) {
    dcaBDocQualification.setState(3);
        }
        else{
    dcaBDocQualification.setState(state);
        }
    dcaBDocQualification.setDisplayIndex(display);
        display+=1;
    dcaBDocQualification.setCreateUserId(currentUser.getUserId());
    dcaBDocQualification.setUserAccount(currentUser.getUsername());
    dcaBDocQualification.setUserAccountName(currentUser.getRealname());
        this.iDcaBDocQualificationService.createDcaBDocQualification(dcaBDocQualification);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBDocQualification(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBDocQualification dcaBDocQualification= JSON.parseObject(jsonStr, new TypeReference<DcaBDocQualification>() {
        });
    dcaBDocQualification.setState(state);
    /**
        if (auditState >= 0) {
        if(state==2){
    dcaBDocQualification.setAuditState(0);
        }
        else {
    dcaBDocQualification.setAuditState(auditState+1);
        }

        }*/
    dcaBDocQualification.setAuditMan(currentUser.getUsername());
    dcaBDocQualification.setAuditManName(currentUser.getRealname());
    dcaBDocQualification.setAuditDate(DateUtil.date());
        this.iDcaBDocQualificationService.updateDcaBDocQualification(dcaBDocQualification);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBDocQualification
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBDocQualification(@Valid DcaBDocQualification dcaBDocQualification)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocQualification.setCreateUserId(currentUser.getUserId());
    dcaBDocQualification.setUserAccount(currentUser.getUsername());
        this.iDcaBDocQualificationService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBDocQualificationService.createDcaBDocQualification(dcaBDocQualification);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBDocQualification
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBDocQualification:update")
public void updateDcaBDocQualification(@Valid DcaBDocQualification dcaBDocQualification)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocQualification.setModifyUserId(currentUser.getUserId());
        this.iDcaBDocQualificationService.updateDcaBDocQualification(dcaBDocQualification);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBDocQualification:delete")
public void deleteDcaBDocQualifications(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBDocQualificationService.deleteDcaBDocQualifications(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@PostMapping("excel")
public void export(QueryRequest request, DcaBDocQualification dcaBDocQualification,String dataJson,HttpServletResponse response)throws FebsException{
        try{
        request.setPageNum(1);
        request.setPageSize(10000);
        User currentUser = FebsUtil.getCurrentUser();

    dcaBDocQualification.setIsDeletemark(1);
        request.setSortField("user_account asc,state ");
        request.setSortOrder("ascend");
        List<DcaBDocQualification> dcaBDocQualificationList=  this.iDcaBDocQualificationService.findDcaBDocQualifications(request, dcaBDocQualification).getRecords();
        //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
        ExportExcelUtils.exportCustomExcel_han(response, dcaBDocQualificationList,dataJson,"");
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@GetMapping("/{id}")
public DcaBDocQualification detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBDocQualification dcaBDocQualification=this.iDcaBDocQualificationService.getById(id);
        return dcaBDocQualification;
        }
        }