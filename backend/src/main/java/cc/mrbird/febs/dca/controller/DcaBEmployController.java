package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.service.IDcaBEmployService;
import cc.mrbird.febs.dca.entity.DcaBEmploy;

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
@RequestMapping("dcaBEmploy")

public class DcaBEmployController extends BaseController{

private String message;
@Autowired
public IDcaBEmployService iDcaBEmployService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBEmploy 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBEmploy:view")
public Map<String, Object> List(QueryRequest request, DcaBEmploy dcaBEmploy){
        return getDataTable(this.iDcaBEmployService.findDcaBEmploys(request, dcaBEmploy));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBEmploy dcaBEmploy){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBEmploy.setUserAccount(currentUser.getUsername());
    dcaBEmploy.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("state");
        request.setSortOrder("descend");
        return getDataTable(this.iDcaBEmployService.findDcaBEmploys(request, dcaBEmploy));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBEmploy dcaBEmploy){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBEmploy.setIsDeletemark(1);
        request.setSortField("state");
        request.setSortOrder("descend");
        return getDataTable(this.iDcaBEmployService.findDcaBEmploys(request, dcaBEmploy));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBEmployCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBEmploy> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBEmploy>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBEmployService.deleteByuseraccount(currentUser.getUsername());
        for(DcaBEmploy dcaBEmploy:list
        ){
        if(dcaBEmploy.getState()!=null&&dcaBEmploy.getState().equals(3)) {
    dcaBEmploy.setState(3);
        }
        else{
    dcaBEmploy.setState(state);
        }
    dcaBEmploy.setCreateUserId(currentUser.getUserId());
    dcaBEmploy.setUserAccount(currentUser.getUsername());
    dcaBEmploy.setUserAccountName(currentUser.getRealname());
        this.iDcaBEmployService.createDcaBEmploy(dcaBEmploy);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBEmploy(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBEmploy dcaBEmploy= JSON.parseObject(jsonStr, new TypeReference<DcaBEmploy>() {
        });
    dcaBEmploy.setState(state);
    dcaBEmploy.setAuditMan(currentUser.getUsername());
    dcaBEmploy.setAuditManName(currentUser.getRealname());
    dcaBEmploy.setAuditDate(DateUtil.date());
        this.iDcaBEmployService.updateDcaBEmploy(dcaBEmploy);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBEmploy
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBEmploy(@Valid DcaBEmploy dcaBEmploy)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBEmploy.setCreateUserId(currentUser.getUserId());
    dcaBEmploy.setUserAccount(currentUser.getUsername());
        this.iDcaBEmployService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBEmployService.createDcaBEmploy(dcaBEmploy);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBEmploy
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBEmploy:update")
public void updateDcaBEmploy(@Valid DcaBEmploy dcaBEmploy)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBEmploy.setModifyUserId(currentUser.getUserId());
        this.iDcaBEmployService.updateDcaBEmploy(dcaBEmploy);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBEmploy:delete")
public void deleteDcaBEmploys(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBEmployService.deleteDcaBEmploys(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBEmploy:export")
public void export(QueryRequest request, DcaBEmploy dcaBEmploy,HttpServletResponse response)throws FebsException{
        try{
        List<DcaBEmploy> dcaBEmploys=this.iDcaBEmployService.findDcaBEmploys(request, dcaBEmploy).getRecords();
        ExcelKit.$Export(DcaBEmploy.class,response).downXlsx(dcaBEmploys,false);
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBEmploy detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBEmploy dcaBEmploy=this.iDcaBEmployService.getById(id);
        return dcaBEmploy;
        }
        }