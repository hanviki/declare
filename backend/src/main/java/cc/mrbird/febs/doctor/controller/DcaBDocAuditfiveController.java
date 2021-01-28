package cc.mrbird.febs.doctor.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.doctor.service.IDcaBDocAuditfiveService;
import cc.mrbird.febs.doctor.entity.DcaBDocAuditfive;
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
@RequestMapping("dcaBDocAuditfive")

public class DcaBDocAuditfiveController extends BaseController{

private String message;
@Autowired
public IDcaBDocAuditfiveService iDcaBDocAuditfiveService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBDocAuditfive 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, DcaBDocAuditfive dcaBDocAuditfive){
        return getDataTable(this.iDcaBDocAuditfiveService.findDcaBDocAuditfives(request, dcaBDocAuditfive));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBDocAuditfive dcaBDocAuditfive){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocAuditfive.setUserAccount(currentUser.getUsername());
    dcaBDocAuditfive.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocAuditfiveService.findDcaBDocAuditfives(request, dcaBDocAuditfive));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBDocAuditfive dcaBDocAuditfive){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocAuditfive.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocAuditfiveService.findDcaBDocAuditfives(request, dcaBDocAuditfive));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBDocAuditfiveCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBDocAuditfive> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBDocAuditfive>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBDocAuditfiveService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBDocAuditfiveService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBDocAuditfive dcaBDocAuditfive:list
        ){
        if(dcaBDocAuditfive.getState()!=null&&dcaBDocAuditfive.getState().equals(3)) {
    dcaBDocAuditfive.setState(3);
        }
        else{
    dcaBDocAuditfive.setState(state);
        }
    dcaBDocAuditfive.setDisplayIndex(display);
        display+=1;
    dcaBDocAuditfive.setCreateUserId(currentUser.getUserId());
    dcaBDocAuditfive.setUserAccount(currentUser.getUsername());
    dcaBDocAuditfive.setUserAccountName(currentUser.getRealname());
        this.iDcaBDocAuditfiveService.createDcaBDocAuditfive(dcaBDocAuditfive);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBDocAuditfive(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBDocAuditfive dcaBDocAuditfive= JSON.parseObject(jsonStr, new TypeReference<DcaBDocAuditfive>() {
        });
    dcaBDocAuditfive.setState(state);
    /**
        if (auditState >= 0) {
        if(state==2){
    dcaBDocAuditfive.setAuditState(0);
        }
        else {
    dcaBDocAuditfive.setAuditState(auditState+1);
        }

        }*/
    dcaBDocAuditfive.setAuditMan(currentUser.getUsername());
    dcaBDocAuditfive.setAuditManName(currentUser.getRealname());
    dcaBDocAuditfive.setAuditDate(DateUtil.date());
        this.iDcaBDocAuditfiveService.updateDcaBDocAuditfive(dcaBDocAuditfive);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBDocAuditfive
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBDocAuditfive(@Valid DcaBDocAuditfive dcaBDocAuditfive)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocAuditfive.setCreateUserId(currentUser.getUserId());
    dcaBDocAuditfive.setUserAccount(currentUser.getUsername());
        this.iDcaBDocAuditfiveService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBDocAuditfiveService.createDcaBDocAuditfive(dcaBDocAuditfive);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBDocAuditfive
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBDocAuditfive:update")
public void updateDcaBDocAuditfive(@Valid DcaBDocAuditfive dcaBDocAuditfive)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocAuditfive.setModifyUserId(currentUser.getUserId());
        this.iDcaBDocAuditfiveService.updateDcaBDocAuditfive(dcaBDocAuditfive);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBDocAuditfive:delete")
public void deleteDcaBDocAuditfives(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBDocAuditfiveService.deleteDcaBDocAuditfives(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@PostMapping("excel")
public void export(QueryRequest request, DcaBDocAuditfive dcaBDocAuditfive,String dataJson,HttpServletResponse response)throws FebsException{
        try{
        request.setPageNum(1);
        request.setPageSize(10000);
        User currentUser = FebsUtil.getCurrentUser();

    dcaBDocAuditfive.setIsDeletemark(1);
        request.setSortField("user_account asc,state ");
        request.setSortOrder("ascend");
        List<DcaBDocAuditfive> dcaBDocAuditfiveList=  this.iDcaBDocAuditfiveService.findDcaBDocAuditfives(request, dcaBDocAuditfive).getRecords();
        //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
        ExportExcelUtils.exportCustomExcel_han(response, dcaBDocAuditfiveList,dataJson,"");
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@GetMapping("/{id}")
public DcaBDocAuditfive detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBDocAuditfive dcaBDocAuditfive=this.iDcaBDocAuditfiveService.getById(id);
        return dcaBDocAuditfive;
        }
        }