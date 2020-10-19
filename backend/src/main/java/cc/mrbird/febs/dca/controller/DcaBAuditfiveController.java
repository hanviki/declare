package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.service.IDcaBAuditfiveService;
import cc.mrbird.febs.dca.entity.DcaBAuditfive;

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
 * @since 2020-10-19
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBAuditfive")

public class DcaBAuditfiveController extends BaseController{

private String message;
@Autowired
public IDcaBAuditfiveService iDcaBAuditfiveService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBAuditfive 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBAuditfive:view")
public Map<String, Object> List(QueryRequest request, DcaBAuditfive dcaBAuditfive){
        return getDataTable(this.iDcaBAuditfiveService.findDcaBAuditfives(request, dcaBAuditfive));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBAuditfive dcaBAuditfive){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBAuditfive.setUserAccount(currentUser.getUsername());
    dcaBAuditfive.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("state");
        request.setSortOrder("descend");
        return getDataTable(this.iDcaBAuditfiveService.findDcaBAuditfives(request, dcaBAuditfive));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBAuditfive dcaBAuditfive){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBAuditfive.setIsDeletemark(1);
        request.setSortField("state");
        request.setSortOrder("descend");
        return getDataTable(this.iDcaBAuditfiveService.findDcaBAuditfives(request, dcaBAuditfive));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBAuditfiveCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBAuditfive> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBAuditfive>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBAuditfiveService.deleteByuseraccount(currentUser.getUsername());
        for(DcaBAuditfive dcaBAuditfive:list
        ){
        if(dcaBAuditfive.getState()!=null&&dcaBAuditfive.getState().equals(3)) {
    dcaBAuditfive.setState(3);
        }
        else{
    dcaBAuditfive.setState(state);
        }
    dcaBAuditfive.setCreateUserId(currentUser.getUserId());
    dcaBAuditfive.setUserAccount(currentUser.getUsername());
    dcaBAuditfive.setUserAccountName(currentUser.getRealname());
        this.iDcaBAuditfiveService.createDcaBAuditfive(dcaBAuditfive);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBAuditfive(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBAuditfive dcaBAuditfive= JSON.parseObject(jsonStr, new TypeReference<DcaBAuditfive>() {
        });
    dcaBAuditfive.setState(state);
    dcaBAuditfive.setAuditMan(currentUser.getUsername());
    dcaBAuditfive.setAuditManName(currentUser.getRealname());
    dcaBAuditfive.setAuditDate(DateUtil.date());
        this.iDcaBAuditfiveService.updateDcaBAuditfive(dcaBAuditfive);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBAuditfive
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBAuditfive(@Valid DcaBAuditfive dcaBAuditfive)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBAuditfive.setCreateUserId(currentUser.getUserId());
    dcaBAuditfive.setUserAccount(currentUser.getUsername());
        this.iDcaBAuditfiveService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBAuditfiveService.createDcaBAuditfive(dcaBAuditfive);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBAuditfive
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBAuditfive:update")
public void updateDcaBAuditfive(@Valid DcaBAuditfive dcaBAuditfive)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBAuditfive.setModifyUserId(currentUser.getUserId());
        this.iDcaBAuditfiveService.updateDcaBAuditfive(dcaBAuditfive);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBAuditfive:delete")
public void deleteDcaBAuditfives(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBAuditfiveService.deleteDcaBAuditfives(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBAuditfive:export")
public void export(QueryRequest request, DcaBAuditfive dcaBAuditfive,HttpServletResponse response)throws FebsException{
        try{
        List<DcaBAuditfive> dcaBAuditfives=this.iDcaBAuditfiveService.findDcaBAuditfives(request, dcaBAuditfive).getRecords();
        ExcelKit.$Export(DcaBAuditfive.class,response).downXlsx(dcaBAuditfives,false);
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBAuditfive detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBAuditfive dcaBAuditfive=this.iDcaBAuditfiveService.getById(id);
        return dcaBAuditfive;
        }
        }