package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import cc.mrbird.febs.dca.service.IDcaBUndergraduateService;
import cc.mrbird.febs.dca.entity.DcaBUndergraduate;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
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
 * @since 2020-08-11
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBUndergraduate")

public class DcaBUndergraduateController extends BaseController{

private String message;
@Autowired
public IDcaBUndergraduateService iDcaBUndergraduateService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBUndergraduate 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBUndergraduate:view")
public Map<String, Object> List(QueryRequest request, DcaBUndergraduate dcaBUndergraduate){
        return getDataTable(this.iDcaBUndergraduateService.findDcaBUndergraduates(request, dcaBUndergraduate));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBUndergraduate dcaBUndergraduate){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBUndergraduate.setUserAccount(currentUser.getUsername());
    dcaBUndergraduate.setIsDeletemark(1);
        return getDataTable(this.iDcaBUndergraduateService.findDcaBUndergraduates(request, dcaBUndergraduate));
        }

/**
 * 添加
 * @param  dcaBUndergraduate
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaBUndergraduate:add")
public void addDcaBUndergraduate(@Valid DcaBUndergraduate dcaBUndergraduate)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBUndergraduate.setCreateUserId(currentUser.getUserId());
        this.iDcaBUndergraduateService.createDcaBUndergraduate(dcaBUndergraduate);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBUndergraduate(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBUndergraduate> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBUndergraduate>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBUndergraduateService.deleteByuseraccount(currentUser.getUsername());
        for(DcaBUndergraduate dcaBUndergraduate:list
        ){
    dcaBUndergraduate.setState (state);

    dcaBUndergraduate.setCreateUserId(currentUser.getUserId());
    dcaBUndergraduate.setUserAccount(currentUser.getUsername());
        this.iDcaBUndergraduateService.createDcaBUndergraduate(dcaBUndergraduate);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
/**
 * 修改
 * @param dcaBUndergraduate
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBUndergraduate:update")
public void updateDcaBUndergraduate(@Valid DcaBUndergraduate dcaBUndergraduate)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBUndergraduate.setModifyUserId(currentUser.getUserId());
        this.iDcaBUndergraduateService.updateDcaBUndergraduate(dcaBUndergraduate);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBUndergraduate:delete")
public void deleteDcaBUndergraduates(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBUndergraduateService.deleteDcaBUndergraduates(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBUndergraduate:export")
public void export(QueryRequest request, DcaBUndergraduate dcaBUndergraduate,HttpServletResponse response)throws FebsException{
        try{
        List<DcaBUndergraduate> dcaBUndergraduates=this.iDcaBUndergraduateService.findDcaBUndergraduates(request, dcaBUndergraduate).getRecords();
        ExcelKit.$Export(DcaBUndergraduate.class,response).downXlsx(dcaBUndergraduates,false);
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBUndergraduate detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBUndergraduate dcaBUndergraduate=this.iDcaBUndergraduateService.getById(id);
        return dcaBUndergraduate;
        }
        }