package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.service.IDcaBAcademicService;
import cc.mrbird.febs.dca.entity.DcaBAcademic;

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
@RequestMapping("dcaBAcademic")

public class DcaBAcademicController extends BaseController{

private String message;
@Autowired
public IDcaBAcademicService iDcaBAcademicService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBAcademic 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBAcademic:view")
public Map<String, Object> List(QueryRequest request, DcaBAcademic dcaBAcademic){
        return getDataTable(this.iDcaBAcademicService.findDcaBAcademics(request, dcaBAcademic));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBAcademic dcaBAcademic){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBAcademic.setUserAccount(currentUser.getUsername());
    dcaBAcademic.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBAcademicService.findDcaBAcademics(request, dcaBAcademic));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBAcademic dcaBAcademic){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBAcademic.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBAcademicService.findDcaBAcademics(request, dcaBAcademic));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBAcademicCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBAcademic> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBAcademic>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBAcademicService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBAcademicService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBAcademic dcaBAcademic:list
        ){
        if(dcaBAcademic.getState()!=null&&dcaBAcademic.getState().equals(3)) {
    dcaBAcademic.setState(3);
        }
        else{
    dcaBAcademic.setState(state);
        }
    dcaBAcademic.setDisplayIndex(display);
        display+=1;
    dcaBAcademic.setCreateUserId(currentUser.getUserId());
    dcaBAcademic.setUserAccount(currentUser.getUsername());
    dcaBAcademic.setUserAccountName(currentUser.getRealname());
        this.iDcaBAcademicService.createDcaBAcademic(dcaBAcademic);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBAcademic(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBAcademic dcaBAcademic= JSON.parseObject(jsonStr, new TypeReference<DcaBAcademic>() {
        });
    dcaBAcademic.setState(state);
    /**
        if (auditState >= 0) {
        if(state==2){
    dcaBAcademic.setAuditState(0);
        }
        else {
    dcaBAcademic.setAuditState(auditState+1);
        }

        }*/
    dcaBAcademic.setAuditMan(currentUser.getUsername());
    dcaBAcademic.setAuditManName(currentUser.getRealname());
    dcaBAcademic.setAuditDate(DateUtil.date());
        this.iDcaBAcademicService.updateDcaBAcademic(dcaBAcademic);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBAcademic
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBAcademic(@Valid DcaBAcademic dcaBAcademic)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBAcademic.setCreateUserId(currentUser.getUserId());
    dcaBAcademic.setUserAccount(currentUser.getUsername());
        this.iDcaBAcademicService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBAcademicService.createDcaBAcademic(dcaBAcademic);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBAcademic
 * @return
 */
@Log("修改")
@PutMapping
public void updateDcaBAcademic(@Valid DcaBAcademic dcaBAcademic)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBAcademic.setModifyUserId(currentUser.getUserId());
        this.iDcaBAcademicService.updateDcaBAcademic(dcaBAcademic);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBAcademic:delete")
public void deleteDcaBAcademics(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBAcademicService.deleteDcaBAcademics(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBAcademic:export")
public void export(QueryRequest request, DcaBAcademic dcaBAcademic,HttpServletResponse response)throws FebsException{
        try{
        List<DcaBAcademic> dcaBAcademics=this.iDcaBAcademicService.findDcaBAcademics(request, dcaBAcademic).getRecords();
        ExcelKit.$Export(DcaBAcademic.class,response).downXlsx(dcaBAcademics,false);
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBAcademic detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBAcademic dcaBAcademic=this.iDcaBAcademicService.getById(id);
        return dcaBAcademic;
        }
        }