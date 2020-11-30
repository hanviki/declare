package cc.mrbird.febs.dcacopy.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dcacopy.service.IDcaBCopyTeacherprizeService;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyTeacherprize;

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
@RequestMapping("dcaBCopyTeacherprize")

public class DcaBCopyTeacherprizeController extends BaseController{

private String message;
@Autowired
public IDcaBCopyTeacherprizeService iDcaBCopyTeacherprizeService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'任现职以来教学获奖','/dca/DcaBCopyTeacherprize/DcaBCopyTeacherprize','dca/DcaBCopyTeacherprize/DcaBCopyTeacherprize','dcaBCopyTeacherprize:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'任现职以来教学获奖新增','dcaBCopyTeacherprize:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'任现职以来教学获奖编辑','dcaBCopyTeacherprize:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'任现职以来教学获奖删除','dcaBCopyTeacherprize:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBCopyTeacherprize 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBCopyTeacherprize:view")
public Map<String, Object> List(QueryRequest request, DcaBCopyTeacherprize dcaBCopyTeacherprize){
        return getDataTable(this.iDcaBCopyTeacherprizeService.findDcaBCopyTeacherprizes(request, dcaBCopyTeacherprize));
        }

/**
 * 添加
 * @param  dcaBCopyTeacherprize
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaBCopyTeacherprize:add")
public void addDcaBCopyTeacherprize(@Valid DcaBCopyTeacherprize dcaBCopyTeacherprize)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaBCopyTeacherprize.setCreateUserId(currentUser.getUserId());
        this.iDcaBCopyTeacherprizeService.createDcaBCopyTeacherprize(dcaBCopyTeacherprize);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBCopyTeacherprize
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBCopyTeacherprize:update")
public void updateDcaBCopyTeacherprize(@Valid DcaBCopyTeacherprize dcaBCopyTeacherprize)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBCopyTeacherprize.setModifyUserId(currentUser.getUserId());
        this.iDcaBCopyTeacherprizeService.updateDcaBCopyTeacherprize(dcaBCopyTeacherprize);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBCopyTeacherprize:delete")
public void deleteDcaBCopyTeacherprizes(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBCopyTeacherprizeService.deleteDcaBCopyTeacherprizes(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBCopyTeacherprize:export")
public void export(QueryRequest request, DcaBCopyTeacherprize dcaBCopyTeacherprize, HttpServletResponse response) throws FebsException {
        try {
        List<DcaBCopyTeacherprize> dcaBCopyTeacherprizes = this.iDcaBCopyTeacherprizeService.findDcaBCopyTeacherprizes(request, dcaBCopyTeacherprize).getRecords();
        ExcelKit.$Export(DcaBCopyTeacherprize.class, response).downXlsx(dcaBCopyTeacherprizes, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBCopyTeacherprize detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBCopyTeacherprize dcaBCopyTeacherprize=this.iDcaBCopyTeacherprizeService.getById(id);
        return dcaBCopyTeacherprize;
        }
        }