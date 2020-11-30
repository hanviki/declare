package cc.mrbird.febs.dcacopy.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dcacopy.service.IDcaBCopyPatentService;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyPatent;

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
@RequestMapping("dcaBCopyPatent")

public class DcaBCopyPatentController extends BaseController{

private String message;
@Autowired
public IDcaBCopyPatentService iDcaBCopyPatentService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'任现职以来申请专利情况','/dca/DcaBCopyPatent/DcaBCopyPatent','dca/DcaBCopyPatent/DcaBCopyPatent','dcaBCopyPatent:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'任现职以来申请专利情况新增','dcaBCopyPatent:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'任现职以来申请专利情况编辑','dcaBCopyPatent:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'任现职以来申请专利情况删除','dcaBCopyPatent:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBCopyPatent 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBCopyPatent:view")
public Map<String, Object> List(QueryRequest request, DcaBCopyPatent dcaBCopyPatent){
        return getDataTable(this.iDcaBCopyPatentService.findDcaBCopyPatents(request, dcaBCopyPatent));
        }

/**
 * 添加
 * @param  dcaBCopyPatent
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaBCopyPatent:add")
public void addDcaBCopyPatent(@Valid DcaBCopyPatent dcaBCopyPatent)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaBCopyPatent.setCreateUserId(currentUser.getUserId());
        this.iDcaBCopyPatentService.createDcaBCopyPatent(dcaBCopyPatent);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBCopyPatent
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBCopyPatent:update")
public void updateDcaBCopyPatent(@Valid DcaBCopyPatent dcaBCopyPatent)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBCopyPatent.setModifyUserId(currentUser.getUserId());
        this.iDcaBCopyPatentService.updateDcaBCopyPatent(dcaBCopyPatent);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBCopyPatent:delete")
public void deleteDcaBCopyPatents(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBCopyPatentService.deleteDcaBCopyPatents(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBCopyPatent:export")
public void export(QueryRequest request, DcaBCopyPatent dcaBCopyPatent, HttpServletResponse response) throws FebsException {
        try {
        List<DcaBCopyPatent> dcaBCopyPatents = this.iDcaBCopyPatentService.findDcaBCopyPatents(request, dcaBCopyPatent).getRecords();
        ExcelKit.$Export(DcaBCopyPatent.class, response).downXlsx(dcaBCopyPatents, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBCopyPatent detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBCopyPatent dcaBCopyPatent=this.iDcaBCopyPatentService.getById(id);
        return dcaBCopyPatent;
        }
        }