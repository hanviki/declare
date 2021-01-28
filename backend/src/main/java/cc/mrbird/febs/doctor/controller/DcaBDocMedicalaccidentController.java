package cc.mrbird.febs.doctor.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.doctor.service.IDcaBDocMedicalaccidentService;
import cc.mrbird.febs.doctor.entity.DcaBDocMedicalaccident;
import cc.mrbird.febs.common.utils.ExportExcelUtils;

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
@RequestMapping("dcaBDocMedicalaccident")

public class DcaBDocMedicalaccidentController extends BaseController{

private String message;
@Autowired
public IDcaBDocMedicalaccidentService iDcaBDocMedicalaccidentService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBDocMedicalaccident 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, DcaBDocMedicalaccident dcaBDocMedicalaccident){
        return getDataTable(this.iDcaBDocMedicalaccidentService.findDcaBDocMedicalaccidents(request, dcaBDocMedicalaccident));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBDocMedicalaccident dcaBDocMedicalaccident){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocMedicalaccident.setUserAccount(currentUser.getUsername());
    dcaBDocMedicalaccident.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocMedicalaccidentService.findDcaBDocMedicalaccidents(request, dcaBDocMedicalaccident));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBDocMedicalaccident dcaBDocMedicalaccident){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocMedicalaccident.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocMedicalaccidentService.findDcaBDocMedicalaccidents(request, dcaBDocMedicalaccident));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBDocMedicalaccidentCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBDocMedicalaccident> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBDocMedicalaccident>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBDocMedicalaccidentService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBDocMedicalaccidentService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBDocMedicalaccident dcaBDocMedicalaccident:list
        ){
        if(dcaBDocMedicalaccident.getState()!=null&&dcaBDocMedicalaccident.getState().equals(3)) {
    dcaBDocMedicalaccident.setState(3);
        }
        else{
    dcaBDocMedicalaccident.setState(state);
        }
    dcaBDocMedicalaccident.setDisplayIndex(display);
        display+=1;
    dcaBDocMedicalaccident.setCreateUserId(currentUser.getUserId());
    dcaBDocMedicalaccident.setUserAccount(currentUser.getUsername());
    dcaBDocMedicalaccident.setUserAccountName(currentUser.getRealname());
        this.iDcaBDocMedicalaccidentService.createDcaBDocMedicalaccident(dcaBDocMedicalaccident);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBDocMedicalaccident(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBDocMedicalaccident dcaBDocMedicalaccident= JSON.parseObject(jsonStr, new TypeReference<DcaBDocMedicalaccident>() {
        });
    dcaBDocMedicalaccident.setState(state);
    /**
        if (auditState >= 0) {
        if(state==2){
    dcaBDocMedicalaccident.setAuditState(0);
        }
        else {
    dcaBDocMedicalaccident.setAuditState(auditState+1);
        }

        }*/
    dcaBDocMedicalaccident.setAuditMan(currentUser.getUsername());
    dcaBDocMedicalaccident.setAuditManName(currentUser.getRealname());
    dcaBDocMedicalaccident.setAuditDate(DateUtil.date());
        this.iDcaBDocMedicalaccidentService.updateDcaBDocMedicalaccident(dcaBDocMedicalaccident);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBDocMedicalaccident
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBDocMedicalaccident(@Valid DcaBDocMedicalaccident dcaBDocMedicalaccident)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocMedicalaccident.setCreateUserId(currentUser.getUserId());
    dcaBDocMedicalaccident.setUserAccount(currentUser.getUsername());
        this.iDcaBDocMedicalaccidentService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBDocMedicalaccidentService.createDcaBDocMedicalaccident(dcaBDocMedicalaccident);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBDocMedicalaccident
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBDocMedicalaccident:update")
public void updateDcaBDocMedicalaccident(@Valid DcaBDocMedicalaccident dcaBDocMedicalaccident)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocMedicalaccident.setModifyUserId(currentUser.getUserId());
        this.iDcaBDocMedicalaccidentService.updateDcaBDocMedicalaccident(dcaBDocMedicalaccident);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBDocMedicalaccident:delete")
public void deleteDcaBDocMedicalaccidents(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBDocMedicalaccidentService.deleteDcaBDocMedicalaccidents(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@PostMapping("excel")
public void export(QueryRequest request, DcaBDocMedicalaccident dcaBDocMedicalaccident,String dataJson,HttpServletResponse response)throws FebsException{
        try{
        request.setPageNum(1);
        request.setPageSize(10000);
        User currentUser = FebsUtil.getCurrentUser();

    dcaBDocMedicalaccident.setIsDeletemark(1);
        request.setSortField("user_account asc,state ");
        request.setSortOrder("ascend");
        List<DcaBDocMedicalaccident> dcaBDocMedicalaccidentList=  this.iDcaBDocMedicalaccidentService.findDcaBDocMedicalaccidents(request, dcaBDocMedicalaccident).getRecords();
        //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
        ExportExcelUtils.exportCustomExcel_han(response, dcaBDocMedicalaccidentList,dataJson,"");
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@GetMapping("/{id}")
public DcaBDocMedicalaccident detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBDocMedicalaccident dcaBDocMedicalaccident=this.iDcaBDocMedicalaccidentService.getById(id);
        return dcaBDocMedicalaccident;
        }
        }