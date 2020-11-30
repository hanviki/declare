package cc.mrbird.febs.dcacopy.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dcacopy.service.IDcaBCopyGraduateService;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyGraduate;

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
@RequestMapping("dcaBCopyGraduate")

public class DcaBCopyGraduateController extends BaseController{

private String message;
@Autowired
public IDcaBCopyGraduateService iDcaBCopyGraduateService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'任现职以来独立指导研究生情况(教师系列需填写)','/dca/DcaBCopyGraduate/DcaBCopyGraduate','dca/DcaBCopyGraduate/DcaBCopyGraduate','dcaBCopyGraduate:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'任现职以来独立指导研究生情况(教师系列需填写)新增','dcaBCopyGraduate:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'任现职以来独立指导研究生情况(教师系列需填写)编辑','dcaBCopyGraduate:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'任现职以来独立指导研究生情况(教师系列需填写)删除','dcaBCopyGraduate:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBCopyGraduate 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBCopyGraduate:view")
public Map<String, Object> List(QueryRequest request, DcaBCopyGraduate dcaBCopyGraduate){
        return getDataTable(this.iDcaBCopyGraduateService.findDcaBCopyGraduates(request, dcaBCopyGraduate));
        }

/**
 * 添加
 * @param  dcaBCopyGraduate
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaBCopyGraduate:add")
public void addDcaBCopyGraduate(@Valid DcaBCopyGraduate dcaBCopyGraduate)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaBCopyGraduate.setCreateUserId(currentUser.getUserId());
        this.iDcaBCopyGraduateService.createDcaBCopyGraduate(dcaBCopyGraduate);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBCopyGraduate
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBCopyGraduate:update")
public void updateDcaBCopyGraduate(@Valid DcaBCopyGraduate dcaBCopyGraduate)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBCopyGraduate.setModifyUserId(currentUser.getUserId());
        this.iDcaBCopyGraduateService.updateDcaBCopyGraduate(dcaBCopyGraduate);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBCopyGraduate:delete")
public void deleteDcaBCopyGraduates(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBCopyGraduateService.deleteDcaBCopyGraduates(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBCopyGraduate:export")
public void export(QueryRequest request, DcaBCopyGraduate dcaBCopyGraduate, HttpServletResponse response) throws FebsException {
        try {
        List<DcaBCopyGraduate> dcaBCopyGraduates = this.iDcaBCopyGraduateService.findDcaBCopyGraduates(request, dcaBCopyGraduate).getRecords();
        ExcelKit.$Export(DcaBCopyGraduate.class, response).downXlsx(dcaBCopyGraduates, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBCopyGraduate detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBCopyGraduate dcaBCopyGraduate=this.iDcaBCopyGraduateService.getById(id);
        return dcaBCopyGraduate;
        }
        }