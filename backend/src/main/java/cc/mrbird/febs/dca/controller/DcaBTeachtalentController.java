package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.service.IDcaBTeachtalentService;
import cc.mrbird.febs.dca.entity.DcaBTeachtalent;

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
 * @since 2020-09-27
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBTeachtalent")

public class DcaBTeachtalentController extends BaseController{

private String message;
@Autowired
public IDcaBTeachtalentService iDcaBTeachtalentService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBTeachtalent 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBTeachtalent:view")
public Map<String, Object> List(QueryRequest request, DcaBTeachtalent dcaBTeachtalent){
        return getDataTable(this.iDcaBTeachtalentService.findDcaBTeachtalents(request, dcaBTeachtalent));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBTeachtalent dcaBTeachtalent){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBTeachtalent.setUserAccount(currentUser.getUsername());
    dcaBTeachtalent.setIsDeletemark(1);
        request.setPageSize(100);
    request.setSortField("display_Index");
    request.setSortOrder("ascend");
        return getDataTable(this.iDcaBTeachtalentService.findDcaBTeachtalents(request, dcaBTeachtalent));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBTeachtalent dcaBTeachtalent){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBTeachtalent.setIsDeletemark(1);
        request.setSortField("state");
        request.setSortOrder("descend");
        return getDataTable(this.iDcaBTeachtalentService.findDcaBTeachtalents(request, dcaBTeachtalent));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBTeachtalentCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBTeachtalent> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBTeachtalent>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBTeachtalentService.deleteByuseraccount(currentUser.getUsername());
            int displayIndex=1;
        for(DcaBTeachtalent dcaBTeachtalent:list
        ){
        if(dcaBTeachtalent.getState()!=null&&dcaBTeachtalent.getState().equals(3)) {
    dcaBTeachtalent.setState(3);
        }
        else{
    dcaBTeachtalent.setState(state);
        }
            dcaBTeachtalent.setDisplayIndex(displayIndex);
            displayIndex+=1;
    dcaBTeachtalent.setCreateUserId(currentUser.getUserId());
    dcaBTeachtalent.setUserAccount(currentUser.getUsername());
    dcaBTeachtalent.setUserAccountName(currentUser.getRealname());
        this.iDcaBTeachtalentService.createDcaBTeachtalent(dcaBTeachtalent);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBTeachtalent(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBTeachtalent dcaBTeachtalent= JSON.parseObject(jsonStr, new TypeReference<DcaBTeachtalent>() {
        });
    dcaBTeachtalent.setState(state);
    dcaBTeachtalent.setAuditMan(currentUser.getUsername());
    dcaBTeachtalent.setAuditManName(currentUser.getRealname());
    dcaBTeachtalent.setAuditDate(DateUtil.date());
        this.iDcaBTeachtalentService.updateDcaBTeachtalent(dcaBTeachtalent);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBTeachtalent
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBTeachtalent(@Valid DcaBTeachtalent dcaBTeachtalent)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBTeachtalent.setCreateUserId(currentUser.getUserId());
    dcaBTeachtalent.setUserAccount(currentUser.getUsername());
        this.iDcaBTeachtalentService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBTeachtalentService.createDcaBTeachtalent(dcaBTeachtalent);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBTeachtalent
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBTeachtalent:update")
public void updateDcaBTeachtalent(@Valid DcaBTeachtalent dcaBTeachtalent)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBTeachtalent.setModifyUserId(currentUser.getUserId());
        this.iDcaBTeachtalentService.updateDcaBTeachtalent(dcaBTeachtalent);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBTeachtalent:delete")
public void deleteDcaBTeachtalents(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBTeachtalentService.deleteDcaBTeachtalents(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBTeachtalent:export")
public void export(QueryRequest request, DcaBTeachtalent dcaBTeachtalent,HttpServletResponse response)throws FebsException{
        try{
        List<DcaBTeachtalent> dcaBTeachtalents=this.iDcaBTeachtalentService.findDcaBTeachtalents(request, dcaBTeachtalent).getRecords();
        ExcelKit.$Export(DcaBTeachtalent.class,response).downXlsx(dcaBTeachtalents,false);
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBTeachtalent detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBTeachtalent dcaBTeachtalent=this.iDcaBTeachtalentService.getById(id);
        return dcaBTeachtalent;
        }
        }