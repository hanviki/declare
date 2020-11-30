package cc.mrbird.febs.dcacopy.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dcacopy.service.IDcaBCopyAuditfiveService;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyAuditfive;

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
@RequestMapping("dcaBCopyAuditfive")

public class DcaBCopyAuditfiveController extends BaseController{

private String message;
@Autowired
public IDcaBCopyAuditfiveService iDcaBCopyAuditfiveService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'近五年总体评价情况','/dca/DcaBCopyAuditfive/DcaBCopyAuditfive','dca/DcaBCopyAuditfive/DcaBCopyAuditfive','dcaBCopyAuditfive:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'近五年总体评价情况新增','dcaBCopyAuditfive:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'近五年总体评价情况编辑','dcaBCopyAuditfive:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'近五年总体评价情况删除','dcaBCopyAuditfive:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBCopyAuditfive 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBCopyAuditfive:view")
public Map<String, Object> List(QueryRequest request, DcaBCopyAuditfive dcaBCopyAuditfive){
        return getDataTable(this.iDcaBCopyAuditfiveService.findDcaBCopyAuditfives(request, dcaBCopyAuditfive));
        }

/**
 * 添加
 * @param  dcaBCopyAuditfive
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaBCopyAuditfive:add")
public void addDcaBCopyAuditfive(@Valid DcaBCopyAuditfive dcaBCopyAuditfive)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaBCopyAuditfive.setCreateUserId(currentUser.getUserId());
        this.iDcaBCopyAuditfiveService.createDcaBCopyAuditfive(dcaBCopyAuditfive);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBCopyAuditfive
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBCopyAuditfive:update")
public void updateDcaBCopyAuditfive(@Valid DcaBCopyAuditfive dcaBCopyAuditfive)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBCopyAuditfive.setModifyUserId(currentUser.getUserId());
        this.iDcaBCopyAuditfiveService.updateDcaBCopyAuditfive(dcaBCopyAuditfive);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBCopyAuditfive:delete")
public void deleteDcaBCopyAuditfives(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBCopyAuditfiveService.deleteDcaBCopyAuditfives(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBCopyAuditfive:export")
public void export(QueryRequest request, DcaBCopyAuditfive dcaBCopyAuditfive, HttpServletResponse response) throws FebsException {
        try {
        List<DcaBCopyAuditfive> dcaBCopyAuditfives = this.iDcaBCopyAuditfiveService.findDcaBCopyAuditfives(request, dcaBCopyAuditfive).getRecords();
        ExcelKit.$Export(DcaBCopyAuditfive.class, response).downXlsx(dcaBCopyAuditfives, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBCopyAuditfive detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBCopyAuditfive dcaBCopyAuditfive=this.iDcaBCopyAuditfiveService.getById(id);
        return dcaBCopyAuditfive;
        }
        }