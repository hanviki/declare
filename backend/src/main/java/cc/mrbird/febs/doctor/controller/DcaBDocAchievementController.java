package cc.mrbird.febs.doctor.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.doctor.service.IDcaBDocAchievementService;
import cc.mrbird.febs.doctor.entity.DcaBDocAchievement;

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
import cc.mrbird.febs.common.utils.ExportExcelUtils;

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
@RequestMapping("dcaBDocAchievement")

public class DcaBDocAchievementController extends BaseController{

private String message;
@Autowired
public IDcaBDocAchievementService iDcaBDocAchievementService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBDocAchievement 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, DcaBDocAchievement dcaBDocAchievement){
        return getDataTable(this.iDcaBDocAchievementService.findDcaBDocAchievements(request, dcaBDocAchievement));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBDocAchievement dcaBDocAchievement){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocAchievement.setUserAccount(currentUser.getUsername());
    dcaBDocAchievement.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocAchievementService.findDcaBDocAchievements(request, dcaBDocAchievement));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBDocAchievement dcaBDocAchievement){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocAchievement.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocAchievementService.findDcaBDocAchievements(request, dcaBDocAchievement));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBDocAchievementCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBDocAchievement> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBDocAchievement>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBDocAchievementService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBDocAchievementService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBDocAchievement dcaBDocAchievement:list
        ){
        if(dcaBDocAchievement.getState()!=null&&dcaBDocAchievement.getState().equals(3)) {
    dcaBDocAchievement.setState(3);
        }
        else{
    dcaBDocAchievement.setState(state);
        }
    dcaBDocAchievement.setDisplayIndex(display);
        display+=1;
    dcaBDocAchievement.setCreateUserId(currentUser.getUserId());
    dcaBDocAchievement.setUserAccount(currentUser.getUsername());
    dcaBDocAchievement.setUserAccountName(currentUser.getRealname());
        this.iDcaBDocAchievementService.createDcaBDocAchievement(dcaBDocAchievement);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBDocAchievement(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBDocAchievement dcaBDocAchievement= JSON.parseObject(jsonStr, new TypeReference<DcaBDocAchievement>() {
        });
    dcaBDocAchievement.setState(state);
    /**
        if (auditState >= 0) {
        if(state==2){
    dcaBDocAchievement.setAuditState(0);
        }
        else {
    dcaBDocAchievement.setAuditState(auditState+1);
        }

        }*/
    dcaBDocAchievement.setAuditMan(currentUser.getUsername());
    dcaBDocAchievement.setAuditManName(currentUser.getRealname());
    dcaBDocAchievement.setAuditDate(DateUtil.date());
        this.iDcaBDocAchievementService.updateDcaBDocAchievement(dcaBDocAchievement);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBDocAchievement
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBDocAchievement(@Valid DcaBDocAchievement dcaBDocAchievement)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocAchievement.setCreateUserId(currentUser.getUserId());
    dcaBDocAchievement.setUserAccount(currentUser.getUsername());
        this.iDcaBDocAchievementService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBDocAchievementService.createDcaBDocAchievement(dcaBDocAchievement);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBDocAchievement
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBDocAchievement:update")
public void updateDcaBDocAchievement(@Valid DcaBDocAchievement dcaBDocAchievement)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocAchievement.setModifyUserId(currentUser.getUserId());
        this.iDcaBDocAchievementService.updateDcaBDocAchievement(dcaBDocAchievement);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBDocAchievement:delete")
public void deleteDcaBDocAchievements(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBDocAchievementService.deleteDcaBDocAchievements(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@PostMapping("excel")
public void export(QueryRequest request, DcaBDocAchievement dcaBDocAchievement,String dataJson,HttpServletResponse response)throws FebsException{
        try{
        request.setPageNum(1);
        request.setPageSize(10000);
        User currentUser = FebsUtil.getCurrentUser();

    dcaBDocAchievement.setIsDeletemark(1);
        request.setSortField("user_account asc,state ");
        request.setSortOrder("ascend");
        List<DcaBDocAchievement> dcaBDocAchievementList=  this.iDcaBDocAchievementService.findDcaBDocAchievements(request, dcaBDocAchievement).getRecords();
        //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
        ExportExcelUtils.exportCustomExcel_han(response, dcaBDocAchievementList,dataJson,"");
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@GetMapping("/{id}")
public DcaBDocAchievement detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBDocAchievement dcaBDocAchievement=this.iDcaBDocAchievementService.getById(id);
        return dcaBDocAchievement;
        }
        }