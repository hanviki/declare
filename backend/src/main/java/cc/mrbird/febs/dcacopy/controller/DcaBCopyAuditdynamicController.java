package cc.mrbird.febs.dcacopy.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dcacopy.service.IDcaBCopyAuditdynamicService;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyAuditdynamic;

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
 * @since 2020-11-23
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBCopyAuditdynamic")

public class DcaBCopyAuditdynamicController extends BaseController{

private String message;
@Autowired
public IDcaBCopyAuditdynamicService iDcaBCopyAuditdynamicService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/dca/DcaBCopyAuditdynamic/DcaBCopyAuditdynamic','dca/DcaBCopyAuditdynamic/DcaBCopyAuditdynamic','dcaBCopyAuditdynamic:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'新增','dcaBCopyAuditdynamic:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'编辑','dcaBCopyAuditdynamic:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'删除','dcaBCopyAuditdynamic:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBCopyAuditdynamic 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, DcaBCopyAuditdynamic dcaBCopyAuditdynamic){
        return getDataTable(this.iDcaBCopyAuditdynamicService.findDcaBCopyAuditdynamics(request, dcaBCopyAuditdynamic));
        }

    @GetMapping("account")
    public List<DcaBCopyAuditdynamic> List2(@Valid String userAccount,String dcaYear){
        return this.iDcaBCopyAuditdynamicService.GetAllInfo(userAccount,dcaYear);
    }

/**
 * 添加
 * @param  dcaBCopyAuditdynamic
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaBCopyAuditdynamic:add")
public void addDcaBCopyAuditdynamic(@Valid DcaBCopyAuditdynamic dcaBCopyAuditdynamic)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaBCopyAuditdynamic.setCreateUserId(currentUser.getUserId());
        this.iDcaBCopyAuditdynamicService.createDcaBCopyAuditdynamic(dcaBCopyAuditdynamic);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBCopyAuditdynamic
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBCopyAuditdynamic:update")
public void updateDcaBCopyAuditdynamic(@Valid DcaBCopyAuditdynamic dcaBCopyAuditdynamic)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBCopyAuditdynamic.setModifyUserId(currentUser.getUserId());
        this.iDcaBCopyAuditdynamicService.updateDcaBCopyAuditdynamic(dcaBCopyAuditdynamic);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBCopyAuditdynamic:delete")
public void deleteDcaBCopyAuditdynamics(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBCopyAuditdynamicService.deleteDcaBCopyAuditdynamics(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBCopyAuditdynamic:export")
public void export(QueryRequest request, DcaBCopyAuditdynamic dcaBCopyAuditdynamic, HttpServletResponse response) throws FebsException {
        try {
        List<DcaBCopyAuditdynamic> dcaBCopyAuditdynamics = this.iDcaBCopyAuditdynamicService.findDcaBCopyAuditdynamics(request, dcaBCopyAuditdynamic).getRecords();
        ExcelKit.$Export(DcaBCopyAuditdynamic.class, response).downXlsx(dcaBCopyAuditdynamics, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBCopyAuditdynamic detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBCopyAuditdynamic dcaBCopyAuditdynamic=this.iDcaBCopyAuditdynamicService.getById(id);
        return dcaBCopyAuditdynamic;
        }
        }