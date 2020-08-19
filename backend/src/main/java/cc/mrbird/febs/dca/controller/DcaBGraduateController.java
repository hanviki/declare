package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import cc.mrbird.febs.dca.service.IDcaBGraduateService;
import cc.mrbird.febs.dca.entity.DcaBGraduate;

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
@RequestMapping("dcaBGraduate")

public class DcaBGraduateController extends BaseController{

private String message;
@Autowired
public IDcaBGraduateService iDcaBGraduateService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBGraduate 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBGraduate:view")
public Map<String, Object> List(QueryRequest request, DcaBGraduate dcaBGraduate){
        return getDataTable(this.iDcaBGraduateService.findDcaBGraduates(request, dcaBGraduate));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBGraduate dcaBGraduate){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBGraduate.setUserAccount(currentUser.getUsername());
    dcaBGraduate.setIsDeletemark(1);
        return getDataTable(this.iDcaBGraduateService.findDcaBGraduates(request, dcaBGraduate));
        }

/**
 * 添加
 * @param  dcaBGraduate
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaBGraduate:add")
public void addDcaBGraduate(@Valid DcaBGraduate dcaBGraduate)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBGraduate.setCreateUserId(currentUser.getUserId());
        this.iDcaBGraduateService.createDcaBGraduate(dcaBGraduate);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBGraduate(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBGraduate> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBGraduate>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBGraduateService.deleteByuseraccount(currentUser.getUsername());
        for(DcaBGraduate dcaBGraduate:list
        ){

    dcaBGraduate.setState (state);

    dcaBGraduate.setCreateUserId(currentUser.getUserId());
    dcaBGraduate.setUserAccount(currentUser.getUsername());
        this.iDcaBGraduateService.createDcaBGraduate(dcaBGraduate);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
/**
 * 修改
 * @param dcaBGraduate
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBGraduate:update")
public void updateDcaBGraduate(@Valid DcaBGraduate dcaBGraduate)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBGraduate.setModifyUserId(currentUser.getUserId());
        this.iDcaBGraduateService.updateDcaBGraduate(dcaBGraduate);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBGraduate:delete")
public void deleteDcaBGraduates(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBGraduateService.deleteDcaBGraduates(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBGraduate:export")
public void export(QueryRequest request, DcaBGraduate dcaBGraduate,HttpServletResponse response)throws FebsException{
        try{
        List<DcaBGraduate> dcaBGraduates=this.iDcaBGraduateService.findDcaBGraduates(request, dcaBGraduate).getRecords();
        ExcelKit.$Export(DcaBGraduate.class,response).downXlsx(dcaBGraduates,false);
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBGraduate detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBGraduate dcaBGraduate=this.iDcaBGraduateService.getById(id);
        return dcaBGraduate;
        }
        }