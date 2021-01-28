package cc.mrbird.febs.doctor.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.doctor.service.IDcaBDocGraduateService;
import cc.mrbird.febs.doctor.entity.DcaBDocGraduate;
import cc.mrbird.febs.common.utils.ExportExcelUtils;

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
@RequestMapping("dcaBDocGraduate")

public class DcaBDocGraduateController extends BaseController{

private String message;
@Autowired
public IDcaBDocGraduateService iDcaBDocGraduateService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBDocGraduate 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, DcaBDocGraduate dcaBDocGraduate){
        return getDataTable(this.iDcaBDocGraduateService.findDcaBDocGraduates(request, dcaBDocGraduate));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBDocGraduate dcaBDocGraduate){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocGraduate.setUserAccount(currentUser.getUsername());
    dcaBDocGraduate.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocGraduateService.findDcaBDocGraduates(request, dcaBDocGraduate));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBDocGraduate dcaBDocGraduate){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocGraduate.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocGraduateService.findDcaBDocGraduates(request, dcaBDocGraduate));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBDocGraduateCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBDocGraduate> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBDocGraduate>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBDocGraduateService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBDocGraduateService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBDocGraduate dcaBDocGraduate:list
        ){
        if(dcaBDocGraduate.getState()!=null&&dcaBDocGraduate.getState().equals(3)) {
    dcaBDocGraduate.setState(3);
        }
        else{
    dcaBDocGraduate.setState(state);
        }
    dcaBDocGraduate.setDisplayIndex(display);
        display+=1;
    dcaBDocGraduate.setCreateUserId(currentUser.getUserId());
    dcaBDocGraduate.setUserAccount(currentUser.getUsername());
    dcaBDocGraduate.setUserAccountName(currentUser.getRealname());
        this.iDcaBDocGraduateService.createDcaBDocGraduate(dcaBDocGraduate);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBDocGraduate(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBDocGraduate dcaBDocGraduate= JSON.parseObject(jsonStr, new TypeReference<DcaBDocGraduate>() {
        });
    dcaBDocGraduate.setState(state);
    /**
        if (auditState >= 0) {
        if(state==2){
    dcaBDocGraduate.setAuditState(0);
        }
        else {
    dcaBDocGraduate.setAuditState(auditState+1);
        }

        }*/
    dcaBDocGraduate.setAuditMan(currentUser.getUsername());
    dcaBDocGraduate.setAuditManName(currentUser.getRealname());
    dcaBDocGraduate.setAuditDate(DateUtil.date());
        this.iDcaBDocGraduateService.updateDcaBDocGraduate(dcaBDocGraduate);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBDocGraduate
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBDocGraduate(@Valid DcaBDocGraduate dcaBDocGraduate)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocGraduate.setCreateUserId(currentUser.getUserId());
    dcaBDocGraduate.setUserAccount(currentUser.getUsername());
        this.iDcaBDocGraduateService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBDocGraduateService.createDcaBDocGraduate(dcaBDocGraduate);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBDocGraduate
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBDocGraduate:update")
public void updateDcaBDocGraduate(@Valid DcaBDocGraduate dcaBDocGraduate)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocGraduate.setModifyUserId(currentUser.getUserId());
        this.iDcaBDocGraduateService.updateDcaBDocGraduate(dcaBDocGraduate);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBDocGraduate:delete")
public void deleteDcaBDocGraduates(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBDocGraduateService.deleteDcaBDocGraduates(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@PostMapping("excel")
public void export(QueryRequest request, DcaBDocGraduate dcaBDocGraduate,String dataJson,HttpServletResponse response)throws FebsException{
        try{
        request.setPageNum(1);
        request.setPageSize(10000);
        User currentUser = FebsUtil.getCurrentUser();

    dcaBDocGraduate.setIsDeletemark(1);
        request.setSortField("user_account asc,state ");
        request.setSortOrder("ascend");
        List<DcaBDocGraduate> dcaBDocGraduateList=  this.iDcaBDocGraduateService.findDcaBDocGraduates(request, dcaBDocGraduate).getRecords();
        //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
        ExportExcelUtils.exportCustomExcel_han(response, dcaBDocGraduateList,dataJson,"");
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@GetMapping("/{id}")
public DcaBDocGraduate detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBDocGraduate dcaBDocGraduate=this.iDcaBDocGraduateService.getById(id);
        return dcaBDocGraduate;
        }
        }