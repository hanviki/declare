package cc.mrbird.febs.doctor.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.ExportExcelUtils;

import cc.mrbird.febs.doctor.service.IDcaBDocSciencepublishService;
import cc.mrbird.febs.doctor.entity.DcaBDocSciencepublish;

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
@RequestMapping("dcaBDocSciencepublish")

public class DcaBDocSciencepublishController extends BaseController{

private String message;
@Autowired
public IDcaBDocSciencepublishService iDcaBDocSciencepublishService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBDocSciencepublish 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, DcaBDocSciencepublish dcaBDocSciencepublish){
        return getDataTable(this.iDcaBDocSciencepublishService.findDcaBDocSciencepublishs(request, dcaBDocSciencepublish));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBDocSciencepublish dcaBDocSciencepublish){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocSciencepublish.setUserAccount(currentUser.getUsername());
    dcaBDocSciencepublish.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocSciencepublishService.findDcaBDocSciencepublishs(request, dcaBDocSciencepublish));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBDocSciencepublish dcaBDocSciencepublish){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocSciencepublish.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocSciencepublishService.findDcaBDocSciencepublishs(request, dcaBDocSciencepublish));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBDocSciencepublishCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBDocSciencepublish> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBDocSciencepublish>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBDocSciencepublishService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBDocSciencepublishService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBDocSciencepublish dcaBDocSciencepublish:list
        ){
        if(dcaBDocSciencepublish.getState()!=null&&dcaBDocSciencepublish.getState().equals(3)) {
    dcaBDocSciencepublish.setState(3);
        }
        else{
    dcaBDocSciencepublish.setState(state);
        }
    dcaBDocSciencepublish.setDisplayIndex(display);
        display+=1;
    dcaBDocSciencepublish.setCreateUserId(currentUser.getUserId());
    dcaBDocSciencepublish.setUserAccount(currentUser.getUsername());
    dcaBDocSciencepublish.setUserAccountName(currentUser.getRealname());
        this.iDcaBDocSciencepublishService.createDcaBDocSciencepublish(dcaBDocSciencepublish);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBDocSciencepublish(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBDocSciencepublish dcaBDocSciencepublish= JSON.parseObject(jsonStr, new TypeReference<DcaBDocSciencepublish>() {
        });
    dcaBDocSciencepublish.setState(state);
    /**
        if (auditState >= 0) {
        if(state==2){
    dcaBDocSciencepublish.setAuditState(0);
        }
        else {
    dcaBDocSciencepublish.setAuditState(auditState+1);
        }

        }*/
    dcaBDocSciencepublish.setAuditMan(currentUser.getUsername());
    dcaBDocSciencepublish.setAuditManName(currentUser.getRealname());
    dcaBDocSciencepublish.setAuditDate(DateUtil.date());
        this.iDcaBDocSciencepublishService.updateDcaBDocSciencepublish(dcaBDocSciencepublish);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBDocSciencepublish
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBDocSciencepublish(@Valid DcaBDocSciencepublish dcaBDocSciencepublish)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocSciencepublish.setCreateUserId(currentUser.getUserId());
    dcaBDocSciencepublish.setUserAccount(currentUser.getUsername());
        this.iDcaBDocSciencepublishService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBDocSciencepublishService.createDcaBDocSciencepublish(dcaBDocSciencepublish);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBDocSciencepublish
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBDocSciencepublish:update")
public void updateDcaBDocSciencepublish(@Valid DcaBDocSciencepublish dcaBDocSciencepublish)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocSciencepublish.setModifyUserId(currentUser.getUserId());
        this.iDcaBDocSciencepublishService.updateDcaBDocSciencepublish(dcaBDocSciencepublish);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBDocSciencepublish:delete")
public void deleteDcaBDocSciencepublishs(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBDocSciencepublishService.deleteDcaBDocSciencepublishs(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@PostMapping("excel")
public void export(QueryRequest request, DcaBDocSciencepublish dcaBDocSciencepublish,String dataJson,HttpServletResponse response)throws FebsException{
        try{
        request.setPageNum(1);
        request.setPageSize(10000);
        User currentUser = FebsUtil.getCurrentUser();

    dcaBDocSciencepublish.setIsDeletemark(1);
        request.setSortField("user_account asc,state ");
        request.setSortOrder("ascend");
        List<DcaBDocSciencepublish> dcaBDocSciencepublishList=  this.iDcaBDocSciencepublishService.findDcaBDocSciencepublishs(request, dcaBDocSciencepublish).getRecords();
        //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
        ExportExcelUtils.exportCustomExcel_han(response, dcaBDocSciencepublishList,dataJson,"");
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@GetMapping("/{id}")
public DcaBDocSciencepublish detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBDocSciencepublish dcaBDocSciencepublish=this.iDcaBDocSciencepublishService.getById(id);
        return dcaBDocSciencepublish;
        }
        }