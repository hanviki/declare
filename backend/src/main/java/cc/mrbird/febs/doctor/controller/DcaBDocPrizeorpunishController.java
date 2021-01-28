package cc.mrbird.febs.doctor.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.ExportExcelUtils;

import cc.mrbird.febs.doctor.service.IDcaBDocPrizeorpunishService;
import cc.mrbird.febs.doctor.entity.DcaBDocPrizeorpunish;

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
 * @since 2021-01-13
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBDocPrizeorpunish")

public class DcaBDocPrizeorpunishController extends BaseController{

private String message;
@Autowired
public IDcaBDocPrizeorpunishService iDcaBDocPrizeorpunishService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBDocPrizeorpunish 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, DcaBDocPrizeorpunish dcaBDocPrizeorpunish){
        return getDataTable(this.iDcaBDocPrizeorpunishService.findDcaBDocPrizeorpunishs(request, dcaBDocPrizeorpunish));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBDocPrizeorpunish dcaBDocPrizeorpunish){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocPrizeorpunish.setUserAccount(currentUser.getUsername());
    dcaBDocPrizeorpunish.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocPrizeorpunishService.findDcaBDocPrizeorpunishs(request, dcaBDocPrizeorpunish));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBDocPrizeorpunish dcaBDocPrizeorpunish){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocPrizeorpunish.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocPrizeorpunishService.findDcaBDocPrizeorpunishs(request, dcaBDocPrizeorpunish));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBDocPrizeorpunishCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBDocPrizeorpunish> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBDocPrizeorpunish>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBDocPrizeorpunishService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBDocPrizeorpunishService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBDocPrizeorpunish dcaBDocPrizeorpunish:list
        ){
        if(dcaBDocPrizeorpunish.getState()!=null&&dcaBDocPrizeorpunish.getState().equals(3)) {
    dcaBDocPrizeorpunish.setState(3);
        }
        else{
    dcaBDocPrizeorpunish.setState(state);
        }
    dcaBDocPrizeorpunish.setDisplayIndex(display);
        display+=1;
    dcaBDocPrizeorpunish.setCreateUserId(currentUser.getUserId());
    dcaBDocPrizeorpunish.setUserAccount(currentUser.getUsername());
    dcaBDocPrizeorpunish.setUserAccountName(currentUser.getRealname());
        this.iDcaBDocPrizeorpunishService.createDcaBDocPrizeorpunish(dcaBDocPrizeorpunish);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBDocPrizeorpunish(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBDocPrizeorpunish dcaBDocPrizeorpunish= JSON.parseObject(jsonStr, new TypeReference<DcaBDocPrizeorpunish>() {
        });
    dcaBDocPrizeorpunish.setState(state);
    /**
        if (auditState >= 0) {
        if(state==2){
    dcaBDocPrizeorpunish.setAuditState(0);
        }
        else {
    dcaBDocPrizeorpunish.setAuditState(auditState+1);
        }

        }*/
    dcaBDocPrizeorpunish.setAuditMan(currentUser.getUsername());
    dcaBDocPrizeorpunish.setAuditManName(currentUser.getRealname());
    dcaBDocPrizeorpunish.setAuditDate(DateUtil.date());
        this.iDcaBDocPrizeorpunishService.updateDcaBDocPrizeorpunish(dcaBDocPrizeorpunish);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBDocPrizeorpunish
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBDocPrizeorpunish(@Valid DcaBDocPrizeorpunish dcaBDocPrizeorpunish)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocPrizeorpunish.setCreateUserId(currentUser.getUserId());
    dcaBDocPrizeorpunish.setUserAccount(currentUser.getUsername());
        this.iDcaBDocPrizeorpunishService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBDocPrizeorpunishService.createDcaBDocPrizeorpunish(dcaBDocPrizeorpunish);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBDocPrizeorpunish
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBDocPrizeorpunish:update")
public void updateDcaBDocPrizeorpunish(@Valid DcaBDocPrizeorpunish dcaBDocPrizeorpunish)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocPrizeorpunish.setModifyUserId(currentUser.getUserId());
        this.iDcaBDocPrizeorpunishService.updateDcaBDocPrizeorpunish(dcaBDocPrizeorpunish);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBDocPrizeorpunish:delete")
public void deleteDcaBDocPrizeorpunishs(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBDocPrizeorpunishService.deleteDcaBDocPrizeorpunishs(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@PostMapping("excel")
public void export(QueryRequest request, DcaBDocPrizeorpunish dcaBDocPrizeorpunish,String dataJson,HttpServletResponse response)throws FebsException{
        try{
        request.setPageNum(1);
        request.setPageSize(10000);
        User currentUser = FebsUtil.getCurrentUser();

    dcaBDocPrizeorpunish.setIsDeletemark(1);
        request.setSortField("user_account asc,state ");
        request.setSortOrder("ascend");
        List<DcaBDocPrizeorpunish> dcaBDocPrizeorpunishList=  this.iDcaBDocPrizeorpunishService.findDcaBDocPrizeorpunishs(request, dcaBDocPrizeorpunish).getRecords();
        //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
        ExportExcelUtils.exportCustomExcel_han(response, dcaBDocPrizeorpunishList,dataJson,"");
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@GetMapping("/{id}")
public DcaBDocPrizeorpunish detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBDocPrizeorpunish dcaBDocPrizeorpunish=this.iDcaBDocPrizeorpunishService.getById(id);
        return dcaBDocPrizeorpunish;
        }
        }