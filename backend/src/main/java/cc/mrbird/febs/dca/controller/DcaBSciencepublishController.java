package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.service.IDcaBSciencepublishService;
import cc.mrbird.febs.dca.entity.DcaBSciencepublish;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
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
@RequestMapping("dcaBSciencepublish")

public class DcaBSciencepublishController extends BaseController{

private String message;
@Autowired
public IDcaBSciencepublishService iDcaBSciencepublishService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBSciencepublish 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBSciencepublish:view")
public Map<String, Object> List(QueryRequest request, DcaBSciencepublish dcaBSciencepublish){
        return getDataTable(this.iDcaBSciencepublishService.findDcaBSciencepublishs(request, dcaBSciencepublish));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBSciencepublish dcaBSciencepublish){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBSciencepublish.setUserAccount(currentUser.getUsername());
    dcaBSciencepublish.setIsDeletemark(1);
        return getDataTable(this.iDcaBSciencepublishService.findDcaBSciencepublishs(request, dcaBSciencepublish));
        }

/**
 * 添加
 * @param  dcaBSciencepublish
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaBSciencepublish:add")
public void addDcaBSciencepublish(@Valid DcaBSciencepublish dcaBSciencepublish)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBSciencepublish.setCreateUserId(currentUser.getUserId());
        this.iDcaBSciencepublishService.createDcaBSciencepublish(dcaBSciencepublish);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBSciencepublish(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBSciencepublish> list= JSON.parseObject(jsonStr,new TypeReference<List<DcaBSciencepublish>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBSciencepublishService.deleteByuseraccount(currentUser.getUsername());
        for(DcaBSciencepublish dcaBSciencepublish:list
        ){
    dcaBSciencepublish.setState (state);

    dcaBSciencepublish.setCreateUserId(currentUser.getUserId());
    dcaBSciencepublish.setUserAccount(currentUser.getUsername());
        this.iDcaBSciencepublishService.createDcaBSciencepublish(dcaBSciencepublish);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
/**
 * 修改
 * @param dcaBSciencepublish
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBSciencepublish:update")
public void updateDcaBSciencepublish(@Valid DcaBSciencepublish dcaBSciencepublish)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBSciencepublish.setModifyUserId(currentUser.getUserId());
        this.iDcaBSciencepublishService.updateDcaBSciencepublish(dcaBSciencepublish);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBSciencepublish:delete")
public void deleteDcaBSciencepublishs(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBSciencepublishService.deleteDcaBSciencepublishs(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBSciencepublish:export")
public void export(QueryRequest request, DcaBSciencepublish dcaBSciencepublish,HttpServletResponse response)throws FebsException{
        try{
        List<DcaBSciencepublish> dcaBSciencepublishs=this.iDcaBSciencepublishService.findDcaBSciencepublishs(request, dcaBSciencepublish).getRecords();
        ExcelKit.$Export(DcaBSciencepublish.class,response).downXlsx(dcaBSciencepublishs,false);
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBSciencepublish detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBSciencepublish dcaBSciencepublish=this.iDcaBSciencepublishService.getById(id);
        return dcaBSciencepublish;
        }
        }