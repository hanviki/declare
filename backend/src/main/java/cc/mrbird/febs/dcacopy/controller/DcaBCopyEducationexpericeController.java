package cc.mrbird.febs.dcacopy.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dcacopy.service.IDcaBCopyEducationexpericeService;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyEducationexperice;

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
@RequestMapping("dcaBCopyEducationexperice")

public class DcaBCopyEducationexpericeController extends BaseController{

private String message;
@Autowired
public IDcaBCopyEducationexpericeService iDcaBCopyEducationexpericeService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'学习工作经历','/dca/DcaBCopyEducationexperice/DcaBCopyEducationexperice','dca/DcaBCopyEducationexperice/DcaBCopyEducationexperice','dcaBCopyEducationexperice:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'学习工作经历新增','dcaBCopyEducationexperice:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'学习工作经历编辑','dcaBCopyEducationexperice:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'学习工作经历删除','dcaBCopyEducationexperice:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBCopyEducationexperice 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBCopyEducationexperice:view")
public Map<String, Object> List(QueryRequest request, DcaBCopyEducationexperice dcaBCopyEducationexperice){
        return getDataTable(this.iDcaBCopyEducationexpericeService.findDcaBCopyEducationexperices(request, dcaBCopyEducationexperice));
        }

/**
 * 添加
 * @param  dcaBCopyEducationexperice
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaBCopyEducationexperice:add")
public void addDcaBCopyEducationexperice(@Valid DcaBCopyEducationexperice dcaBCopyEducationexperice)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaBCopyEducationexperice.setCreateUserId(currentUser.getUserId());
        this.iDcaBCopyEducationexpericeService.createDcaBCopyEducationexperice(dcaBCopyEducationexperice);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBCopyEducationexperice
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBCopyEducationexperice:update")
public void updateDcaBCopyEducationexperice(@Valid DcaBCopyEducationexperice dcaBCopyEducationexperice)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBCopyEducationexperice.setModifyUserId(currentUser.getUserId());
        this.iDcaBCopyEducationexpericeService.updateDcaBCopyEducationexperice(dcaBCopyEducationexperice);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBCopyEducationexperice:delete")
public void deleteDcaBCopyEducationexperices(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBCopyEducationexpericeService.deleteDcaBCopyEducationexperices(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBCopyEducationexperice:export")
public void export(QueryRequest request, DcaBCopyEducationexperice dcaBCopyEducationexperice, HttpServletResponse response) throws FebsException {
        try {
        List<DcaBCopyEducationexperice> dcaBCopyEducationexperices = this.iDcaBCopyEducationexpericeService.findDcaBCopyEducationexperices(request, dcaBCopyEducationexperice).getRecords();
        ExcelKit.$Export(DcaBCopyEducationexperice.class, response).downXlsx(dcaBCopyEducationexperices, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBCopyEducationexperice detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBCopyEducationexperice dcaBCopyEducationexperice=this.iDcaBCopyEducationexpericeService.getById(id);
        return dcaBCopyEducationexperice;
        }
        }