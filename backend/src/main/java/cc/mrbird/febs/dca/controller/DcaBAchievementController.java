package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.service.IDcaBAchievementService;
import cc.mrbird.febs.dca.entity.DcaBAchievement;

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
 * @since 2020-12-15
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBAchievement")

public class DcaBAchievementController extends BaseController{

private String message;
@Autowired
public IDcaBAchievementService iDcaBAchievementService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBAchievement 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBAchievement:view")
public Map<String, Object> List(QueryRequest request, DcaBAchievement dcaBAchievement){
        return getDataTable(this.iDcaBAchievementService.findDcaBAchievements(request, dcaBAchievement));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBAchievement dcaBAchievement){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBAchievement.setUserAccount(currentUser.getUsername());
    dcaBAchievement.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBAchievementService.findDcaBAchievements(request, dcaBAchievement));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBAchievement dcaBAchievement){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBAchievement.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBAchievementService.findDcaBAchievements(request, dcaBAchievement));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBAchievementCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBAchievement> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBAchievement>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBAchievementService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBAchievementService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBAchievement dcaBAchievement:list
        ){
        if(dcaBAchievement.getState()!=null&&dcaBAchievement.getState().equals(3)) {
    dcaBAchievement.setState(3);
        }
        else{
    dcaBAchievement.setState(state);
        }
    dcaBAchievement.setDisplayIndex(display);
        display+=1;
    dcaBAchievement.setCreateUserId(currentUser.getUserId());
    dcaBAchievement.setUserAccount(currentUser.getUsername());
    dcaBAchievement.setUserAccountName(currentUser.getRealname());
        this.iDcaBAchievementService.createDcaBAchievement(dcaBAchievement);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBAchievement(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBAchievement dcaBAchievement= JSON.parseObject(jsonStr, new TypeReference<DcaBAchievement>() {
        });
    dcaBAchievement.setState(state);
    /**
        if (auditState >= 0) {
        if(state==2){
    dcaBAchievement.setAuditState(0);
        }
        else {
    dcaBAchievement.setAuditState(auditState+1);
        }

        }*/
    dcaBAchievement.setAuditMan(currentUser.getUsername());
    dcaBAchievement.setAuditManName(currentUser.getRealname());
    dcaBAchievement.setAuditDate(DateUtil.date());
        this.iDcaBAchievementService.updateDcaBAchievement(dcaBAchievement);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBAchievement
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBAchievement(@Valid DcaBAchievement dcaBAchievement)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBAchievement.setCreateUserId(currentUser.getUserId());
    dcaBAchievement.setUserAccount(currentUser.getUsername());
        this.iDcaBAchievementService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBAchievementService.createDcaBAchievement(dcaBAchievement);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBAchievement
 * @return
 */
@Log("修改")
@PutMapping
public void updateDcaBAchievement(@Valid DcaBAchievement dcaBAchievement)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBAchievement.setModifyUserId(currentUser.getUserId());
        this.iDcaBAchievementService.updateDcaBAchievement(dcaBAchievement);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBAchievement:delete")
public void deleteDcaBAchievements(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBAchievementService.deleteDcaBAchievements(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBAchievement:export")
public void export(QueryRequest request, DcaBAchievement dcaBAchievement,HttpServletResponse response)throws FebsException{
        try{
        List<DcaBAchievement> dcaBAchievements=this.iDcaBAchievementService.findDcaBAchievements(request, dcaBAchievement).getRecords();
        ExcelKit.$Export(DcaBAchievement.class,response).downXlsx(dcaBAchievements,false);
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBAchievement detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBAchievement dcaBAchievement=this.iDcaBAchievementService.getById(id);
        return dcaBAchievement;
        }
        }