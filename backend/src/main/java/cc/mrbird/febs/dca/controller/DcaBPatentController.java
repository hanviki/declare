package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.common.utils.ExportExcelUtils;
import cc.mrbird.febs.dca.service.IDcaBPatentService;
import cc.mrbird.febs.dca.entity.DcaBPatent;

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
@RequestMapping("dcaBPatent")

public class DcaBPatentController extends BaseController{

private String message;
@Autowired
public IDcaBPatentService iDcaBPatentService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBPatent 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBPatent:view")
public Map<String, Object> List(QueryRequest request, DcaBPatent dcaBPatent){
        return getDataTable(this.iDcaBPatentService.findDcaBPatents(request, dcaBPatent));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBPatent dcaBPatent){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBPatent.setUserAccount(currentUser.getUsername());
    dcaBPatent.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBPatentService.findDcaBPatents(request, dcaBPatent));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBPatent dcaBPatent){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBPatent.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBPatentService.findDcaBPatents(request, dcaBPatent));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBPatentCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBPatent> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBPatent>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBPatentService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBPatentService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBPatent dcaBPatent:list
        ){
        if(dcaBPatent.getState()!=null&&dcaBPatent.getState().equals(3)) {
    dcaBPatent.setState(3);
        }
        else{
    dcaBPatent.setState(state);
        }
    dcaBPatent.setDisplayIndex(display);
        display+=1;
    dcaBPatent.setCreateUserId(currentUser.getUserId());
    dcaBPatent.setUserAccount(currentUser.getUsername());
    dcaBPatent.setUserAccountName(currentUser.getRealname());
        this.iDcaBPatentService.createDcaBPatent(dcaBPatent);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBPatent(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBPatent dcaBPatent= JSON.parseObject(jsonStr, new TypeReference<DcaBPatent>() {
        });
    dcaBPatent.setState(state);
    dcaBPatent.setAuditMan(currentUser.getUsername());
    dcaBPatent.setAuditManName(currentUser.getRealname());
    dcaBPatent.setAuditDate(DateUtil.date());
        this.iDcaBPatentService.updateDcaBPatent(dcaBPatent);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBPatent
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBPatent(@Valid DcaBPatent dcaBPatent)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBPatent.setCreateUserId(currentUser.getUserId());
    dcaBPatent.setUserAccount(currentUser.getUsername());
        this.iDcaBPatentService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBPatentService.createDcaBPatent(dcaBPatent);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBPatent
 * @return
 */
@Log("修改")
@PutMapping
public void updateDcaBPatent(@Valid DcaBPatent dcaBPatent)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBPatent.setModifyUserId(currentUser.getUserId());
        this.iDcaBPatentService.updateDcaBPatent(dcaBPatent);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBPatent:delete")
public void deleteDcaBPatents(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBPatentService.deleteDcaBPatents(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
    @PostMapping("excel")
    public void export(QueryRequest request, DcaBPatent dcaBPatent,String dataJson,HttpServletResponse response)throws FebsException{
        try{
            request.setPageNum(1);
            request.setPageSize(10000);
            User currentUser = FebsUtil.getCurrentUser();

            dcaBPatent.setIsDeletemark(1);
            request.setSortField("user_account asc,state asc,display_Index");
            request.setSortOrder("ascend");
            List<DcaBPatent> dcaBSciencepublishList=  this.iDcaBPatentService.findDcaBPatents(request, dcaBPatent).getRecords();


            //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
            ExportExcelUtils.exportCustomExcel_han(response, dcaBSciencepublishList,dataJson,"");
        }catch(Exception e){
            message="导出Excel失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

@GetMapping("/{id}")
public DcaBPatent detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBPatent dcaBPatent=this.iDcaBPatentService.getById(id);
        return dcaBPatent;
        }
        }