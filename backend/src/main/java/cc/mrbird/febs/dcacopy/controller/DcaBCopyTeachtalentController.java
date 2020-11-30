package cc.mrbird.febs.dcacopy.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dcacopy.service.IDcaBCopyTeachtalentService;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyTeachtalent;

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
@RequestMapping("dcaBCopyTeachtalent")

public class DcaBCopyTeachtalentController extends BaseController{

private String message;
@Autowired
public IDcaBCopyTeachtalentService iDcaBCopyTeachtalentService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'任现职以来完成教学、人才培养情况','/dca/DcaBCopyTeachtalent/DcaBCopyTeachtalent','dca/DcaBCopyTeachtalent/DcaBCopyTeachtalent','dcaBCopyTeachtalent:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'任现职以来完成教学、人才培养情况新增','dcaBCopyTeachtalent:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'任现职以来完成教学、人才培养情况编辑','dcaBCopyTeachtalent:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'任现职以来完成教学、人才培养情况删除','dcaBCopyTeachtalent:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBCopyTeachtalent 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBCopyTeachtalent:view")
public Map<String, Object> List(QueryRequest request, DcaBCopyTeachtalent dcaBCopyTeachtalent){
        return getDataTable(this.iDcaBCopyTeachtalentService.findDcaBCopyTeachtalents(request, dcaBCopyTeachtalent));
        }

/**
 * 添加
 * @param  dcaBCopyTeachtalent
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaBCopyTeachtalent:add")
public void addDcaBCopyTeachtalent(@Valid DcaBCopyTeachtalent dcaBCopyTeachtalent)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaBCopyTeachtalent.setCreateUserId(currentUser.getUserId());
        this.iDcaBCopyTeachtalentService.createDcaBCopyTeachtalent(dcaBCopyTeachtalent);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBCopyTeachtalent
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBCopyTeachtalent:update")
public void updateDcaBCopyTeachtalent(@Valid DcaBCopyTeachtalent dcaBCopyTeachtalent)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBCopyTeachtalent.setModifyUserId(currentUser.getUserId());
        this.iDcaBCopyTeachtalentService.updateDcaBCopyTeachtalent(dcaBCopyTeachtalent);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBCopyTeachtalent:delete")
public void deleteDcaBCopyTeachtalents(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBCopyTeachtalentService.deleteDcaBCopyTeachtalents(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBCopyTeachtalent:export")
public void export(QueryRequest request, DcaBCopyTeachtalent dcaBCopyTeachtalent, HttpServletResponse response) throws FebsException {
        try {
        List<DcaBCopyTeachtalent> dcaBCopyTeachtalents = this.iDcaBCopyTeachtalentService.findDcaBCopyTeachtalents(request, dcaBCopyTeachtalent).getRecords();
        ExcelKit.$Export(DcaBCopyTeachtalent.class, response).downXlsx(dcaBCopyTeachtalents, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBCopyTeachtalent detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBCopyTeachtalent dcaBCopyTeachtalent=this.iDcaBCopyTeachtalentService.getById(id);
        return dcaBCopyTeachtalent;
        }
        }