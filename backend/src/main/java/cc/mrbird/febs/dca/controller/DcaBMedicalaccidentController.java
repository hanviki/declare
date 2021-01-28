package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.service.IDcaBMedicalaccidentService;
import cc.mrbird.febs.dca.entity.DcaBMedicalaccident;

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
 * @since 2020-12-15
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBMedicalaccident")

public class DcaBMedicalaccidentController extends BaseController{

private String message;
@Autowired
public IDcaBMedicalaccidentService iDcaBMedicalaccidentService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBMedicalaccident 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBMedicalaccident:view")
public Map<String, Object> List(QueryRequest request, DcaBMedicalaccident dcaBMedicalaccident){
        return getDataTable(this.iDcaBMedicalaccidentService.findDcaBMedicalaccidents(request, dcaBMedicalaccident));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBMedicalaccident dcaBMedicalaccident){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBMedicalaccident.setUserAccount(currentUser.getUsername());
    dcaBMedicalaccident.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBMedicalaccidentService.findDcaBMedicalaccidents(request, dcaBMedicalaccident));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBMedicalaccident dcaBMedicalaccident){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBMedicalaccident.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBMedicalaccidentService.findDcaBMedicalaccidents(request, dcaBMedicalaccident));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBMedicalaccidentCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBMedicalaccident> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBMedicalaccident>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBMedicalaccidentService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBMedicalaccidentService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBMedicalaccident dcaBMedicalaccident:list
        ){
        if(dcaBMedicalaccident.getState()!=null&&dcaBMedicalaccident.getState().equals(3)) {
    dcaBMedicalaccident.setState(3);
        }
        else{
    dcaBMedicalaccident.setState(state);
        }
    dcaBMedicalaccident.setDisplayIndex(display);
        display+=1;
    dcaBMedicalaccident.setCreateUserId(currentUser.getUserId());
    dcaBMedicalaccident.setUserAccount(currentUser.getUsername());
    dcaBMedicalaccident.setUserAccountName(currentUser.getRealname());
        this.iDcaBMedicalaccidentService.createDcaBMedicalaccident(dcaBMedicalaccident);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBMedicalaccident(@Valid String jsonStr ,int state  )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBMedicalaccident dcaBMedicalaccident= JSON.parseObject(jsonStr, new TypeReference<DcaBMedicalaccident>() {
        });
    dcaBMedicalaccident.setState(state);
    /**
        if (auditState >= 0) {
        if(state==2){
    dcaBMedicalaccident.setAuditState(0);
        }
        else {
    dcaBMedicalaccident.setAuditState(auditState+1);
        }

        }*/
    dcaBMedicalaccident.setAuditMan(currentUser.getUsername());
    dcaBMedicalaccident.setAuditManName(currentUser.getRealname());
    dcaBMedicalaccident.setAuditDate(DateUtil.date());
        this.iDcaBMedicalaccidentService.updateDcaBMedicalaccident(dcaBMedicalaccident);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBMedicalaccident
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBMedicalaccident(@Valid DcaBMedicalaccident dcaBMedicalaccident)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBMedicalaccident.setCreateUserId(currentUser.getUserId());
    dcaBMedicalaccident.setUserAccount(currentUser.getUsername());
        this.iDcaBMedicalaccidentService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBMedicalaccidentService.createDcaBMedicalaccident(dcaBMedicalaccident);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBMedicalaccident
 * @return
 */
@Log("修改")
@PutMapping
public void updateDcaBMedicalaccident(@Valid DcaBMedicalaccident dcaBMedicalaccident)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBMedicalaccident.setModifyUserId(currentUser.getUserId());
        this.iDcaBMedicalaccidentService.updateDcaBMedicalaccident(dcaBMedicalaccident);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBMedicalaccident:delete")
public void deleteDcaBMedicalaccidents(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBMedicalaccidentService.deleteDcaBMedicalaccidents(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBMedicalaccident:export")
public void export(QueryRequest request, DcaBMedicalaccident dcaBMedicalaccident,HttpServletResponse response)throws FebsException{
        try{
        List<DcaBMedicalaccident> dcaBMedicalaccidents=this.iDcaBMedicalaccidentService.findDcaBMedicalaccidents(request, dcaBMedicalaccident).getRecords();
        ExcelKit.$Export(DcaBMedicalaccident.class,response).downXlsx(dcaBMedicalaccidents,false);
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBMedicalaccident detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBMedicalaccident dcaBMedicalaccident=this.iDcaBMedicalaccidentService.getById(id);
        return dcaBMedicalaccident;
        }
        }