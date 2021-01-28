package cc.mrbird.febs.doctor.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.common.utils.ExportExcelUtils;
import cc.mrbird.febs.doctor.service.IDcaBDocAcademicService;
import cc.mrbird.febs.doctor.entity.DcaBDocAcademic;

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
@RequestMapping("dcaBDocAcademic")

public class DcaBDocAcademicController extends BaseController{

private String message;
@Autowired
public IDcaBDocAcademicService iDcaBDocAcademicService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBDocAcademic 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, DcaBDocAcademic dcaBDocAcademic){
        return getDataTable(this.iDcaBDocAcademicService.findDcaBDocAcademics(request, dcaBDocAcademic));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBDocAcademic dcaBDocAcademic){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocAcademic.setUserAccount(currentUser.getUsername());
    dcaBDocAcademic.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocAcademicService.findDcaBDocAcademics(request, dcaBDocAcademic));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBDocAcademic dcaBDocAcademic){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocAcademic.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocAcademicService.findDcaBDocAcademics(request, dcaBDocAcademic));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBDocAcademicCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBDocAcademic> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBDocAcademic>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBDocAcademicService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBDocAcademicService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBDocAcademic dcaBDocAcademic:list
        ){
        if(dcaBDocAcademic.getState()!=null&&dcaBDocAcademic.getState().equals(3)) {
    dcaBDocAcademic.setState(3);
        }
        else{
    dcaBDocAcademic.setState(state);
        }
    dcaBDocAcademic.setDisplayIndex(display);
        display+=1;
    dcaBDocAcademic.setCreateUserId(currentUser.getUserId());
    dcaBDocAcademic.setUserAccount(currentUser.getUsername());
    dcaBDocAcademic.setUserAccountName(currentUser.getRealname());
        this.iDcaBDocAcademicService.createDcaBDocAcademic(dcaBDocAcademic);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBDocAcademic(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBDocAcademic dcaBDocAcademic= JSON.parseObject(jsonStr, new TypeReference<DcaBDocAcademic>() {
        });
    dcaBDocAcademic.setState(state);
    /**
        if (auditState >= 0) {
        if(state==2){
    dcaBDocAcademic.setAuditState(0);
        }
        else {
    dcaBDocAcademic.setAuditState(auditState+1);
        }

        }*/
    dcaBDocAcademic.setAuditMan(currentUser.getUsername());
    dcaBDocAcademic.setAuditManName(currentUser.getRealname());
    dcaBDocAcademic.setAuditDate(DateUtil.date());
        this.iDcaBDocAcademicService.updateDcaBDocAcademic(dcaBDocAcademic);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBDocAcademic
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBDocAcademic(@Valid DcaBDocAcademic dcaBDocAcademic)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocAcademic.setCreateUserId(currentUser.getUserId());
    dcaBDocAcademic.setUserAccount(currentUser.getUsername());
        this.iDcaBDocAcademicService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBDocAcademicService.createDcaBDocAcademic(dcaBDocAcademic);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBDocAcademic
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBDocAcademic:update")
public void updateDcaBDocAcademic(@Valid DcaBDocAcademic dcaBDocAcademic)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocAcademic.setModifyUserId(currentUser.getUserId());
        this.iDcaBDocAcademicService.updateDcaBDocAcademic(dcaBDocAcademic);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBDocAcademic:delete")
public void deleteDcaBDocAcademics(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBDocAcademicService.deleteDcaBDocAcademics(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@PostMapping("excel")
public void export(QueryRequest request, DcaBDocAcademic dcaBDocAcademic,String dataJson,HttpServletResponse response)throws FebsException{
        try{
        request.setPageNum(1);
        request.setPageSize(10000);
        User currentUser = FebsUtil.getCurrentUser();

    dcaBDocAcademic.setIsDeletemark(1);
        request.setSortField("user_account asc,state ");
        request.setSortOrder("ascend");
        List<DcaBDocAcademic> dcaBDocAcademicList=  this.iDcaBDocAcademicService.findDcaBDocAcademics(request, dcaBDocAcademic).getRecords();
        //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
        ExportExcelUtils.exportCustomExcel_han(response, dcaBDocAcademicList,dataJson,"");
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@GetMapping("/{id}")
public DcaBDocAcademic detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBDocAcademic dcaBDocAcademic=this.iDcaBDocAcademicService.getById(id);
        return dcaBDocAcademic;
        }
        }