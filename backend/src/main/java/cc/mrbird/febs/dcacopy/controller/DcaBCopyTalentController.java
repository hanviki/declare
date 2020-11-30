package cc.mrbird.febs.dcacopy.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dcacopy.service.IDcaBCopyTalentService;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyTalent;

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
@RequestMapping("dcaBCopyTalent")

public class DcaBCopyTalentController extends BaseController{

private String message;
@Autowired
public IDcaBCopyTalentService iDcaBCopyTalentService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'任现职以来完成研究生教学人才培养情况','/dca/DcaBCopyTalent/DcaBCopyTalent','dca/DcaBCopyTalent/DcaBCopyTalent','dcaBCopyTalent:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'任现职以来完成研究生教学人才培养情况新增','dcaBCopyTalent:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'任现职以来完成研究生教学人才培养情况编辑','dcaBCopyTalent:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'任现职以来完成研究生教学人才培养情况删除','dcaBCopyTalent:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBCopyTalent 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBCopyTalent:view")
public Map<String, Object> List(QueryRequest request, DcaBCopyTalent dcaBCopyTalent){
        return getDataTable(this.iDcaBCopyTalentService.findDcaBCopyTalents(request, dcaBCopyTalent));
        }

/**
 * 添加
 * @param  dcaBCopyTalent
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaBCopyTalent:add")
public void addDcaBCopyTalent(@Valid DcaBCopyTalent dcaBCopyTalent)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaBCopyTalent.setCreateUserId(currentUser.getUserId());
        this.iDcaBCopyTalentService.createDcaBCopyTalent(dcaBCopyTalent);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBCopyTalent
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBCopyTalent:update")
public void updateDcaBCopyTalent(@Valid DcaBCopyTalent dcaBCopyTalent)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBCopyTalent.setModifyUserId(currentUser.getUserId());
        this.iDcaBCopyTalentService.updateDcaBCopyTalent(dcaBCopyTalent);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBCopyTalent:delete")
public void deleteDcaBCopyTalents(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBCopyTalentService.deleteDcaBCopyTalents(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBCopyTalent:export")
public void export(QueryRequest request, DcaBCopyTalent dcaBCopyTalent, HttpServletResponse response) throws FebsException {
        try {
        List<DcaBCopyTalent> dcaBCopyTalents = this.iDcaBCopyTalentService.findDcaBCopyTalents(request, dcaBCopyTalent).getRecords();
        ExcelKit.$Export(DcaBCopyTalent.class, response).downXlsx(dcaBCopyTalents, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBCopyTalent detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBCopyTalent dcaBCopyTalent=this.iDcaBCopyTalentService.getById(id);
        return dcaBCopyTalent;
        }
        }