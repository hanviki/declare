package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.service.IDcaBQualificationService;
import cc.mrbird.febs.dca.entity.DcaBQualification;

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
 * @since 2020-12-25
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBQualification")

public class DcaBQualificationController extends BaseController{

private String message;
@Autowired
public IDcaBQualificationService iDcaBQualificationService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBQualification 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBQualification:view")
public Map<String, Object> List(QueryRequest request, DcaBQualification dcaBQualification){
        return getDataTable(this.iDcaBQualificationService.findDcaBQualifications(request, dcaBQualification));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBQualification dcaBQualification){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBQualification.setUserAccount(currentUser.getUsername());
    dcaBQualification.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBQualificationService.findDcaBQualifications(request, dcaBQualification));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBQualification dcaBQualification){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBQualification.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBQualificationService.findDcaBQualifications(request, dcaBQualification));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBQualificationCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBQualification> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBQualification>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBQualificationService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBQualificationService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBQualification dcaBQualification:list
        ){
        if(dcaBQualification.getState()!=null&&dcaBQualification.getState().equals(3)) {
    dcaBQualification.setState(3);
        }
        else{
    dcaBQualification.setState(state);
        }
    dcaBQualification.setDisplayIndex(display);
        display+=1;
    dcaBQualification.setCreateUserId(currentUser.getUserId());
    dcaBQualification.setUserAccount(currentUser.getUsername());
    dcaBQualification.setUserAccountName(currentUser.getRealname());
        this.iDcaBQualificationService.createDcaBQualification(dcaBQualification);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBQualification(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBQualification dcaBQualification= JSON.parseObject(jsonStr, new TypeReference<DcaBQualification>() {
        });
    dcaBQualification.setState(state);
    /**
        if (auditState >= 0) {
        if(state==2){
    dcaBQualification.setAuditState(0);
        }
        else {
    dcaBQualification.setAuditState(auditState+1);
        }

        }*/
    dcaBQualification.setAuditMan(currentUser.getUsername());
    dcaBQualification.setAuditManName(currentUser.getRealname());
    dcaBQualification.setAuditDate(DateUtil.date());
        this.iDcaBQualificationService.updateDcaBQualification(dcaBQualification);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBQualification
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBQualification(@Valid DcaBQualification dcaBQualification)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBQualification.setCreateUserId(currentUser.getUserId());
    dcaBQualification.setUserAccount(currentUser.getUsername());
        this.iDcaBQualificationService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBQualificationService.createDcaBQualification(dcaBQualification);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBQualification
 * @return
 */
@Log("修改")
@PutMapping
public void updateDcaBQualification(@Valid DcaBQualification dcaBQualification)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBQualification.setModifyUserId(currentUser.getUserId());
        this.iDcaBQualificationService.updateDcaBQualification(dcaBQualification);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBQualification:delete")
public void deleteDcaBQualifications(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBQualificationService.deleteDcaBQualifications(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBQualification:export")
public void export(QueryRequest request, DcaBQualification dcaBQualification,HttpServletResponse response)throws FebsException{
        try{
        List<DcaBQualification> dcaBQualifications=this.iDcaBQualificationService.findDcaBQualifications(request, dcaBQualification).getRecords();
        ExcelKit.$Export(DcaBQualification.class,response).downXlsx(dcaBQualifications,false);
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBQualification detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBQualification dcaBQualification=this.iDcaBQualificationService.getById(id);
        return dcaBQualification;
        }
        }