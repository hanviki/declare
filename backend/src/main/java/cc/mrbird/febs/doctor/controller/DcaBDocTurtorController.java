package cc.mrbird.febs.doctor.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.common.utils.ExportExcelUtils;

import cc.mrbird.febs.doctor.service.IDcaBDocTurtorService;
import cc.mrbird.febs.doctor.entity.DcaBDocTurtor;

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
@RequestMapping("dcaBDocTurtor")

public class DcaBDocTurtorController extends BaseController{

private String message;
@Autowired
public IDcaBDocTurtorService iDcaBDocTurtorService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBDocTurtor 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, DcaBDocTurtor dcaBDocTurtor){
        return getDataTable(this.iDcaBDocTurtorService.findDcaBDocTurtors(request, dcaBDocTurtor));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBDocTurtor dcaBDocTurtor){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocTurtor.setUserAccount(currentUser.getUsername());
    dcaBDocTurtor.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocTurtorService.findDcaBDocTurtors(request, dcaBDocTurtor));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBDocTurtor dcaBDocTurtor){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocTurtor.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocTurtorService.findDcaBDocTurtors(request, dcaBDocTurtor));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBDocTurtorCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBDocTurtor> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBDocTurtor>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBDocTurtorService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBDocTurtorService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBDocTurtor dcaBDocTurtor:list
        ){
        if(dcaBDocTurtor.getState()!=null&&dcaBDocTurtor.getState().equals(3)) {
    dcaBDocTurtor.setState(3);
        }
        else{
    dcaBDocTurtor.setState(state);
        }
    dcaBDocTurtor.setDisplayIndex(display);
        display+=1;
    dcaBDocTurtor.setCreateUserId(currentUser.getUserId());
    dcaBDocTurtor.setUserAccount(currentUser.getUsername());
    dcaBDocTurtor.setUserAccountName(currentUser.getRealname());
        this.iDcaBDocTurtorService.createDcaBDocTurtor(dcaBDocTurtor);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBDocTurtor(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBDocTurtor dcaBDocTurtor= JSON.parseObject(jsonStr, new TypeReference<DcaBDocTurtor>() {
        });
    dcaBDocTurtor.setState(state);
    /**
        if (auditState >= 0) {
        if(state==2){
    dcaBDocTurtor.setAuditState(0);
        }
        else {
    dcaBDocTurtor.setAuditState(auditState+1);
        }

        }*/
    dcaBDocTurtor.setAuditMan(currentUser.getUsername());
    dcaBDocTurtor.setAuditManName(currentUser.getRealname());
    dcaBDocTurtor.setAuditDate(DateUtil.date());
        this.iDcaBDocTurtorService.updateDcaBDocTurtor(dcaBDocTurtor);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBDocTurtor
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBDocTurtor(@Valid DcaBDocTurtor dcaBDocTurtor)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocTurtor.setCreateUserId(currentUser.getUserId());
    dcaBDocTurtor.setUserAccount(currentUser.getUsername());
        this.iDcaBDocTurtorService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBDocTurtorService.createDcaBDocTurtor(dcaBDocTurtor);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBDocTurtor
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBDocTurtor:update")
public void updateDcaBDocTurtor(@Valid DcaBDocTurtor dcaBDocTurtor)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocTurtor.setModifyUserId(currentUser.getUserId());
        this.iDcaBDocTurtorService.updateDcaBDocTurtor(dcaBDocTurtor);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBDocTurtor:delete")
public void deleteDcaBDocTurtors(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBDocTurtorService.deleteDcaBDocTurtors(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@PostMapping("excel")
public void export(QueryRequest request, DcaBDocTurtor dcaBDocTurtor,String dataJson,HttpServletResponse response)throws FebsException{
        try{
        request.setPageNum(1);
        request.setPageSize(10000);
        User currentUser = FebsUtil.getCurrentUser();

    dcaBDocTurtor.setIsDeletemark(1);
        request.setSortField("user_account asc,state ");
        request.setSortOrder("ascend");
        List<DcaBDocTurtor> dcaBDocTurtorList=  this.iDcaBDocTurtorService.findDcaBDocTurtors(request, dcaBDocTurtor).getRecords();
        //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
        ExportExcelUtils.exportCustomExcel_han(response, dcaBDocTurtorList,dataJson,"");
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@GetMapping("/{id}")
public DcaBDocTurtor detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBDocTurtor dcaBDocTurtor=this.iDcaBDocTurtorService.getById(id);
        return dcaBDocTurtor;
        }
        }