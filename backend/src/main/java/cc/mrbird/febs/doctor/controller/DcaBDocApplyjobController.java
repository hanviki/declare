package cc.mrbird.febs.doctor.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.doctor.service.IDcaBDocApplyjobService;
import cc.mrbird.febs.doctor.entity.DcaBDocApplyjob;
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
@RequestMapping("dcaBDocApplyjob")

public class DcaBDocApplyjobController extends BaseController{

private String message;
@Autowired
public IDcaBDocApplyjobService iDcaBDocApplyjobService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBDocApplyjob 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, DcaBDocApplyjob dcaBDocApplyjob){
        return getDataTable(this.iDcaBDocApplyjobService.findDcaBDocApplyjobs(request, dcaBDocApplyjob));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBDocApplyjob dcaBDocApplyjob){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocApplyjob.setUserAccount(currentUser.getUsername());
    dcaBDocApplyjob.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocApplyjobService.findDcaBDocApplyjobs(request, dcaBDocApplyjob));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBDocApplyjob dcaBDocApplyjob){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocApplyjob.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocApplyjobService.findDcaBDocApplyjobs(request, dcaBDocApplyjob));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBDocApplyjobCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBDocApplyjob> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBDocApplyjob>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBDocApplyjobService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBDocApplyjobService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBDocApplyjob dcaBDocApplyjob:list
        ){
        if(dcaBDocApplyjob.getState()!=null&&dcaBDocApplyjob.getState().equals(3)) {
    dcaBDocApplyjob.setState(3);
        }
        else{
    dcaBDocApplyjob.setState(state);
        }
    dcaBDocApplyjob.setDisplayIndex(display);
        display+=1;
    dcaBDocApplyjob.setCreateUserId(currentUser.getUserId());
    dcaBDocApplyjob.setUserAccount(currentUser.getUsername());
    dcaBDocApplyjob.setUserAccountName(currentUser.getRealname());
        this.iDcaBDocApplyjobService.createDcaBDocApplyjob(dcaBDocApplyjob);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBDocApplyjob(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBDocApplyjob dcaBDocApplyjob= JSON.parseObject(jsonStr, new TypeReference<DcaBDocApplyjob>() {
        });
    dcaBDocApplyjob.setState(state);
    /**
        if (auditState >= 0) {
        if(state==2){
    dcaBDocApplyjob.setAuditState(0);
        }
        else {
    dcaBDocApplyjob.setAuditState(auditState+1);
        }

        }*/
    dcaBDocApplyjob.setAuditMan(currentUser.getUsername());
    dcaBDocApplyjob.setAuditManName(currentUser.getRealname());
    dcaBDocApplyjob.setAuditDate(DateUtil.date());
        this.iDcaBDocApplyjobService.updateDcaBDocApplyjob(dcaBDocApplyjob);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBDocApplyjob
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBDocApplyjob(@Valid DcaBDocApplyjob dcaBDocApplyjob)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocApplyjob.setCreateUserId(currentUser.getUserId());
    dcaBDocApplyjob.setUserAccount(currentUser.getUsername());
        this.iDcaBDocApplyjobService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBDocApplyjobService.createDcaBDocApplyjob(dcaBDocApplyjob);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBDocApplyjob
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBDocApplyjob:update")
public void updateDcaBDocApplyjob(@Valid DcaBDocApplyjob dcaBDocApplyjob)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocApplyjob.setModifyUserId(currentUser.getUserId());
        this.iDcaBDocApplyjobService.updateDcaBDocApplyjob(dcaBDocApplyjob);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBDocApplyjob:delete")
public void deleteDcaBDocApplyjobs(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBDocApplyjobService.deleteDcaBDocApplyjobs(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@PostMapping("excel")
public void export(QueryRequest request, DcaBDocApplyjob dcaBDocApplyjob,String dataJson,HttpServletResponse response)throws FebsException{
        try{
        request.setPageNum(1);
        request.setPageSize(10000);
        User currentUser = FebsUtil.getCurrentUser();

    dcaBDocApplyjob.setIsDeletemark(1);
        request.setSortField("user_account asc,state ");
        request.setSortOrder("ascend");
        List<DcaBDocApplyjob> dcaBDocApplyjobList=  this.iDcaBDocApplyjobService.findDcaBDocApplyjobs(request, dcaBDocApplyjob).getRecords();
        //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
        ExportExcelUtils.exportCustomExcel_han(response, dcaBDocApplyjobList,dataJson,"");
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@GetMapping("/{id}")
public DcaBDocApplyjob detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBDocApplyjob dcaBDocApplyjob=this.iDcaBDocApplyjobService.getById(id);
        return dcaBDocApplyjob;
        }
        }