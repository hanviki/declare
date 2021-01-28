package cc.mrbird.febs.doctor.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.doctor.service.IDcaBDocPersonalsummaryService;
import cc.mrbird.febs.doctor.entity.DcaBDocPersonalsummary;
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
@RequestMapping("dcaBDocPersonalsummary")

public class DcaBDocPersonalsummaryController extends BaseController{

private String message;
@Autowired
public IDcaBDocPersonalsummaryService iDcaBDocPersonalsummaryService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBDocPersonalsummary 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, DcaBDocPersonalsummary dcaBDocPersonalsummary){
        return getDataTable(this.iDcaBDocPersonalsummaryService.findDcaBDocPersonalsummarys(request, dcaBDocPersonalsummary));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBDocPersonalsummary dcaBDocPersonalsummary){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocPersonalsummary.setUserAccount(currentUser.getUsername());
    dcaBDocPersonalsummary.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocPersonalsummaryService.findDcaBDocPersonalsummarys(request, dcaBDocPersonalsummary));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBDocPersonalsummary dcaBDocPersonalsummary){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocPersonalsummary.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocPersonalsummaryService.findDcaBDocPersonalsummarys(request, dcaBDocPersonalsummary));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBDocPersonalsummaryCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBDocPersonalsummary> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBDocPersonalsummary>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBDocPersonalsummaryService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBDocPersonalsummaryService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBDocPersonalsummary dcaBDocPersonalsummary:list
        ){
        if(dcaBDocPersonalsummary.getState()!=null&&dcaBDocPersonalsummary.getState().equals(3)) {
    dcaBDocPersonalsummary.setState(3);
        }
        else{
    dcaBDocPersonalsummary.setState(state);
        }
    dcaBDocPersonalsummary.setDisplayIndex(display);
        display+=1;
    dcaBDocPersonalsummary.setCreateUserId(currentUser.getUserId());
    dcaBDocPersonalsummary.setUserAccount(currentUser.getUsername());
    dcaBDocPersonalsummary.setUserAccountName(currentUser.getRealname());
        this.iDcaBDocPersonalsummaryService.createDcaBDocPersonalsummary(dcaBDocPersonalsummary);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBDocPersonalsummary(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBDocPersonalsummary dcaBDocPersonalsummary= JSON.parseObject(jsonStr, new TypeReference<DcaBDocPersonalsummary>() {
        });
    dcaBDocPersonalsummary.setState(state);
    /**
        if (auditState >= 0) {
        if(state==2){
    dcaBDocPersonalsummary.setAuditState(0);
        }
        else {
    dcaBDocPersonalsummary.setAuditState(auditState+1);
        }

        }*/
    dcaBDocPersonalsummary.setAuditMan(currentUser.getUsername());
    dcaBDocPersonalsummary.setAuditManName(currentUser.getRealname());
    dcaBDocPersonalsummary.setAuditDate(DateUtil.date());
        this.iDcaBDocPersonalsummaryService.updateDcaBDocPersonalsummary(dcaBDocPersonalsummary);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBDocPersonalsummary
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBDocPersonalsummary(@Valid DcaBDocPersonalsummary dcaBDocPersonalsummary)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocPersonalsummary.setCreateUserId(currentUser.getUserId());
    dcaBDocPersonalsummary.setUserAccount(currentUser.getUsername());
        this.iDcaBDocPersonalsummaryService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBDocPersonalsummaryService.createDcaBDocPersonalsummary(dcaBDocPersonalsummary);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBDocPersonalsummary
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBDocPersonalsummary:update")
public void updateDcaBDocPersonalsummary(@Valid DcaBDocPersonalsummary dcaBDocPersonalsummary)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocPersonalsummary.setModifyUserId(currentUser.getUserId());
        this.iDcaBDocPersonalsummaryService.updateDcaBDocPersonalsummary(dcaBDocPersonalsummary);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBDocPersonalsummary:delete")
public void deleteDcaBDocPersonalsummarys(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBDocPersonalsummaryService.deleteDcaBDocPersonalsummarys(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@PostMapping("excel")
public void export(QueryRequest request, DcaBDocPersonalsummary dcaBDocPersonalsummary,String dataJson,HttpServletResponse response)throws FebsException{
        try{
        request.setPageNum(1);
        request.setPageSize(10000);
        User currentUser = FebsUtil.getCurrentUser();

    dcaBDocPersonalsummary.setIsDeletemark(1);
        request.setSortField("user_account asc,state ");
        request.setSortOrder("ascend");
        List<DcaBDocPersonalsummary> dcaBDocPersonalsummaryList=  this.iDcaBDocPersonalsummaryService.findDcaBDocPersonalsummarys(request, dcaBDocPersonalsummary).getRecords();
        //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
        ExportExcelUtils.exportCustomExcel_han(response, dcaBDocPersonalsummaryList,dataJson,"");
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@GetMapping("/{id}")
public DcaBDocPersonalsummary detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBDocPersonalsummary dcaBDocPersonalsummary=this.iDcaBDocPersonalsummaryService.getById(id);
        return dcaBDocPersonalsummary;
        }
        }