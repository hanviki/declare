package cc.mrbird.febs.doctor.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.ExportExcelUtils;

import cc.mrbird.febs.doctor.service.IDcaBDocUndergraduateService;
import cc.mrbird.febs.doctor.entity.DcaBDocUndergraduate;

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
@RequestMapping("dcaBDocUndergraduate")

public class DcaBDocUndergraduateController extends BaseController{

private String message;
@Autowired
public IDcaBDocUndergraduateService iDcaBDocUndergraduateService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBDocUndergraduate 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, DcaBDocUndergraduate dcaBDocUndergraduate){
        return getDataTable(this.iDcaBDocUndergraduateService.findDcaBDocUndergraduates(request, dcaBDocUndergraduate));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBDocUndergraduate dcaBDocUndergraduate){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocUndergraduate.setUserAccount(currentUser.getUsername());
    dcaBDocUndergraduate.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocUndergraduateService.findDcaBDocUndergraduates(request, dcaBDocUndergraduate));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBDocUndergraduate dcaBDocUndergraduate){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocUndergraduate.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocUndergraduateService.findDcaBDocUndergraduates(request, dcaBDocUndergraduate));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBDocUndergraduateCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBDocUndergraduate> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBDocUndergraduate>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBDocUndergraduateService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBDocUndergraduateService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBDocUndergraduate dcaBDocUndergraduate:list
        ){
        if(dcaBDocUndergraduate.getState()!=null&&dcaBDocUndergraduate.getState().equals(3)) {
    dcaBDocUndergraduate.setState(3);
        }
        else{
    dcaBDocUndergraduate.setState(state);
        }
    dcaBDocUndergraduate.setDisplayIndex(display);
        display+=1;
    dcaBDocUndergraduate.setCreateUserId(currentUser.getUserId());
    dcaBDocUndergraduate.setUserAccount(currentUser.getUsername());
    dcaBDocUndergraduate.setUserAccountName(currentUser.getRealname());
        this.iDcaBDocUndergraduateService.createDcaBDocUndergraduate(dcaBDocUndergraduate);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBDocUndergraduate(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBDocUndergraduate dcaBDocUndergraduate= JSON.parseObject(jsonStr, new TypeReference<DcaBDocUndergraduate>() {
        });
    dcaBDocUndergraduate.setState(state);
    /**
        if (auditState >= 0) {
        if(state==2){
    dcaBDocUndergraduate.setAuditState(0);
        }
        else {
    dcaBDocUndergraduate.setAuditState(auditState+1);
        }

        }*/
    dcaBDocUndergraduate.setAuditMan(currentUser.getUsername());
    dcaBDocUndergraduate.setAuditManName(currentUser.getRealname());
    dcaBDocUndergraduate.setAuditDate(DateUtil.date());
        this.iDcaBDocUndergraduateService.updateDcaBDocUndergraduate(dcaBDocUndergraduate);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBDocUndergraduate
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBDocUndergraduate(@Valid DcaBDocUndergraduate dcaBDocUndergraduate)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocUndergraduate.setCreateUserId(currentUser.getUserId());
    dcaBDocUndergraduate.setUserAccount(currentUser.getUsername());
        this.iDcaBDocUndergraduateService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBDocUndergraduateService.createDcaBDocUndergraduate(dcaBDocUndergraduate);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBDocUndergraduate
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBDocUndergraduate:update")
public void updateDcaBDocUndergraduate(@Valid DcaBDocUndergraduate dcaBDocUndergraduate)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocUndergraduate.setModifyUserId(currentUser.getUserId());
        this.iDcaBDocUndergraduateService.updateDcaBDocUndergraduate(dcaBDocUndergraduate);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBDocUndergraduate:delete")
public void deleteDcaBDocUndergraduates(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBDocUndergraduateService.deleteDcaBDocUndergraduates(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@PostMapping("excel")
public void export(QueryRequest request, DcaBDocUndergraduate dcaBDocUndergraduate,String dataJson,HttpServletResponse response)throws FebsException{
        try{
        request.setPageNum(1);
        request.setPageSize(10000);
        User currentUser = FebsUtil.getCurrentUser();

    dcaBDocUndergraduate.setIsDeletemark(1);
        request.setSortField("user_account asc,state ");
        request.setSortOrder("ascend");
        List<DcaBDocUndergraduate> dcaBDocUndergraduateList=  this.iDcaBDocUndergraduateService.findDcaBDocUndergraduates(request, dcaBDocUndergraduate).getRecords();
        //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
        ExportExcelUtils.exportCustomExcel_han(response, dcaBDocUndergraduateList,dataJson,"");
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@GetMapping("/{id}")
public DcaBDocUndergraduate detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBDocUndergraduate dcaBDocUndergraduate=this.iDcaBDocUndergraduateService.getById(id);
        return dcaBDocUndergraduate;
        }
        }