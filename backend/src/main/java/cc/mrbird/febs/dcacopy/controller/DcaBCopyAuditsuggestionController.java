package cc.mrbird.febs.dcacopy.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dcacopy.service.IDcaBCopyAuditsuggestionService;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyAuditsuggestion;

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
 * @since 2020-11-26
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBCopyAuditsuggestion")

public class DcaBCopyAuditsuggestionController extends BaseController{

private String message;
@Autowired
public IDcaBCopyAuditsuggestionService iDcaBCopyAuditsuggestionService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'审核意见表','/dca/DcaBCopyAuditsuggestion/DcaBCopyAuditsuggestion','dca/DcaBCopyAuditsuggestion/DcaBCopyAuditsuggestion','dcaBCopyAuditsuggestion:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'审核意见表新增','dcaBCopyAuditsuggestion:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'审核意见表编辑','dcaBCopyAuditsuggestion:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'审核意见表删除','dcaBCopyAuditsuggestion:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBCopyAuditsuggestion 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBCopyAuditsuggestion:view")
public Map<String, Object> List(QueryRequest request, DcaBCopyAuditsuggestion dcaBCopyAuditsuggestion){
        return getDataTable(this.iDcaBCopyAuditsuggestionService.findDcaBCopyAuditsuggestions(request, dcaBCopyAuditsuggestion));
        }

/**
 * 添加
 * @param  dcaBCopyAuditsuggestion
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaBCopyAuditsuggestion:add")
public void addDcaBCopyAuditsuggestion(@Valid DcaBCopyAuditsuggestion dcaBCopyAuditsuggestion)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaBCopyAuditsuggestion.setCreateUserId(currentUser.getUserId());
        this.iDcaBCopyAuditsuggestionService.createDcaBCopyAuditsuggestion(dcaBCopyAuditsuggestion);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBCopyAuditsuggestion
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBCopyAuditsuggestion:update")
public void updateDcaBCopyAuditsuggestion(@Valid DcaBCopyAuditsuggestion dcaBCopyAuditsuggestion)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBCopyAuditsuggestion.setModifyUserId(currentUser.getUserId());
        this.iDcaBCopyAuditsuggestionService.updateDcaBCopyAuditsuggestion(dcaBCopyAuditsuggestion);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBCopyAuditsuggestion:delete")
public void deleteDcaBCopyAuditsuggestions(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBCopyAuditsuggestionService.deleteDcaBCopyAuditsuggestions(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBCopyAuditsuggestion:export")
public void export(QueryRequest request, DcaBCopyAuditsuggestion dcaBCopyAuditsuggestion, HttpServletResponse response) throws FebsException {
        try {
        List<DcaBCopyAuditsuggestion> dcaBCopyAuditsuggestions = this.iDcaBCopyAuditsuggestionService.findDcaBCopyAuditsuggestions(request, dcaBCopyAuditsuggestion).getRecords();
        ExcelKit.$Export(DcaBCopyAuditsuggestion.class, response).downXlsx(dcaBCopyAuditsuggestions, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBCopyAuditsuggestion detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBCopyAuditsuggestion dcaBCopyAuditsuggestion=this.iDcaBCopyAuditsuggestionService.getById(id);
        return dcaBCopyAuditsuggestion;
        }
        }