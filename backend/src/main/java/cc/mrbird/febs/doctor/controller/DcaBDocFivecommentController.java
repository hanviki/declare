package cc.mrbird.febs.doctor.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.doctor.service.IDcaBDocFivecommentService;
import cc.mrbird.febs.doctor.entity.DcaBDocFivecomment;
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
@RequestMapping("dcaBDocFivecomment")

public class DcaBDocFivecommentController extends BaseController{

private String message;
@Autowired
public IDcaBDocFivecommentService iDcaBDocFivecommentService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBDocFivecomment 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, DcaBDocFivecomment dcaBDocFivecomment){
        return getDataTable(this.iDcaBDocFivecommentService.findDcaBDocFivecomments(request, dcaBDocFivecomment));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBDocFivecomment dcaBDocFivecomment){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocFivecomment.setUserAccount(currentUser.getUsername());
    dcaBDocFivecomment.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocFivecommentService.findDcaBDocFivecomments(request, dcaBDocFivecomment));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBDocFivecomment dcaBDocFivecomment){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocFivecomment.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocFivecommentService.findDcaBDocFivecomments(request, dcaBDocFivecomment));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBDocFivecommentCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBDocFivecomment> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBDocFivecomment>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBDocFivecommentService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBDocFivecommentService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBDocFivecomment dcaBDocFivecomment:list
        ){
        if(dcaBDocFivecomment.getState()!=null&&dcaBDocFivecomment.getState().equals(3)) {
    dcaBDocFivecomment.setState(3);
        }
        else{
    dcaBDocFivecomment.setState(state);
        }
    dcaBDocFivecomment.setDisplayIndex(display);
        display+=1;
    dcaBDocFivecomment.setCreateUserId(currentUser.getUserId());
    dcaBDocFivecomment.setUserAccount(currentUser.getUsername());
    dcaBDocFivecomment.setUserAccountName(currentUser.getRealname());
        this.iDcaBDocFivecommentService.createDcaBDocFivecomment(dcaBDocFivecomment);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBDocFivecomment(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBDocFivecomment dcaBDocFivecomment= JSON.parseObject(jsonStr, new TypeReference<DcaBDocFivecomment>() {
        });
    dcaBDocFivecomment.setState(state);
    /**
        if (auditState >= 0) {
        if(state==2){
    dcaBDocFivecomment.setAuditState(0);
        }
        else {
    dcaBDocFivecomment.setAuditState(auditState+1);
        }

        }*/
    dcaBDocFivecomment.setAuditMan(currentUser.getUsername());
    dcaBDocFivecomment.setAuditManName(currentUser.getRealname());
    dcaBDocFivecomment.setAuditDate(DateUtil.date());
        this.iDcaBDocFivecommentService.updateDcaBDocFivecomment(dcaBDocFivecomment);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBDocFivecomment
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBDocFivecomment(@Valid DcaBDocFivecomment dcaBDocFivecomment)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocFivecomment.setCreateUserId(currentUser.getUserId());
    dcaBDocFivecomment.setUserAccount(currentUser.getUsername());
        this.iDcaBDocFivecommentService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBDocFivecommentService.createDcaBDocFivecomment(dcaBDocFivecomment);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBDocFivecomment
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBDocFivecomment:update")
public void updateDcaBDocFivecomment(@Valid DcaBDocFivecomment dcaBDocFivecomment)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocFivecomment.setModifyUserId(currentUser.getUserId());
        this.iDcaBDocFivecommentService.updateDcaBDocFivecomment(dcaBDocFivecomment);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBDocFivecomment:delete")
public void deleteDcaBDocFivecomments(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBDocFivecommentService.deleteDcaBDocFivecomments(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@PostMapping("excel")
public void export(QueryRequest request, DcaBDocFivecomment dcaBDocFivecomment,String dataJson,HttpServletResponse response)throws FebsException{
        try{
        request.setPageNum(1);
        request.setPageSize(10000);
        User currentUser = FebsUtil.getCurrentUser();

    dcaBDocFivecomment.setIsDeletemark(1);
        request.setSortField("user_account asc,state ");
        request.setSortOrder("ascend");
        List<DcaBDocFivecomment> dcaBDocFivecommentList=  this.iDcaBDocFivecommentService.findDcaBDocFivecomments(request, dcaBDocFivecomment).getRecords();
        //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
        ExportExcelUtils.exportCustomExcel_han(response, dcaBDocFivecommentList,dataJson,"");
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@GetMapping("/{id}")
public DcaBDocFivecomment detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBDocFivecomment dcaBDocFivecomment=this.iDcaBDocFivecommentService.getById(id);
        return dcaBDocFivecomment;
        }
        }