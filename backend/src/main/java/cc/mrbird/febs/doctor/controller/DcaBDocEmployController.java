package cc.mrbird.febs.doctor.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.ExportExcelUtils;

import cc.mrbird.febs.doctor.service.IDcaBDocEmployService;
import cc.mrbird.febs.doctor.entity.DcaBDocEmploy;

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
@RequestMapping("dcaBDocEmploy")

public class DcaBDocEmployController extends BaseController{

private String message;
@Autowired
public IDcaBDocEmployService iDcaBDocEmployService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBDocEmploy 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, DcaBDocEmploy dcaBDocEmploy){
        return getDataTable(this.iDcaBDocEmployService.findDcaBDocEmploys(request, dcaBDocEmploy));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBDocEmploy dcaBDocEmploy){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocEmploy.setUserAccount(currentUser.getUsername());
    dcaBDocEmploy.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocEmployService.findDcaBDocEmploys(request, dcaBDocEmploy));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBDocEmploy dcaBDocEmploy){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocEmploy.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocEmployService.findDcaBDocEmploys(request, dcaBDocEmploy));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBDocEmployCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBDocEmploy> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBDocEmploy>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBDocEmployService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBDocEmployService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBDocEmploy dcaBDocEmploy:list
        ){
        if(dcaBDocEmploy.getState()!=null&&dcaBDocEmploy.getState().equals(3)) {
    dcaBDocEmploy.setState(3);
        }
        else{
    dcaBDocEmploy.setState(state);
        }
    dcaBDocEmploy.setDisplayIndex(display);
        display+=1;
    dcaBDocEmploy.setCreateUserId(currentUser.getUserId());
    dcaBDocEmploy.setUserAccount(currentUser.getUsername());
    dcaBDocEmploy.setUserAccountName(currentUser.getRealname());
        this.iDcaBDocEmployService.createDcaBDocEmploy(dcaBDocEmploy);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBDocEmploy(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBDocEmploy dcaBDocEmploy= JSON.parseObject(jsonStr, new TypeReference<DcaBDocEmploy>() {
        });
    dcaBDocEmploy.setState(state);
    /**
        if (auditState >= 0) {
        if(state==2){
    dcaBDocEmploy.setAuditState(0);
        }
        else {
    dcaBDocEmploy.setAuditState(auditState+1);
        }

        }*/
    dcaBDocEmploy.setAuditMan(currentUser.getUsername());
    dcaBDocEmploy.setAuditManName(currentUser.getRealname());
    dcaBDocEmploy.setAuditDate(DateUtil.date());
        this.iDcaBDocEmployService.updateDcaBDocEmploy(dcaBDocEmploy);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBDocEmploy
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBDocEmploy(@Valid DcaBDocEmploy dcaBDocEmploy)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocEmploy.setCreateUserId(currentUser.getUserId());
    dcaBDocEmploy.setUserAccount(currentUser.getUsername());
        this.iDcaBDocEmployService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBDocEmployService.createDcaBDocEmploy(dcaBDocEmploy);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBDocEmploy
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBDocEmploy:update")
public void updateDcaBDocEmploy(@Valid DcaBDocEmploy dcaBDocEmploy)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocEmploy.setModifyUserId(currentUser.getUserId());
        this.iDcaBDocEmployService.updateDcaBDocEmploy(dcaBDocEmploy);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBDocEmploy:delete")
public void deleteDcaBDocEmploys(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBDocEmployService.deleteDcaBDocEmploys(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@PostMapping("excel")
public void export(QueryRequest request, DcaBDocEmploy dcaBDocEmploy,String dataJson,HttpServletResponse response)throws FebsException{
        try{
        request.setPageNum(1);
        request.setPageSize(10000);
        User currentUser = FebsUtil.getCurrentUser();

    dcaBDocEmploy.setIsDeletemark(1);
        request.setSortField("user_account asc,state ");
        request.setSortOrder("ascend");
        List<DcaBDocEmploy> dcaBDocEmployList=  this.iDcaBDocEmployService.findDcaBDocEmploys(request, dcaBDocEmploy).getRecords();
        //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
        ExportExcelUtils.exportCustomExcel_han(response, dcaBDocEmployList,dataJson,"");
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@GetMapping("/{id}")
public DcaBDocEmploy detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBDocEmploy dcaBDocEmploy=this.iDcaBDocEmployService.getById(id);
        return dcaBDocEmploy;
        }
        }