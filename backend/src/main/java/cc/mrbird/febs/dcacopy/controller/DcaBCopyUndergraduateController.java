package cc.mrbird.febs.dcacopy.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dcacopy.service.IDcaBCopyUndergraduateService;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyUndergraduate;

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
@RequestMapping("dcaBCopyUndergraduate")

public class DcaBCopyUndergraduateController extends BaseController{

private String message;
@Autowired
public IDcaBCopyUndergraduateService iDcaBCopyUndergraduateService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'本科教学情况','/dca/DcaBCopyUndergraduate/DcaBCopyUndergraduate','dca/DcaBCopyUndergraduate/DcaBCopyUndergraduate','dcaBCopyUndergraduate:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'本科教学情况新增','dcaBCopyUndergraduate:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'本科教学情况编辑','dcaBCopyUndergraduate:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'本科教学情况删除','dcaBCopyUndergraduate:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBCopyUndergraduate 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBCopyUndergraduate:view")
public Map<String, Object> List(QueryRequest request, DcaBCopyUndergraduate dcaBCopyUndergraduate){
        return getDataTable(this.iDcaBCopyUndergraduateService.findDcaBCopyUndergraduates(request, dcaBCopyUndergraduate));
        }

/**
 * 添加
 * @param  dcaBCopyUndergraduate
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaBCopyUndergraduate:add")
public void addDcaBCopyUndergraduate(@Valid DcaBCopyUndergraduate dcaBCopyUndergraduate)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaBCopyUndergraduate.setCreateUserId(currentUser.getUserId());
        this.iDcaBCopyUndergraduateService.createDcaBCopyUndergraduate(dcaBCopyUndergraduate);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBCopyUndergraduate
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBCopyUndergraduate:update")
public void updateDcaBCopyUndergraduate(@Valid DcaBCopyUndergraduate dcaBCopyUndergraduate)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBCopyUndergraduate.setModifyUserId(currentUser.getUserId());
        this.iDcaBCopyUndergraduateService.updateDcaBCopyUndergraduate(dcaBCopyUndergraduate);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBCopyUndergraduate:delete")
public void deleteDcaBCopyUndergraduates(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBCopyUndergraduateService.deleteDcaBCopyUndergraduates(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBCopyUndergraduate:export")
public void export(QueryRequest request, DcaBCopyUndergraduate dcaBCopyUndergraduate, HttpServletResponse response) throws FebsException {
        try {
        List<DcaBCopyUndergraduate> dcaBCopyUndergraduates = this.iDcaBCopyUndergraduateService.findDcaBCopyUndergraduates(request, dcaBCopyUndergraduate).getRecords();
        ExcelKit.$Export(DcaBCopyUndergraduate.class, response).downXlsx(dcaBCopyUndergraduates, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBCopyUndergraduate detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBCopyUndergraduate dcaBCopyUndergraduate=this.iDcaBCopyUndergraduateService.getById(id);
        return dcaBCopyUndergraduate;
        }
        }