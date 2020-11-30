package cc.mrbird.febs.dcacopy.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dcacopy.service.IDcaBCopySciencepublishService;
import cc.mrbird.febs.dcacopy.entity.DcaBCopySciencepublish;

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
@RequestMapping("dcaBCopySciencepublish")

public class DcaBCopySciencepublishController extends BaseController{

private String message;
@Autowired
public IDcaBCopySciencepublishService iDcaBCopySciencepublishService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'任现职以来发表的论文、出版著作','/dca/DcaBCopySciencepublish/DcaBCopySciencepublish','dca/DcaBCopySciencepublish/DcaBCopySciencepublish','dcaBCopySciencepublish:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'任现职以来发表的论文、出版著作新增','dcaBCopySciencepublish:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'任现职以来发表的论文、出版著作编辑','dcaBCopySciencepublish:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'任现职以来发表的论文、出版著作删除','dcaBCopySciencepublish:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBCopySciencepublish 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBCopySciencepublish:view")
public Map<String, Object> List(QueryRequest request, DcaBCopySciencepublish dcaBCopySciencepublish){
        return getDataTable(this.iDcaBCopySciencepublishService.findDcaBCopySciencepublishs(request, dcaBCopySciencepublish));
        }

/**
 * 添加
 * @param  dcaBCopySciencepublish
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaBCopySciencepublish:add")
public void addDcaBCopySciencepublish(@Valid DcaBCopySciencepublish dcaBCopySciencepublish)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaBCopySciencepublish.setCreateUserId(currentUser.getUserId());
        this.iDcaBCopySciencepublishService.createDcaBCopySciencepublish(dcaBCopySciencepublish);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBCopySciencepublish
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBCopySciencepublish:update")
public void updateDcaBCopySciencepublish(@Valid DcaBCopySciencepublish dcaBCopySciencepublish)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBCopySciencepublish.setModifyUserId(currentUser.getUserId());
        this.iDcaBCopySciencepublishService.updateDcaBCopySciencepublish(dcaBCopySciencepublish);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBCopySciencepublish:delete")
public void deleteDcaBCopySciencepublishs(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBCopySciencepublishService.deleteDcaBCopySciencepublishs(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBCopySciencepublish:export")
public void export(QueryRequest request, DcaBCopySciencepublish dcaBCopySciencepublish, HttpServletResponse response) throws FebsException {
        try {
        List<DcaBCopySciencepublish> dcaBCopySciencepublishs = this.iDcaBCopySciencepublishService.findDcaBCopySciencepublishs(request, dcaBCopySciencepublish).getRecords();
        ExcelKit.$Export(DcaBCopySciencepublish.class, response).downXlsx(dcaBCopySciencepublishs, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBCopySciencepublish detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBCopySciencepublish dcaBCopySciencepublish=this.iDcaBCopySciencepublishService.getById(id);
        return dcaBCopySciencepublish;
        }
        }