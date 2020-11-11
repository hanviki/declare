package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.service.IDcaBAttachfileService;
import cc.mrbird.febs.dca.entity.DcaBAttachfile;

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
@RequestMapping("dcaBAttachfile")

public class DcaBAttachfileController extends BaseController{

private String message;
@Autowired
public IDcaBAttachfileService iDcaBAttachfileService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBAttachfile 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBAttachfile:view")
public Map<String, Object> List(QueryRequest request, DcaBAttachfile dcaBAttachfile){
        return getDataTable(this.iDcaBAttachfileService.findDcaBAttachfiles(request, dcaBAttachfile));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBAttachfile dcaBAttachfile){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBAttachfile.setUserAccount(currentUser.getUsername());
    dcaBAttachfile.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBAttachfileService.findDcaBAttachfiles(request, dcaBAttachfile));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBAttachfile dcaBAttachfile){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBAttachfile.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBAttachfileService.findDcaBAttachfiles(request, dcaBAttachfile));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBAttachfileCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBAttachfile> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBAttachfile>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBAttachfileService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBAttachfileService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBAttachfile dcaBAttachfile:list
        ){
        if(dcaBAttachfile.getState()!=null&&dcaBAttachfile.getState().equals(3)) {
    dcaBAttachfile.setState(3);
        }
        else{
    dcaBAttachfile.setState(state);
        }
    dcaBAttachfile.setDisplayIndex(display);
        display+=1;
    dcaBAttachfile.setCreateUserId(currentUser.getUserId());
    dcaBAttachfile.setUserAccount(currentUser.getUsername());
    dcaBAttachfile.setUserAccountName(currentUser.getRealname());
        this.iDcaBAttachfileService.createDcaBAttachfile(dcaBAttachfile);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBAttachfile(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBAttachfile dcaBAttachfile= JSON.parseObject(jsonStr, new TypeReference<DcaBAttachfile>() {
        });
    dcaBAttachfile.setState(state);
    dcaBAttachfile.setAuditMan(currentUser.getUsername());
    dcaBAttachfile.setAuditManName(currentUser.getRealname());
    dcaBAttachfile.setAuditDate(DateUtil.date());
        this.iDcaBAttachfileService.updateDcaBAttachfile(dcaBAttachfile);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBAttachfile
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBAttachfile(@Valid DcaBAttachfile dcaBAttachfile)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBAttachfile.setCreateUserId(currentUser.getUserId());
    dcaBAttachfile.setUserAccount(currentUser.getUsername());
        this.iDcaBAttachfileService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBAttachfileService.createDcaBAttachfile(dcaBAttachfile);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBAttachfile
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBAttachfile:update")
public void updateDcaBAttachfile(@Valid DcaBAttachfile dcaBAttachfile)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBAttachfile.setModifyUserId(currentUser.getUserId());
        this.iDcaBAttachfileService.updateDcaBAttachfile(dcaBAttachfile);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBAttachfile:delete")
public void deleteDcaBAttachfiles(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBAttachfileService.deleteDcaBAttachfiles(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBAttachfile:export")
public void export(QueryRequest request, DcaBAttachfile dcaBAttachfile,HttpServletResponse response)throws FebsException{
        try{
        List<DcaBAttachfile> dcaBAttachfiles=this.iDcaBAttachfileService.findDcaBAttachfiles(request, dcaBAttachfile).getRecords();
        ExcelKit.$Export(DcaBAttachfile.class,response).downXlsx(dcaBAttachfiles,false);
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBAttachfile detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBAttachfile dcaBAttachfile=this.iDcaBAttachfileService.getById(id);
        return dcaBAttachfile;
        }
        }