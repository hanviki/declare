package cc.mrbird.febs.dcacopy.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dcacopy.service.IDcaBCopyLastemployService;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyLastemploy;

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
@RequestMapping("dcaBCopyLastemploy")

public class DcaBCopyLastemployController extends BaseController{

private String message;
@Autowired
public IDcaBCopyLastemployService iDcaBCopyLastemployService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'完成上一聘期','/dca/DcaBCopyLastemploy/DcaBCopyLastemploy','dca/DcaBCopyLastemploy/DcaBCopyLastemploy','dcaBCopyLastemploy:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'完成上一聘期新增','dcaBCopyLastemploy:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'完成上一聘期编辑','dcaBCopyLastemploy:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'完成上一聘期删除','dcaBCopyLastemploy:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBCopyLastemploy 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBCopyLastemploy:view")
public Map<String, Object> List(QueryRequest request, DcaBCopyLastemploy dcaBCopyLastemploy){
        return getDataTable(this.iDcaBCopyLastemployService.findDcaBCopyLastemploys(request, dcaBCopyLastemploy));
        }

/**
 * 添加
 * @param  dcaBCopyLastemploy
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaBCopyLastemploy:add")
public void addDcaBCopyLastemploy(@Valid DcaBCopyLastemploy dcaBCopyLastemploy)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaBCopyLastemploy.setCreateUserId(currentUser.getUserId());
        this.iDcaBCopyLastemployService.createDcaBCopyLastemploy(dcaBCopyLastemploy);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBCopyLastemploy
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBCopyLastemploy:update")
public void updateDcaBCopyLastemploy(@Valid DcaBCopyLastemploy dcaBCopyLastemploy)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBCopyLastemploy.setModifyUserId(currentUser.getUserId());
        this.iDcaBCopyLastemployService.updateDcaBCopyLastemploy(dcaBCopyLastemploy);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBCopyLastemploy:delete")
public void deleteDcaBCopyLastemploys(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBCopyLastemployService.deleteDcaBCopyLastemploys(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBCopyLastemploy:export")
public void export(QueryRequest request, DcaBCopyLastemploy dcaBCopyLastemploy, HttpServletResponse response) throws FebsException {
        try {
        List<DcaBCopyLastemploy> dcaBCopyLastemploys = this.iDcaBCopyLastemployService.findDcaBCopyLastemploys(request, dcaBCopyLastemploy).getRecords();
        ExcelKit.$Export(DcaBCopyLastemploy.class, response).downXlsx(dcaBCopyLastemploys, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBCopyLastemploy detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBCopyLastemploy dcaBCopyLastemploy=this.iDcaBCopyLastemployService.getById(id);
        return dcaBCopyLastemploy;
        }
        }