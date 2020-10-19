package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.service.IDcaBYearapplyService;
import cc.mrbird.febs.dca.entity.DcaBYearapply;

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
 * @since 2020-09-24
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBYearapply")

public class DcaBYearapplyController extends BaseController{

private String message;
@Autowired
public IDcaBYearapplyService iDcaBYearapplyService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/dca/DcaBYearapply/DcaBYearapply','dca/DcaBYearapply/DcaBYearapply','dcaBYearapply:view','fork',0,1,NOW())

 INSERT into t_menu(parent_id,perms,type,order_num,CREATE_time) VALUES(0,'dcaBYearapply:add',1,1,NOW())
 INSERT into t_menu(parent_id,perms,type,order_num,CREATE_time) VALUES(0,'dcaBYearapply:edit',1,1,NOW())
 INSERT into t_menu(parent_id,perms,type,order_num,CREATE_time) VALUES(0,'dcaBYearapply:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBYearapply 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBYearapply:view")
public Map<String, Object> List(QueryRequest request, DcaBYearapply dcaBYearapply){
        return getDataTable(this.iDcaBYearapplyService.findDcaBYearapplys(request, dcaBYearapply));
        }

/**
 * 添加
 * @param  dcaBYearapply
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaBYearapply:add")
public void addDcaBYearapply(@Valid DcaBYearapply dcaBYearapply)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaBYearapply.setCreateUserId(currentUser.getUserId());
        this.iDcaBYearapplyService.createDcaBYearapply(dcaBYearapply);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBYearapply
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBYearapply:update")
public void updateDcaBYearapply(@Valid DcaBYearapply dcaBYearapply)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBYearapply.setModifyUserId(currentUser.getUserId());
        this.iDcaBYearapplyService.updateDcaBYearapply(dcaBYearapply);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBYearapply:delete")
public void deleteDcaBYearapplys(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBYearapplyService.deleteDcaBYearapplys(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBYearapply:export")
public void export(QueryRequest request, DcaBYearapply dcaBYearapply, HttpServletResponse response) throws FebsException {
        try {
        List<DcaBYearapply> dcaBYearapplys = this.iDcaBYearapplyService.findDcaBYearapplys(request, dcaBYearapply).getRecords();
        ExcelKit.$Export(DcaBYearapply.class, response).downXlsx(dcaBYearapplys, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBYearapply detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBYearapply dcaBYearapply=this.iDcaBYearapplyService.getById(id);
        return dcaBYearapply;
        }
        }