package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.common.utils.ExportExcelUtils;
import cc.mrbird.febs.dca.entity.*;
import cc.mrbird.febs.dca.service.*;

import cc.mrbird.febs.dcacopy.service.IDcaBCopyAuditfiveService;
import cc.mrbird.febs.dcacopy.service.IDcaBCopyParttimejobService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
 * @since 2020-10-15
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBUser")

public class DcaBUserController extends BaseController{

private String message;
@Autowired
public IDcaBUserService iDcaBUserService;
    @Autowired
    public IDcaBAcademicService iDcaBAcademicService;
    @Autowired
    public IDcaBParttimejobService iDcaBParttimejobService;
    @Autowired
    public IDcaBAuditfiveService iDcaBAuditfiveService;
    @Autowired
    public IDcaBExportcountryService iDcaBExportcountryService;

    @Autowired
    public IDcaBPrizeorpunishService iDcaBPrizeorpunishService;

    @Autowired
    public IDcaBEducationexpericeService iDcaBEducationexpericeService;

/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBUser 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, DcaBUser dcaBUser){
        return getDataTable(this.iDcaBUserService.findDcaBUserswithDoctor(request, dcaBUser));
        }
    @GetMapping("person")
    public List<DcaBUser> List3(){
        User currentUser= FebsUtil.getCurrentUser();
        return this.iDcaBUserService.findPerson(currentUser.getUsername());
    }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBUser dcaBUser){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBUser.setUserAccount(currentUser.getUsername());
    dcaBUser.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("state");
        request.setSortOrder("descend");
        return getDataTable(this.iDcaBUserService.findDcaBUsers(request, dcaBUser));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBUser dcaBUser){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBUser.setIsDeletemark(1);
        request.setSortField("user_account");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBUserService.findDcaBUsers(request, dcaBUser));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBUserCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBUser> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBUser>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBUserService.deleteByuseraccount(currentUser.getUsername());
        for(DcaBUser dcaBUser:list
        ){
        if(dcaBUser.getState()!=null&&dcaBUser.getState().equals(3)) {
    dcaBUser.setState(3);
        }
        else{
    dcaBUser.setState(state);
        }
    dcaBUser.setCreateUserId(currentUser.getUserId());
    dcaBUser.setUserAccount(currentUser.getUsername());
    dcaBUser.setUserAccountName(currentUser.getRealname());
        this.iDcaBUserService.createDcaBUser(dcaBUser);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBUser(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBUser dcaBUser= JSON.parseObject(jsonStr, new TypeReference<DcaBUser>() {
        });
    dcaBUser.setState(state);
    dcaBUser.setAuditMan(currentUser.getUsername());
    dcaBUser.setAuditManName(currentUser.getRealname());
    dcaBUser.setAuditDate(DateUtil.date());
        this.iDcaBUserService.updateDcaBUser(dcaBUser);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBUser
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBUser(@Valid DcaBUser dcaBUser)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBUser.setCreateUserId(currentUser.getUserId());
    dcaBUser.setUserAccount(currentUser.getUsername());
        this.iDcaBUserService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBUserService.createDcaBUser(dcaBUser);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBUser
 * @return
 */
@Log("修改")
@PutMapping
public void updateDcaBUser(@Valid DcaBUser dcaBUser)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBUser.setModifyUserId(currentUser.getUserId());
        this.iDcaBUserService.updateDcaBUser(dcaBUser);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
public void deleteDcaBUsers(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBUserService.deleteDcaBUsers(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
public void export(QueryRequest request, DcaBUser dcaBUser,HttpServletResponse response)throws FebsException{
        try{
            request.setPageNum(1);
            request.setPageSize(10000);
        List<DcaBUser> dcaBUsers=this.iDcaBUserService.findDcaBUsersAll(request, dcaBUser).getRecords();
        ExcelKit.$Export(DcaBUser.class,response).downXlsx(dcaBUsers,false);
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
    @PostMapping("excel2")
    public void export(QueryRequest request, DcaBUser dcaBSciencepublish,String dataJson,HttpServletResponse response)throws FebsException{
        try{
            request.setPageNum(1);
            request.setPageSize(10000);
            User currentUser = FebsUtil.getCurrentUser();

            dcaBSciencepublish.setIsDeletemark(1);
            request.setSortField("user_account asc,state asc,display_Index");
            request.setSortOrder("ascend");
            List<DcaBUser> dcaBSciencepublishList=  this.iDcaBUserService.findDcaBUsers(request, dcaBSciencepublish).getRecords();


            //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
            ExportExcelUtils.exportCustomExcel_han(response, dcaBSciencepublishList,dataJson,"");
        }catch(Exception e){
            message="导出Excel失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }
@GetMapping("/{id}")
public DcaBUser detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBUser dcaBUser=this.iDcaBUserService.getById(id);
        return dcaBUser;
        }


    @GetMapping("getBaseInfo/{userAccount}")
    public UserInfo getBaseInfo(@NotBlank(message = "{required}") @PathVariable String userAccount){
        UserInfo userInfo= new UserInfo();
        LambdaQueryWrapper<DcaBAcademic> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBAcademic::getIsDeletemark, 1);//1是未删 0是已删
        queryWrapper.eq(DcaBAcademic::getUserAccount,userAccount);
        List<DcaBAcademic> academicList=this.iDcaBAcademicService.list(queryWrapper);

        LambdaQueryWrapper<DcaBEducationexperice> queryWrapper2=new LambdaQueryWrapper<>();
        queryWrapper2.eq(DcaBEducationexperice::getIsDeletemark, 1);//1是未删 0是已删
        queryWrapper2.eq(DcaBEducationexperice::getUserAccount,userAccount);
        List<DcaBEducationexperice> eduList=this.iDcaBEducationexpericeService.list(queryWrapper2);

        LambdaQueryWrapper<DcaBExportcountry> queryWrapper3=new LambdaQueryWrapper<>();
        queryWrapper3.eq(DcaBExportcountry::getIsDeletemark, 1);//1是未删 0是已删
        queryWrapper3.eq(DcaBExportcountry::getUserAccount,userAccount);
        List<DcaBExportcountry> boradList=this.iDcaBExportcountryService.list(queryWrapper3);


        LambdaQueryWrapper<DcaBPrizeorpunish> queryWrapper4=new LambdaQueryWrapper<>();
        queryWrapper4.eq(DcaBPrizeorpunish::getIsDeletemark, 1);//1是未删 0是已删
        queryWrapper4.eq(DcaBPrizeorpunish::getUserAccount,userAccount);
        List<DcaBPrizeorpunish> ppList=this.iDcaBPrizeorpunishService.list(queryWrapper4);

        LambdaQueryWrapper<DcaBAuditfive> queryWrapper5=new LambdaQueryWrapper<>();
        queryWrapper5.eq(DcaBAuditfive::getIsDeletemark, 1);//1是未删 0是已删
        queryWrapper5.eq(DcaBAuditfive::getUserAccount,userAccount);
        List<DcaBAuditfive> auditList=this.iDcaBAuditfiveService.list(queryWrapper5);

        LambdaQueryWrapper<DcaBParttimejob> queryWrapper6=new LambdaQueryWrapper<>();
        queryWrapper6.eq(DcaBParttimejob::getIsDeletemark, 1);//1是未删 0是已删
        queryWrapper6.eq(DcaBParttimejob::getUserAccount,userAccount);
        List<DcaBParttimejob> parttimejobList=this.iDcaBParttimejobService.list(queryWrapper6);

        userInfo.setAcdemicList(academicList);
        userInfo.setAuditList(auditList);
        userInfo.setBoardList(boradList);
        userInfo.setEduList(eduList);
        userInfo.setPartjobList(parttimejobList);
        userInfo.setPunishOrPrizeList(ppList);
       return  userInfo;
    }
        }