package cc.mrbird.febs.doctor.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.doctor.service.IDcaBDocParttimejobService;
import cc.mrbird.febs.doctor.entity.DcaBDocParttimejob;
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
@RequestMapping("dcaBDocParttimejob")

public class DcaBDocParttimejobController extends BaseController{

private String message;
@Autowired
public IDcaBDocParttimejobService iDcaBDocParttimejobService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBDocParttimejob 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, DcaBDocParttimejob dcaBDocParttimejob){
        return getDataTable(this.iDcaBDocParttimejobService.findDcaBDocParttimejobs(request, dcaBDocParttimejob));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBDocParttimejob dcaBDocParttimejob){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocParttimejob.setUserAccount(currentUser.getUsername());
    dcaBDocParttimejob.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocParttimejobService.findDcaBDocParttimejobs(request, dcaBDocParttimejob));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBDocParttimejob dcaBDocParttimejob){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocParttimejob.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocParttimejobService.findDcaBDocParttimejobs(request, dcaBDocParttimejob));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBDocParttimejobCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBDocParttimejob> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBDocParttimejob>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBDocParttimejobService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBDocParttimejobService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBDocParttimejob dcaBDocParttimejob:list
        ){
        if(dcaBDocParttimejob.getState()!=null&&dcaBDocParttimejob.getState().equals(3)) {
    dcaBDocParttimejob.setState(3);
        }
        else{
    dcaBDocParttimejob.setState(state);
        }
    dcaBDocParttimejob.setDisplayIndex(display);
        display+=1;
    dcaBDocParttimejob.setCreateUserId(currentUser.getUserId());
    dcaBDocParttimejob.setUserAccount(currentUser.getUsername());
    dcaBDocParttimejob.setUserAccountName(currentUser.getRealname());
        this.iDcaBDocParttimejobService.createDcaBDocParttimejob(dcaBDocParttimejob);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBDocParttimejob(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBDocParttimejob dcaBDocParttimejob= JSON.parseObject(jsonStr, new TypeReference<DcaBDocParttimejob>() {
        });
    dcaBDocParttimejob.setState(state);
    /**
        if (auditState >= 0) {
        if(state==2){
    dcaBDocParttimejob.setAuditState(0);
        }
        else {
    dcaBDocParttimejob.setAuditState(auditState+1);
        }

        }*/
    dcaBDocParttimejob.setAuditMan(currentUser.getUsername());
    dcaBDocParttimejob.setAuditManName(currentUser.getRealname());
    dcaBDocParttimejob.setAuditDate(DateUtil.date());
        this.iDcaBDocParttimejobService.updateDcaBDocParttimejob(dcaBDocParttimejob);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBDocParttimejob
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBDocParttimejob(@Valid DcaBDocParttimejob dcaBDocParttimejob)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocParttimejob.setCreateUserId(currentUser.getUserId());
    dcaBDocParttimejob.setUserAccount(currentUser.getUsername());
        this.iDcaBDocParttimejobService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBDocParttimejobService.createDcaBDocParttimejob(dcaBDocParttimejob);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBDocParttimejob
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBDocParttimejob:update")
public void updateDcaBDocParttimejob(@Valid DcaBDocParttimejob dcaBDocParttimejob)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocParttimejob.setModifyUserId(currentUser.getUserId());
        this.iDcaBDocParttimejobService.updateDcaBDocParttimejob(dcaBDocParttimejob);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBDocParttimejob:delete")
public void deleteDcaBDocParttimejobs(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBDocParttimejobService.deleteDcaBDocParttimejobs(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@PostMapping("excel")
public void export(QueryRequest request, DcaBDocParttimejob dcaBDocParttimejob,String dataJson,HttpServletResponse response)throws FebsException{
        try{
        request.setPageNum(1);
        request.setPageSize(10000);
        User currentUser = FebsUtil.getCurrentUser();

    dcaBDocParttimejob.setIsDeletemark(1);
        request.setSortField("user_account asc,state ");
        request.setSortOrder("ascend");
        List<DcaBDocParttimejob> dcaBDocParttimejobList=  this.iDcaBDocParttimejobService.findDcaBDocParttimejobs(request, dcaBDocParttimejob).getRecords();
        //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
        ExportExcelUtils.exportCustomExcel_han(response, dcaBDocParttimejobList,dataJson,"");
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@GetMapping("/{id}")
public DcaBDocParttimejob detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBDocParttimejob dcaBDocParttimejob=this.iDcaBDocParttimejobService.getById(id);
        return dcaBDocParttimejob;
        }
        }