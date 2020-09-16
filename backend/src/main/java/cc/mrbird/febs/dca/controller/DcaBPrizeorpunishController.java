package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.service.IDcaBPrizeorpunishService;
import cc.mrbird.febs.dca.entity.DcaBPrizeorpunish;

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
@RequestMapping("dcaBPrizeorpunish")

public class DcaBPrizeorpunishController extends BaseController{

private String message;
@Autowired
public IDcaBPrizeorpunishService iDcaBPrizeorpunishService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBPrizeorpunish 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBPrizeorpunish:view")
public Map<String, Object> List(QueryRequest request, DcaBPrizeorpunish dcaBPrizeorpunish){
        return getDataTable(this.iDcaBPrizeorpunishService.findDcaBPrizeorpunishs(request, dcaBPrizeorpunish));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBPrizeorpunish dcaBPrizeorpunish){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBPrizeorpunish.setUserAccount(currentUser.getUsername());
    dcaBPrizeorpunish.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("state");
        request.setSortOrder("descend");
        return getDataTable(this.iDcaBPrizeorpunishService.findDcaBPrizeorpunishs(request, dcaBPrizeorpunish));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBPrizeorpunish dcaBPrizeorpunish){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBPrizeorpunish.setIsDeletemark(1);
        request.setSortField("state");
        request.setSortOrder("descend");
        return getDataTable(this.iDcaBPrizeorpunishService.findDcaBPrizeorpunishs(request, dcaBPrizeorpunish));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBPrizeorpunishCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBPrizeorpunish> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBPrizeorpunish>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBPrizeorpunishService.deleteByuseraccount(currentUser.getUsername());
        for(DcaBPrizeorpunish dcaBPrizeorpunish:list
        ){
        if(dcaBPrizeorpunish.getState()!=null&&dcaBPrizeorpunish.getState().equals(3)) {
    dcaBPrizeorpunish.setState(3);
        }
        else{
    dcaBPrizeorpunish.setState(state);
        }
    dcaBPrizeorpunish.setCreateUserId(currentUser.getUserId());
    dcaBPrizeorpunish.setUserAccount(currentUser.getUsername());
    dcaBPrizeorpunish.setUserAccountName(currentUser.getRealname());
        this.iDcaBPrizeorpunishService.createDcaBPrizeorpunish(dcaBPrizeorpunish);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBPrizeorpunish(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBPrizeorpunish dcaBPrizeorpunish= JSON.parseObject(jsonStr, new TypeReference<DcaBPrizeorpunish>() {
        });
    dcaBPrizeorpunish.setState(state);
    dcaBPrizeorpunish.setAuditMan(currentUser.getUsername());
    dcaBPrizeorpunish.setAuditManName(currentUser.getRealname());
    dcaBPrizeorpunish.setAuditDate(DateUtil.date());
        this.iDcaBPrizeorpunishService.updateDcaBPrizeorpunish(dcaBPrizeorpunish);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBPrizeorpunish
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBPrizeorpunish(@Valid DcaBPrizeorpunish dcaBPrizeorpunish)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBPrizeorpunish.setCreateUserId(currentUser.getUserId());
    dcaBPrizeorpunish.setUserAccount(currentUser.getUsername());
        this.iDcaBPrizeorpunishService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBPrizeorpunishService.createDcaBPrizeorpunish(dcaBPrizeorpunish);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBPrizeorpunish
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBPrizeorpunish:update")
public void updateDcaBPrizeorpunish(@Valid DcaBPrizeorpunish dcaBPrizeorpunish)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBPrizeorpunish.setModifyUserId(currentUser.getUserId());
        this.iDcaBPrizeorpunishService.updateDcaBPrizeorpunish(dcaBPrizeorpunish);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBPrizeorpunish:delete")
public void deleteDcaBPrizeorpunishs(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBPrizeorpunishService.deleteDcaBPrizeorpunishs(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBPrizeorpunish:export")
public void export(QueryRequest request, DcaBPrizeorpunish dcaBPrizeorpunish,HttpServletResponse response)throws FebsException{
        try{
        List<DcaBPrizeorpunish> dcaBPrizeorpunishs=this.iDcaBPrizeorpunishService.findDcaBPrizeorpunishs(request, dcaBPrizeorpunish).getRecords();
        ExcelKit.$Export(DcaBPrizeorpunish.class,response).downXlsx(dcaBPrizeorpunishs,false);
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBPrizeorpunish detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBPrizeorpunish dcaBPrizeorpunish=this.iDcaBPrizeorpunishService.getById(id);
        return dcaBPrizeorpunish;
        }
        }