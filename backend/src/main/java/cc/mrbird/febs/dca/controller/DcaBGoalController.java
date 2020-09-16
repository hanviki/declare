package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.service.IDcaBGoalService;
import cc.mrbird.febs.dca.entity.DcaBGoal;

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
 * @since 2020-09-16
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBGoal")

public class DcaBGoalController extends BaseController{

private String message;
@Autowired
public IDcaBGoalService iDcaBGoalService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBGoal 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBGoal:view")
public Map<String, Object> List(QueryRequest request, DcaBGoal dcaBGoal){
        return getDataTable(this.iDcaBGoalService.findDcaBGoals(request, dcaBGoal));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBGoal dcaBGoal){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBGoal.setUserAccount(currentUser.getUsername());
    dcaBGoal.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("state");
        request.setSortOrder("descend");
        return getDataTable(this.iDcaBGoalService.findDcaBGoals(request, dcaBGoal));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBGoal dcaBGoal){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBGoal.setIsDeletemark(1);
        request.setSortField("state");
        request.setSortOrder("descend");
        return getDataTable(this.iDcaBGoalService.findDcaBGoals(request, dcaBGoal));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBGoalCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBGoal> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBGoal>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBGoalService.deleteByuseraccount(currentUser.getUsername());
        for(DcaBGoal dcaBGoal:list
        ){
        if(dcaBGoal.getState()!=null&&dcaBGoal.getState().equals(3)) {
    dcaBGoal.setState(3);
        }
        else{
    dcaBGoal.setState(state);
        }
    dcaBGoal.setCreateUserId(currentUser.getUserId());
    dcaBGoal.setUserAccount(currentUser.getUsername());
    dcaBGoal.setUserAccountName(currentUser.getRealname());
        this.iDcaBGoalService.createDcaBGoal(dcaBGoal);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBGoal(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBGoal dcaBGoal= JSON.parseObject(jsonStr, new TypeReference<DcaBGoal>() {
        });
    dcaBGoal.setState(state);
    dcaBGoal.setAuditMan(currentUser.getUsername());
    dcaBGoal.setAuditManName(currentUser.getRealname());
    dcaBGoal.setAuditDate(DateUtil.date());
        this.iDcaBGoalService.updateDcaBGoal(dcaBGoal);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBGoal
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBGoal(@Valid DcaBGoal dcaBGoal)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBGoal.setCreateUserId(currentUser.getUserId());
    dcaBGoal.setUserAccount(currentUser.getUsername());
        this.iDcaBGoalService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBGoalService.createDcaBGoal(dcaBGoal);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBGoal
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBGoal:update")
public void updateDcaBGoal(@Valid DcaBGoal dcaBGoal)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBGoal.setModifyUserId(currentUser.getUserId());
        this.iDcaBGoalService.updateDcaBGoal(dcaBGoal);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBGoal:delete")
public void deleteDcaBGoals(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBGoalService.deleteDcaBGoals(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBGoal:export")
public void export(QueryRequest request, DcaBGoal dcaBGoal,HttpServletResponse response)throws FebsException{
        try{
        List<DcaBGoal> dcaBGoals=this.iDcaBGoalService.findDcaBGoals(request, dcaBGoal).getRecords();
        ExcelKit.$Export(DcaBGoal.class,response).downXlsx(dcaBGoals,false);
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBGoal detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBGoal dcaBGoal=this.iDcaBGoalService.getById(id);
        return dcaBGoal;
        }
        }