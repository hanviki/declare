package cc.mrbird.febs.dcacopy.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dcacopy.service.IDcaBCopyEssaypublishService;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyEssaypublish;

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
@RequestMapping("dcaBCopyEssaypublish")

public class DcaBCopyEssaypublishController extends BaseController{

private String message;
@Autowired
public IDcaBCopyEssaypublishService iDcaBCopyEssaypublishService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'论文出版','/dca/DcaBCopyEssaypublish/DcaBCopyEssaypublish','dca/DcaBCopyEssaypublish/DcaBCopyEssaypublish','dcaBCopyEssaypublish:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'论文出版新增','dcaBCopyEssaypublish:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'论文出版编辑','dcaBCopyEssaypublish:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'论文出版删除','dcaBCopyEssaypublish:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBCopyEssaypublish 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBCopyEssaypublish:view")
public Map<String, Object> List(QueryRequest request, DcaBCopyEssaypublish dcaBCopyEssaypublish){
        return getDataTable(this.iDcaBCopyEssaypublishService.findDcaBCopyEssaypublishs(request, dcaBCopyEssaypublish));
        }

/**
 * 添加
 * @param  dcaBCopyEssaypublish
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaBCopyEssaypublish:add")
public void addDcaBCopyEssaypublish(@Valid DcaBCopyEssaypublish dcaBCopyEssaypublish)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaBCopyEssaypublish.setCreateUserId(currentUser.getUserId());
        this.iDcaBCopyEssaypublishService.createDcaBCopyEssaypublish(dcaBCopyEssaypublish);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBCopyEssaypublish
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBCopyEssaypublish:update")
public void updateDcaBCopyEssaypublish(@Valid DcaBCopyEssaypublish dcaBCopyEssaypublish)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBCopyEssaypublish.setModifyUserId(currentUser.getUserId());
        this.iDcaBCopyEssaypublishService.updateDcaBCopyEssaypublish(dcaBCopyEssaypublish);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBCopyEssaypublish:delete")
public void deleteDcaBCopyEssaypublishs(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBCopyEssaypublishService.deleteDcaBCopyEssaypublishs(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBCopyEssaypublish:export")
public void export(QueryRequest request, DcaBCopyEssaypublish dcaBCopyEssaypublish, HttpServletResponse response) throws FebsException {
        try {
        List<DcaBCopyEssaypublish> dcaBCopyEssaypublishs = this.iDcaBCopyEssaypublishService.findDcaBCopyEssaypublishs(request, dcaBCopyEssaypublish).getRecords();
        ExcelKit.$Export(DcaBCopyEssaypublish.class, response).downXlsx(dcaBCopyEssaypublishs, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBCopyEssaypublish detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBCopyEssaypublish dcaBCopyEssaypublish=this.iDcaBCopyEssaypublishService.getById(id);
        return dcaBCopyEssaypublish;
        }
        }