package cc.mrbird.febs.doctor.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.doctor.service.IDcaBDocOtherworkService;
import cc.mrbird.febs.doctor.entity.DcaBDocOtherwork;
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
@RequestMapping("dcaBDocOtherwork")

public class DcaBDocOtherworkController extends BaseController{

private String message;
@Autowired
public IDcaBDocOtherworkService iDcaBDocOtherworkService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBDocOtherwork 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, DcaBDocOtherwork dcaBDocOtherwork){
        return getDataTable(this.iDcaBDocOtherworkService.findDcaBDocOtherworks(request, dcaBDocOtherwork));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBDocOtherwork dcaBDocOtherwork){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocOtherwork.setUserAccount(currentUser.getUsername());
    dcaBDocOtherwork.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocOtherworkService.findDcaBDocOtherworks(request, dcaBDocOtherwork));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBDocOtherwork dcaBDocOtherwork){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocOtherwork.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocOtherworkService.findDcaBDocOtherworks(request, dcaBDocOtherwork));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBDocOtherworkCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBDocOtherwork> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBDocOtherwork>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBDocOtherworkService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBDocOtherworkService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBDocOtherwork dcaBDocOtherwork:list
        ){
        if(dcaBDocOtherwork.getState()!=null&&dcaBDocOtherwork.getState().equals(3)) {
    dcaBDocOtherwork.setState(3);
        }
        else{
    dcaBDocOtherwork.setState(state);
        }
    dcaBDocOtherwork.setDisplayIndex(display);
        display+=1;
    dcaBDocOtherwork.setCreateUserId(currentUser.getUserId());
    dcaBDocOtherwork.setUserAccount(currentUser.getUsername());
    dcaBDocOtherwork.setUserAccountName(currentUser.getRealname());
        this.iDcaBDocOtherworkService.createDcaBDocOtherwork(dcaBDocOtherwork);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBDocOtherwork(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBDocOtherwork dcaBDocOtherwork= JSON.parseObject(jsonStr, new TypeReference<DcaBDocOtherwork>() {
        });
    dcaBDocOtherwork.setState(state);
    /**
        if (auditState >= 0) {
        if(state==2){
    dcaBDocOtherwork.setAuditState(0);
        }
        else {
    dcaBDocOtherwork.setAuditState(auditState+1);
        }

        }*/
    dcaBDocOtherwork.setAuditMan(currentUser.getUsername());
    dcaBDocOtherwork.setAuditManName(currentUser.getRealname());
    dcaBDocOtherwork.setAuditDate(DateUtil.date());
        this.iDcaBDocOtherworkService.updateDcaBDocOtherwork(dcaBDocOtherwork);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBDocOtherwork
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBDocOtherwork(@Valid DcaBDocOtherwork dcaBDocOtherwork)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocOtherwork.setCreateUserId(currentUser.getUserId());
    dcaBDocOtherwork.setUserAccount(currentUser.getUsername());
        this.iDcaBDocOtherworkService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBDocOtherworkService.createDcaBDocOtherwork(dcaBDocOtherwork);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBDocOtherwork
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBDocOtherwork:update")
public void updateDcaBDocOtherwork(@Valid DcaBDocOtherwork dcaBDocOtherwork)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocOtherwork.setModifyUserId(currentUser.getUserId());
        this.iDcaBDocOtherworkService.updateDcaBDocOtherwork(dcaBDocOtherwork);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBDocOtherwork:delete")
public void deleteDcaBDocOtherworks(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBDocOtherworkService.deleteDcaBDocOtherworks(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@PostMapping("excel")
public void export(QueryRequest request, DcaBDocOtherwork dcaBDocOtherwork,String dataJson,HttpServletResponse response)throws FebsException{
        try{
        request.setPageNum(1);
        request.setPageSize(10000);
        User currentUser = FebsUtil.getCurrentUser();

    dcaBDocOtherwork.setIsDeletemark(1);
        request.setSortField("user_account asc,state ");
        request.setSortOrder("ascend");
        List<DcaBDocOtherwork> dcaBDocOtherworkList=  this.iDcaBDocOtherworkService.findDcaBDocOtherworks(request, dcaBDocOtherwork).getRecords();
        //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
        ExportExcelUtils.exportCustomExcel_han(response, dcaBDocOtherworkList,dataJson,"");
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@GetMapping("/{id}")
public DcaBDocOtherwork detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBDocOtherwork dcaBDocOtherwork=this.iDcaBDocOtherworkService.getById(id);
        return dcaBDocOtherwork;
        }
        }