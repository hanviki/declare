package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.service.IDcaBDoctorturtorService;
import cc.mrbird.febs.dca.entity.DcaBDoctorturtor;

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
 * @since 2020-12-15
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBDoctorturtor")

public class DcaBDoctorturtorController extends BaseController{

private String message;
@Autowired
public IDcaBDoctorturtorService iDcaBDoctorturtorService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBDoctorturtor 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBDoctorturtor:view")
public Map<String, Object> List(QueryRequest request, DcaBDoctorturtor dcaBDoctorturtor){
        return getDataTable(this.iDcaBDoctorturtorService.findDcaBDoctorturtors(request, dcaBDoctorturtor));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBDoctorturtor dcaBDoctorturtor){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDoctorturtor.setUserAccount(currentUser.getUsername());
    dcaBDoctorturtor.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDoctorturtorService.findDcaBDoctorturtors(request, dcaBDoctorturtor));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBDoctorturtor dcaBDoctorturtor){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDoctorturtor.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDoctorturtorService.findDcaBDoctorturtors(request, dcaBDoctorturtor));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBDoctorturtorCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBDoctorturtor> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBDoctorturtor>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBDoctorturtorService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBDoctorturtorService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBDoctorturtor dcaBDoctorturtor:list
        ){
        if(dcaBDoctorturtor.getState()!=null&&dcaBDoctorturtor.getState().equals(3)) {
    dcaBDoctorturtor.setState(3);
        }
        else{
    dcaBDoctorturtor.setState(state);
        }
    dcaBDoctorturtor.setDisplayIndex(display);
        display+=1;
    dcaBDoctorturtor.setCreateUserId(currentUser.getUserId());
    dcaBDoctorturtor.setUserAccount(currentUser.getUsername());
    dcaBDoctorturtor.setUserAccountName(currentUser.getRealname());
        this.iDcaBDoctorturtorService.createDcaBDoctorturtor(dcaBDoctorturtor);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBDoctorturtor(@Valid String jsonStr ,int state  )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBDoctorturtor dcaBDoctorturtor= JSON.parseObject(jsonStr, new TypeReference<DcaBDoctorturtor>() {
        });
    dcaBDoctorturtor.setState(state);
    /**
        if (auditState >= 0) {
        if(state==2){
    dcaBDoctorturtor.setAuditState(0);
        }
        else {
    dcaBDoctorturtor.setAuditState(auditState+1);
        }

        }*/
    dcaBDoctorturtor.setAuditMan(currentUser.getUsername());
    dcaBDoctorturtor.setAuditManName(currentUser.getRealname());
    dcaBDoctorturtor.setAuditDate(DateUtil.date());
        this.iDcaBDoctorturtorService.updateDcaBDoctorturtor(dcaBDoctorturtor);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBDoctorturtor
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBDoctorturtor(@Valid DcaBDoctorturtor dcaBDoctorturtor)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDoctorturtor.setCreateUserId(currentUser.getUserId());
    dcaBDoctorturtor.setUserAccount(currentUser.getUsername());
        this.iDcaBDoctorturtorService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBDoctorturtorService.createDcaBDoctorturtor(dcaBDoctorturtor);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBDoctorturtor
 * @return
 */
@Log("修改")
@PutMapping
public void updateDcaBDoctorturtor(@Valid DcaBDoctorturtor dcaBDoctorturtor)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDoctorturtor.setModifyUserId(currentUser.getUserId());
        this.iDcaBDoctorturtorService.updateDcaBDoctorturtor(dcaBDoctorturtor);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBDoctorturtor:delete")
public void deleteDcaBDoctorturtors(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBDoctorturtorService.deleteDcaBDoctorturtors(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBDoctorturtor:export")
public void export(QueryRequest request, DcaBDoctorturtor dcaBDoctorturtor,HttpServletResponse response)throws FebsException{
        try{
        List<DcaBDoctorturtor> dcaBDoctorturtors=this.iDcaBDoctorturtorService.findDcaBDoctorturtors(request, dcaBDoctorturtor).getRecords();
        ExcelKit.$Export(DcaBDoctorturtor.class,response).downXlsx(dcaBDoctorturtors,false);
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBDoctorturtor detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBDoctorturtor dcaBDoctorturtor=this.iDcaBDoctorturtorService.getById(id);
        return dcaBDoctorturtor;
        }
        }