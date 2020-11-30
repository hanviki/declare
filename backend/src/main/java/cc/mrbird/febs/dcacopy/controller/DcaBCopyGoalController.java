package cc.mrbird.febs.dcacopy.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dcacopy.service.IDcaBCopyGoalService;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyGoal;

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
@RequestMapping("dcaBCopyGoal")

public class DcaBCopyGoalController extends BaseController{

private String message;
@Autowired
public IDcaBCopyGoalService iDcaBCopyGoalService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/dca/DcaBCopyGoal/DcaBCopyGoal','dca/DcaBCopyGoal/DcaBCopyGoal','dcaBCopyGoal:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'新增','dcaBCopyGoal:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'编辑','dcaBCopyGoal:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'删除','dcaBCopyGoal:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBCopyGoal 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBCopyGoal:view")
public Map<String, Object> List(QueryRequest request, DcaBCopyGoal dcaBCopyGoal){
        return getDataTable(this.iDcaBCopyGoalService.findDcaBCopyGoals(request, dcaBCopyGoal));
        }

/**
 * 添加
 * @param  dcaBCopyGoal
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaBCopyGoal:add")
public void addDcaBCopyGoal(@Valid DcaBCopyGoal dcaBCopyGoal)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaBCopyGoal.setCreateUserId(currentUser.getUserId());
        this.iDcaBCopyGoalService.createDcaBCopyGoal(dcaBCopyGoal);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBCopyGoal
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBCopyGoal:update")
public void updateDcaBCopyGoal(@Valid DcaBCopyGoal dcaBCopyGoal)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBCopyGoal.setModifyUserId(currentUser.getUserId());
        this.iDcaBCopyGoalService.updateDcaBCopyGoal(dcaBCopyGoal);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBCopyGoal:delete")
public void deleteDcaBCopyGoals(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBCopyGoalService.deleteDcaBCopyGoals(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBCopyGoal:export")
public void export(QueryRequest request, DcaBCopyGoal dcaBCopyGoal, HttpServletResponse response) throws FebsException {
        try {
        List<DcaBCopyGoal> dcaBCopyGoals = this.iDcaBCopyGoalService.findDcaBCopyGoals(request, dcaBCopyGoal).getRecords();
        ExcelKit.$Export(DcaBCopyGoal.class, response).downXlsx(dcaBCopyGoals, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBCopyGoal detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBCopyGoal dcaBCopyGoal=this.iDcaBCopyGoalService.getById(id);
        return dcaBCopyGoal;
        }
        }