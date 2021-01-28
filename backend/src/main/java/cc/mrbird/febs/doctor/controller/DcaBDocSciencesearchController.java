package cc.mrbird.febs.doctor.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.ExportExcelUtils;

import cc.mrbird.febs.doctor.service.IDcaBDocSciencesearchService;
import cc.mrbird.febs.doctor.entity.DcaBDocSciencesearch;

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
@RequestMapping("dcaBDocSciencesearch")

public class DcaBDocSciencesearchController extends BaseController{

private String message;
@Autowired
public IDcaBDocSciencesearchService iDcaBDocSciencesearchService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBDocSciencesearch 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, DcaBDocSciencesearch dcaBDocSciencesearch){
        return getDataTable(this.iDcaBDocSciencesearchService.findDcaBDocSciencesearchs(request, dcaBDocSciencesearch));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBDocSciencesearch dcaBDocSciencesearch){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocSciencesearch.setUserAccount(currentUser.getUsername());
    dcaBDocSciencesearch.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocSciencesearchService.findDcaBDocSciencesearchs(request, dcaBDocSciencesearch));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBDocSciencesearch dcaBDocSciencesearch){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocSciencesearch.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocSciencesearchService.findDcaBDocSciencesearchs(request, dcaBDocSciencesearch));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBDocSciencesearchCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBDocSciencesearch> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBDocSciencesearch>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBDocSciencesearchService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBDocSciencesearchService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBDocSciencesearch dcaBDocSciencesearch:list
        ){
        if(dcaBDocSciencesearch.getState()!=null&&dcaBDocSciencesearch.getState().equals(3)) {
    dcaBDocSciencesearch.setState(3);
        }
        else{
    dcaBDocSciencesearch.setState(state);
        }
    dcaBDocSciencesearch.setDisplayIndex(display);
        display+=1;
    dcaBDocSciencesearch.setCreateUserId(currentUser.getUserId());
    dcaBDocSciencesearch.setUserAccount(currentUser.getUsername());
    dcaBDocSciencesearch.setUserAccountName(currentUser.getRealname());
        this.iDcaBDocSciencesearchService.createDcaBDocSciencesearch(dcaBDocSciencesearch);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBDocSciencesearch(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBDocSciencesearch dcaBDocSciencesearch= JSON.parseObject(jsonStr, new TypeReference<DcaBDocSciencesearch>() {
        });
    dcaBDocSciencesearch.setState(state);
    /**
        if (auditState >= 0) {
        if(state==2){
    dcaBDocSciencesearch.setAuditState(0);
        }
        else {
    dcaBDocSciencesearch.setAuditState(auditState+1);
        }

        }*/
    dcaBDocSciencesearch.setAuditMan(currentUser.getUsername());
    dcaBDocSciencesearch.setAuditManName(currentUser.getRealname());
    dcaBDocSciencesearch.setAuditDate(DateUtil.date());
        this.iDcaBDocSciencesearchService.updateDcaBDocSciencesearch(dcaBDocSciencesearch);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBDocSciencesearch
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBDocSciencesearch(@Valid DcaBDocSciencesearch dcaBDocSciencesearch)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocSciencesearch.setCreateUserId(currentUser.getUserId());
    dcaBDocSciencesearch.setUserAccount(currentUser.getUsername());
        this.iDcaBDocSciencesearchService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBDocSciencesearchService.createDcaBDocSciencesearch(dcaBDocSciencesearch);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBDocSciencesearch
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBDocSciencesearch:update")
public void updateDcaBDocSciencesearch(@Valid DcaBDocSciencesearch dcaBDocSciencesearch)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocSciencesearch.setModifyUserId(currentUser.getUserId());
        this.iDcaBDocSciencesearchService.updateDcaBDocSciencesearch(dcaBDocSciencesearch);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBDocSciencesearch:delete")
public void deleteDcaBDocSciencesearchs(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBDocSciencesearchService.deleteDcaBDocSciencesearchs(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@PostMapping("excel")
public void export(QueryRequest request, DcaBDocSciencesearch dcaBDocSciencesearch,String dataJson,HttpServletResponse response)throws FebsException{
        try{
        request.setPageNum(1);
        request.setPageSize(10000);
        User currentUser = FebsUtil.getCurrentUser();

    dcaBDocSciencesearch.setIsDeletemark(1);
        request.setSortField("user_account asc,state ");
        request.setSortOrder("ascend");
        List<DcaBDocSciencesearch> dcaBDocSciencesearchList=  this.iDcaBDocSciencesearchService.findDcaBDocSciencesearchs(request, dcaBDocSciencesearch).getRecords();
        //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
        ExportExcelUtils.exportCustomExcel_han(response, dcaBDocSciencesearchList,dataJson,"");
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@GetMapping("/{id}")
public DcaBDocSciencesearch detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBDocSciencesearch dcaBDocSciencesearch=this.iDcaBDocSciencesearchService.getById(id);
        return dcaBDocSciencesearch;
        }
        }