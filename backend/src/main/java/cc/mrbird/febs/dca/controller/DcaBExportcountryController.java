package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.service.IDcaBExportcountryService;
import cc.mrbird.febs.dca.entity.DcaBExportcountry;

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
 * @since 2020-10-22
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBExportcountry")

public class DcaBExportcountryController extends BaseController{

private String message;
@Autowired
public IDcaBExportcountryService iDcaBExportcountryService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBExportcountry 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBExportcountry:view")
public Map<String, Object> List(QueryRequest request, DcaBExportcountry dcaBExportcountry){
        return getDataTable(this.iDcaBExportcountryService.findDcaBExportcountrys(request, dcaBExportcountry));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBExportcountry dcaBExportcountry){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBExportcountry.setUserAccount(currentUser.getUsername());
    dcaBExportcountry.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBExportcountryService.findDcaBExportcountrys(request, dcaBExportcountry));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBExportcountry dcaBExportcountry){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBExportcountry.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBExportcountryService.findDcaBExportcountrys(request, dcaBExportcountry));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBExportcountryCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBExportcountry> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBExportcountry>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBExportcountryService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBExportcountryService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBExportcountry dcaBExportcountry:list
        ){
        if(dcaBExportcountry.getState()!=null&&dcaBExportcountry.getState().equals(3)) {
    dcaBExportcountry.setState(3);
        }
        else{
    dcaBExportcountry.setState(state);
        }
    dcaBExportcountry.setDisplayIndex(display);
        display+=1;
    dcaBExportcountry.setCreateUserId(currentUser.getUserId());
    dcaBExportcountry.setUserAccount(currentUser.getUsername());
    dcaBExportcountry.setUserAccountName(currentUser.getRealname());
        this.iDcaBExportcountryService.createDcaBExportcountry(dcaBExportcountry);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBExportcountry(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBExportcountry dcaBExportcountry= JSON.parseObject(jsonStr, new TypeReference<DcaBExportcountry>() {
        });
    dcaBExportcountry.setState(state);
    dcaBExportcountry.setAuditMan(currentUser.getUsername());
    dcaBExportcountry.setAuditManName(currentUser.getRealname());
    dcaBExportcountry.setAuditDate(DateUtil.date());
        this.iDcaBExportcountryService.updateDcaBExportcountry(dcaBExportcountry);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBExportcountry
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBExportcountry(@Valid DcaBExportcountry dcaBExportcountry)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBExportcountry.setCreateUserId(currentUser.getUserId());
    dcaBExportcountry.setUserAccount(currentUser.getUsername());
        this.iDcaBExportcountryService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBExportcountryService.createDcaBExportcountry(dcaBExportcountry);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBExportcountry
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBExportcountry:update")
public void updateDcaBExportcountry(@Valid DcaBExportcountry dcaBExportcountry)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBExportcountry.setModifyUserId(currentUser.getUserId());
        this.iDcaBExportcountryService.updateDcaBExportcountry(dcaBExportcountry);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBExportcountry:delete")
public void deleteDcaBExportcountrys(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBExportcountryService.deleteDcaBExportcountrys(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBExportcountry:export")
public void export(QueryRequest request, DcaBExportcountry dcaBExportcountry,HttpServletResponse response)throws FebsException{
        try{
        List<DcaBExportcountry> dcaBExportcountrys=this.iDcaBExportcountryService.findDcaBExportcountrys(request, dcaBExportcountry).getRecords();
        ExcelKit.$Export(DcaBExportcountry.class,response).downXlsx(dcaBExportcountrys,false);
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBExportcountry detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBExportcountry dcaBExportcountry=this.iDcaBExportcountryService.getById(id);
        return dcaBExportcountry;
        }
        }