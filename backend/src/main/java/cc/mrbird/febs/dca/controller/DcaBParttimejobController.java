package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.service.IDcaBParttimejobService;
import cc.mrbird.febs.dca.entity.DcaBParttimejob;

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
 * @since 2020-10-20
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBParttimejob")

public class DcaBParttimejobController extends BaseController{

private String message;
@Autowired
public IDcaBParttimejobService iDcaBParttimejobService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBParttimejob 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBParttimejob:view")
public Map<String, Object> List(QueryRequest request, DcaBParttimejob dcaBParttimejob){
        return getDataTable(this.iDcaBParttimejobService.findDcaBParttimejobs(request, dcaBParttimejob));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBParttimejob dcaBParttimejob){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBParttimejob.setUserAccount(currentUser.getUsername());
    dcaBParttimejob.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBParttimejobService.findDcaBParttimejobs(request, dcaBParttimejob));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBParttimejob dcaBParttimejob){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBParttimejob.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBParttimejobService.findDcaBParttimejobs(request, dcaBParttimejob));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBParttimejobCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBParttimejob> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBParttimejob>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBParttimejobService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBParttimejobService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBParttimejob dcaBParttimejob:list
        ){
        if(dcaBParttimejob.getState()!=null&&dcaBParttimejob.getState().equals(3)) {
    dcaBParttimejob.setState(3);
        }
        else{
    dcaBParttimejob.setState(state);
        }
    dcaBParttimejob.setDisplayIndex(display);
        display+=1;
    dcaBParttimejob.setCreateUserId(currentUser.getUserId());
    dcaBParttimejob.setUserAccount(currentUser.getUsername());
    dcaBParttimejob.setUserAccountName(currentUser.getRealname());
        this.iDcaBParttimejobService.createDcaBParttimejob(dcaBParttimejob);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBParttimejob(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBParttimejob dcaBParttimejob= JSON.parseObject(jsonStr, new TypeReference<DcaBParttimejob>() {
        });
    dcaBParttimejob.setState(state);
    dcaBParttimejob.setAuditMan(currentUser.getUsername());
    dcaBParttimejob.setAuditManName(currentUser.getRealname());
    dcaBParttimejob.setAuditDate(DateUtil.date());
        this.iDcaBParttimejobService.updateDcaBParttimejob(dcaBParttimejob);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBParttimejob
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBParttimejob(@Valid DcaBParttimejob dcaBParttimejob)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBParttimejob.setCreateUserId(currentUser.getUserId());
    dcaBParttimejob.setUserAccount(currentUser.getUsername());
        this.iDcaBParttimejobService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBParttimejobService.createDcaBParttimejob(dcaBParttimejob);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBParttimejob
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBParttimejob:update")
public void updateDcaBParttimejob(@Valid DcaBParttimejob dcaBParttimejob)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBParttimejob.setModifyUserId(currentUser.getUserId());
        this.iDcaBParttimejobService.updateDcaBParttimejob(dcaBParttimejob);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBParttimejob:delete")
public void deleteDcaBParttimejobs(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBParttimejobService.deleteDcaBParttimejobs(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBParttimejob:export")
public void export(QueryRequest request, DcaBParttimejob dcaBParttimejob,HttpServletResponse response)throws FebsException{
        try{
        List<DcaBParttimejob> dcaBParttimejobs=this.iDcaBParttimejobService.findDcaBParttimejobs(request, dcaBParttimejob).getRecords();
        ExcelKit.$Export(DcaBParttimejob.class,response).downXlsx(dcaBParttimejobs,false);
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBParttimejob detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBParttimejob dcaBParttimejob=this.iDcaBParttimejobService.getById(id);
        return dcaBParttimejob;
        }
        }