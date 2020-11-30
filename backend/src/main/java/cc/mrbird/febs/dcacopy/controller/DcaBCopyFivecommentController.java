package cc.mrbird.febs.dcacopy.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dcacopy.service.IDcaBCopyFivecommentService;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyFivecomment;

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
@RequestMapping("dcaBCopyFivecomment")

public class DcaBCopyFivecommentController extends BaseController{

private String message;
@Autowired
public IDcaBCopyFivecommentService iDcaBCopyFivecommentService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'近五年总体项目评价','/dca/DcaBCopyFivecomment/DcaBCopyFivecomment','dca/DcaBCopyFivecomment/DcaBCopyFivecomment','dcaBCopyFivecomment:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'近五年总体项目评价新增','dcaBCopyFivecomment:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'近五年总体项目评价编辑','dcaBCopyFivecomment:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'近五年总体项目评价删除','dcaBCopyFivecomment:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBCopyFivecomment 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBCopyFivecomment:view")
public Map<String, Object> List(QueryRequest request, DcaBCopyFivecomment dcaBCopyFivecomment){
        return getDataTable(this.iDcaBCopyFivecommentService.findDcaBCopyFivecomments(request, dcaBCopyFivecomment));
        }

/**
 * 添加
 * @param  dcaBCopyFivecomment
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaBCopyFivecomment:add")
public void addDcaBCopyFivecomment(@Valid DcaBCopyFivecomment dcaBCopyFivecomment)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaBCopyFivecomment.setCreateUserId(currentUser.getUserId());
        this.iDcaBCopyFivecommentService.createDcaBCopyFivecomment(dcaBCopyFivecomment);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBCopyFivecomment
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBCopyFivecomment:update")
public void updateDcaBCopyFivecomment(@Valid DcaBCopyFivecomment dcaBCopyFivecomment)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBCopyFivecomment.setModifyUserId(currentUser.getUserId());
        this.iDcaBCopyFivecommentService.updateDcaBCopyFivecomment(dcaBCopyFivecomment);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBCopyFivecomment:delete")
public void deleteDcaBCopyFivecomments(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBCopyFivecommentService.deleteDcaBCopyFivecomments(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBCopyFivecomment:export")
public void export(QueryRequest request, DcaBCopyFivecomment dcaBCopyFivecomment, HttpServletResponse response) throws FebsException {
        try {
        List<DcaBCopyFivecomment> dcaBCopyFivecomments = this.iDcaBCopyFivecommentService.findDcaBCopyFivecomments(request, dcaBCopyFivecomment).getRecords();
        ExcelKit.$Export(DcaBCopyFivecomment.class, response).downXlsx(dcaBCopyFivecomments, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBCopyFivecomment detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBCopyFivecomment dcaBCopyFivecomment=this.iDcaBCopyFivecommentService.getById(id);
        return dcaBCopyFivecomment;
        }
        }