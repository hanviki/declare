package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.service.IDcaBUndergraduateService;
import cc.mrbird.febs.dca.entity.DcaBUndergraduate;

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
 * @since 2020-10-20
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBUndergraduate")

public class DcaBUndergraduateController extends BaseController{

private String message;
@Autowired
public IDcaBUndergraduateService iDcaBUndergraduateService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBUndergraduate 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBUndergraduate:view")
public Map<String, Object> List(QueryRequest request, DcaBUndergraduate dcaBUndergraduate){
        return getDataTable(this.iDcaBUndergraduateService.findDcaBUndergraduates(request, dcaBUndergraduate));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBUndergraduate dcaBUndergraduate){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBUndergraduate.setUserAccount(currentUser.getUsername());
    dcaBUndergraduate.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBUndergraduateService.findDcaBUndergraduates(request, dcaBUndergraduate));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBUndergraduate dcaBUndergraduate){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBUndergraduate.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBUndergraduateService.findDcaBUndergraduates(request, dcaBUndergraduate));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBUndergraduateCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBUndergraduate> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBUndergraduate>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBUndergraduateService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBUndergraduateService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBUndergraduate dcaBUndergraduate:list
        ){
        if(dcaBUndergraduate.getState()!=null&&dcaBUndergraduate.getState().equals(3)) {
    dcaBUndergraduate.setState(3);
        }
        else{
    dcaBUndergraduate.setState(state);
        }
    dcaBUndergraduate.setDisplayIndex(display);
        display+=1;
    dcaBUndergraduate.setCreateUserId(currentUser.getUserId());
    dcaBUndergraduate.setUserAccount(currentUser.getUsername());
    dcaBUndergraduate.setUserAccountName(currentUser.getRealname());
        this.iDcaBUndergraduateService.createDcaBUndergraduate(dcaBUndergraduate);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBUndergraduate(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBUndergraduate dcaBUndergraduate= JSON.parseObject(jsonStr, new TypeReference<DcaBUndergraduate>() {
        });
    dcaBUndergraduate.setState(state);
    dcaBUndergraduate.setAuditMan(currentUser.getUsername());
    dcaBUndergraduate.setAuditManName(currentUser.getRealname());
    dcaBUndergraduate.setAuditDate(DateUtil.date());
        this.iDcaBUndergraduateService.updateDcaBUndergraduate(dcaBUndergraduate);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBUndergraduate
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBUndergraduate(@Valid DcaBUndergraduate dcaBUndergraduate)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBUndergraduate.setCreateUserId(currentUser.getUserId());
    dcaBUndergraduate.setUserAccount(currentUser.getUsername());
        this.iDcaBUndergraduateService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBUndergraduateService.createDcaBUndergraduate(dcaBUndergraduate);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBUndergraduate
 * @return
 */
@Log("修改")
@PutMapping
public void updateDcaBUndergraduate(@Valid DcaBUndergraduate dcaBUndergraduate)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBUndergraduate.setModifyUserId(currentUser.getUserId());
        this.iDcaBUndergraduateService.updateDcaBUndergraduate(dcaBUndergraduate);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBUndergraduate:delete")
public void deleteDcaBUndergraduates(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBUndergraduateService.deleteDcaBUndergraduates(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBUndergraduate:export")
public void export(QueryRequest request, DcaBUndergraduate dcaBUndergraduate,HttpServletResponse response)throws FebsException{
        try{
        List<DcaBUndergraduate> dcaBUndergraduates=this.iDcaBUndergraduateService.findDcaBUndergraduates(request, dcaBUndergraduate).getRecords();
        ExcelKit.$Export(DcaBUndergraduate.class,response).downXlsx(dcaBUndergraduates,false);
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBUndergraduate detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBUndergraduate dcaBUndergraduate=this.iDcaBUndergraduateService.getById(id);
        return dcaBUndergraduate;
        }
        }