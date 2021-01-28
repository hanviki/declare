package cc.mrbird.febs.doctor.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.ExportExcelUtils;

import cc.mrbird.febs.doctor.service.IDcaBDocSchoolprizeService;
import cc.mrbird.febs.doctor.entity.DcaBDocSchoolprize;

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
@RequestMapping("dcaBDocSchoolprize")

public class DcaBDocSchoolprizeController extends BaseController{

private String message;
@Autowired
public IDcaBDocSchoolprizeService iDcaBDocSchoolprizeService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBDocSchoolprize 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, DcaBDocSchoolprize dcaBDocSchoolprize){
        return getDataTable(this.iDcaBDocSchoolprizeService.findDcaBDocSchoolprizes(request, dcaBDocSchoolprize));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBDocSchoolprize dcaBDocSchoolprize){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocSchoolprize.setUserAccount(currentUser.getUsername());
    dcaBDocSchoolprize.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocSchoolprizeService.findDcaBDocSchoolprizes(request, dcaBDocSchoolprize));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBDocSchoolprize dcaBDocSchoolprize){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocSchoolprize.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocSchoolprizeService.findDcaBDocSchoolprizes(request, dcaBDocSchoolprize));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBDocSchoolprizeCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBDocSchoolprize> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBDocSchoolprize>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBDocSchoolprizeService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBDocSchoolprizeService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBDocSchoolprize dcaBDocSchoolprize:list
        ){
        if(dcaBDocSchoolprize.getState()!=null&&dcaBDocSchoolprize.getState().equals(3)) {
    dcaBDocSchoolprize.setState(3);
        }
        else{
    dcaBDocSchoolprize.setState(state);
        }
    dcaBDocSchoolprize.setDisplayIndex(display);
        display+=1;
    dcaBDocSchoolprize.setCreateUserId(currentUser.getUserId());
    dcaBDocSchoolprize.setUserAccount(currentUser.getUsername());
    dcaBDocSchoolprize.setUserAccountName(currentUser.getRealname());
        this.iDcaBDocSchoolprizeService.createDcaBDocSchoolprize(dcaBDocSchoolprize);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBDocSchoolprize(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBDocSchoolprize dcaBDocSchoolprize= JSON.parseObject(jsonStr, new TypeReference<DcaBDocSchoolprize>() {
        });
    dcaBDocSchoolprize.setState(state);
    /**
        if (auditState >= 0) {
        if(state==2){
    dcaBDocSchoolprize.setAuditState(0);
        }
        else {
    dcaBDocSchoolprize.setAuditState(auditState+1);
        }

        }*/
    dcaBDocSchoolprize.setAuditMan(currentUser.getUsername());
    dcaBDocSchoolprize.setAuditManName(currentUser.getRealname());
    dcaBDocSchoolprize.setAuditDate(DateUtil.date());
        this.iDcaBDocSchoolprizeService.updateDcaBDocSchoolprize(dcaBDocSchoolprize);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBDocSchoolprize
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBDocSchoolprize(@Valid DcaBDocSchoolprize dcaBDocSchoolprize)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocSchoolprize.setCreateUserId(currentUser.getUserId());
    dcaBDocSchoolprize.setUserAccount(currentUser.getUsername());
        this.iDcaBDocSchoolprizeService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBDocSchoolprizeService.createDcaBDocSchoolprize(dcaBDocSchoolprize);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBDocSchoolprize
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBDocSchoolprize:update")
public void updateDcaBDocSchoolprize(@Valid DcaBDocSchoolprize dcaBDocSchoolprize)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocSchoolprize.setModifyUserId(currentUser.getUserId());
        this.iDcaBDocSchoolprizeService.updateDcaBDocSchoolprize(dcaBDocSchoolprize);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBDocSchoolprize:delete")
public void deleteDcaBDocSchoolprizes(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBDocSchoolprizeService.deleteDcaBDocSchoolprizes(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@PostMapping("excel")
public void export(QueryRequest request, DcaBDocSchoolprize dcaBDocSchoolprize,String dataJson,HttpServletResponse response)throws FebsException{
        try{
        request.setPageNum(1);
        request.setPageSize(10000);
        User currentUser = FebsUtil.getCurrentUser();

    dcaBDocSchoolprize.setIsDeletemark(1);
        request.setSortField("user_account asc,state ");
        request.setSortOrder("ascend");
        List<DcaBDocSchoolprize> dcaBDocSchoolprizeList=  this.iDcaBDocSchoolprizeService.findDcaBDocSchoolprizes(request, dcaBDocSchoolprize).getRecords();
        //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
        ExportExcelUtils.exportCustomExcel_han(response, dcaBDocSchoolprizeList,dataJson,"");
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@GetMapping("/{id}")
public DcaBDocSchoolprize detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBDocSchoolprize dcaBDocSchoolprize=this.iDcaBDocSchoolprizeService.getById(id);
        return dcaBDocSchoolprize;
        }
        }