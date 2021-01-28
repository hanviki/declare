package cc.mrbird.febs.check.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.check.service.ICheckDTitleService;
import cc.mrbird.febs.check.entity.CheckDTitle;

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
@RequestMapping("checkDTitle")

public class CheckDTitleController extends BaseController{

private String message;
@Autowired
public ICheckDTitleService iCheckDTitleService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'指标表','/dca/CheckDTitle/CheckDTitle','dca/CheckDTitle/CheckDTitle','checkDTitle:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'指标表新增','checkDTitle:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'指标表编辑','checkDTitle:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'指标表删除','checkDTitle:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param checkDTitle 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("checkDTitle:view")
public Map<String, Object> List(QueryRequest request, CheckDTitle checkDTitle){
        return getDataTable(this.iCheckDTitleService.findCheckDTitles(request, checkDTitle));
        }

/**
 * 添加
 * @param  checkDTitle
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("checkDTitle:add")
public void addCheckDTitle(@Valid CheckDTitle checkDTitle)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        checkDTitle.setCreateUserId(currentUser.getUserId());
        this.iCheckDTitleService.createCheckDTitle(checkDTitle);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param checkDTitle
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("checkDTitle:update")
public void updateCheckDTitle(@Valid CheckDTitle checkDTitle)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      checkDTitle.setModifyUserId(currentUser.getUserId());
        this.iCheckDTitleService.updateCheckDTitle(checkDTitle);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("checkDTitle:delete")
public void deleteCheckDTitles(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iCheckDTitleService.deleteCheckDTitles(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("checkDTitle:export")
public void export(QueryRequest request, CheckDTitle checkDTitle, HttpServletResponse response) throws FebsException {
        try {
        List<CheckDTitle> checkDTitles = this.iCheckDTitleService.findCheckDTitles(request, checkDTitle).getRecords();
        ExcelKit.$Export(CheckDTitle.class, response).downXlsx(checkDTitles, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public CheckDTitle detail(@NotBlank(message = "{required}") @PathVariable String id) {
    CheckDTitle checkDTitle=this.iCheckDTitleService.getById(id);
        return checkDTitle;
        }
        }