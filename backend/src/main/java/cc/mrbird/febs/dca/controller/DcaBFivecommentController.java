package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.service.IDcaBFivecommentService;
import cc.mrbird.febs.dca.entity.DcaBFivecomment;

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
 * @since 2020-08-13
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBFivecomment")

public class DcaBFivecommentController extends BaseController{

private String message;
@Autowired
public IDcaBFivecommentService iDcaBFivecommentService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBFivecomment 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBFivecomment:view")
public Map<String, Object> List(QueryRequest request, DcaBFivecomment dcaBFivecomment){
        return getDataTable(this.iDcaBFivecommentService.findDcaBFivecomments(request, dcaBFivecomment));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBFivecomment dcaBFivecomment){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBFivecomment.setUserAccount(currentUser.getUsername());
    dcaBFivecomment.setIsDeletemark(1);
        return getDataTable(this.iDcaBFivecommentService.findDcaBFivecomments(request, dcaBFivecomment));
        }

@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBFivecommentCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBFivecomment> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBFivecomment>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBFivecommentService.deleteByuseraccount(currentUser.getUsername());
        for(DcaBFivecomment dcaBFivecomment:list
        ){
    dcaBFivecomment.setState (state);
    dcaBFivecomment.setCreateUserId(currentUser.getUserId());
    dcaBFivecomment.setUserAccount(currentUser.getUsername());
        this.iDcaBFivecommentService.createDcaBFivecomment(dcaBFivecomment);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
/**
 * 添加
 * @param  dcaBFivecomment
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBFivecomment(@Valid DcaBFivecomment dcaBFivecomment)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBFivecomment.setCreateUserId(currentUser.getUserId());
    dcaBFivecomment.setUserAccount(currentUser.getUsername());
        this.iDcaBFivecommentService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBFivecommentService.createDcaBFivecomment(dcaBFivecomment);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBFivecomment
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBFivecomment:update")
public void updateDcaBFivecomment(@Valid DcaBFivecomment dcaBFivecomment)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBFivecomment.setModifyUserId(currentUser.getUserId());
        this.iDcaBFivecommentService.updateDcaBFivecomment(dcaBFivecomment);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBFivecomment:delete")
public void deleteDcaBFivecomments(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBFivecommentService.deleteDcaBFivecomments(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBFivecomment:export")
public void export(QueryRequest request, DcaBFivecomment dcaBFivecomment,HttpServletResponse response)throws FebsException{
        try{
        List<DcaBFivecomment> dcaBFivecomments=this.iDcaBFivecommentService.findDcaBFivecomments(request, dcaBFivecomment).getRecords();
        ExcelKit.$Export(DcaBFivecomment.class,response).downXlsx(dcaBFivecomments,false);
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBFivecomment detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBFivecomment dcaBFivecomment=this.iDcaBFivecommentService.getById(id);
        return dcaBFivecomment;
        }
        }