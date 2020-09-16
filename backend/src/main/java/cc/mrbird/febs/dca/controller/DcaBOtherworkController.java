package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.service.IDcaBOtherworkService;
import cc.mrbird.febs.dca.entity.DcaBOtherwork;

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
@RequestMapping("dcaBOtherwork")

public class DcaBOtherworkController extends BaseController{

private String message;
@Autowired
public IDcaBOtherworkService iDcaBOtherworkService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBOtherwork 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBOtherwork:view")
public Map<String, Object> List(QueryRequest request, DcaBOtherwork dcaBOtherwork){
        return getDataTable(this.iDcaBOtherworkService.findDcaBOtherworks(request, dcaBOtherwork));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBOtherwork dcaBOtherwork){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBOtherwork.setUserAccount(currentUser.getUsername());
    dcaBOtherwork.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("state");
        request.setSortOrder("descend");
        return getDataTable(this.iDcaBOtherworkService.findDcaBOtherworks(request, dcaBOtherwork));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBOtherwork dcaBOtherwork){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBOtherwork.setIsDeletemark(1);
        request.setSortField("state");
        request.setSortOrder("descend");
        return getDataTable(this.iDcaBOtherworkService.findDcaBOtherworks(request, dcaBOtherwork));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBOtherworkCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBOtherwork> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBOtherwork>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBOtherworkService.deleteByuseraccount(currentUser.getUsername());
        for(DcaBOtherwork dcaBOtherwork:list
        ){
        if(dcaBOtherwork.getState()!=null&&dcaBOtherwork.getState().equals(3)) {
    dcaBOtherwork.setState(3);
        }
        else{
    dcaBOtherwork.setState(state);
        }
    dcaBOtherwork.setCreateUserId(currentUser.getUserId());
    dcaBOtherwork.setUserAccount(currentUser.getUsername());
    dcaBOtherwork.setUserAccountName(currentUser.getRealname());
        this.iDcaBOtherworkService.createDcaBOtherwork(dcaBOtherwork);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBOtherwork(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBOtherwork dcaBOtherwork= JSON.parseObject(jsonStr, new TypeReference<DcaBOtherwork>() {
        });
    dcaBOtherwork.setState(state);
    dcaBOtherwork.setAuditMan(currentUser.getUsername());
    dcaBOtherwork.setAuditManName(currentUser.getRealname());
    dcaBOtherwork.setAuditDate(DateUtil.date());
        this.iDcaBOtherworkService.updateDcaBOtherwork(dcaBOtherwork);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBOtherwork
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBOtherwork(@Valid DcaBOtherwork dcaBOtherwork)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBOtherwork.setCreateUserId(currentUser.getUserId());
    dcaBOtherwork.setUserAccount(currentUser.getUsername());
        this.iDcaBOtherworkService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBOtherworkService.createDcaBOtherwork(dcaBOtherwork);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBOtherwork
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBOtherwork:update")
public void updateDcaBOtherwork(@Valid DcaBOtherwork dcaBOtherwork)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBOtherwork.setModifyUserId(currentUser.getUserId());
        this.iDcaBOtherworkService.updateDcaBOtherwork(dcaBOtherwork);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBOtherwork:delete")
public void deleteDcaBOtherworks(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBOtherworkService.deleteDcaBOtherworks(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBOtherwork:export")
public void export(QueryRequest request, DcaBOtherwork dcaBOtherwork,HttpServletResponse response)throws FebsException{
        try{
        List<DcaBOtherwork> dcaBOtherworks=this.iDcaBOtherworkService.findDcaBOtherworks(request, dcaBOtherwork).getRecords();
        ExcelKit.$Export(DcaBOtherwork.class,response).downXlsx(dcaBOtherworks,false);
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBOtherwork detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBOtherwork dcaBOtherwork=this.iDcaBOtherworkService.getById(id);
        return dcaBOtherwork;
        }
        }