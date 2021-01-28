package cc.mrbird.febs.doctor.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.doctor.service.IDcaBDocPatentService;
import cc.mrbird.febs.doctor.entity.DcaBDocPatent;
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
@RequestMapping("dcaBDocPatent")

public class DcaBDocPatentController extends BaseController{

private String message;
@Autowired
public IDcaBDocPatentService iDcaBDocPatentService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBDocPatent 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, DcaBDocPatent dcaBDocPatent){
        return getDataTable(this.iDcaBDocPatentService.findDcaBDocPatents(request, dcaBDocPatent));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBDocPatent dcaBDocPatent){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocPatent.setUserAccount(currentUser.getUsername());
    dcaBDocPatent.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocPatentService.findDcaBDocPatents(request, dcaBDocPatent));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBDocPatent dcaBDocPatent){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocPatent.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocPatentService.findDcaBDocPatents(request, dcaBDocPatent));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBDocPatentCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBDocPatent> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBDocPatent>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBDocPatentService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBDocPatentService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBDocPatent dcaBDocPatent:list
        ){
        if(dcaBDocPatent.getState()!=null&&dcaBDocPatent.getState().equals(3)) {
    dcaBDocPatent.setState(3);
        }
        else{
    dcaBDocPatent.setState(state);
        }
    dcaBDocPatent.setDisplayIndex(display);
        display+=1;
    dcaBDocPatent.setCreateUserId(currentUser.getUserId());
    dcaBDocPatent.setUserAccount(currentUser.getUsername());
    dcaBDocPatent.setUserAccountName(currentUser.getRealname());
        this.iDcaBDocPatentService.createDcaBDocPatent(dcaBDocPatent);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBDocPatent(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBDocPatent dcaBDocPatent= JSON.parseObject(jsonStr, new TypeReference<DcaBDocPatent>() {
        });
    dcaBDocPatent.setState(state);
    /**
        if (auditState >= 0) {
        if(state==2){
    dcaBDocPatent.setAuditState(0);
        }
        else {
    dcaBDocPatent.setAuditState(auditState+1);
        }

        }*/
    dcaBDocPatent.setAuditMan(currentUser.getUsername());
    dcaBDocPatent.setAuditManName(currentUser.getRealname());
    dcaBDocPatent.setAuditDate(DateUtil.date());
        this.iDcaBDocPatentService.updateDcaBDocPatent(dcaBDocPatent);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBDocPatent
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBDocPatent(@Valid DcaBDocPatent dcaBDocPatent)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocPatent.setCreateUserId(currentUser.getUserId());
    dcaBDocPatent.setUserAccount(currentUser.getUsername());
        this.iDcaBDocPatentService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBDocPatentService.createDcaBDocPatent(dcaBDocPatent);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBDocPatent
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBDocPatent:update")
public void updateDcaBDocPatent(@Valid DcaBDocPatent dcaBDocPatent)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocPatent.setModifyUserId(currentUser.getUserId());
        this.iDcaBDocPatentService.updateDcaBDocPatent(dcaBDocPatent);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBDocPatent:delete")
public void deleteDcaBDocPatents(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBDocPatentService.deleteDcaBDocPatents(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@PostMapping("excel")
public void export(QueryRequest request, DcaBDocPatent dcaBDocPatent,String dataJson,HttpServletResponse response)throws FebsException{
        try{
        request.setPageNum(1);
        request.setPageSize(10000);
        User currentUser = FebsUtil.getCurrentUser();

    dcaBDocPatent.setIsDeletemark(1);
        request.setSortField("user_account asc,state ");
        request.setSortOrder("ascend");
        List<DcaBDocPatent> dcaBDocPatentList=  this.iDcaBDocPatentService.findDcaBDocPatents(request, dcaBDocPatent).getRecords();
        //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
        ExportExcelUtils.exportCustomExcel_han(response, dcaBDocPatentList,dataJson,"");
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@GetMapping("/{id}")
public DcaBDocPatent detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBDocPatent dcaBDocPatent=this.iDcaBDocPatentService.getById(id);
        return dcaBDocPatent;
        }
        }