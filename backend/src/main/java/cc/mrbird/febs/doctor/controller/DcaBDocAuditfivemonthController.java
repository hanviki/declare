package cc.mrbird.febs.doctor.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.ExportExcelUtils;

import cc.mrbird.febs.doctor.service.IDcaBDocAuditfivemonthService;
import cc.mrbird.febs.doctor.entity.DcaBDocAuditfivemonth;

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
@RequestMapping("dcaBDocAuditfivemonth")

public class DcaBDocAuditfivemonthController extends BaseController{

private String message;
@Autowired
public IDcaBDocAuditfivemonthService iDcaBDocAuditfivemonthService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBDocAuditfivemonth 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, DcaBDocAuditfivemonth dcaBDocAuditfivemonth){
        return getDataTable(this.iDcaBDocAuditfivemonthService.findDcaBDocAuditfivemonths(request, dcaBDocAuditfivemonth));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBDocAuditfivemonth dcaBDocAuditfivemonth){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocAuditfivemonth.setUserAccount(currentUser.getUsername());
    dcaBDocAuditfivemonth.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocAuditfivemonthService.findDcaBDocAuditfivemonths(request, dcaBDocAuditfivemonth));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBDocAuditfivemonth dcaBDocAuditfivemonth){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocAuditfivemonth.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocAuditfivemonthService.findDcaBDocAuditfivemonths(request, dcaBDocAuditfivemonth));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBDocAuditfivemonthCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBDocAuditfivemonth> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBDocAuditfivemonth>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBDocAuditfivemonthService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBDocAuditfivemonthService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBDocAuditfivemonth dcaBDocAuditfivemonth:list
        ){
        if(dcaBDocAuditfivemonth.getState()!=null&&dcaBDocAuditfivemonth.getState().equals(3)) {
    dcaBDocAuditfivemonth.setState(3);
        }
        else{
    dcaBDocAuditfivemonth.setState(state);
        }
    dcaBDocAuditfivemonth.setDisplayIndex(display);
        display+=1;
    dcaBDocAuditfivemonth.setCreateUserId(currentUser.getUserId());
    dcaBDocAuditfivemonth.setUserAccount(currentUser.getUsername());
    dcaBDocAuditfivemonth.setUserAccountName(currentUser.getRealname());
        this.iDcaBDocAuditfivemonthService.createDcaBDocAuditfivemonth(dcaBDocAuditfivemonth);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBDocAuditfivemonth(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBDocAuditfivemonth dcaBDocAuditfivemonth= JSON.parseObject(jsonStr, new TypeReference<DcaBDocAuditfivemonth>() {
        });
    dcaBDocAuditfivemonth.setState(state);
    /**
        if (auditState >= 0) {
        if(state==2){
    dcaBDocAuditfivemonth.setAuditState(0);
        }
        else {
    dcaBDocAuditfivemonth.setAuditState(auditState+1);
        }

        }*/
    dcaBDocAuditfivemonth.setAuditMan(currentUser.getUsername());
    dcaBDocAuditfivemonth.setAuditManName(currentUser.getRealname());
    dcaBDocAuditfivemonth.setAuditDate(DateUtil.date());
        this.iDcaBDocAuditfivemonthService.updateDcaBDocAuditfivemonth(dcaBDocAuditfivemonth);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBDocAuditfivemonth
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBDocAuditfivemonth(@Valid DcaBDocAuditfivemonth dcaBDocAuditfivemonth)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocAuditfivemonth.setCreateUserId(currentUser.getUserId());
    dcaBDocAuditfivemonth.setUserAccount(currentUser.getUsername());
        this.iDcaBDocAuditfivemonthService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBDocAuditfivemonthService.createDcaBDocAuditfivemonth(dcaBDocAuditfivemonth);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBDocAuditfivemonth
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBDocAuditfivemonth:update")
public void updateDcaBDocAuditfivemonth(@Valid DcaBDocAuditfivemonth dcaBDocAuditfivemonth)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocAuditfivemonth.setModifyUserId(currentUser.getUserId());
        this.iDcaBDocAuditfivemonthService.updateDcaBDocAuditfivemonth(dcaBDocAuditfivemonth);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBDocAuditfivemonth:delete")
public void deleteDcaBDocAuditfivemonths(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBDocAuditfivemonthService.deleteDcaBDocAuditfivemonths(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@PostMapping("excel")
public void export(QueryRequest request, DcaBDocAuditfivemonth dcaBDocAuditfivemonth,String dataJson,HttpServletResponse response)throws FebsException{
        try{
        request.setPageNum(1);
        request.setPageSize(10000);
        User currentUser = FebsUtil.getCurrentUser();

    dcaBDocAuditfivemonth.setIsDeletemark(1);
        request.setSortField("user_account asc,state ");
        request.setSortOrder("ascend");
        List<DcaBDocAuditfivemonth> dcaBDocAuditfivemonthList=  this.iDcaBDocAuditfivemonthService.findDcaBDocAuditfivemonths(request, dcaBDocAuditfivemonth).getRecords();
        //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
        ExportExcelUtils.exportCustomExcel_han(response, dcaBDocAuditfivemonthList,dataJson,"");
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@GetMapping("/{id}")
public DcaBDocAuditfivemonth detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBDocAuditfivemonth dcaBDocAuditfivemonth=this.iDcaBDocAuditfivemonthService.getById(id);
        return dcaBDocAuditfivemonth;
        }
        }