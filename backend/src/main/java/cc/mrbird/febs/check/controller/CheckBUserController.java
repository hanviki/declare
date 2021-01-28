package cc.mrbird.febs.check.controller;

import cc.mrbird.febs.check.entity.CheckBAuditresult;
import cc.mrbird.febs.check.entity.CheckDTitle;
import cc.mrbird.febs.check.service.ICheckBAuditresultService;
import cc.mrbird.febs.check.service.ICheckBSettingService;
import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.check.service.ICheckBUserService;
import cc.mrbird.febs.check.entity.CheckBUser;

import cc.mrbird.febs.common.utils.ExportExcelUtils;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
 * @since 2021-01-22
 */
@Slf4j
@Validated
@RestController
@RequestMapping("checkBUser")

public class CheckBUserController extends BaseController{

private String message;
@Autowired
public ICheckBUserService iCheckBUserService;

@Autowired
public ICheckBAuditresultService iCheckBAuditresultService;

    @Autowired
    public ICheckBSettingService iCheckBSettingService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'待审核用户','/dca/CheckBUser/CheckBUser','dca/CheckBUser/CheckBUser','checkBUser:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'待审核用户新增','checkBUser:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'待审核用户编辑','checkBUser:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'待审核用户删除','checkBUser:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param checkBUser 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, CheckBUser checkBUser){
    request.setSortField("user_account");
    request.setSortOrder("ascend");
        return getDataTable(this.iCheckBUserService.findCheckBUsers(request, checkBUser));
        }

/**
 * 添加
 * @param  checkBUser
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addCheckBUser(@Valid CheckBUser checkBUser)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        checkBUser.setCreateUserId(currentUser.getUserId());
        this.iCheckBUserService.createCheckBUser(checkBUser);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param checkBUser
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("checkBUser:update")
public void updateCheckBUser(@Valid CheckBUser checkBUser)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      checkBUser.setModifyUserId(currentUser.getUserId());
        this.iCheckBUserService.updateCheckBUser(checkBUser);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("checkBUser:delete")
public void deleteCheckBUsers(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iCheckBUserService.deleteCheckBUsers(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
    @PostMapping("excel")
    public void export(QueryRequest request, CheckBUser dcaBSciencepublish,String dataJson,HttpServletResponse response)throws FebsException{
        try{
            request.setPageNum(1);
            request.setPageSize(10000);
            User currentUser = FebsUtil.getCurrentUser();

            request.setSortField("user_account");
            request.setSortOrder("ascend");
            LambdaQueryWrapper<CheckBUser> queryWrapper = new LambdaQueryWrapper<>();
            if(StringUtils.isNotBlank(dcaBSciencepublish.getDcaYear())){
                queryWrapper.eq(CheckBUser::getDcaYear,dcaBSciencepublish.getDcaYear());
            }
            List<CheckBUser> dcaBSciencepublishList=  this.iCheckBUserService.findCheckBUsers(request, dcaBSciencepublish).getRecords();
            //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
            ExportExcelUtils.exportCustomExcel_checkUser(response, dcaBSciencepublishList,dataJson,"");
        }catch(Exception e){
            message="导出Excel失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

@GetMapping("/{id}")
public CheckBUser detail(@NotBlank(message = "{required}") @PathVariable String id) {
    CheckBUser checkBUser=this.iCheckBUserService.getById(id);
        return checkBUser;
        }
    @GetMapping("user")
    public Map<String, Object> List(QueryRequest request, CheckBUser dcaUserAudit, int state) {
        User currentUser = FebsUtil.getCurrentUser();
        dcaUserAudit.setCreateUserId(currentUser.getUserId());
        return getDataTable(this.iCheckBUserService.findDcaBUsersAudit(request,currentUser.getUsername() ,dcaUserAudit, state));
    }
    @PostMapping("excel2")
    public void export(QueryRequest request, CheckBUser dcaBUser,String dataJson,int state,HttpServletResponse response)throws FebsException{
        try{
            request.setPageNum(1);
            request.setPageSize(10000);
            User currentUser = FebsUtil.getCurrentUser();
            dcaBUser.setCreateUserId(currentUser.getUserId());
            List<CheckBUser> dcaBAuditdynamics=this.iCheckBUserService.findDcaBUsersAuditCustom(request,currentUser.getUsername(), dcaBUser,state).getRecords();
          //  LambdaQueryWrapper<CheckBAuditresult> queryWrapper = new LambdaQueryWrapper<>();
          //  queryWrapper.apply("check_b_auditresult.ks in (select ks from check_b_setting where user_account='"+currentUser.getUsername()+"')");
            List<CheckBAuditresult> listDynamic= this.iCheckBAuditresultService.getAll(currentUser.getUsername(),"");
            List<CheckDTitle> list = this.iCheckBSettingService.getTitleByUserAccount(currentUser.getUsername());
            //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
            ExportExcelUtils.exportCustomExcelCutome_check(response,list,currentUser.getUsername(), dcaBAuditdynamics,dataJson,listDynamic,"");
        }catch(Exception e){
            message="导出Excel失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }
        }