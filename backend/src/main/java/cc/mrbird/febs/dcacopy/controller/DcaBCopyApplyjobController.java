package cc.mrbird.febs.dcacopy.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dcacopy.service.IDcaBCopyApplyjobService;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyApplyjob;

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
@RequestMapping("dcaBCopyApplyjob")

public class DcaBCopyApplyjobController extends BaseController{

private String message;
@Autowired
public IDcaBCopyApplyjobService iDcaBCopyApplyjobService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'拟聘岗位','/dca/DcaBCopyApplyjob/DcaBCopyApplyjob','dca/DcaBCopyApplyjob/DcaBCopyApplyjob','dcaBCopyApplyjob:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'拟聘岗位新增','dcaBCopyApplyjob:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'拟聘岗位编辑','dcaBCopyApplyjob:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'拟聘岗位删除','dcaBCopyApplyjob:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBCopyApplyjob 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBCopyApplyjob:view")
public Map<String, Object> List(QueryRequest request, DcaBCopyApplyjob dcaBCopyApplyjob){
        return getDataTable(this.iDcaBCopyApplyjobService.findDcaBCopyApplyjobs(request, dcaBCopyApplyjob));
        }

/**
 * 添加
 * @param  dcaBCopyApplyjob
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaBCopyApplyjob:add")
public void addDcaBCopyApplyjob(@Valid DcaBCopyApplyjob dcaBCopyApplyjob)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaBCopyApplyjob.setCreateUserId(currentUser.getUserId());
        this.iDcaBCopyApplyjobService.createDcaBCopyApplyjob(dcaBCopyApplyjob);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBCopyApplyjob
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBCopyApplyjob:update")
public void updateDcaBCopyApplyjob(@Valid DcaBCopyApplyjob dcaBCopyApplyjob)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBCopyApplyjob.setModifyUserId(currentUser.getUserId());
        this.iDcaBCopyApplyjobService.updateDcaBCopyApplyjob(dcaBCopyApplyjob);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBCopyApplyjob:delete")
public void deleteDcaBCopyApplyjobs(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBCopyApplyjobService.deleteDcaBCopyApplyjobs(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBCopyApplyjob:export")
public void export(QueryRequest request, DcaBCopyApplyjob dcaBCopyApplyjob, HttpServletResponse response) throws FebsException {
        try {
        List<DcaBCopyApplyjob> dcaBCopyApplyjobs = this.iDcaBCopyApplyjobService.findDcaBCopyApplyjobs(request, dcaBCopyApplyjob).getRecords();
        ExcelKit.$Export(DcaBCopyApplyjob.class, response).downXlsx(dcaBCopyApplyjobs, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBCopyApplyjob detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBCopyApplyjob dcaBCopyApplyjob=this.iDcaBCopyApplyjobService.getById(id);
        return dcaBCopyApplyjob;
        }
        }