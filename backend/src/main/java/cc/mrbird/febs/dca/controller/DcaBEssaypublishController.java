package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.service.IDcaBEssaypublishService;
import cc.mrbird.febs.dca.entity.DcaBEssaypublish;

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
@RequestMapping("dcaBEssaypublish")

public class DcaBEssaypublishController extends BaseController{

private String message;
@Autowired
public IDcaBEssaypublishService iDcaBEssaypublishService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBEssaypublish 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBEssaypublish:view")
public Map<String, Object> List(QueryRequest request, DcaBEssaypublish dcaBEssaypublish){
        return getDataTable(this.iDcaBEssaypublishService.findDcaBEssaypublishs(request, dcaBEssaypublish));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBEssaypublish dcaBEssaypublish){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBEssaypublish.setUserAccount(currentUser.getUsername());
    dcaBEssaypublish.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("state");
        request.setSortOrder("descend");
        return getDataTable(this.iDcaBEssaypublishService.findDcaBEssaypublishs(request, dcaBEssaypublish));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBEssaypublish dcaBEssaypublish){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBEssaypublish.setIsDeletemark(1);
        request.setSortField("state");
        request.setSortOrder("descend");
        return getDataTable(this.iDcaBEssaypublishService.findDcaBEssaypublishs(request, dcaBEssaypublish));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBEssaypublishCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBEssaypublish> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBEssaypublish>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBEssaypublishService.deleteByuseraccount(currentUser.getUsername());
        for(DcaBEssaypublish dcaBEssaypublish:list
        ){
        if(dcaBEssaypublish.getState()!=null&&dcaBEssaypublish.getState().equals(3)) {
    dcaBEssaypublish.setState(3);
        }
        else{
    dcaBEssaypublish.setState(state);
        }
    dcaBEssaypublish.setCreateUserId(currentUser.getUserId());
    dcaBEssaypublish.setUserAccount(currentUser.getUsername());
    dcaBEssaypublish.setUserAccountName(currentUser.getRealname());
        this.iDcaBEssaypublishService.createDcaBEssaypublish(dcaBEssaypublish);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBEssaypublish(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBEssaypublish dcaBEssaypublish= JSON.parseObject(jsonStr, new TypeReference<DcaBEssaypublish>() {
        });
    dcaBEssaypublish.setState(state);
    dcaBEssaypublish.setAuditMan(currentUser.getUsername());
    dcaBEssaypublish.setAuditManName(currentUser.getRealname());
    dcaBEssaypublish.setAuditDate(DateUtil.date());
        this.iDcaBEssaypublishService.updateDcaBEssaypublish(dcaBEssaypublish);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBEssaypublish
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBEssaypublish(@Valid DcaBEssaypublish dcaBEssaypublish)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBEssaypublish.setCreateUserId(currentUser.getUserId());
    dcaBEssaypublish.setUserAccount(currentUser.getUsername());
        this.iDcaBEssaypublishService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBEssaypublishService.createDcaBEssaypublish(dcaBEssaypublish);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBEssaypublish
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBEssaypublish:update")
public void updateDcaBEssaypublish(@Valid DcaBEssaypublish dcaBEssaypublish)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBEssaypublish.setModifyUserId(currentUser.getUserId());
        this.iDcaBEssaypublishService.updateDcaBEssaypublish(dcaBEssaypublish);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBEssaypublish:delete")
public void deleteDcaBEssaypublishs(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBEssaypublishService.deleteDcaBEssaypublishs(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBEssaypublish:export")
public void export(QueryRequest request, DcaBEssaypublish dcaBEssaypublish,HttpServletResponse response)throws FebsException{
        try{
        List<DcaBEssaypublish> dcaBEssaypublishs=this.iDcaBEssaypublishService.findDcaBEssaypublishs(request, dcaBEssaypublish).getRecords();
        ExcelKit.$Export(DcaBEssaypublish.class,response).downXlsx(dcaBEssaypublishs,false);
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBEssaypublish detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBEssaypublish dcaBEssaypublish=this.iDcaBEssaypublishService.getById(id);
        return dcaBEssaypublish;
        }
        }