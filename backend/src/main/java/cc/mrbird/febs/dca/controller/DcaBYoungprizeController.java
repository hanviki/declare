package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.service.IDcaBYoungprizeService;
import cc.mrbird.febs.dca.entity.DcaBYoungprize;

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
 * @since 2020-11-03
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBYoungprize")

public class DcaBYoungprizeController extends BaseController{

private String message;
@Autowired
public IDcaBYoungprizeService iDcaBYoungprizeService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBYoungprize 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBYoungprize:view")
public Map<String, Object> List(QueryRequest request, DcaBYoungprize dcaBYoungprize){
        return getDataTable(this.iDcaBYoungprizeService.findDcaBYoungprizes(request, dcaBYoungprize));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBYoungprize dcaBYoungprize){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBYoungprize.setUserAccount(currentUser.getUsername());
    dcaBYoungprize.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBYoungprizeService.findDcaBYoungprizes(request, dcaBYoungprize));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBYoungprize dcaBYoungprize){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBYoungprize.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBYoungprizeService.findDcaBYoungprizes(request, dcaBYoungprize));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBYoungprizeCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBYoungprize> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBYoungprize>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBYoungprizeService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBYoungprizeService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBYoungprize dcaBYoungprize:list
        ){
        if(dcaBYoungprize.getState()!=null&&dcaBYoungprize.getState().equals(3)) {
    dcaBYoungprize.setState(3);
        }
        else{
    dcaBYoungprize.setState(state);
        }
    dcaBYoungprize.setDisplayIndex(display);
        display+=1;
    dcaBYoungprize.setCreateUserId(currentUser.getUserId());
    dcaBYoungprize.setUserAccount(currentUser.getUsername());
    dcaBYoungprize.setUserAccountName(currentUser.getRealname());
        this.iDcaBYoungprizeService.createDcaBYoungprize(dcaBYoungprize);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBYoungprize(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBYoungprize dcaBYoungprize= JSON.parseObject(jsonStr, new TypeReference<DcaBYoungprize>() {
        });
    dcaBYoungprize.setState(state);
    dcaBYoungprize.setAuditMan(currentUser.getUsername());
    dcaBYoungprize.setAuditManName(currentUser.getRealname());
    dcaBYoungprize.setAuditDate(DateUtil.date());
        this.iDcaBYoungprizeService.updateDcaBYoungprize(dcaBYoungprize);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBYoungprize
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBYoungprize(@Valid DcaBYoungprize dcaBYoungprize)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBYoungprize.setCreateUserId(currentUser.getUserId());
    dcaBYoungprize.setUserAccount(currentUser.getUsername());
        this.iDcaBYoungprizeService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBYoungprizeService.createDcaBYoungprize(dcaBYoungprize);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBYoungprize
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBYoungprize:update")
public void updateDcaBYoungprize(@Valid DcaBYoungprize dcaBYoungprize)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBYoungprize.setModifyUserId(currentUser.getUserId());
        this.iDcaBYoungprizeService.updateDcaBYoungprize(dcaBYoungprize);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBYoungprize:delete")
public void deleteDcaBYoungprizes(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBYoungprizeService.deleteDcaBYoungprizes(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBYoungprize:export")
public void export(QueryRequest request, DcaBYoungprize dcaBYoungprize,HttpServletResponse response)throws FebsException{
        try{
        List<DcaBYoungprize> dcaBYoungprizes=this.iDcaBYoungprizeService.findDcaBYoungprizes(request, dcaBYoungprize).getRecords();
        ExcelKit.$Export(DcaBYoungprize.class,response).downXlsx(dcaBYoungprizes,false);
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBYoungprize detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBYoungprize dcaBYoungprize=this.iDcaBYoungprizeService.getById(id);
        return dcaBYoungprize;
        }
        }