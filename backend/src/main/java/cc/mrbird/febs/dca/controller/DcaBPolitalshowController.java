package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.service.IDcaBPolitalshowService;
import cc.mrbird.febs.dca.entity.DcaBPolitalshow;

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
@RequestMapping("dcaBPolitalshow")

public class DcaBPolitalshowController extends BaseController{

private String message;
@Autowired
public IDcaBPolitalshowService iDcaBPolitalshowService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBPolitalshow 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBPolitalshow:view")
public Map<String, Object> List(QueryRequest request, DcaBPolitalshow dcaBPolitalshow){
        return getDataTable(this.iDcaBPolitalshowService.findDcaBPolitalshows(request, dcaBPolitalshow));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBPolitalshow dcaBPolitalshow){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBPolitalshow.setUserAccount(currentUser.getUsername());
    dcaBPolitalshow.setIsDeletemark(1);
        return getDataTable(this.iDcaBPolitalshowService.findDcaBPolitalshows(request, dcaBPolitalshow));
        }

/**
 * 添加
 * @param  dcaBPolitalshow
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBPolitalshow(@Valid DcaBPolitalshow dcaBPolitalshow)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBPolitalshow.setCreateUserId(currentUser.getUserId());
    dcaBPolitalshow.setUserAccount(currentUser.getUsername());
            this.iDcaBPolitalshowService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBPolitalshowService.createDcaBPolitalshow(dcaBPolitalshow);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
/**
 * 修改
 * @param dcaBPolitalshow
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBPolitalshow:update")
public void updateDcaBPolitalshow(@Valid DcaBPolitalshow dcaBPolitalshow)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBPolitalshow.setModifyUserId(currentUser.getUserId());
        this.iDcaBPolitalshowService.updateDcaBPolitalshow(dcaBPolitalshow);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBPolitalshow:delete")
public void deleteDcaBPolitalshows(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBPolitalshowService.deleteDcaBPolitalshows(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBPolitalshow:export")
public void export(QueryRequest request, DcaBPolitalshow dcaBPolitalshow,HttpServletResponse response)throws FebsException{
        try{
        List<DcaBPolitalshow> dcaBPolitalshows=this.iDcaBPolitalshowService.findDcaBPolitalshows(request, dcaBPolitalshow).getRecords();
        ExcelKit.$Export(DcaBPolitalshow.class,response).downXlsx(dcaBPolitalshows,false);
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBPolitalshow detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBPolitalshow dcaBPolitalshow=this.iDcaBPolitalshowService.getById(id);
        return dcaBPolitalshow;
        }
        }