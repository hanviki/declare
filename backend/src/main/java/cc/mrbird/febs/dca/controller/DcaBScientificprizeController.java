package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.service.IDcaBScientificprizeService;
import cc.mrbird.febs.dca.entity.DcaBScientificprize;

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
@RequestMapping("dcaBScientificprize")

public class DcaBScientificprizeController extends BaseController{

private String message;
@Autowired
public IDcaBScientificprizeService iDcaBScientificprizeService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBScientificprize 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBScientificprize:view")
public Map<String, Object> List(QueryRequest request, DcaBScientificprize dcaBScientificprize){
        return getDataTable(this.iDcaBScientificprizeService.findDcaBScientificprizes(request, dcaBScientificprize));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBScientificprize dcaBScientificprize){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBScientificprize.setUserAccount(currentUser.getUsername());
    dcaBScientificprize.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBScientificprizeService.findDcaBScientificprizes(request, dcaBScientificprize));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBScientificprize dcaBScientificprize){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBScientificprize.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBScientificprizeService.findDcaBScientificprizes(request, dcaBScientificprize));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBScientificprizeCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBScientificprize> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBScientificprize>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBScientificprizeService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBScientificprizeService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBScientificprize dcaBScientificprize:list
        ){
        if(dcaBScientificprize.getState()!=null&&dcaBScientificprize.getState().equals(3)) {
    dcaBScientificprize.setState(3);
        }
        else{
    dcaBScientificprize.setState(state);
        }
    dcaBScientificprize.setDisplayIndex(display);
        display+=1;
    dcaBScientificprize.setCreateUserId(currentUser.getUserId());
    dcaBScientificprize.setUserAccount(currentUser.getUsername());
    dcaBScientificprize.setUserAccountName(currentUser.getRealname());
        this.iDcaBScientificprizeService.createDcaBScientificprize(dcaBScientificprize);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBScientificprize(@Valid String jsonStr ,int state,int auditState  )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBScientificprize dcaBScientificprize= JSON.parseObject(jsonStr, new TypeReference<DcaBScientificprize>() {
        });
    dcaBScientificprize.setState(state);
        if (auditState >= 0) {
        if(state==2){
    dcaBScientificprize.setAuditState(0);
        }
        else {
    dcaBScientificprize.setAuditState(auditState+1);
        }

        }
    dcaBScientificprize.setAuditMan(currentUser.getUsername());
    dcaBScientificprize.setAuditManName(currentUser.getRealname());
    dcaBScientificprize.setAuditDate(DateUtil.date());
        this.iDcaBScientificprizeService.updateDcaBScientificprize(dcaBScientificprize);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBScientificprize
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBScientificprize(@Valid DcaBScientificprize dcaBScientificprize)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBScientificprize.setCreateUserId(currentUser.getUserId());
    dcaBScientificprize.setUserAccount(currentUser.getUsername());
        this.iDcaBScientificprizeService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBScientificprizeService.createDcaBScientificprize(dcaBScientificprize);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBScientificprize
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBScientificprize:update")
public void updateDcaBScientificprize(@Valid DcaBScientificprize dcaBScientificprize)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBScientificprize.setModifyUserId(currentUser.getUserId());
        this.iDcaBScientificprizeService.updateDcaBScientificprize(dcaBScientificprize);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBScientificprize:delete")
public void deleteDcaBScientificprizes(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBScientificprizeService.deleteDcaBScientificprizes(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBScientificprize:export")
public void export(QueryRequest request, DcaBScientificprize dcaBScientificprize,HttpServletResponse response)throws FebsException{
        try{
        List<DcaBScientificprize> dcaBScientificprizes=this.iDcaBScientificprizeService.findDcaBScientificprizes(request, dcaBScientificprize).getRecords();
        ExcelKit.$Export(DcaBScientificprize.class,response).downXlsx(dcaBScientificprizes,false);
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBScientificprize detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBScientificprize dcaBScientificprize=this.iDcaBScientificprizeService.getById(id);
        return dcaBScientificprize;
        }
        }