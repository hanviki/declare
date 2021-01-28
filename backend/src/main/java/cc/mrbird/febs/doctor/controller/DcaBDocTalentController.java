package cc.mrbird.febs.doctor.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.ExportExcelUtils;
import cc.mrbird.febs.doctor.service.IDcaBDocTalentService;
import cc.mrbird.febs.doctor.entity.DcaBDocTalent;

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
@RequestMapping("dcaBDocTalent")

public class DcaBDocTalentController extends BaseController{

private String message;
@Autowired
public IDcaBDocTalentService iDcaBDocTalentService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBDocTalent 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, DcaBDocTalent dcaBDocTalent){
        return getDataTable(this.iDcaBDocTalentService.findDcaBDocTalents(request, dcaBDocTalent));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBDocTalent dcaBDocTalent){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocTalent.setUserAccount(currentUser.getUsername());
    dcaBDocTalent.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocTalentService.findDcaBDocTalents(request, dcaBDocTalent));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBDocTalent dcaBDocTalent){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocTalent.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocTalentService.findDcaBDocTalents(request, dcaBDocTalent));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBDocTalentCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBDocTalent> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBDocTalent>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBDocTalentService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBDocTalentService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBDocTalent dcaBDocTalent:list
        ){
        if(dcaBDocTalent.getState()!=null&&dcaBDocTalent.getState().equals(3)) {
    dcaBDocTalent.setState(3);
        }
        else{
    dcaBDocTalent.setState(state);
        }
    dcaBDocTalent.setDisplayIndex(display);
        display+=1;
    dcaBDocTalent.setCreateUserId(currentUser.getUserId());
    dcaBDocTalent.setUserAccount(currentUser.getUsername());
    dcaBDocTalent.setUserAccountName(currentUser.getRealname());
        this.iDcaBDocTalentService.createDcaBDocTalent(dcaBDocTalent);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBDocTalent(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBDocTalent dcaBDocTalent= JSON.parseObject(jsonStr, new TypeReference<DcaBDocTalent>() {
        });
    dcaBDocTalent.setState(state);
    /**
        if (auditState >= 0) {
        if(state==2){
    dcaBDocTalent.setAuditState(0);
        }
        else {
    dcaBDocTalent.setAuditState(auditState+1);
        }

        }*/
    dcaBDocTalent.setAuditMan(currentUser.getUsername());
    dcaBDocTalent.setAuditManName(currentUser.getRealname());
    dcaBDocTalent.setAuditDate(DateUtil.date());
        this.iDcaBDocTalentService.updateDcaBDocTalent(dcaBDocTalent);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBDocTalent
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBDocTalent(@Valid DcaBDocTalent dcaBDocTalent)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocTalent.setCreateUserId(currentUser.getUserId());
    dcaBDocTalent.setUserAccount(currentUser.getUsername());
        this.iDcaBDocTalentService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBDocTalentService.createDcaBDocTalent(dcaBDocTalent);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBDocTalent
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBDocTalent:update")
public void updateDcaBDocTalent(@Valid DcaBDocTalent dcaBDocTalent)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocTalent.setModifyUserId(currentUser.getUserId());
        this.iDcaBDocTalentService.updateDcaBDocTalent(dcaBDocTalent);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBDocTalent:delete")
public void deleteDcaBDocTalents(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBDocTalentService.deleteDcaBDocTalents(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@PostMapping("excel")
public void export(QueryRequest request, DcaBDocTalent dcaBDocTalent,String dataJson,HttpServletResponse response)throws FebsException{
        try{
        request.setPageNum(1);
        request.setPageSize(10000);
        User currentUser = FebsUtil.getCurrentUser();

    dcaBDocTalent.setIsDeletemark(1);
        request.setSortField("user_account asc,state ");
        request.setSortOrder("ascend");
        List<DcaBDocTalent> dcaBDocTalentList=  this.iDcaBDocTalentService.findDcaBDocTalents(request, dcaBDocTalent).getRecords();
        //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
        ExportExcelUtils.exportCustomExcel_han(response, dcaBDocTalentList,dataJson,"");
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@GetMapping("/{id}")
public DcaBDocTalent detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBDocTalent dcaBDocTalent=this.iDcaBDocTalentService.getById(id);
        return dcaBDocTalent;
        }
        }