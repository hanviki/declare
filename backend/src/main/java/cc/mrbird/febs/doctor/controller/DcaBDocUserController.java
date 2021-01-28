package cc.mrbird.febs.doctor.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.common.utils.ExportExcelUtils;
import cc.mrbird.febs.doctor.service.IDcaBDocUserService;
import cc.mrbird.febs.doctor.entity.DcaBDocUser;

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
 * @since 2021-01-12
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBDocUser")

public class DcaBDocUserController extends BaseController{

private String message;
@Autowired
public IDcaBDocUserService iDcaBDocUserService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBDocUser 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, DcaBDocUser dcaBDocUser){
        return getDataTable(this.iDcaBDocUserService.findDcaBDocUsers(request, dcaBDocUser));
        }

    @GetMapping("person")
    public List<DcaBDocUser> List3(){
        User currentUser= FebsUtil.getCurrentUser();
        return this.iDcaBDocUserService.findPerson(currentUser.getUsername());
    }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBDocUser dcaBDocUser){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocUser.setUserAccount(currentUser.getUsername());
    dcaBDocUser.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocUserService.findDcaBDocUsers(request, dcaBDocUser));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBDocUser dcaBDocUser){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocUser.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocUserService.findDcaBDocUsers(request, dcaBDocUser));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBDocUserCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBDocUser> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBDocUser>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBDocUserService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBDocUserService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBDocUser dcaBDocUser:list
        ){
        if(dcaBDocUser.getState()!=null&&dcaBDocUser.getState().equals(3)) {
    dcaBDocUser.setState(3);
        }
        else{
    dcaBDocUser.setState(state);
        }
    dcaBDocUser.setDisplayIndex(display);
        display+=1;
    dcaBDocUser.setCreateUserId(currentUser.getUserId());
    dcaBDocUser.setUserAccount(currentUser.getUsername());
    dcaBDocUser.setUserAccountName(currentUser.getRealname());
        this.iDcaBDocUserService.createDcaBDocUser(dcaBDocUser);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBDocUser(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBDocUser dcaBDocUser= JSON.parseObject(jsonStr, new TypeReference<DcaBDocUser>() {
        });
    dcaBDocUser.setState(state);
    /**
        if (auditState >= 0) {
        if(state==2){
    dcaBDocUser.setAuditState(0);
        }
        else {
    dcaBDocUser.setAuditState(auditState+1);
        }

        }*/

        this.iDcaBDocUserService.updateDcaBDocUser(dcaBDocUser);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBDocUser
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBDocUser(@Valid DcaBDocUser dcaBDocUser)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocUser.setCreateUserId(currentUser.getUserId());
    dcaBDocUser.setUserAccount(currentUser.getUsername());
        this.iDcaBDocUserService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBDocUserService.createDcaBDocUser(dcaBDocUser);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBDocUser
 * @return
 */
@Log("修改")
@PutMapping
public void updateDcaBDocUser(@Valid DcaBDocUser dcaBDocUser)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocUser.setModifyUserId(currentUser.getUserId());
        this.iDcaBDocUserService.updateDcaBDocUser(dcaBDocUser);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBDocUser:delete")
public void deleteDcaBDocUsers(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBDocUserService.deleteDcaBDocUsers(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
    @PostMapping("excel")
    public void export(QueryRequest request, DcaBDocUser dcaBDocUndergraduate,String dataJson,HttpServletResponse response)throws FebsException{
        try{
            request.setPageNum(1);
            request.setPageSize(10000);
            User currentUser = FebsUtil.getCurrentUser();

            dcaBDocUndergraduate.setIsDeletemark(1);
            request.setSortField("user_account");
            request.setSortOrder("ascend");
            List<DcaBDocUser> dcaBDocUndergraduateList=  this.iDcaBDocUserService.findDcaBDocUsers(request, dcaBDocUndergraduate).getRecords();
            //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
            ExportExcelUtils.exportCustomExcel_han(response, dcaBDocUndergraduateList,dataJson,"");
        }catch(Exception e){
            message="导出Excel失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }
@GetMapping("/{id}")
public DcaBDocUser detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBDocUser dcaBDocUser=this.iDcaBDocUserService.getById(id);
        return dcaBDocUser;
        }
        }