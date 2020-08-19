package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import cc.mrbird.febs.dca.service.IDcaBEducationexpericeService;
import cc.mrbird.febs.dca.entity.DcaBEducationexperice;

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
@RequestMapping("dcaBEducationexperice")

public class DcaBEducationexpericeController extends BaseController{

private String message;
@Autowired
public IDcaBEducationexpericeService iDcaBEducationexpericeService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBEducationexperice 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBEducationexperice:view")
public Map<String, Object> List(QueryRequest request, DcaBEducationexperice dcaBEducationexperice){
        return getDataTable(this.iDcaBEducationexpericeService.findDcaBEducationexperices(request, dcaBEducationexperice));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBEducationexperice dcaBEducationexperice){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBEducationexperice.setUserAccount(currentUser.getUsername());
    dcaBEducationexperice.setIsDeletemark(1);
        return getDataTable(this.iDcaBEducationexpericeService.findDcaBEducationexperices(request, dcaBEducationexperice));
        }

/**
 * 添加
 * @param  dcaBEducationexperice
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaBEducationexperice:add")
public void addDcaBEducationexperice(@Valid DcaBEducationexperice dcaBEducationexperice)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBEducationexperice.setCreateUserId(currentUser.getUserId());
        this.iDcaBEducationexpericeService.createDcaBEducationexperice(dcaBEducationexperice);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBEducationexperice(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBEducationexperice> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBEducationexperice>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBEducationexpericeService.deleteByuseraccount(currentUser.getUsername());
        for(DcaBEducationexperice dcaBEducationexperice:list
        ){
    dcaBEducationexperice.setState (state);
    dcaBEducationexperice.setCreateUserId(currentUser.getUserId());
    dcaBEducationexperice.setUserAccount(currentUser.getUsername());
        this.iDcaBEducationexpericeService.createDcaBEducationexperice(dcaBEducationexperice);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
/**
 * 修改
 * @param dcaBEducationexperice
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBEducationexperice:update")
public void updateDcaBEducationexperice(@Valid DcaBEducationexperice dcaBEducationexperice)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBEducationexperice.setModifyUserId(currentUser.getUserId());
        this.iDcaBEducationexpericeService.updateDcaBEducationexperice(dcaBEducationexperice);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBEducationexperice:delete")
public void deleteDcaBEducationexperices(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBEducationexpericeService.deleteDcaBEducationexperices(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBEducationexperice:export")
public void export(QueryRequest request, DcaBEducationexperice dcaBEducationexperice,HttpServletResponse response)throws FebsException{
        try{
        List<DcaBEducationexperice> dcaBEducationexperices=this.iDcaBEducationexpericeService.findDcaBEducationexperices(request, dcaBEducationexperice).getRecords();
        ExcelKit.$Export(DcaBEducationexperice.class,response).downXlsx(dcaBEducationexperices,false);
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBEducationexperice detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBEducationexperice dcaBEducationexperice=this.iDcaBEducationexpericeService.getById(id);
        return dcaBEducationexperice;
        }
        }