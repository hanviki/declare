package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.service.IDcaBSchoolprizeService;
import cc.mrbird.febs.dca.entity.DcaBSchoolprize;

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
 * @since 2020-11-03
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBSchoolprize")

public class DcaBSchoolprizeController extends BaseController{

private String message;
@Autowired
public IDcaBSchoolprizeService iDcaBSchoolprizeService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBSchoolprize 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBSchoolprize:view")
public Map<String, Object> List(QueryRequest request, DcaBSchoolprize dcaBSchoolprize){
        return getDataTable(this.iDcaBSchoolprizeService.findDcaBSchoolprizes(request, dcaBSchoolprize));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBSchoolprize dcaBSchoolprize){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBSchoolprize.setUserAccount(currentUser.getUsername());
    dcaBSchoolprize.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBSchoolprizeService.findDcaBSchoolprizes(request, dcaBSchoolprize));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBSchoolprize dcaBSchoolprize){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBSchoolprize.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBSchoolprizeService.findDcaBSchoolprizes(request, dcaBSchoolprize));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBSchoolprizeCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBSchoolprize> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBSchoolprize>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBSchoolprizeService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBSchoolprizeService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBSchoolprize dcaBSchoolprize:list
        ){
        if(dcaBSchoolprize.getState()!=null&&dcaBSchoolprize.getState().equals(3)) {
    dcaBSchoolprize.setState(3);
        }
        else{
    dcaBSchoolprize.setState(state);
        }
    dcaBSchoolprize.setDisplayIndex(display);
        display+=1;
    dcaBSchoolprize.setCreateUserId(currentUser.getUserId());
    dcaBSchoolprize.setUserAccount(currentUser.getUsername());
    dcaBSchoolprize.setUserAccountName(currentUser.getRealname());
        this.iDcaBSchoolprizeService.createDcaBSchoolprize(dcaBSchoolprize);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBSchoolprize(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBSchoolprize dcaBSchoolprize= JSON.parseObject(jsonStr, new TypeReference<DcaBSchoolprize>() {
        });
    dcaBSchoolprize.setState(state);
    dcaBSchoolprize.setAuditMan(currentUser.getUsername());
    dcaBSchoolprize.setAuditManName(currentUser.getRealname());
    dcaBSchoolprize.setAuditDate(DateUtil.date());
        this.iDcaBSchoolprizeService.updateDcaBSchoolprize(dcaBSchoolprize);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBSchoolprize
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBSchoolprize(@Valid DcaBSchoolprize dcaBSchoolprize)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBSchoolprize.setCreateUserId(currentUser.getUserId());
    dcaBSchoolprize.setUserAccount(currentUser.getUsername());
        this.iDcaBSchoolprizeService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBSchoolprizeService.createDcaBSchoolprize(dcaBSchoolprize);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBSchoolprize
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBSchoolprize:update")
public void updateDcaBSchoolprize(@Valid DcaBSchoolprize dcaBSchoolprize)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBSchoolprize.setModifyUserId(currentUser.getUserId());
        this.iDcaBSchoolprizeService.updateDcaBSchoolprize(dcaBSchoolprize);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBSchoolprize:delete")
public void deleteDcaBSchoolprizes(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBSchoolprizeService.deleteDcaBSchoolprizes(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBSchoolprize:export")
public void export(QueryRequest request, DcaBSchoolprize dcaBSchoolprize,HttpServletResponse response)throws FebsException{
        try{
        List<DcaBSchoolprize> dcaBSchoolprizes=this.iDcaBSchoolprizeService.findDcaBSchoolprizes(request, dcaBSchoolprize).getRecords();
        ExcelKit.$Export(DcaBSchoolprize.class,response).downXlsx(dcaBSchoolprizes,false);
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBSchoolprize detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBSchoolprize dcaBSchoolprize=this.iDcaBSchoolprizeService.getById(id);
        return dcaBSchoolprize;
        }
        }