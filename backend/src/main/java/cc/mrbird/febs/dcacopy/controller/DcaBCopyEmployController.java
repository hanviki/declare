package cc.mrbird.febs.dcacopy.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dcacopy.service.IDcaBCopyEmployService;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyEmploy;

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
@RequestMapping("dcaBCopyEmploy")

public class DcaBCopyEmployController extends BaseController{

private String message;
@Autowired
public IDcaBCopyEmployService iDcaBCopyEmployService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'任职培养','/dca/DcaBCopyEmploy/DcaBCopyEmploy','dca/DcaBCopyEmploy/DcaBCopyEmploy','dcaBCopyEmploy:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'任职培养新增','dcaBCopyEmploy:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'任职培养编辑','dcaBCopyEmploy:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'任职培养删除','dcaBCopyEmploy:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBCopyEmploy 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBCopyEmploy:view")
public Map<String, Object> List(QueryRequest request, DcaBCopyEmploy dcaBCopyEmploy){
        return getDataTable(this.iDcaBCopyEmployService.findDcaBCopyEmploys(request, dcaBCopyEmploy));
        }

/**
 * 添加
 * @param  dcaBCopyEmploy
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaBCopyEmploy:add")
public void addDcaBCopyEmploy(@Valid DcaBCopyEmploy dcaBCopyEmploy)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaBCopyEmploy.setCreateUserId(currentUser.getUserId());
        this.iDcaBCopyEmployService.createDcaBCopyEmploy(dcaBCopyEmploy);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBCopyEmploy
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBCopyEmploy:update")
public void updateDcaBCopyEmploy(@Valid DcaBCopyEmploy dcaBCopyEmploy)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBCopyEmploy.setModifyUserId(currentUser.getUserId());
        this.iDcaBCopyEmployService.updateDcaBCopyEmploy(dcaBCopyEmploy);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBCopyEmploy:delete")
public void deleteDcaBCopyEmploys(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBCopyEmployService.deleteDcaBCopyEmploys(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBCopyEmploy:export")
public void export(QueryRequest request, DcaBCopyEmploy dcaBCopyEmploy, HttpServletResponse response) throws FebsException {
        try {
        List<DcaBCopyEmploy> dcaBCopyEmploys = this.iDcaBCopyEmployService.findDcaBCopyEmploys(request, dcaBCopyEmploy).getRecords();
        ExcelKit.$Export(DcaBCopyEmploy.class, response).downXlsx(dcaBCopyEmploys, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBCopyEmploy detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBCopyEmploy dcaBCopyEmploy=this.iDcaBCopyEmployService.getById(id);
        return dcaBCopyEmploy;
        }
        }