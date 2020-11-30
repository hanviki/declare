package cc.mrbird.febs.dcacopy.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dcacopy.service.IDcaBCopyUndergraduateprizeService;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyUndergraduateprize;

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
@RequestMapping("dcaBCopyUndergraduateprize")

public class DcaBCopyUndergraduateprizeController extends BaseController{

private String message;
@Autowired
public IDcaBCopyUndergraduateprizeService iDcaBCopyUndergraduateprizeService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'任现职以来本科教学工作获奖情况(教师系列需填写)','/dca/DcaBCopyUndergraduateprize/DcaBCopyUndergraduateprize','dca/DcaBCopyUndergraduateprize/DcaBCopyUndergraduateprize','dcaBCopyUndergraduateprize:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'任现职以来本科教学工作获奖情况(教师系列需填写)新增','dcaBCopyUndergraduateprize:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'任现职以来本科教学工作获奖情况(教师系列需填写)编辑','dcaBCopyUndergraduateprize:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'任现职以来本科教学工作获奖情况(教师系列需填写)删除','dcaBCopyUndergraduateprize:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBCopyUndergraduateprize 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBCopyUndergraduateprize:view")
public Map<String, Object> List(QueryRequest request, DcaBCopyUndergraduateprize dcaBCopyUndergraduateprize){
        return getDataTable(this.iDcaBCopyUndergraduateprizeService.findDcaBCopyUndergraduateprizes(request, dcaBCopyUndergraduateprize));
        }

/**
 * 添加
 * @param  dcaBCopyUndergraduateprize
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaBCopyUndergraduateprize:add")
public void addDcaBCopyUndergraduateprize(@Valid DcaBCopyUndergraduateprize dcaBCopyUndergraduateprize)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaBCopyUndergraduateprize.setCreateUserId(currentUser.getUserId());
        this.iDcaBCopyUndergraduateprizeService.createDcaBCopyUndergraduateprize(dcaBCopyUndergraduateprize);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBCopyUndergraduateprize
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBCopyUndergraduateprize:update")
public void updateDcaBCopyUndergraduateprize(@Valid DcaBCopyUndergraduateprize dcaBCopyUndergraduateprize)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBCopyUndergraduateprize.setModifyUserId(currentUser.getUserId());
        this.iDcaBCopyUndergraduateprizeService.updateDcaBCopyUndergraduateprize(dcaBCopyUndergraduateprize);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBCopyUndergraduateprize:delete")
public void deleteDcaBCopyUndergraduateprizes(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBCopyUndergraduateprizeService.deleteDcaBCopyUndergraduateprizes(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBCopyUndergraduateprize:export")
public void export(QueryRequest request, DcaBCopyUndergraduateprize dcaBCopyUndergraduateprize, HttpServletResponse response) throws FebsException {
        try {
        List<DcaBCopyUndergraduateprize> dcaBCopyUndergraduateprizes = this.iDcaBCopyUndergraduateprizeService.findDcaBCopyUndergraduateprizes(request, dcaBCopyUndergraduateprize).getRecords();
        ExcelKit.$Export(DcaBCopyUndergraduateprize.class, response).downXlsx(dcaBCopyUndergraduateprizes, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBCopyUndergraduateprize detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBCopyUndergraduateprize dcaBCopyUndergraduateprize=this.iDcaBCopyUndergraduateprizeService.getById(id);
        return dcaBCopyUndergraduateprize;
        }
        }