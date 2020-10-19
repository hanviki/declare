package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.service.IDcaBWorknumService;
import cc.mrbird.febs.dca.entity.DcaBWorknum;

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
 * @since 2020-10-16
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBWorknum")

public class DcaBWorknumController extends BaseController{

private String message;
@Autowired
public IDcaBWorknumService iDcaBWorknumService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBWorknum 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBWorknum:view")
public Map<String, Object> List(QueryRequest request, DcaBWorknum dcaBWorknum){
        return getDataTable(this.iDcaBWorknumService.findDcaBWorknums(request, dcaBWorknum));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBWorknum dcaBWorknum){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBWorknum.setUserAccount(currentUser.getUsername());
    dcaBWorknum.setIsDeletemark(1);
        request.setPageSize(100);
    request.setSortField("display_Index");
    request.setSortOrder("ascend");
        return getDataTable(this.iDcaBWorknumService.findDcaBWorknums(request, dcaBWorknum));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBWorknum dcaBWorknum){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBWorknum.setIsDeletemark(1);
        request.setSortField("state");
        request.setSortOrder("descend");
        return getDataTable(this.iDcaBWorknumService.findDcaBWorknums(request, dcaBWorknum));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBWorknumCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBWorknum> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBWorknum>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBWorknumService.deleteByuseraccount(currentUser.getUsername());
            int displayIndex=1;
        for(DcaBWorknum dcaBWorknum:list
        ){
        if(dcaBWorknum.getState()!=null&&dcaBWorknum.getState().equals(3)) {
    dcaBWorknum.setState(3);
        }
        else{
    dcaBWorknum.setState(state);
        }
            dcaBWorknum.setDisplayIndex(displayIndex);
            displayIndex+=1;
    dcaBWorknum.setCreateUserId(currentUser.getUserId());
    dcaBWorknum.setUserAccount(currentUser.getUsername());
    dcaBWorknum.setUserAccountName(currentUser.getRealname());
        this.iDcaBWorknumService.createDcaBWorknum(dcaBWorknum);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBWorknum(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBWorknum dcaBWorknum= JSON.parseObject(jsonStr, new TypeReference<DcaBWorknum>() {
        });
    dcaBWorknum.setState(state);
    dcaBWorknum.setAuditMan(currentUser.getUsername());
    dcaBWorknum.setAuditManName(currentUser.getRealname());
    dcaBWorknum.setAuditDate(DateUtil.date());
        this.iDcaBWorknumService.updateDcaBWorknum(dcaBWorknum);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBWorknum
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBWorknum(@Valid DcaBWorknum dcaBWorknum)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBWorknum.setCreateUserId(currentUser.getUserId());
    dcaBWorknum.setUserAccount(currentUser.getUsername());
        this.iDcaBWorknumService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBWorknumService.createDcaBWorknum(dcaBWorknum);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBWorknum
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBWorknum:update")
public void updateDcaBWorknum(@Valid DcaBWorknum dcaBWorknum)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBWorknum.setModifyUserId(currentUser.getUserId());
        this.iDcaBWorknumService.updateDcaBWorknum(dcaBWorknum);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBWorknum:delete")
public void deleteDcaBWorknums(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBWorknumService.deleteDcaBWorknums(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBWorknum:export")
public void export(QueryRequest request, DcaBWorknum dcaBWorknum,HttpServletResponse response)throws FebsException{
        try{
        List<DcaBWorknum> dcaBWorknums=this.iDcaBWorknumService.findDcaBWorknums(request, dcaBWorknum).getRecords();
        ExcelKit.$Export(DcaBWorknum.class,response).downXlsx(dcaBWorknums,false);
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBWorknum detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBWorknum dcaBWorknum=this.iDcaBWorknumService.getById(id);
        return dcaBWorknum;
        }
        }