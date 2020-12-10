package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.common.utils.ExportExcelUtils;
import cc.mrbird.febs.dca.service.IDcaBPublicarticleService;
import cc.mrbird.febs.dca.entity.DcaBPublicarticle;

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
@RequestMapping("dcaBPublicarticle")

public class DcaBPublicarticleController extends BaseController{

private String message;
@Autowired
public IDcaBPublicarticleService iDcaBPublicarticleService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBPublicarticle 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBPublicarticle:view")
public Map<String, Object> List(QueryRequest request, DcaBPublicarticle dcaBPublicarticle){
        return getDataTable(this.iDcaBPublicarticleService.findDcaBPublicarticles(request, dcaBPublicarticle));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBPublicarticle dcaBPublicarticle){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBPublicarticle.setUserAccount(currentUser.getUsername());
    dcaBPublicarticle.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBPublicarticleService.findDcaBPublicarticles(request, dcaBPublicarticle));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBPublicarticle dcaBPublicarticle){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBPublicarticle.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBPublicarticleService.findDcaBPublicarticles(request, dcaBPublicarticle));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBPublicarticleCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBPublicarticle> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBPublicarticle>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBPublicarticleService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBPublicarticleService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBPublicarticle dcaBPublicarticle:list
        ){
        if(dcaBPublicarticle.getState()!=null&&dcaBPublicarticle.getState().equals(3)) {
    dcaBPublicarticle.setState(3);
        }
        else{
    dcaBPublicarticle.setState(state);
        }
    dcaBPublicarticle.setDisplayIndex(display);
        display+=1;
    dcaBPublicarticle.setCreateUserId(currentUser.getUserId());
    dcaBPublicarticle.setUserAccount(currentUser.getUsername());
    dcaBPublicarticle.setUserAccountName(currentUser.getRealname());
        this.iDcaBPublicarticleService.createDcaBPublicarticle(dcaBPublicarticle);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBPublicarticle(@Valid String jsonStr ,int state,int auditState  )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBPublicarticle dcaBPublicarticle= JSON.parseObject(jsonStr, new TypeReference<DcaBPublicarticle>() {
        });
    dcaBPublicarticle.setState(state);
        if (auditState >= 0) {
        if(state==2){
    dcaBPublicarticle.setAuditState(0);
        }
        else {
    dcaBPublicarticle.setAuditState(auditState+1);
        }

        }
    dcaBPublicarticle.setAuditMan(currentUser.getUsername());
    dcaBPublicarticle.setAuditManName(currentUser.getRealname());
    dcaBPublicarticle.setAuditDate(DateUtil.date());
        this.iDcaBPublicarticleService.updateDcaBPublicarticle(dcaBPublicarticle);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBPublicarticle
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBPublicarticle(@Valid DcaBPublicarticle dcaBPublicarticle)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBPublicarticle.setCreateUserId(currentUser.getUserId());
    dcaBPublicarticle.setUserAccount(currentUser.getUsername());
        this.iDcaBPublicarticleService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBPublicarticleService.createDcaBPublicarticle(dcaBPublicarticle);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBPublicarticle
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBPublicarticle:update")
public void updateDcaBPublicarticle(@Valid DcaBPublicarticle dcaBPublicarticle)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBPublicarticle.setModifyUserId(currentUser.getUserId());
        this.iDcaBPublicarticleService.updateDcaBPublicarticle(dcaBPublicarticle);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBPublicarticle:delete")
public void deleteDcaBPublicarticles(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBPublicarticleService.deleteDcaBPublicarticles(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

    @PostMapping("excel")
    public void export(QueryRequest request, DcaBPublicarticle dcaBPublicarticles,String dataJson,HttpServletResponse response)throws FebsException{
        try{
            request.setPageNum(1);
            request.setPageSize(10000);
            User currentUser = FebsUtil.getCurrentUser();

            dcaBPublicarticles.setIsDeletemark(1);
            request.setSortField("user_account asc,state asc,display_Index");
            request.setSortOrder("ascend");
            List<DcaBPublicarticle> dcaBSciencepublishList=  this.iDcaBPublicarticleService.findDcaBPublicarticles(request, dcaBPublicarticles).getRecords();


            //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
            ExportExcelUtils.exportCustomExcel_han(response, dcaBSciencepublishList,dataJson,"");
        }catch(Exception e){
            message="导出Excel失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }
@GetMapping("/{id}")
public DcaBPublicarticle detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBPublicarticle dcaBPublicarticle=this.iDcaBPublicarticleService.getById(id);
        return dcaBPublicarticle;
        }
        }