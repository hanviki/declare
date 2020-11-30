package cc.mrbird.febs.dcacopy.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dcacopy.service.IDcaBCopyTeacherqualifyService;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyTeacherqualify;

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
@RequestMapping("dcaBCopyTeacherqualify")

public class DcaBCopyTeacherqualifyController extends BaseController{

private String message;
@Autowired
public IDcaBCopyTeacherqualifyService iDcaBCopyTeacherqualifyService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'教师资格证书(教师系列需填写)','/dca/DcaBCopyTeacherqualify/DcaBCopyTeacherqualify','dca/DcaBCopyTeacherqualify/DcaBCopyTeacherqualify','dcaBCopyTeacherqualify:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'教师资格证书(教师系列需填写)新增','dcaBCopyTeacherqualify:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'教师资格证书(教师系列需填写)编辑','dcaBCopyTeacherqualify:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'教师资格证书(教师系列需填写)删除','dcaBCopyTeacherqualify:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBCopyTeacherqualify 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBCopyTeacherqualify:view")
public Map<String, Object> List(QueryRequest request, DcaBCopyTeacherqualify dcaBCopyTeacherqualify){
        return getDataTable(this.iDcaBCopyTeacherqualifyService.findDcaBCopyTeacherqualifys(request, dcaBCopyTeacherqualify));
        }

/**
 * 添加
 * @param  dcaBCopyTeacherqualify
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaBCopyTeacherqualify:add")
public void addDcaBCopyTeacherqualify(@Valid DcaBCopyTeacherqualify dcaBCopyTeacherqualify)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaBCopyTeacherqualify.setCreateUserId(currentUser.getUserId());
        this.iDcaBCopyTeacherqualifyService.createDcaBCopyTeacherqualify(dcaBCopyTeacherqualify);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBCopyTeacherqualify
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBCopyTeacherqualify:update")
public void updateDcaBCopyTeacherqualify(@Valid DcaBCopyTeacherqualify dcaBCopyTeacherqualify)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBCopyTeacherqualify.setModifyUserId(currentUser.getUserId());
        this.iDcaBCopyTeacherqualifyService.updateDcaBCopyTeacherqualify(dcaBCopyTeacherqualify);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBCopyTeacherqualify:delete")
public void deleteDcaBCopyTeacherqualifys(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBCopyTeacherqualifyService.deleteDcaBCopyTeacherqualifys(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBCopyTeacherqualify:export")
public void export(QueryRequest request, DcaBCopyTeacherqualify dcaBCopyTeacherqualify, HttpServletResponse response) throws FebsException {
        try {
        List<DcaBCopyTeacherqualify> dcaBCopyTeacherqualifys = this.iDcaBCopyTeacherqualifyService.findDcaBCopyTeacherqualifys(request, dcaBCopyTeacherqualify).getRecords();
        ExcelKit.$Export(DcaBCopyTeacherqualify.class, response).downXlsx(dcaBCopyTeacherqualifys, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBCopyTeacherqualify detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBCopyTeacherqualify dcaBCopyTeacherqualify=this.iDcaBCopyTeacherqualifyService.getById(id);
        return dcaBCopyTeacherqualify;
        }
        }