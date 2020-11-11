package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.common.utils.ExportExcelUtils;
import cc.mrbird.febs.dca.service.IDcaBAuditdynamicService;
import cc.mrbird.febs.dca.entity.DcaBAuditdynamic;

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
 * @since 2020-10-27
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBAuditdynamic")

public class DcaBAuditdynamicController extends BaseController{

private String message;
@Autowired
public IDcaBAuditdynamicService iDcaBAuditdynamicService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBAuditdynamic 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBAuditdynamic:view")
public Map<String, Object> List(QueryRequest request, DcaBAuditdynamic dcaBAuditdynamic){
        return getDataTable(this.iDcaBAuditdynamicService.findDcaBAuditdynamics(request, dcaBAuditdynamic));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBAuditdynamic dcaBAuditdynamic){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBAuditdynamic.setUserAccount(currentUser.getUsername());
    dcaBAuditdynamic.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBAuditdynamicService.findDcaBAuditdynamics(request, dcaBAuditdynamic));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBAuditdynamic dcaBAuditdynamic){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBAuditdynamic.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBAuditdynamicService.findDcaBAuditdynamics(request, dcaBAuditdynamic));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBAuditdynamicCustom(@Valid String jsonStr)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBAuditdynamic> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBAuditdynamic>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
       // this.iDcaBAuditdynamicService.deleteByuseraccount(currentUser.getUsername());
      //  int display=this.iDcaBAuditdynamicService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBAuditdynamic dcaBAuditdynamic:list
        ){
    dcaBAuditdynamic.setCreateUserId(currentUser.getUserId());
   // dcaBAuditdynamic.setUserAccount(currentUser.getUsername());
  //  dcaBAuditdynamic.setUserAccountName(currentUser.getRealname());
        this.iDcaBAuditdynamicService.createDcaBAuditdynamic(dcaBAuditdynamic);
        }
        }catch(Exception e){
        message="审核失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBAuditdynamic(@Valid String jsonStr  )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBAuditdynamic dcaBAuditdynamic= JSON.parseObject(jsonStr, new TypeReference<DcaBAuditdynamic>() {
        });
        this.iDcaBAuditdynamicService.updateDcaBAuditdynamic(dcaBAuditdynamic);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBAuditdynamic
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBAuditdynamic(@Valid DcaBAuditdynamic dcaBAuditdynamic)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBAuditdynamic.setCreateUserId(currentUser.getUserId());
    dcaBAuditdynamic.setUserAccount(currentUser.getUsername());
        this.iDcaBAuditdynamicService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBAuditdynamicService.createDcaBAuditdynamic(dcaBAuditdynamic);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBAuditdynamic
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBAuditdynamic:update")
public void updateDcaBAuditdynamic(@Valid DcaBAuditdynamic dcaBAuditdynamic)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBAuditdynamic.setModifyUserId(currentUser.getUserId());
        this.iDcaBAuditdynamicService.updateDcaBAuditdynamic(dcaBAuditdynamic);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBAuditdynamic:delete")
public void deleteDcaBAuditdynamics(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBAuditdynamicService.deleteDcaBAuditdynamics(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@GetMapping("/{id}")
public DcaBAuditdynamic detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBAuditdynamic dcaBAuditdynamic=this.iDcaBAuditdynamicService.getById(id);
        return dcaBAuditdynamic;
        }
        }