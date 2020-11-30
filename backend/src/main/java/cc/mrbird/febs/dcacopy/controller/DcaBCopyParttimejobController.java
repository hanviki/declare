package cc.mrbird.febs.dcacopy.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dcacopy.service.IDcaBCopyParttimejobService;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyParttimejob;

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
@RequestMapping("dcaBCopyParttimejob")

public class DcaBCopyParttimejobController extends BaseController{

private String message;
@Autowired
public IDcaBCopyParttimejobService iDcaBCopyParttimejobService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'社会兼职表','/dca/DcaBCopyParttimejob/DcaBCopyParttimejob','dca/DcaBCopyParttimejob/DcaBCopyParttimejob','dcaBCopyParttimejob:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'社会兼职表新增','dcaBCopyParttimejob:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'社会兼职表编辑','dcaBCopyParttimejob:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'社会兼职表删除','dcaBCopyParttimejob:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBCopyParttimejob 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBCopyParttimejob:view")
public Map<String, Object> List(QueryRequest request, DcaBCopyParttimejob dcaBCopyParttimejob){
        return getDataTable(this.iDcaBCopyParttimejobService.findDcaBCopyParttimejobs(request, dcaBCopyParttimejob));
        }

/**
 * 添加
 * @param  dcaBCopyParttimejob
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaBCopyParttimejob:add")
public void addDcaBCopyParttimejob(@Valid DcaBCopyParttimejob dcaBCopyParttimejob)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaBCopyParttimejob.setCreateUserId(currentUser.getUserId());
        this.iDcaBCopyParttimejobService.createDcaBCopyParttimejob(dcaBCopyParttimejob);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBCopyParttimejob
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBCopyParttimejob:update")
public void updateDcaBCopyParttimejob(@Valid DcaBCopyParttimejob dcaBCopyParttimejob)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBCopyParttimejob.setModifyUserId(currentUser.getUserId());
        this.iDcaBCopyParttimejobService.updateDcaBCopyParttimejob(dcaBCopyParttimejob);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBCopyParttimejob:delete")
public void deleteDcaBCopyParttimejobs(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBCopyParttimejobService.deleteDcaBCopyParttimejobs(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBCopyParttimejob:export")
public void export(QueryRequest request, DcaBCopyParttimejob dcaBCopyParttimejob, HttpServletResponse response) throws FebsException {
        try {
        List<DcaBCopyParttimejob> dcaBCopyParttimejobs = this.iDcaBCopyParttimejobService.findDcaBCopyParttimejobs(request, dcaBCopyParttimejob).getRecords();
        ExcelKit.$Export(DcaBCopyParttimejob.class, response).downXlsx(dcaBCopyParttimejobs, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBCopyParttimejob detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBCopyParttimejob dcaBCopyParttimejob=this.iDcaBCopyParttimejobService.getById(id);
        return dcaBCopyParttimejob;
        }
        }