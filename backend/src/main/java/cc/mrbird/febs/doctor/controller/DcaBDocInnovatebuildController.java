package cc.mrbird.febs.doctor.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.doctor.service.IDcaBDocInnovatebuildService;
import cc.mrbird.febs.doctor.entity.DcaBDocInnovatebuild;
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
@RequestMapping("dcaBDocInnovatebuild")

public class DcaBDocInnovatebuildController extends BaseController{

private String message;
@Autowired
public IDcaBDocInnovatebuildService iDcaBDocInnovatebuildService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBDocInnovatebuild 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, DcaBDocInnovatebuild dcaBDocInnovatebuild){
        return getDataTable(this.iDcaBDocInnovatebuildService.findDcaBDocInnovatebuilds(request, dcaBDocInnovatebuild));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBDocInnovatebuild dcaBDocInnovatebuild){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocInnovatebuild.setUserAccount(currentUser.getUsername());
    dcaBDocInnovatebuild.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocInnovatebuildService.findDcaBDocInnovatebuilds(request, dcaBDocInnovatebuild));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBDocInnovatebuild dcaBDocInnovatebuild){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocInnovatebuild.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocInnovatebuildService.findDcaBDocInnovatebuilds(request, dcaBDocInnovatebuild));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBDocInnovatebuildCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBDocInnovatebuild> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBDocInnovatebuild>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBDocInnovatebuildService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBDocInnovatebuildService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBDocInnovatebuild dcaBDocInnovatebuild:list
        ){
        if(dcaBDocInnovatebuild.getState()!=null&&dcaBDocInnovatebuild.getState().equals(3)) {
    dcaBDocInnovatebuild.setState(3);
        }
        else{
    dcaBDocInnovatebuild.setState(state);
        }
    dcaBDocInnovatebuild.setDisplayIndex(display);
        display+=1;
    dcaBDocInnovatebuild.setCreateUserId(currentUser.getUserId());
    dcaBDocInnovatebuild.setUserAccount(currentUser.getUsername());
    dcaBDocInnovatebuild.setUserAccountName(currentUser.getRealname());
        this.iDcaBDocInnovatebuildService.createDcaBDocInnovatebuild(dcaBDocInnovatebuild);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBDocInnovatebuild(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBDocInnovatebuild dcaBDocInnovatebuild= JSON.parseObject(jsonStr, new TypeReference<DcaBDocInnovatebuild>() {
        });
    dcaBDocInnovatebuild.setState(state);
    /**
        if (auditState >= 0) {
        if(state==2){
    dcaBDocInnovatebuild.setAuditState(0);
        }
        else {
    dcaBDocInnovatebuild.setAuditState(auditState+1);
        }

        }*/
    dcaBDocInnovatebuild.setAuditMan(currentUser.getUsername());
    dcaBDocInnovatebuild.setAuditManName(currentUser.getRealname());
    dcaBDocInnovatebuild.setAuditDate(DateUtil.date());
        this.iDcaBDocInnovatebuildService.updateDcaBDocInnovatebuild(dcaBDocInnovatebuild);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBDocInnovatebuild
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBDocInnovatebuild(@Valid DcaBDocInnovatebuild dcaBDocInnovatebuild)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocInnovatebuild.setCreateUserId(currentUser.getUserId());
    dcaBDocInnovatebuild.setUserAccount(currentUser.getUsername());
        this.iDcaBDocInnovatebuildService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBDocInnovatebuildService.createDcaBDocInnovatebuild(dcaBDocInnovatebuild);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBDocInnovatebuild
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBDocInnovatebuild:update")
public void updateDcaBDocInnovatebuild(@Valid DcaBDocInnovatebuild dcaBDocInnovatebuild)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocInnovatebuild.setModifyUserId(currentUser.getUserId());
        this.iDcaBDocInnovatebuildService.updateDcaBDocInnovatebuild(dcaBDocInnovatebuild);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBDocInnovatebuild:delete")
public void deleteDcaBDocInnovatebuilds(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBDocInnovatebuildService.deleteDcaBDocInnovatebuilds(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@PostMapping("excel")
public void export(QueryRequest request, DcaBDocInnovatebuild dcaBDocInnovatebuild,String dataJson,HttpServletResponse response)throws FebsException{
        try{
        request.setPageNum(1);
        request.setPageSize(10000);
        User currentUser = FebsUtil.getCurrentUser();

    dcaBDocInnovatebuild.setIsDeletemark(1);
        request.setSortField("user_account asc,state ");
        request.setSortOrder("ascend");
        List<DcaBDocInnovatebuild> dcaBDocInnovatebuildList=  this.iDcaBDocInnovatebuildService.findDcaBDocInnovatebuilds(request, dcaBDocInnovatebuild).getRecords();
        //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
        ExportExcelUtils.exportCustomExcel_han(response, dcaBDocInnovatebuildList,dataJson,"");
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@GetMapping("/{id}")
public DcaBDocInnovatebuild detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBDocInnovatebuild dcaBDocInnovatebuild=this.iDcaBDocInnovatebuildService.getById(id);
        return dcaBDocInnovatebuild;
        }
        }