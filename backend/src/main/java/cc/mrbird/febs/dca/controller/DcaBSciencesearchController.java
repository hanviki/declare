package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.common.utils.ExportExcelUtils;
import cc.mrbird.febs.dca.service.IDcaBSciencesearchService;
import cc.mrbird.febs.dca.entity.DcaBSciencesearch;

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
 * @since 2020-11-06
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBSciencesearch")

public class DcaBSciencesearchController extends BaseController{

private String message;
@Autowired
public IDcaBSciencesearchService iDcaBSciencesearchService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBSciencesearch 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBSciencesearch:view")
public Map<String, Object> List(QueryRequest request, DcaBSciencesearch dcaBSciencesearch){
        return getDataTable(this.iDcaBSciencesearchService.findDcaBSciencesearchs(request, dcaBSciencesearch));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBSciencesearch dcaBSciencesearch){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBSciencesearch.setUserAccount(currentUser.getUsername());
    dcaBSciencesearch.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBSciencesearchService.findDcaBSciencesearchs(request, dcaBSciencesearch));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBSciencesearch dcaBSciencesearch){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBSciencesearch.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBSciencesearchService.findDcaBSciencesearchs(request, dcaBSciencesearch));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBSciencesearchCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBSciencesearch> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBSciencesearch>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBSciencesearchService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBSciencesearchService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBSciencesearch dcaBSciencesearch:list
        ){
        if(dcaBSciencesearch.getState()!=null&&dcaBSciencesearch.getState().equals(3)) {
    dcaBSciencesearch.setState(3);
        }
        else{
    dcaBSciencesearch.setState(state);
        }
    dcaBSciencesearch.setDisplayIndex(display);
        display+=1;
    dcaBSciencesearch.setCreateUserId(currentUser.getUserId());
    dcaBSciencesearch.setUserAccount(currentUser.getUsername());
    dcaBSciencesearch.setUserAccountName(currentUser.getRealname());
        this.iDcaBSciencesearchService.createDcaBSciencesearch(dcaBSciencesearch);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBSciencesearch(@Valid String jsonStr ,int state,int auditState  )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBSciencesearch dcaBSciencesearch= JSON.parseObject(jsonStr, new TypeReference<DcaBSciencesearch>() {
        });
    dcaBSciencesearch.setState(state);
        if (auditState >= 0) {
        if(state==2){
    dcaBSciencesearch.setAuditState(0);
        }
        else {
    dcaBSciencesearch.setAuditState(auditState+1);
        }

        }
    dcaBSciencesearch.setAuditMan(currentUser.getUsername());
    dcaBSciencesearch.setAuditManName(currentUser.getRealname());
    dcaBSciencesearch.setAuditDate(DateUtil.date());
        this.iDcaBSciencesearchService.updateDcaBSciencesearch(dcaBSciencesearch);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBSciencesearch
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBSciencesearch(@Valid DcaBSciencesearch dcaBSciencesearch)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBSciencesearch.setCreateUserId(currentUser.getUserId());
    dcaBSciencesearch.setUserAccount(currentUser.getUsername());
        this.iDcaBSciencesearchService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBSciencesearchService.createDcaBSciencesearch(dcaBSciencesearch);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBSciencesearch
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBSciencesearch:update")
public void updateDcaBSciencesearch(@Valid DcaBSciencesearch dcaBSciencesearch)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBSciencesearch.setModifyUserId(currentUser.getUserId());
        this.iDcaBSciencesearchService.updateDcaBSciencesearch(dcaBSciencesearch);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBSciencesearch:delete")
public void deleteDcaBSciencesearchs(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBSciencesearchService.deleteDcaBSciencesearchs(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

    @PostMapping("excel")
    public void export(QueryRequest request, DcaBSciencesearch dcaBSciencesearch,String dataJson,HttpServletResponse response)throws FebsException{
        try{
            request.setPageNum(1);
            request.setPageSize(10000);
            User currentUser = FebsUtil.getCurrentUser();

            dcaBSciencesearch.setIsDeletemark(1);
            request.setSortField("user_account asc,state asc,display_Index");
            request.setSortOrder("ascend");
            List<DcaBSciencesearch> dcaBSciencepublishList=  this.iDcaBSciencesearchService.findDcaBSciencesearchs(request, dcaBSciencesearch).getRecords();


            //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
            ExportExcelUtils.exportCustomExcel_han(response, dcaBSciencepublishList,dataJson,"");
        }catch(Exception e){
            message="导出Excel失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }
@GetMapping("/{id}")
public DcaBSciencesearch detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBSciencesearch dcaBSciencesearch=this.iDcaBSciencesearchService.getById(id);
        return dcaBSciencesearch;
        }
        }