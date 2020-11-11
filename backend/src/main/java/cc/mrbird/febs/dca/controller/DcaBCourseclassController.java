package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.service.IDcaBCourseclassService;
import cc.mrbird.febs.dca.entity.DcaBCourseclass;

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
@RequestMapping("dcaBCourseclass")

public class DcaBCourseclassController extends BaseController{

private String message;
@Autowired
public IDcaBCourseclassService iDcaBCourseclassService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBCourseclass 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBCourseclass:view")
public Map<String, Object> List(QueryRequest request, DcaBCourseclass dcaBCourseclass){
        return getDataTable(this.iDcaBCourseclassService.findDcaBCourseclasss(request, dcaBCourseclass));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBCourseclass dcaBCourseclass){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBCourseclass.setUserAccount(currentUser.getUsername());
    dcaBCourseclass.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBCourseclassService.findDcaBCourseclasss(request, dcaBCourseclass));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBCourseclass dcaBCourseclass){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBCourseclass.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBCourseclassService.findDcaBCourseclasss(request, dcaBCourseclass));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBCourseclassCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBCourseclass> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBCourseclass>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBCourseclassService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBCourseclassService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBCourseclass dcaBCourseclass:list
        ){
        if(dcaBCourseclass.getState()!=null&&dcaBCourseclass.getState().equals(3)) {
    dcaBCourseclass.setState(3);
        }
        else{
    dcaBCourseclass.setState(state);
        }
    dcaBCourseclass.setDisplayIndex(display);
        display+=1;
    dcaBCourseclass.setCreateUserId(currentUser.getUserId());
    dcaBCourseclass.setUserAccount(currentUser.getUsername());
    dcaBCourseclass.setUserAccountName(currentUser.getRealname());
        this.iDcaBCourseclassService.createDcaBCourseclass(dcaBCourseclass);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBCourseclass(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBCourseclass dcaBCourseclass= JSON.parseObject(jsonStr, new TypeReference<DcaBCourseclass>() {
        });
    dcaBCourseclass.setState(state);
    dcaBCourseclass.setAuditMan(currentUser.getUsername());
    dcaBCourseclass.setAuditManName(currentUser.getRealname());
    dcaBCourseclass.setAuditDate(DateUtil.date());
        this.iDcaBCourseclassService.updateDcaBCourseclass(dcaBCourseclass);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBCourseclass
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBCourseclass(@Valid DcaBCourseclass dcaBCourseclass)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBCourseclass.setCreateUserId(currentUser.getUserId());
    dcaBCourseclass.setUserAccount(currentUser.getUsername());
        this.iDcaBCourseclassService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBCourseclassService.createDcaBCourseclass(dcaBCourseclass);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBCourseclass
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBCourseclass:update")
public void updateDcaBCourseclass(@Valid DcaBCourseclass dcaBCourseclass)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBCourseclass.setModifyUserId(currentUser.getUserId());
        this.iDcaBCourseclassService.updateDcaBCourseclass(dcaBCourseclass);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBCourseclass:delete")
public void deleteDcaBCourseclasss(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBCourseclassService.deleteDcaBCourseclasss(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBCourseclass:export")
public void export(QueryRequest request, DcaBCourseclass dcaBCourseclass,HttpServletResponse response)throws FebsException{
        try{
        List<DcaBCourseclass> dcaBCourseclasss=this.iDcaBCourseclassService.findDcaBCourseclasss(request, dcaBCourseclass).getRecords();
        ExcelKit.$Export(DcaBCourseclass.class,response).downXlsx(dcaBCourseclasss,false);
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBCourseclass detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBCourseclass dcaBCourseclass=this.iDcaBCourseclassService.getById(id);
        return dcaBCourseclass;
        }
        }