package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.service.IDcaBLastemployService;
import cc.mrbird.febs.dca.entity.DcaBLastemploy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

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
 * @since 2020-08-12
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBLastemploy")

public class DcaBLastemployController extends BaseController{

private String message;
@Autowired
public IDcaBLastemployService iDcaBLastemployService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBLastemploy 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBLastemploy:view")
public Map<String, Object> List(QueryRequest request, DcaBLastemploy dcaBLastemploy){
        return getDataTable(this.iDcaBLastemployService.findDcaBLastemploys(request, dcaBLastemploy));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBLastemploy dcaBLastemploy){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBLastemploy.setUserAccount(currentUser.getUsername());
    dcaBLastemploy.setIsDeletemark(1);
        return getDataTable(this.iDcaBLastemployService.findDcaBLastemploys(request, dcaBLastemploy));
        }

/**
 * 添加
 * @param  dcaBLastemploy
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBLastemploy(@Valid DcaBLastemploy dcaBLastemploy)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBLastemploy.setCreateUserId(currentUser.getUserId());
    dcaBLastemploy.setUserAccount(currentUser.getUsername());
            this.iDcaBLastemployService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBLastemployService.createDcaBLastemploy(dcaBLastemploy);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
/**
 * 修改
 * @param dcaBLastemploy
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBLastemploy:update")
public void updateDcaBLastemploy(@Valid DcaBLastemploy dcaBLastemploy)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBLastemploy.setModifyUserId(currentUser.getUserId());
        this.iDcaBLastemployService.updateDcaBLastemploy(dcaBLastemploy);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBLastemploy:delete")
public void deleteDcaBLastemploys(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBLastemployService.deleteDcaBLastemploys(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBLastemploy:export")
public void export(QueryRequest request, DcaBLastemploy dcaBLastemploy,HttpServletResponse response)throws FebsException{
        try{
        List<DcaBLastemploy> dcaBLastemploys=this.iDcaBLastemployService.findDcaBLastemploys(request, dcaBLastemploy).getRecords();
        ExcelKit.$Export(DcaBLastemploy.class,response).downXlsx(dcaBLastemploys,false);
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBLastemploy detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBLastemploy dcaBLastemploy=this.iDcaBLastemployService.getById(id);
        return dcaBLastemploy;
        }
        }