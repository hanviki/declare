package cc.mrbird.febs.dcacopy.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dcacopy.service.IDcaBCopyPersonalsummaryService;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyPersonalsummary;

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
@RequestMapping("dcaBCopyPersonalsummary")

public class DcaBCopyPersonalsummaryController extends BaseController{

private String message;
@Autowired
public IDcaBCopyPersonalsummaryService iDcaBCopyPersonalsummaryService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'个人总结','/dca/DcaBCopyPersonalsummary/DcaBCopyPersonalsummary','dca/DcaBCopyPersonalsummary/DcaBCopyPersonalsummary','dcaBCopyPersonalsummary:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'个人总结新增','dcaBCopyPersonalsummary:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'个人总结编辑','dcaBCopyPersonalsummary:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'个人总结删除','dcaBCopyPersonalsummary:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBCopyPersonalsummary 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBCopyPersonalsummary:view")
public Map<String, Object> List(QueryRequest request, DcaBCopyPersonalsummary dcaBCopyPersonalsummary){
        return getDataTable(this.iDcaBCopyPersonalsummaryService.findDcaBCopyPersonalsummarys(request, dcaBCopyPersonalsummary));
        }

/**
 * 添加
 * @param  dcaBCopyPersonalsummary
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaBCopyPersonalsummary:add")
public void addDcaBCopyPersonalsummary(@Valid DcaBCopyPersonalsummary dcaBCopyPersonalsummary)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaBCopyPersonalsummary.setCreateUserId(currentUser.getUserId());
        this.iDcaBCopyPersonalsummaryService.createDcaBCopyPersonalsummary(dcaBCopyPersonalsummary);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBCopyPersonalsummary
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBCopyPersonalsummary:update")
public void updateDcaBCopyPersonalsummary(@Valid DcaBCopyPersonalsummary dcaBCopyPersonalsummary)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBCopyPersonalsummary.setModifyUserId(currentUser.getUserId());
        this.iDcaBCopyPersonalsummaryService.updateDcaBCopyPersonalsummary(dcaBCopyPersonalsummary);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBCopyPersonalsummary:delete")
public void deleteDcaBCopyPersonalsummarys(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBCopyPersonalsummaryService.deleteDcaBCopyPersonalsummarys(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBCopyPersonalsummary:export")
public void export(QueryRequest request, DcaBCopyPersonalsummary dcaBCopyPersonalsummary, HttpServletResponse response) throws FebsException {
        try {
        List<DcaBCopyPersonalsummary> dcaBCopyPersonalsummarys = this.iDcaBCopyPersonalsummaryService.findDcaBCopyPersonalsummarys(request, dcaBCopyPersonalsummary).getRecords();
        ExcelKit.$Export(DcaBCopyPersonalsummary.class, response).downXlsx(dcaBCopyPersonalsummarys, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBCopyPersonalsummary detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBCopyPersonalsummary dcaBCopyPersonalsummary=this.iDcaBCopyPersonalsummaryService.getById(id);
        return dcaBCopyPersonalsummary;
        }
        }