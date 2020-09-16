package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.service.IDcaBTalentService;
import cc.mrbird.febs.dca.entity.DcaBTalent;

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
@RequestMapping("dcaBTalent")

public class DcaBTalentController extends BaseController{

private String message;
@Autowired
public IDcaBTalentService iDcaBTalentService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBTalent 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBTalent:view")
public Map<String, Object> List(QueryRequest request, DcaBTalent dcaBTalent){
        return getDataTable(this.iDcaBTalentService.findDcaBTalents(request, dcaBTalent));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBTalent dcaBTalent){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBTalent.setUserAccount(currentUser.getUsername());
    dcaBTalent.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("state");
        request.setSortOrder("descend");
        return getDataTable(this.iDcaBTalentService.findDcaBTalents(request, dcaBTalent));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBTalent dcaBTalent){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBTalent.setIsDeletemark(1);
        request.setSortField("state");
        request.setSortOrder("descend");
        return getDataTable(this.iDcaBTalentService.findDcaBTalents(request, dcaBTalent));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBTalentCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBTalent> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBTalent>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBTalentService.deleteByuseraccount(currentUser.getUsername());
        for(DcaBTalent dcaBTalent:list
        ){
        if(dcaBTalent.getState()!=null&&dcaBTalent.getState().equals(3)) {
    dcaBTalent.setState(3);
        }
        else{
    dcaBTalent.setState(state);
        }
    dcaBTalent.setCreateUserId(currentUser.getUserId());
    dcaBTalent.setUserAccount(currentUser.getUsername());
    dcaBTalent.setUserAccountName(currentUser.getRealname());
        this.iDcaBTalentService.createDcaBTalent(dcaBTalent);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBTalent(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBTalent dcaBTalent= JSON.parseObject(jsonStr, new TypeReference<DcaBTalent>() {
        });
    dcaBTalent.setState(state);
    dcaBTalent.setAuditMan(currentUser.getUsername());
    dcaBTalent.setAuditManName(currentUser.getRealname());
    dcaBTalent.setAuditDate(DateUtil.date());
        this.iDcaBTalentService.updateDcaBTalent(dcaBTalent);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBTalent
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBTalent(@Valid DcaBTalent dcaBTalent)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBTalent.setCreateUserId(currentUser.getUserId());
    dcaBTalent.setUserAccount(currentUser.getUsername());
        this.iDcaBTalentService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBTalentService.createDcaBTalent(dcaBTalent);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBTalent
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBTalent:update")
public void updateDcaBTalent(@Valid DcaBTalent dcaBTalent)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBTalent.setModifyUserId(currentUser.getUserId());
        this.iDcaBTalentService.updateDcaBTalent(dcaBTalent);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBTalent:delete")
public void deleteDcaBTalents(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBTalentService.deleteDcaBTalents(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBTalent:export")
public void export(QueryRequest request, DcaBTalent dcaBTalent,HttpServletResponse response)throws FebsException{
        try{
        List<DcaBTalent> dcaBTalents=this.iDcaBTalentService.findDcaBTalents(request, dcaBTalent).getRecords();
        ExcelKit.$Export(DcaBTalent.class,response).downXlsx(dcaBTalents,false);
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBTalent detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBTalent dcaBTalent=this.iDcaBTalentService.getById(id);
        return dcaBTalent;
        }
        }