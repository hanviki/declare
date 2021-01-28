package cc.mrbird.febs.check.controller;

import cc.mrbird.febs.check.entity.CheckDTitle;
import cc.mrbird.febs.check.entity.CheckShowTitle;
import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.check.service.ICheckBSettingService;
import cc.mrbird.febs.check.entity.CheckBSetting;

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
 * @since 2021-01-22
 */
@Slf4j
@Validated
@RestController
@RequestMapping("checkBSetting")

public class CheckBSettingController extends BaseController{

private String message;
@Autowired
public ICheckBSettingService iCheckBSettingService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'指标配置表','/dca/CheckBSetting/CheckBSetting','dca/CheckBSetting/CheckBSetting','checkBSetting:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'指标配置表新增','checkBSetting:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'指标配置表编辑','checkBSetting:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'指标配置表删除','checkBSetting:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param checkBSetting 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("checkBSetting:view")
public Map<String, Object> List(QueryRequest request, CheckBSetting checkBSetting){
        return getDataTable(this.iCheckBSettingService.findCheckBSettings(request, checkBSetting));
        }

/**
 * 添加
 * @param  checkBSetting
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("checkBSetting:add")
public void addCheckBSetting(@Valid CheckBSetting checkBSetting)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        checkBSetting.setCreateUserId(currentUser.getUserId());
        this.iCheckBSettingService.createCheckBSetting(checkBSetting);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param checkBSetting
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("checkBSetting:update")
public void updateCheckBSetting(@Valid CheckBSetting checkBSetting)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      checkBSetting.setModifyUserId(currentUser.getUserId());
        this.iCheckBSettingService.updateCheckBSetting(checkBSetting);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("checkBSetting:delete")
public void deleteCheckBSettings(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iCheckBSettingService.deleteCheckBSettings(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("checkBSetting:export")
public void export(QueryRequest request, CheckBSetting checkBSetting, HttpServletResponse response) throws FebsException {
        try {
        List<CheckBSetting> checkBSettings = this.iCheckBSettingService.findCheckBSettings(request, checkBSetting).getRecords();
        ExcelKit.$Export(CheckBSetting.class, response).downXlsx(checkBSettings, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public CheckBSetting detail(@NotBlank(message = "{required}") @PathVariable String id) {
    CheckBSetting checkBSetting=this.iCheckBSettingService.getById(id);
        return checkBSetting;
        }
    @GetMapping("userCheck")
    public List<CheckDTitle> ListByUseraccount(){
        User currentUser=FebsUtil.getCurrentUser();
        List<CheckDTitle> list = this.iCheckBSettingService.getTitleByUserAccount(currentUser.getUsername());
        return list;
    }

    @GetMapping("userAll")
    public List<CheckShowTitle> UserAll(){
        User currentUser=FebsUtil.getCurrentUser();
        List<CheckShowTitle> list = this.iCheckBSettingService.findAllTitle();
        return list;
    }
        }