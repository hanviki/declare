package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.service.IDcaBApplyjobService;
import cc.mrbird.febs.dca.entity.DcaBApplyjob;

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
@RequestMapping("dcaBApplyjob")

public class DcaBApplyjobController extends BaseController{

private String message;
@Autowired
public IDcaBApplyjobService iDcaBApplyjobService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBApplyjob 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBApplyjob:view")
public Map<String, Object> List(QueryRequest request, DcaBApplyjob dcaBApplyjob){
        return getDataTable(this.iDcaBApplyjobService.findDcaBApplyjobs(request, dcaBApplyjob));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBApplyjob dcaBApplyjob){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBApplyjob.setUserAccount(currentUser.getUsername());
    dcaBApplyjob.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("state");
        request.setSortOrder("descend");
        return getDataTable(this.iDcaBApplyjobService.findDcaBApplyjobs(request, dcaBApplyjob));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBApplyjob dcaBApplyjob){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBApplyjob.setIsDeletemark(1);
        request.setSortField("state");
        request.setSortOrder("descend");
        return getDataTable(this.iDcaBApplyjobService.findDcaBApplyjobs(request, dcaBApplyjob));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBApplyjobCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBApplyjob> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBApplyjob>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBApplyjobService.deleteByuseraccount(currentUser.getUsername());
        for(DcaBApplyjob dcaBApplyjob:list
        ){
        if(dcaBApplyjob.getState()!=null&&dcaBApplyjob.getState().equals(3)) {
    dcaBApplyjob.setState(3);
        }
        else{
    dcaBApplyjob.setState(state);
        }
    dcaBApplyjob.setCreateUserId(currentUser.getUserId());
    dcaBApplyjob.setUserAccount(currentUser.getUsername());
    dcaBApplyjob.setUserAccountName(currentUser.getRealname());
        this.iDcaBApplyjobService.createDcaBApplyjob(dcaBApplyjob);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBApplyjob(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBApplyjob dcaBApplyjob= JSON.parseObject(jsonStr, new TypeReference<DcaBApplyjob>() {
        });
    dcaBApplyjob.setState(state);
    dcaBApplyjob.setAuditMan(currentUser.getUsername());
    dcaBApplyjob.setAuditManName(currentUser.getRealname());
    dcaBApplyjob.setAuditDate(DateUtil.date());
        this.iDcaBApplyjobService.updateDcaBApplyjob(dcaBApplyjob);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBApplyjob
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBApplyjob(@Valid DcaBApplyjob dcaBApplyjob)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBApplyjob.setCreateUserId(currentUser.getUserId());
    dcaBApplyjob.setUserAccount(currentUser.getUsername());
        this.iDcaBApplyjobService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBApplyjobService.createDcaBApplyjob(dcaBApplyjob);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBApplyjob
 * @return
 */
@Log("修改")
@PutMapping
public void updateDcaBApplyjob(@Valid DcaBApplyjob dcaBApplyjob)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBApplyjob.setModifyUserId(currentUser.getUserId());
        this.iDcaBApplyjobService.updateDcaBApplyjob(dcaBApplyjob);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBApplyjob:delete")
public void deleteDcaBApplyjobs(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBApplyjobService.deleteDcaBApplyjobs(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBApplyjob:export")
public void export(QueryRequest request, DcaBApplyjob dcaBApplyjob,HttpServletResponse response)throws FebsException{
        try{
        List<DcaBApplyjob> dcaBApplyjobs=this.iDcaBApplyjobService.findDcaBApplyjobs(request, dcaBApplyjob).getRecords();
        ExcelKit.$Export(DcaBApplyjob.class,response).downXlsx(dcaBApplyjobs,false);
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBApplyjob detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBApplyjob dcaBApplyjob=this.iDcaBApplyjobService.getById(id);
        return dcaBApplyjob;
        }
        }