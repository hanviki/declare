package cc.mrbird.febs.doctor.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.doctor.service.IDcaBDocAttachfileService;
import cc.mrbird.febs.doctor.entity.DcaBDocAttachfile;
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
@RequestMapping("dcaBDocAttachfile")

public class DcaBDocAttachfileController extends BaseController{

private String message;
@Autowired
public IDcaBDocAttachfileService iDcaBDocAttachfileService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBDocAttachfile 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, DcaBDocAttachfile dcaBDocAttachfile){
        return getDataTable(this.iDcaBDocAttachfileService.findDcaBDocAttachfiles(request, dcaBDocAttachfile));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBDocAttachfile dcaBDocAttachfile){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocAttachfile.setUserAccount(currentUser.getUsername());
    dcaBDocAttachfile.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocAttachfileService.findDcaBDocAttachfiles(request, dcaBDocAttachfile));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBDocAttachfile dcaBDocAttachfile){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocAttachfile.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocAttachfileService.findDcaBDocAttachfiles(request, dcaBDocAttachfile));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBDocAttachfileCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBDocAttachfile> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBDocAttachfile>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBDocAttachfileService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBDocAttachfileService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBDocAttachfile dcaBDocAttachfile:list
        ){
        if(dcaBDocAttachfile.getState()!=null&&dcaBDocAttachfile.getState().equals(3)) {
    dcaBDocAttachfile.setState(3);
        }
        else{
    dcaBDocAttachfile.setState(state);
        }
    dcaBDocAttachfile.setDisplayIndex(display);
        display+=1;
    dcaBDocAttachfile.setCreateUserId(currentUser.getUserId());
    dcaBDocAttachfile.setUserAccount(currentUser.getUsername());
    dcaBDocAttachfile.setUserAccountName(currentUser.getRealname());
        this.iDcaBDocAttachfileService.createDcaBDocAttachfile(dcaBDocAttachfile);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBDocAttachfile(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBDocAttachfile dcaBDocAttachfile= JSON.parseObject(jsonStr, new TypeReference<DcaBDocAttachfile>() {
        });
    dcaBDocAttachfile.setState(state);
    /**
        if (auditState >= 0) {
        if(state==2){
    dcaBDocAttachfile.setAuditState(0);
        }
        else {
    dcaBDocAttachfile.setAuditState(auditState+1);
        }

        }*/
    dcaBDocAttachfile.setAuditMan(currentUser.getUsername());
    dcaBDocAttachfile.setAuditManName(currentUser.getRealname());
    dcaBDocAttachfile.setAuditDate(DateUtil.date());
        this.iDcaBDocAttachfileService.updateDcaBDocAttachfile(dcaBDocAttachfile);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBDocAttachfile
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBDocAttachfile(@Valid DcaBDocAttachfile dcaBDocAttachfile)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocAttachfile.setCreateUserId(currentUser.getUserId());
    dcaBDocAttachfile.setUserAccount(currentUser.getUsername());
        this.iDcaBDocAttachfileService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBDocAttachfileService.createDcaBDocAttachfile(dcaBDocAttachfile);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBDocAttachfile
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBDocAttachfile:update")
public void updateDcaBDocAttachfile(@Valid DcaBDocAttachfile dcaBDocAttachfile)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocAttachfile.setModifyUserId(currentUser.getUserId());
        this.iDcaBDocAttachfileService.updateDcaBDocAttachfile(dcaBDocAttachfile);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBDocAttachfile:delete")
public void deleteDcaBDocAttachfiles(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBDocAttachfileService.deleteDcaBDocAttachfiles(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@PostMapping("excel")
public void export(QueryRequest request, DcaBDocAttachfile dcaBDocAttachfile,String dataJson,HttpServletResponse response)throws FebsException{
        try{
        request.setPageNum(1);
        request.setPageSize(10000);
        User currentUser = FebsUtil.getCurrentUser();

    dcaBDocAttachfile.setIsDeletemark(1);
        request.setSortField("user_account asc,state ");
        request.setSortOrder("ascend");
        List<DcaBDocAttachfile> dcaBDocAttachfileList=  this.iDcaBDocAttachfileService.findDcaBDocAttachfiles(request, dcaBDocAttachfile).getRecords();
        //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
        ExportExcelUtils.exportCustomExcel_han(response, dcaBDocAttachfileList,dataJson,"");
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@GetMapping("/{id}")
public DcaBDocAttachfile detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBDocAttachfile dcaBDocAttachfile=this.iDcaBDocAttachfileService.getById(id);
        return dcaBDocAttachfile;
        }
        }