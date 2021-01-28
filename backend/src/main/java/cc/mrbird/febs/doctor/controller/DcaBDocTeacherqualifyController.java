package cc.mrbird.febs.doctor.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.ExportExcelUtils;

import cc.mrbird.febs.doctor.service.IDcaBDocTeacherqualifyService;
import cc.mrbird.febs.doctor.entity.DcaBDocTeacherqualify;

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
@RequestMapping("dcaBDocTeacherqualify")

public class DcaBDocTeacherqualifyController extends BaseController{

private String message;
@Autowired
public IDcaBDocTeacherqualifyService iDcaBDocTeacherqualifyService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBDocTeacherqualify 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, DcaBDocTeacherqualify dcaBDocTeacherqualify){
        return getDataTable(this.iDcaBDocTeacherqualifyService.findDcaBDocTeacherqualifys(request, dcaBDocTeacherqualify));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBDocTeacherqualify dcaBDocTeacherqualify){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocTeacherqualify.setUserAccount(currentUser.getUsername());
    dcaBDocTeacherqualify.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocTeacherqualifyService.findDcaBDocTeacherqualifys(request, dcaBDocTeacherqualify));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBDocTeacherqualify dcaBDocTeacherqualify){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocTeacherqualify.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocTeacherqualifyService.findDcaBDocTeacherqualifys(request, dcaBDocTeacherqualify));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBDocTeacherqualifyCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBDocTeacherqualify> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBDocTeacherqualify>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBDocTeacherqualifyService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBDocTeacherqualifyService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBDocTeacherqualify dcaBDocTeacherqualify:list
        ){
        if(dcaBDocTeacherqualify.getState()!=null&&dcaBDocTeacherqualify.getState().equals(3)) {
    dcaBDocTeacherqualify.setState(3);
        }
        else{
    dcaBDocTeacherqualify.setState(state);
        }
    dcaBDocTeacherqualify.setDisplayIndex(display);
        display+=1;
    dcaBDocTeacherqualify.setCreateUserId(currentUser.getUserId());
    dcaBDocTeacherqualify.setUserAccount(currentUser.getUsername());
    dcaBDocTeacherqualify.setUserAccountName(currentUser.getRealname());
        this.iDcaBDocTeacherqualifyService.createDcaBDocTeacherqualify(dcaBDocTeacherqualify);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBDocTeacherqualify(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBDocTeacherqualify dcaBDocTeacherqualify= JSON.parseObject(jsonStr, new TypeReference<DcaBDocTeacherqualify>() {
        });
    dcaBDocTeacherqualify.setState(state);
    /**
        if (auditState >= 0) {
        if(state==2){
    dcaBDocTeacherqualify.setAuditState(0);
        }
        else {
    dcaBDocTeacherqualify.setAuditState(auditState+1);
        }

        }*/
    dcaBDocTeacherqualify.setAuditMan(currentUser.getUsername());
    dcaBDocTeacherqualify.setAuditManName(currentUser.getRealname());
    dcaBDocTeacherqualify.setAuditDate(DateUtil.date());
        this.iDcaBDocTeacherqualifyService.updateDcaBDocTeacherqualify(dcaBDocTeacherqualify);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBDocTeacherqualify
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBDocTeacherqualify(@Valid DcaBDocTeacherqualify dcaBDocTeacherqualify)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocTeacherqualify.setCreateUserId(currentUser.getUserId());
    dcaBDocTeacherqualify.setUserAccount(currentUser.getUsername());
        this.iDcaBDocTeacherqualifyService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBDocTeacherqualifyService.createDcaBDocTeacherqualify(dcaBDocTeacherqualify);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBDocTeacherqualify
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBDocTeacherqualify:update")
public void updateDcaBDocTeacherqualify(@Valid DcaBDocTeacherqualify dcaBDocTeacherqualify)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocTeacherqualify.setModifyUserId(currentUser.getUserId());
        this.iDcaBDocTeacherqualifyService.updateDcaBDocTeacherqualify(dcaBDocTeacherqualify);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBDocTeacherqualify:delete")
public void deleteDcaBDocTeacherqualifys(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBDocTeacherqualifyService.deleteDcaBDocTeacherqualifys(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@PostMapping("excel")
public void export(QueryRequest request, DcaBDocTeacherqualify dcaBDocTeacherqualify,String dataJson,HttpServletResponse response)throws FebsException{
        try{
        request.setPageNum(1);
        request.setPageSize(10000);
        User currentUser = FebsUtil.getCurrentUser();

    dcaBDocTeacherqualify.setIsDeletemark(1);
        request.setSortField("user_account asc,state ");
        request.setSortOrder("ascend");
        List<DcaBDocTeacherqualify> dcaBDocTeacherqualifyList=  this.iDcaBDocTeacherqualifyService.findDcaBDocTeacherqualifys(request, dcaBDocTeacherqualify).getRecords();
        //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
        ExportExcelUtils.exportCustomExcel_han(response, dcaBDocTeacherqualifyList,dataJson,"");
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@GetMapping("/{id}")
public DcaBDocTeacherqualify detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBDocTeacherqualify dcaBDocTeacherqualify=this.iDcaBDocTeacherqualifyService.getById(id);
        return dcaBDocTeacherqualify;
        }
        }