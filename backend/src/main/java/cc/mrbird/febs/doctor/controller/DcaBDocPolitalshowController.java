package cc.mrbird.febs.doctor.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.ExportExcelUtils;

import cc.mrbird.febs.doctor.service.IDcaBDocPolitalshowService;
import cc.mrbird.febs.doctor.entity.DcaBDocPolitalshow;

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
@RequestMapping("dcaBDocPolitalshow")

public class DcaBDocPolitalshowController extends BaseController{

private String message;
@Autowired
public IDcaBDocPolitalshowService iDcaBDocPolitalshowService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBDocPolitalshow 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, DcaBDocPolitalshow dcaBDocPolitalshow){
        return getDataTable(this.iDcaBDocPolitalshowService.findDcaBDocPolitalshows(request, dcaBDocPolitalshow));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBDocPolitalshow dcaBDocPolitalshow){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocPolitalshow.setUserAccount(currentUser.getUsername());
    dcaBDocPolitalshow.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocPolitalshowService.findDcaBDocPolitalshows(request, dcaBDocPolitalshow));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBDocPolitalshow dcaBDocPolitalshow){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocPolitalshow.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocPolitalshowService.findDcaBDocPolitalshows(request, dcaBDocPolitalshow));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBDocPolitalshowCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBDocPolitalshow> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBDocPolitalshow>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBDocPolitalshowService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBDocPolitalshowService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBDocPolitalshow dcaBDocPolitalshow:list
        ){
        if(dcaBDocPolitalshow.getState()!=null&&dcaBDocPolitalshow.getState().equals(3)) {
    dcaBDocPolitalshow.setState(3);
        }
        else{
    dcaBDocPolitalshow.setState(state);
        }
    dcaBDocPolitalshow.setDisplayIndex(display);
        display+=1;
    dcaBDocPolitalshow.setCreateUserId(currentUser.getUserId());
    dcaBDocPolitalshow.setUserAccount(currentUser.getUsername());
    dcaBDocPolitalshow.setUserAccountName(currentUser.getRealname());
        this.iDcaBDocPolitalshowService.createDcaBDocPolitalshow(dcaBDocPolitalshow);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBDocPolitalshow(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBDocPolitalshow dcaBDocPolitalshow= JSON.parseObject(jsonStr, new TypeReference<DcaBDocPolitalshow>() {
        });
    dcaBDocPolitalshow.setState(state);
    /**
        if (auditState >= 0) {
        if(state==2){
    dcaBDocPolitalshow.setAuditState(0);
        }
        else {
    dcaBDocPolitalshow.setAuditState(auditState+1);
        }

        }*/
    dcaBDocPolitalshow.setAuditMan(currentUser.getUsername());
    dcaBDocPolitalshow.setAuditManName(currentUser.getRealname());
    dcaBDocPolitalshow.setAuditDate(DateUtil.date());
        this.iDcaBDocPolitalshowService.updateDcaBDocPolitalshow(dcaBDocPolitalshow);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBDocPolitalshow
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBDocPolitalshow(@Valid DcaBDocPolitalshow dcaBDocPolitalshow)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocPolitalshow.setCreateUserId(currentUser.getUserId());
    dcaBDocPolitalshow.setUserAccount(currentUser.getUsername());
        this.iDcaBDocPolitalshowService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBDocPolitalshowService.createDcaBDocPolitalshow(dcaBDocPolitalshow);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBDocPolitalshow
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBDocPolitalshow:update")
public void updateDcaBDocPolitalshow(@Valid DcaBDocPolitalshow dcaBDocPolitalshow)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocPolitalshow.setModifyUserId(currentUser.getUserId());
        this.iDcaBDocPolitalshowService.updateDcaBDocPolitalshow(dcaBDocPolitalshow);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBDocPolitalshow:delete")
public void deleteDcaBDocPolitalshows(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBDocPolitalshowService.deleteDcaBDocPolitalshows(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@PostMapping("excel")
public void export(QueryRequest request, DcaBDocPolitalshow dcaBDocPolitalshow,String dataJson,HttpServletResponse response)throws FebsException{
        try{
        request.setPageNum(1);
        request.setPageSize(10000);
        User currentUser = FebsUtil.getCurrentUser();

    dcaBDocPolitalshow.setIsDeletemark(1);
        request.setSortField("user_account asc,state ");
        request.setSortOrder("ascend");
        List<DcaBDocPolitalshow> dcaBDocPolitalshowList=  this.iDcaBDocPolitalshowService.findDcaBDocPolitalshows(request, dcaBDocPolitalshow).getRecords();
        //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
        ExportExcelUtils.exportCustomExcel_han(response, dcaBDocPolitalshowList,dataJson,"");
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@GetMapping("/{id}")
public DcaBDocPolitalshow detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBDocPolitalshow dcaBDocPolitalshow=this.iDcaBDocPolitalshowService.getById(id);
        return dcaBDocPolitalshow;
        }
        }