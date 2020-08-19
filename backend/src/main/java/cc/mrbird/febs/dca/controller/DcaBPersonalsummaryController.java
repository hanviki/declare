package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.service.IDcaBPersonalsummaryService;
import cc.mrbird.febs.dca.entity.DcaBPersonalsummary;

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
@RequestMapping("dcaBPersonalsummary")

public class DcaBPersonalsummaryController extends BaseController{

private String message;
@Autowired
public IDcaBPersonalsummaryService iDcaBPersonalsummaryService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBPersonalsummary 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBPersonalsummary:view")
public Map<String, Object> List(QueryRequest request, DcaBPersonalsummary dcaBPersonalsummary){
        return getDataTable(this.iDcaBPersonalsummaryService.findDcaBPersonalsummarys(request, dcaBPersonalsummary));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBPersonalsummary dcaBPersonalsummary){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBPersonalsummary.setUserAccount(currentUser.getUsername());
    dcaBPersonalsummary.setIsDeletemark(1);
        return getDataTable(this.iDcaBPersonalsummaryService.findDcaBPersonalsummarys(request, dcaBPersonalsummary));
        }

/**
 * 添加
 * @param  dcaBPersonalsummary
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBPersonalsummary(@Valid DcaBPersonalsummary dcaBPersonalsummary)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBPersonalsummary.setCreateUserId(currentUser.getUserId());
    dcaBPersonalsummary.setUserAccount(currentUser.getUsername());
            this.iDcaBPersonalsummaryService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBPersonalsummaryService.createDcaBPersonalsummary(dcaBPersonalsummary);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
/**
 * 修改
 * @param dcaBPersonalsummary
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBPersonalsummary:update")
public void updateDcaBPersonalsummary(@Valid DcaBPersonalsummary dcaBPersonalsummary)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBPersonalsummary.setModifyUserId(currentUser.getUserId());
        this.iDcaBPersonalsummaryService.updateDcaBPersonalsummary(dcaBPersonalsummary);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBPersonalsummary:delete")
public void deleteDcaBPersonalsummarys(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBPersonalsummaryService.deleteDcaBPersonalsummarys(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBPersonalsummary:export")
public void export(QueryRequest request, DcaBPersonalsummary dcaBPersonalsummary,HttpServletResponse response)throws FebsException{
        try{
        List<DcaBPersonalsummary> dcaBPersonalsummarys=this.iDcaBPersonalsummaryService.findDcaBPersonalsummarys(request, dcaBPersonalsummary).getRecords();
        ExcelKit.$Export(DcaBPersonalsummary.class,response).downXlsx(dcaBPersonalsummarys,false);
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBPersonalsummary detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBPersonalsummary dcaBPersonalsummary=this.iDcaBPersonalsummaryService.getById(id);
        return dcaBPersonalsummary;
        }
        }