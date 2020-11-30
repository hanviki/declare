package cc.mrbird.febs.dcacopy.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dcacopy.service.IDcaBCopyPaperspublishService;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyPaperspublish;

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
@RequestMapping("dcaBCopyPaperspublish")

public class DcaBCopyPaperspublishController extends BaseController{

private String message;
@Autowired
public IDcaBCopyPaperspublishService iDcaBCopyPaperspublishService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'教学论文出版教材','/dca/DcaBCopyPaperspublish/DcaBCopyPaperspublish','dca/DcaBCopyPaperspublish/DcaBCopyPaperspublish','dcaBCopyPaperspublish:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'教学论文出版教材新增','dcaBCopyPaperspublish:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'教学论文出版教材编辑','dcaBCopyPaperspublish:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'教学论文出版教材删除','dcaBCopyPaperspublish:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBCopyPaperspublish 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBCopyPaperspublish:view")
public Map<String, Object> List(QueryRequest request, DcaBCopyPaperspublish dcaBCopyPaperspublish){
        return getDataTable(this.iDcaBCopyPaperspublishService.findDcaBCopyPaperspublishs(request, dcaBCopyPaperspublish));
        }

/**
 * 添加
 * @param  dcaBCopyPaperspublish
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaBCopyPaperspublish:add")
public void addDcaBCopyPaperspublish(@Valid DcaBCopyPaperspublish dcaBCopyPaperspublish)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaBCopyPaperspublish.setCreateUserId(currentUser.getUserId());
        this.iDcaBCopyPaperspublishService.createDcaBCopyPaperspublish(dcaBCopyPaperspublish);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBCopyPaperspublish
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBCopyPaperspublish:update")
public void updateDcaBCopyPaperspublish(@Valid DcaBCopyPaperspublish dcaBCopyPaperspublish)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBCopyPaperspublish.setModifyUserId(currentUser.getUserId());
        this.iDcaBCopyPaperspublishService.updateDcaBCopyPaperspublish(dcaBCopyPaperspublish);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBCopyPaperspublish:delete")
public void deleteDcaBCopyPaperspublishs(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBCopyPaperspublishService.deleteDcaBCopyPaperspublishs(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBCopyPaperspublish:export")
public void export(QueryRequest request, DcaBCopyPaperspublish dcaBCopyPaperspublish, HttpServletResponse response) throws FebsException {
        try {
        List<DcaBCopyPaperspublish> dcaBCopyPaperspublishs = this.iDcaBCopyPaperspublishService.findDcaBCopyPaperspublishs(request, dcaBCopyPaperspublish).getRecords();
        ExcelKit.$Export(DcaBCopyPaperspublish.class, response).downXlsx(dcaBCopyPaperspublishs, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBCopyPaperspublish detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBCopyPaperspublish dcaBCopyPaperspublish=this.iDcaBCopyPaperspublishService.getById(id);
        return dcaBCopyPaperspublish;
        }
        }