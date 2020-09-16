package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.service.IDcaBTeacherqualifyService;
import cc.mrbird.febs.dca.entity.DcaBTeacherqualify;

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
 * @since 2020-09-15
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBTeacherqualify")

public class DcaBTeacherqualifyController extends BaseController{

private String message;
@Autowired
public IDcaBTeacherqualifyService iDcaBTeacherqualifyService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBTeacherqualify 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBTeacherqualify:view")
public Map<String, Object> List(QueryRequest request, DcaBTeacherqualify dcaBTeacherqualify){
        return getDataTable(this.iDcaBTeacherqualifyService.findDcaBTeacherqualifys(request, dcaBTeacherqualify));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBTeacherqualify dcaBTeacherqualify){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBTeacherqualify.setUserAccount(currentUser.getUsername());
    dcaBTeacherqualify.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("state");
        request.setSortOrder("descend");
        return getDataTable(this.iDcaBTeacherqualifyService.findDcaBTeacherqualifys(request, dcaBTeacherqualify));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBTeacherqualify dcaBTeacherqualify){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBTeacherqualify.setIsDeletemark(1);
        request.setSortField("state");
        request.setSortOrder("descend");
        return getDataTable(this.iDcaBTeacherqualifyService.findDcaBTeacherqualifys(request, dcaBTeacherqualify));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBTeacherqualifyCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBTeacherqualify> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBTeacherqualify>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBTeacherqualifyService.deleteByuseraccount(currentUser.getUsername());
        for(DcaBTeacherqualify dcaBTeacherqualify:list
        ){
        if(dcaBTeacherqualify.getState()!=null&&dcaBTeacherqualify.getState().equals(3)) {
    dcaBTeacherqualify.setState(3);
        }
        else{
    dcaBTeacherqualify.setState(state);
        }
    dcaBTeacherqualify.setCreateUserId(currentUser.getUserId());
    dcaBTeacherqualify.setUserAccount(currentUser.getUsername());
    dcaBTeacherqualify.setUserAccountName(currentUser.getRealname());
        this.iDcaBTeacherqualifyService.createDcaBTeacherqualify(dcaBTeacherqualify);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBTeacherqualify(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBTeacherqualify dcaBTeacherqualify= JSON.parseObject(jsonStr, new TypeReference<DcaBTeacherqualify>() {
        });
    dcaBTeacherqualify.setState(state);
    dcaBTeacherqualify.setAuditMan(currentUser.getUsername());
    dcaBTeacherqualify.setAuditManName(currentUser.getRealname());
    dcaBTeacherqualify.setAuditDate(DateUtil.date());
        this.iDcaBTeacherqualifyService.updateDcaBTeacherqualify(dcaBTeacherqualify);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBTeacherqualify
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBTeacherqualify(@Valid DcaBTeacherqualify dcaBTeacherqualify)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBTeacherqualify.setCreateUserId(currentUser.getUserId());
    dcaBTeacherqualify.setUserAccount(currentUser.getUsername());
        this.iDcaBTeacherqualifyService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBTeacherqualifyService.createDcaBTeacherqualify(dcaBTeacherqualify);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBTeacherqualify
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBTeacherqualify:update")
public void updateDcaBTeacherqualify(@Valid DcaBTeacherqualify dcaBTeacherqualify)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBTeacherqualify.setModifyUserId(currentUser.getUserId());
        this.iDcaBTeacherqualifyService.updateDcaBTeacherqualify(dcaBTeacherqualify);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBTeacherqualify:delete")
public void deleteDcaBTeacherqualifys(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBTeacherqualifyService.deleteDcaBTeacherqualifys(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBTeacherqualify:export")
public void export(QueryRequest request, DcaBTeacherqualify dcaBTeacherqualify,HttpServletResponse response)throws FebsException{
        try{
        List<DcaBTeacherqualify> dcaBTeacherqualifys=this.iDcaBTeacherqualifyService.findDcaBTeacherqualifys(request, dcaBTeacherqualify).getRecords();
        ExcelKit.$Export(DcaBTeacherqualify.class,response).downXlsx(dcaBTeacherqualifys,false);
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBTeacherqualify detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBTeacherqualify dcaBTeacherqualify=this.iDcaBTeacherqualifyService.getById(id);
        return dcaBTeacherqualify;
        }
        }