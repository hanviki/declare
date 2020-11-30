package cc.mrbird.febs.dcacopy.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dcacopy.service.IDcaBCopyTurtorService;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyTurtor;

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
@RequestMapping("dcaBCopyTurtor")

public class DcaBCopyTurtorController extends BaseController{

private String message;
@Autowired
public IDcaBCopyTurtorService iDcaBCopyTurtorService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'担任辅导员教师班主任情况(教师系列需填写)','/dca/DcaBCopyTurtor/DcaBCopyTurtor','dca/DcaBCopyTurtor/DcaBCopyTurtor','dcaBCopyTurtor:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'担任辅导员教师班主任情况(教师系列需填写)新增','dcaBCopyTurtor:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'担任辅导员教师班主任情况(教师系列需填写)编辑','dcaBCopyTurtor:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'担任辅导员教师班主任情况(教师系列需填写)删除','dcaBCopyTurtor:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBCopyTurtor 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBCopyTurtor:view")
public Map<String, Object> List(QueryRequest request, DcaBCopyTurtor dcaBCopyTurtor){
        return getDataTable(this.iDcaBCopyTurtorService.findDcaBCopyTurtors(request, dcaBCopyTurtor));
        }

/**
 * 添加
 * @param  dcaBCopyTurtor
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaBCopyTurtor:add")
public void addDcaBCopyTurtor(@Valid DcaBCopyTurtor dcaBCopyTurtor)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaBCopyTurtor.setCreateUserId(currentUser.getUserId());
        this.iDcaBCopyTurtorService.createDcaBCopyTurtor(dcaBCopyTurtor);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBCopyTurtor
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBCopyTurtor:update")
public void updateDcaBCopyTurtor(@Valid DcaBCopyTurtor dcaBCopyTurtor)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBCopyTurtor.setModifyUserId(currentUser.getUserId());
        this.iDcaBCopyTurtorService.updateDcaBCopyTurtor(dcaBCopyTurtor);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBCopyTurtor:delete")
public void deleteDcaBCopyTurtors(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBCopyTurtorService.deleteDcaBCopyTurtors(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBCopyTurtor:export")
public void export(QueryRequest request, DcaBCopyTurtor dcaBCopyTurtor, HttpServletResponse response) throws FebsException {
        try {
        List<DcaBCopyTurtor> dcaBCopyTurtors = this.iDcaBCopyTurtorService.findDcaBCopyTurtors(request, dcaBCopyTurtor).getRecords();
        ExcelKit.$Export(DcaBCopyTurtor.class, response).downXlsx(dcaBCopyTurtors, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBCopyTurtor detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBCopyTurtor dcaBCopyTurtor=this.iDcaBCopyTurtorService.getById(id);
        return dcaBCopyTurtor;
        }
        }