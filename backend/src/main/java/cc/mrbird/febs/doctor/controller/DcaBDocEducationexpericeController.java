package cc.mrbird.febs.doctor.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.ExportExcelUtils;

import cc.mrbird.febs.doctor.service.IDcaBDocEducationexpericeService;
import cc.mrbird.febs.doctor.entity.DcaBDocEducationexperice;

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
@RequestMapping("dcaBDocEducationexperice")

public class DcaBDocEducationexpericeController extends BaseController{

private String message;
@Autowired
public IDcaBDocEducationexpericeService iDcaBDocEducationexpericeService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBDocEducationexperice 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, DcaBDocEducationexperice dcaBDocEducationexperice){
        return getDataTable(this.iDcaBDocEducationexpericeService.findDcaBDocEducationexperices(request, dcaBDocEducationexperice));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBDocEducationexperice dcaBDocEducationexperice){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocEducationexperice.setUserAccount(currentUser.getUsername());
    dcaBDocEducationexperice.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocEducationexpericeService.findDcaBDocEducationexperices(request, dcaBDocEducationexperice));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBDocEducationexperice dcaBDocEducationexperice){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocEducationexperice.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocEducationexpericeService.findDcaBDocEducationexperices(request, dcaBDocEducationexperice));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBDocEducationexpericeCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBDocEducationexperice> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBDocEducationexperice>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBDocEducationexpericeService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBDocEducationexpericeService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBDocEducationexperice dcaBDocEducationexperice:list
        ){
        if(dcaBDocEducationexperice.getState()!=null&&dcaBDocEducationexperice.getState().equals(3)) {
    dcaBDocEducationexperice.setState(3);
        }
        else{
    dcaBDocEducationexperice.setState(state);
        }
    dcaBDocEducationexperice.setDisplayIndex(display);
        display+=1;
    dcaBDocEducationexperice.setCreateUserId(currentUser.getUserId());
    dcaBDocEducationexperice.setUserAccount(currentUser.getUsername());
    dcaBDocEducationexperice.setUserAccountName(currentUser.getRealname());
        this.iDcaBDocEducationexpericeService.createDcaBDocEducationexperice(dcaBDocEducationexperice);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBDocEducationexperice(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBDocEducationexperice dcaBDocEducationexperice= JSON.parseObject(jsonStr, new TypeReference<DcaBDocEducationexperice>() {
        });
    dcaBDocEducationexperice.setState(state);
    /**
        if (auditState >= 0) {
        if(state==2){
    dcaBDocEducationexperice.setAuditState(0);
        }
        else {
    dcaBDocEducationexperice.setAuditState(auditState+1);
        }

        }*/
    dcaBDocEducationexperice.setAuditMan(currentUser.getUsername());
    dcaBDocEducationexperice.setAuditManName(currentUser.getRealname());
    dcaBDocEducationexperice.setAuditDate(DateUtil.date());
        this.iDcaBDocEducationexpericeService.updateDcaBDocEducationexperice(dcaBDocEducationexperice);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBDocEducationexperice
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBDocEducationexperice(@Valid DcaBDocEducationexperice dcaBDocEducationexperice)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocEducationexperice.setCreateUserId(currentUser.getUserId());
    dcaBDocEducationexperice.setUserAccount(currentUser.getUsername());
        this.iDcaBDocEducationexpericeService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBDocEducationexpericeService.createDcaBDocEducationexperice(dcaBDocEducationexperice);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBDocEducationexperice
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBDocEducationexperice:update")
public void updateDcaBDocEducationexperice(@Valid DcaBDocEducationexperice dcaBDocEducationexperice)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocEducationexperice.setModifyUserId(currentUser.getUserId());
        this.iDcaBDocEducationexpericeService.updateDcaBDocEducationexperice(dcaBDocEducationexperice);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBDocEducationexperice:delete")
public void deleteDcaBDocEducationexperices(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBDocEducationexpericeService.deleteDcaBDocEducationexperices(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@PostMapping("excel")
public void export(QueryRequest request, DcaBDocEducationexperice dcaBDocEducationexperice,String dataJson,HttpServletResponse response)throws FebsException{
        try{
        request.setPageNum(1);
        request.setPageSize(10000);
        User currentUser = FebsUtil.getCurrentUser();

    dcaBDocEducationexperice.setIsDeletemark(1);
        request.setSortField("user_account asc,state ");
        request.setSortOrder("ascend");
        List<DcaBDocEducationexperice> dcaBDocEducationexpericeList=  this.iDcaBDocEducationexpericeService.findDcaBDocEducationexperices(request, dcaBDocEducationexperice).getRecords();
        //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
        ExportExcelUtils.exportCustomExcel_han(response, dcaBDocEducationexpericeList,dataJson,"");
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@GetMapping("/{id}")
public DcaBDocEducationexperice detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBDocEducationexperice dcaBDocEducationexperice=this.iDcaBDocEducationexpericeService.getById(id);
        return dcaBDocEducationexperice;
        }
        }