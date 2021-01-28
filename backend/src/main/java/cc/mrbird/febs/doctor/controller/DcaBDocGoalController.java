package cc.mrbird.febs.doctor.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.doctor.service.IDcaBDocGoalService;
import cc.mrbird.febs.doctor.entity.DcaBDocGoal;
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
@RequestMapping("dcaBDocGoal")

public class DcaBDocGoalController extends BaseController{

private String message;
@Autowired
public IDcaBDocGoalService iDcaBDocGoalService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBDocGoal 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, DcaBDocGoal dcaBDocGoal){
        return getDataTable(this.iDcaBDocGoalService.findDcaBDocGoals(request, dcaBDocGoal));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBDocGoal dcaBDocGoal){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocGoal.setUserAccount(currentUser.getUsername());
    dcaBDocGoal.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocGoalService.findDcaBDocGoals(request, dcaBDocGoal));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBDocGoal dcaBDocGoal){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocGoal.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocGoalService.findDcaBDocGoals(request, dcaBDocGoal));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBDocGoalCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBDocGoal> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBDocGoal>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBDocGoalService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBDocGoalService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBDocGoal dcaBDocGoal:list
        ){
        if(dcaBDocGoal.getState()!=null&&dcaBDocGoal.getState().equals(3)) {
    dcaBDocGoal.setState(3);
        }
        else{
    dcaBDocGoal.setState(state);
        }
    dcaBDocGoal.setDisplayIndex(display);
        display+=1;
    dcaBDocGoal.setCreateUserId(currentUser.getUserId());
    dcaBDocGoal.setUserAccount(currentUser.getUsername());
    dcaBDocGoal.setUserAccountName(currentUser.getRealname());
        this.iDcaBDocGoalService.createDcaBDocGoal(dcaBDocGoal);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBDocGoal(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBDocGoal dcaBDocGoal= JSON.parseObject(jsonStr, new TypeReference<DcaBDocGoal>() {
        });
    dcaBDocGoal.setState(state);
    /**
        if (auditState >= 0) {
        if(state==2){
    dcaBDocGoal.setAuditState(0);
        }
        else {
    dcaBDocGoal.setAuditState(auditState+1);
        }

        }*/
    dcaBDocGoal.setAuditMan(currentUser.getUsername());
    dcaBDocGoal.setAuditManName(currentUser.getRealname());
    dcaBDocGoal.setAuditDate(DateUtil.date());
        this.iDcaBDocGoalService.updateDcaBDocGoal(dcaBDocGoal);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBDocGoal
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBDocGoal(@Valid DcaBDocGoal dcaBDocGoal)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocGoal.setCreateUserId(currentUser.getUserId());
    dcaBDocGoal.setUserAccount(currentUser.getUsername());
        this.iDcaBDocGoalService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBDocGoalService.createDcaBDocGoal(dcaBDocGoal);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBDocGoal
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBDocGoal:update")
public void updateDcaBDocGoal(@Valid DcaBDocGoal dcaBDocGoal)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocGoal.setModifyUserId(currentUser.getUserId());
        this.iDcaBDocGoalService.updateDcaBDocGoal(dcaBDocGoal);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBDocGoal:delete")
public void deleteDcaBDocGoals(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBDocGoalService.deleteDcaBDocGoals(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@PostMapping("excel")
public void export(QueryRequest request, DcaBDocGoal dcaBDocGoal,String dataJson,HttpServletResponse response)throws FebsException{
        try{
        request.setPageNum(1);
        request.setPageSize(10000);
        User currentUser = FebsUtil.getCurrentUser();

    dcaBDocGoal.setIsDeletemark(1);
        request.setSortField("user_account asc,state ");
        request.setSortOrder("ascend");
        List<DcaBDocGoal> dcaBDocGoalList=  this.iDcaBDocGoalService.findDcaBDocGoals(request, dcaBDocGoal).getRecords();
        //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
        ExportExcelUtils.exportCustomExcel_han(response, dcaBDocGoalList,dataJson,"");
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@GetMapping("/{id}")
public DcaBDocGoal detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBDocGoal dcaBDocGoal=this.iDcaBDocGoalService.getById(id);
        return dcaBDocGoal;
        }
        }