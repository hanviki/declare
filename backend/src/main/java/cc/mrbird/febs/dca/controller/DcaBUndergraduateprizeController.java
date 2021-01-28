package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.service.IDcaBUndergraduateprizeService;
import cc.mrbird.febs.dca.entity.DcaBUndergraduateprize;

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
@RequestMapping("dcaBUndergraduateprize")

public class DcaBUndergraduateprizeController extends BaseController{

private String message;
@Autowired
public IDcaBUndergraduateprizeService iDcaBUndergraduateprizeService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBUndergraduateprize 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBUndergraduateprize:view")
public Map<String, Object> List(QueryRequest request, DcaBUndergraduateprize dcaBUndergraduateprize){
        return getDataTable(this.iDcaBUndergraduateprizeService.findDcaBUndergraduateprizes(request, dcaBUndergraduateprize));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBUndergraduateprize dcaBUndergraduateprize){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBUndergraduateprize.setUserAccount(currentUser.getUsername());
    dcaBUndergraduateprize.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBUndergraduateprizeService.findDcaBUndergraduateprizes(request, dcaBUndergraduateprize));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBUndergraduateprize dcaBUndergraduateprize){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBUndergraduateprize.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBUndergraduateprizeService.findDcaBUndergraduateprizes(request, dcaBUndergraduateprize));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBUndergraduateprizeCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBUndergraduateprize> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBUndergraduateprize>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBUndergraduateprizeService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBUndergraduateprizeService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBUndergraduateprize dcaBUndergraduateprize:list
        ){
        if(dcaBUndergraduateprize.getState()!=null&&dcaBUndergraduateprize.getState().equals(3)) {
    dcaBUndergraduateprize.setState(3);
        }
        else{
    dcaBUndergraduateprize.setState(state);
        }
    dcaBUndergraduateprize.setDisplayIndex(display);
        display+=1;
    dcaBUndergraduateprize.setCreateUserId(currentUser.getUserId());
    dcaBUndergraduateprize.setUserAccount(currentUser.getUsername());
    dcaBUndergraduateprize.setUserAccountName(currentUser.getRealname());
        this.iDcaBUndergraduateprizeService.createDcaBUndergraduateprize(dcaBUndergraduateprize);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBUndergraduateprize(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBUndergraduateprize dcaBUndergraduateprize= JSON.parseObject(jsonStr, new TypeReference<DcaBUndergraduateprize>() {
        });
    dcaBUndergraduateprize.setState(state);
    dcaBUndergraduateprize.setAuditMan(currentUser.getUsername());
    dcaBUndergraduateprize.setAuditManName(currentUser.getRealname());
    dcaBUndergraduateprize.setAuditDate(DateUtil.date());
        this.iDcaBUndergraduateprizeService.updateDcaBUndergraduateprize(dcaBUndergraduateprize);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBUndergraduateprize
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBUndergraduateprize(@Valid DcaBUndergraduateprize dcaBUndergraduateprize)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBUndergraduateprize.setCreateUserId(currentUser.getUserId());
    dcaBUndergraduateprize.setUserAccount(currentUser.getUsername());
        this.iDcaBUndergraduateprizeService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBUndergraduateprizeService.createDcaBUndergraduateprize(dcaBUndergraduateprize);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBUndergraduateprize
 * @return
 */
@Log("修改")
@PutMapping
public void updateDcaBUndergraduateprize(@Valid DcaBUndergraduateprize dcaBUndergraduateprize)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBUndergraduateprize.setModifyUserId(currentUser.getUserId());
        this.iDcaBUndergraduateprizeService.updateDcaBUndergraduateprize(dcaBUndergraduateprize);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBUndergraduateprize:delete")
public void deleteDcaBUndergraduateprizes(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBUndergraduateprizeService.deleteDcaBUndergraduateprizes(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBUndergraduateprize:export")
public void export(QueryRequest request, DcaBUndergraduateprize dcaBUndergraduateprize,HttpServletResponse response)throws FebsException{
        try{
        List<DcaBUndergraduateprize> dcaBUndergraduateprizes=this.iDcaBUndergraduateprizeService.findDcaBUndergraduateprizes(request, dcaBUndergraduateprize).getRecords();
        ExcelKit.$Export(DcaBUndergraduateprize.class,response).downXlsx(dcaBUndergraduateprizes,false);
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBUndergraduateprize detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBUndergraduateprize dcaBUndergraduateprize=this.iDcaBUndergraduateprizeService.getById(id);
        return dcaBUndergraduateprize;
        }
        }