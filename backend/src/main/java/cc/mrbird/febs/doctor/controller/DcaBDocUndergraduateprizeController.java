package cc.mrbird.febs.doctor.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.ExportExcelUtils;

import cc.mrbird.febs.doctor.service.IDcaBDocUndergraduateprizeService;
import cc.mrbird.febs.doctor.entity.DcaBDocUndergraduateprize;

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
@RequestMapping("dcaBDocUndergraduateprize")

public class DcaBDocUndergraduateprizeController extends BaseController{

private String message;
@Autowired
public IDcaBDocUndergraduateprizeService iDcaBDocUndergraduateprizeService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBDocUndergraduateprize 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, DcaBDocUndergraduateprize dcaBDocUndergraduateprize){
        return getDataTable(this.iDcaBDocUndergraduateprizeService.findDcaBDocUndergraduateprizes(request, dcaBDocUndergraduateprize));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBDocUndergraduateprize dcaBDocUndergraduateprize){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocUndergraduateprize.setUserAccount(currentUser.getUsername());
    dcaBDocUndergraduateprize.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocUndergraduateprizeService.findDcaBDocUndergraduateprizes(request, dcaBDocUndergraduateprize));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBDocUndergraduateprize dcaBDocUndergraduateprize){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocUndergraduateprize.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocUndergraduateprizeService.findDcaBDocUndergraduateprizes(request, dcaBDocUndergraduateprize));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBDocUndergraduateprizeCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBDocUndergraduateprize> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBDocUndergraduateprize>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBDocUndergraduateprizeService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBDocUndergraduateprizeService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBDocUndergraduateprize dcaBDocUndergraduateprize:list
        ){
        if(dcaBDocUndergraduateprize.getState()!=null&&dcaBDocUndergraduateprize.getState().equals(3)) {
    dcaBDocUndergraduateprize.setState(3);
        }
        else{
    dcaBDocUndergraduateprize.setState(state);
        }
    dcaBDocUndergraduateprize.setDisplayIndex(display);
        display+=1;
    dcaBDocUndergraduateprize.setCreateUserId(currentUser.getUserId());
    dcaBDocUndergraduateprize.setUserAccount(currentUser.getUsername());
    dcaBDocUndergraduateprize.setUserAccountName(currentUser.getRealname());
        this.iDcaBDocUndergraduateprizeService.createDcaBDocUndergraduateprize(dcaBDocUndergraduateprize);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBDocUndergraduateprize(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBDocUndergraduateprize dcaBDocUndergraduateprize= JSON.parseObject(jsonStr, new TypeReference<DcaBDocUndergraduateprize>() {
        });
    dcaBDocUndergraduateprize.setState(state);
    /**
        if (auditState >= 0) {
        if(state==2){
    dcaBDocUndergraduateprize.setAuditState(0);
        }
        else {
    dcaBDocUndergraduateprize.setAuditState(auditState+1);
        }

        }*/
    dcaBDocUndergraduateprize.setAuditMan(currentUser.getUsername());
    dcaBDocUndergraduateprize.setAuditManName(currentUser.getRealname());
    dcaBDocUndergraduateprize.setAuditDate(DateUtil.date());
        this.iDcaBDocUndergraduateprizeService.updateDcaBDocUndergraduateprize(dcaBDocUndergraduateprize);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBDocUndergraduateprize
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBDocUndergraduateprize(@Valid DcaBDocUndergraduateprize dcaBDocUndergraduateprize)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocUndergraduateprize.setCreateUserId(currentUser.getUserId());
    dcaBDocUndergraduateprize.setUserAccount(currentUser.getUsername());
        this.iDcaBDocUndergraduateprizeService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBDocUndergraduateprizeService.createDcaBDocUndergraduateprize(dcaBDocUndergraduateprize);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBDocUndergraduateprize
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBDocUndergraduateprize:update")
public void updateDcaBDocUndergraduateprize(@Valid DcaBDocUndergraduateprize dcaBDocUndergraduateprize)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocUndergraduateprize.setModifyUserId(currentUser.getUserId());
        this.iDcaBDocUndergraduateprizeService.updateDcaBDocUndergraduateprize(dcaBDocUndergraduateprize);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBDocUndergraduateprize:delete")
public void deleteDcaBDocUndergraduateprizes(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBDocUndergraduateprizeService.deleteDcaBDocUndergraduateprizes(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@PostMapping("excel")
public void export(QueryRequest request, DcaBDocUndergraduateprize dcaBDocUndergraduateprize,String dataJson,HttpServletResponse response)throws FebsException{
        try{
        request.setPageNum(1);
        request.setPageSize(10000);
        User currentUser = FebsUtil.getCurrentUser();

    dcaBDocUndergraduateprize.setIsDeletemark(1);
        request.setSortField("user_account asc,state ");
        request.setSortOrder("ascend");
        List<DcaBDocUndergraduateprize> dcaBDocUndergraduateprizeList=  this.iDcaBDocUndergraduateprizeService.findDcaBDocUndergraduateprizes(request, dcaBDocUndergraduateprize).getRecords();
        //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
        ExportExcelUtils.exportCustomExcel_han(response, dcaBDocUndergraduateprizeList,dataJson,"");
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@GetMapping("/{id}")
public DcaBDocUndergraduateprize detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBDocUndergraduateprize dcaBDocUndergraduateprize=this.iDcaBDocUndergraduateprizeService.getById(id);
        return dcaBDocUndergraduateprize;
        }
        }