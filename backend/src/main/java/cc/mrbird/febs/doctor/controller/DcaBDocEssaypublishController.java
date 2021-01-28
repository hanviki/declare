package cc.mrbird.febs.doctor.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.ExportExcelUtils;

import cc.mrbird.febs.doctor.service.IDcaBDocEssaypublishService;
import cc.mrbird.febs.doctor.entity.DcaBDocEssaypublish;

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
@RequestMapping("dcaBDocEssaypublish")

public class DcaBDocEssaypublishController extends BaseController{

private String message;
@Autowired
public IDcaBDocEssaypublishService iDcaBDocEssaypublishService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBDocEssaypublish 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, DcaBDocEssaypublish dcaBDocEssaypublish){
        return getDataTable(this.iDcaBDocEssaypublishService.findDcaBDocEssaypublishs(request, dcaBDocEssaypublish));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBDocEssaypublish dcaBDocEssaypublish){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocEssaypublish.setUserAccount(currentUser.getUsername());
    dcaBDocEssaypublish.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocEssaypublishService.findDcaBDocEssaypublishs(request, dcaBDocEssaypublish));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBDocEssaypublish dcaBDocEssaypublish){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocEssaypublish.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocEssaypublishService.findDcaBDocEssaypublishs(request, dcaBDocEssaypublish));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBDocEssaypublishCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBDocEssaypublish> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBDocEssaypublish>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBDocEssaypublishService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBDocEssaypublishService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBDocEssaypublish dcaBDocEssaypublish:list
        ){
        if(dcaBDocEssaypublish.getState()!=null&&dcaBDocEssaypublish.getState().equals(3)) {
    dcaBDocEssaypublish.setState(3);
        }
        else{
    dcaBDocEssaypublish.setState(state);
        }
    dcaBDocEssaypublish.setDisplayIndex(display);
        display+=1;
    dcaBDocEssaypublish.setCreateUserId(currentUser.getUserId());
    dcaBDocEssaypublish.setUserAccount(currentUser.getUsername());
    dcaBDocEssaypublish.setUserAccountName(currentUser.getRealname());
        this.iDcaBDocEssaypublishService.createDcaBDocEssaypublish(dcaBDocEssaypublish);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBDocEssaypublish(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBDocEssaypublish dcaBDocEssaypublish= JSON.parseObject(jsonStr, new TypeReference<DcaBDocEssaypublish>() {
        });
    dcaBDocEssaypublish.setState(state);
    /**
        if (auditState >= 0) {
        if(state==2){
    dcaBDocEssaypublish.setAuditState(0);
        }
        else {
    dcaBDocEssaypublish.setAuditState(auditState+1);
        }

        }*/
    dcaBDocEssaypublish.setAuditMan(currentUser.getUsername());
    dcaBDocEssaypublish.setAuditManName(currentUser.getRealname());
    dcaBDocEssaypublish.setAuditDate(DateUtil.date());
        this.iDcaBDocEssaypublishService.updateDcaBDocEssaypublish(dcaBDocEssaypublish);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBDocEssaypublish
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBDocEssaypublish(@Valid DcaBDocEssaypublish dcaBDocEssaypublish)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocEssaypublish.setCreateUserId(currentUser.getUserId());
    dcaBDocEssaypublish.setUserAccount(currentUser.getUsername());
        this.iDcaBDocEssaypublishService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBDocEssaypublishService.createDcaBDocEssaypublish(dcaBDocEssaypublish);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBDocEssaypublish
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBDocEssaypublish:update")
public void updateDcaBDocEssaypublish(@Valid DcaBDocEssaypublish dcaBDocEssaypublish)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocEssaypublish.setModifyUserId(currentUser.getUserId());
        this.iDcaBDocEssaypublishService.updateDcaBDocEssaypublish(dcaBDocEssaypublish);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBDocEssaypublish:delete")
public void deleteDcaBDocEssaypublishs(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBDocEssaypublishService.deleteDcaBDocEssaypublishs(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@PostMapping("excel")
public void export(QueryRequest request, DcaBDocEssaypublish dcaBDocEssaypublish,String dataJson,HttpServletResponse response)throws FebsException{
        try{
        request.setPageNum(1);
        request.setPageSize(10000);
        User currentUser = FebsUtil.getCurrentUser();

    dcaBDocEssaypublish.setIsDeletemark(1);
        request.setSortField("user_account asc,state ");
        request.setSortOrder("ascend");
        List<DcaBDocEssaypublish> dcaBDocEssaypublishList=  this.iDcaBDocEssaypublishService.findDcaBDocEssaypublishs(request, dcaBDocEssaypublish).getRecords();
        //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
        ExportExcelUtils.exportCustomExcel_han(response, dcaBDocEssaypublishList,dataJson,"");
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@GetMapping("/{id}")
public DcaBDocEssaypublish detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBDocEssaypublish dcaBDocEssaypublish=this.iDcaBDocEssaypublishService.getById(id);
        return dcaBDocEssaypublish;
        }
        }