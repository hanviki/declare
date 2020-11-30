package cc.mrbird.febs.dcacopy.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dcacopy.service.IDcaBCopyAttachfileService;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyAttachfile;

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
@RequestMapping("dcaBCopyAttachfile")

public class DcaBCopyAttachfileController extends BaseController{

private String message;
@Autowired
public IDcaBCopyAttachfileService iDcaBCopyAttachfileService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'其他附件材料','/dca/DcaBCopyAttachfile/DcaBCopyAttachfile','dca/DcaBCopyAttachfile/DcaBCopyAttachfile','dcaBCopyAttachfile:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'其他附件材料新增','dcaBCopyAttachfile:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'其他附件材料编辑','dcaBCopyAttachfile:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'其他附件材料删除','dcaBCopyAttachfile:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBCopyAttachfile 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBCopyAttachfile:view")
public Map<String, Object> List(QueryRequest request, DcaBCopyAttachfile dcaBCopyAttachfile){
        return getDataTable(this.iDcaBCopyAttachfileService.findDcaBCopyAttachfiles(request, dcaBCopyAttachfile));
        }

/**
 * 添加
 * @param  dcaBCopyAttachfile
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaBCopyAttachfile:add")
public void addDcaBCopyAttachfile(@Valid DcaBCopyAttachfile dcaBCopyAttachfile)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaBCopyAttachfile.setCreateUserId(currentUser.getUserId());
        this.iDcaBCopyAttachfileService.createDcaBCopyAttachfile(dcaBCopyAttachfile);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBCopyAttachfile
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBCopyAttachfile:update")
public void updateDcaBCopyAttachfile(@Valid DcaBCopyAttachfile dcaBCopyAttachfile)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBCopyAttachfile.setModifyUserId(currentUser.getUserId());
        this.iDcaBCopyAttachfileService.updateDcaBCopyAttachfile(dcaBCopyAttachfile);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBCopyAttachfile:delete")
public void deleteDcaBCopyAttachfiles(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBCopyAttachfileService.deleteDcaBCopyAttachfiles(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBCopyAttachfile:export")
public void export(QueryRequest request, DcaBCopyAttachfile dcaBCopyAttachfile, HttpServletResponse response) throws FebsException {
        try {
        List<DcaBCopyAttachfile> dcaBCopyAttachfiles = this.iDcaBCopyAttachfileService.findDcaBCopyAttachfiles(request, dcaBCopyAttachfile).getRecords();
        ExcelKit.$Export(DcaBCopyAttachfile.class, response).downXlsx(dcaBCopyAttachfiles, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBCopyAttachfile detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBCopyAttachfile dcaBCopyAttachfile=this.iDcaBCopyAttachfileService.getById(id);
        return dcaBCopyAttachfile;
        }
        }