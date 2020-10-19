package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.service.IDcaBTurtorService;
import cc.mrbird.febs.dca.entity.DcaBTurtor;

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
 * @since 2020-09-15
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBTurtor")

public class DcaBTurtorController extends BaseController{

private String message;
@Autowired
public IDcaBTurtorService iDcaBTurtorService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBTurtor 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBTurtor:view")
public Map<String, Object> List(QueryRequest request, DcaBTurtor dcaBTurtor){
        return getDataTable(this.iDcaBTurtorService.findDcaBTurtors(request, dcaBTurtor));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBTurtor dcaBTurtor){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBTurtor.setUserAccount(currentUser.getUsername());
    dcaBTurtor.setIsDeletemark(1);
        request.setPageSize(100);
    request.setSortField("display_Index");
    request.setSortOrder("ascend");
        return getDataTable(this.iDcaBTurtorService.findDcaBTurtors(request, dcaBTurtor));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBTurtor dcaBTurtor){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBTurtor.setIsDeletemark(1);
        request.setSortField("state");
        request.setSortOrder("descend");
        return getDataTable(this.iDcaBTurtorService.findDcaBTurtors(request, dcaBTurtor));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBTurtorCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBTurtor> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBTurtor>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBTurtorService.deleteByuseraccount(currentUser.getUsername());
            int displayIndex=1;
        for(DcaBTurtor dcaBTurtor:list
        ){
        if(dcaBTurtor.getState()!=null&&dcaBTurtor.getState().equals(3)) {
    dcaBTurtor.setState(3);
        }
        else{
    dcaBTurtor.setState(state);
        }
            dcaBTurtor.setDisplayIndex(displayIndex);
            displayIndex+=1;
    dcaBTurtor.setCreateUserId(currentUser.getUserId());
    dcaBTurtor.setUserAccount(currentUser.getUsername());
    dcaBTurtor.setUserAccountName(currentUser.getRealname());
        this.iDcaBTurtorService.createDcaBTurtor(dcaBTurtor);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBTurtor(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBTurtor dcaBTurtor= JSON.parseObject(jsonStr, new TypeReference<DcaBTurtor>() {
        });
    dcaBTurtor.setState(state);
    dcaBTurtor.setAuditMan(currentUser.getUsername());
    dcaBTurtor.setAuditManName(currentUser.getRealname());
    dcaBTurtor.setAuditDate(DateUtil.date());
        this.iDcaBTurtorService.updateDcaBTurtor(dcaBTurtor);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBTurtor
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBTurtor(@Valid DcaBTurtor dcaBTurtor)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBTurtor.setCreateUserId(currentUser.getUserId());
    dcaBTurtor.setUserAccount(currentUser.getUsername());
        this.iDcaBTurtorService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBTurtorService.createDcaBTurtor(dcaBTurtor);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBTurtor
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBTurtor:update")
public void updateDcaBTurtor(@Valid DcaBTurtor dcaBTurtor)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBTurtor.setModifyUserId(currentUser.getUserId());
        this.iDcaBTurtorService.updateDcaBTurtor(dcaBTurtor);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBTurtor:delete")
public void deleteDcaBTurtors(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBTurtorService.deleteDcaBTurtors(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBTurtor:export")
public void export(QueryRequest request, DcaBTurtor dcaBTurtor,HttpServletResponse response)throws FebsException{
        try{
        List<DcaBTurtor> dcaBTurtors=this.iDcaBTurtorService.findDcaBTurtors(request, dcaBTurtor).getRecords();
        ExcelKit.$Export(DcaBTurtor.class,response).downXlsx(dcaBTurtors,false);
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBTurtor detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBTurtor dcaBTurtor=this.iDcaBTurtorService.getById(id);
        return dcaBTurtor;
        }
        }