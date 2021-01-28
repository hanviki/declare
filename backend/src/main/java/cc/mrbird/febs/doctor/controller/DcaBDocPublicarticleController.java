package cc.mrbird.febs.doctor.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.ExportExcelUtils;

import cc.mrbird.febs.doctor.service.IDcaBDocPublicarticleService;
import cc.mrbird.febs.doctor.entity.DcaBDocPublicarticle;

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
@RequestMapping("dcaBDocPublicarticle")

public class DcaBDocPublicarticleController extends BaseController{

private String message;
@Autowired
public IDcaBDocPublicarticleService iDcaBDocPublicarticleService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBDocPublicarticle 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, DcaBDocPublicarticle dcaBDocPublicarticle){
        return getDataTable(this.iDcaBDocPublicarticleService.findDcaBDocPublicarticles(request, dcaBDocPublicarticle));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBDocPublicarticle dcaBDocPublicarticle){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocPublicarticle.setUserAccount(currentUser.getUsername());
    dcaBDocPublicarticle.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocPublicarticleService.findDcaBDocPublicarticles(request, dcaBDocPublicarticle));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBDocPublicarticle dcaBDocPublicarticle){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocPublicarticle.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocPublicarticleService.findDcaBDocPublicarticles(request, dcaBDocPublicarticle));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBDocPublicarticleCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBDocPublicarticle> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBDocPublicarticle>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBDocPublicarticleService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBDocPublicarticleService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBDocPublicarticle dcaBDocPublicarticle:list
        ){
        if(dcaBDocPublicarticle.getState()!=null&&dcaBDocPublicarticle.getState().equals(3)) {
    dcaBDocPublicarticle.setState(3);
        }
        else{
    dcaBDocPublicarticle.setState(state);
        }
    dcaBDocPublicarticle.setDisplayIndex(display);
        display+=1;
    dcaBDocPublicarticle.setCreateUserId(currentUser.getUserId());
    dcaBDocPublicarticle.setUserAccount(currentUser.getUsername());
    dcaBDocPublicarticle.setUserAccountName(currentUser.getRealname());
        this.iDcaBDocPublicarticleService.createDcaBDocPublicarticle(dcaBDocPublicarticle);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBDocPublicarticle(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBDocPublicarticle dcaBDocPublicarticle= JSON.parseObject(jsonStr, new TypeReference<DcaBDocPublicarticle>() {
        });
    dcaBDocPublicarticle.setState(state);
    /**
        if (auditState >= 0) {
        if(state==2){
    dcaBDocPublicarticle.setAuditState(0);
        }
        else {
    dcaBDocPublicarticle.setAuditState(auditState+1);
        }

        }*/
    dcaBDocPublicarticle.setAuditMan(currentUser.getUsername());
    dcaBDocPublicarticle.setAuditManName(currentUser.getRealname());
    dcaBDocPublicarticle.setAuditDate(DateUtil.date());
        this.iDcaBDocPublicarticleService.updateDcaBDocPublicarticle(dcaBDocPublicarticle);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBDocPublicarticle
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBDocPublicarticle(@Valid DcaBDocPublicarticle dcaBDocPublicarticle)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocPublicarticle.setCreateUserId(currentUser.getUserId());
    dcaBDocPublicarticle.setUserAccount(currentUser.getUsername());
        this.iDcaBDocPublicarticleService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBDocPublicarticleService.createDcaBDocPublicarticle(dcaBDocPublicarticle);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBDocPublicarticle
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBDocPublicarticle:update")
public void updateDcaBDocPublicarticle(@Valid DcaBDocPublicarticle dcaBDocPublicarticle)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocPublicarticle.setModifyUserId(currentUser.getUserId());
        this.iDcaBDocPublicarticleService.updateDcaBDocPublicarticle(dcaBDocPublicarticle);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBDocPublicarticle:delete")
public void deleteDcaBDocPublicarticles(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBDocPublicarticleService.deleteDcaBDocPublicarticles(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@PostMapping("excel")
public void export(QueryRequest request, DcaBDocPublicarticle dcaBDocPublicarticle,String dataJson,HttpServletResponse response)throws FebsException{
        try{
        request.setPageNum(1);
        request.setPageSize(10000);
        User currentUser = FebsUtil.getCurrentUser();

    dcaBDocPublicarticle.setIsDeletemark(1);
        request.setSortField("user_account asc,state ");
        request.setSortOrder("ascend");
        List<DcaBDocPublicarticle> dcaBDocPublicarticleList=  this.iDcaBDocPublicarticleService.findDcaBDocPublicarticles(request, dcaBDocPublicarticle).getRecords();
        //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
        ExportExcelUtils.exportCustomExcel_han(response, dcaBDocPublicarticleList,dataJson,"");
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@GetMapping("/{id}")
public DcaBDocPublicarticle detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBDocPublicarticle dcaBDocPublicarticle=this.iDcaBDocPublicarticleService.getById(id);
        return dcaBDocPublicarticle;
        }
        }