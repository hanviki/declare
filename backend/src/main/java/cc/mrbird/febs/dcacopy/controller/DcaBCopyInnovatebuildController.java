package cc.mrbird.febs.dcacopy.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dcacopy.service.IDcaBCopyInnovatebuildService;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyInnovatebuild;

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
@RequestMapping("dcaBCopyInnovatebuild")

public class DcaBCopyInnovatebuildController extends BaseController{

private String message;
@Autowired
public IDcaBCopyInnovatebuildService iDcaBCopyInnovatebuildService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'任现职以来承担的本科教学改革及建设项目(教师系列需填写)','/dca/DcaBCopyInnovatebuild/DcaBCopyInnovatebuild','dca/DcaBCopyInnovatebuild/DcaBCopyInnovatebuild','dcaBCopyInnovatebuild:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'任现职以来承担的本科教学改革及建设项目(教师系列需填写)新增','dcaBCopyInnovatebuild:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'任现职以来承担的本科教学改革及建设项目(教师系列需填写)编辑','dcaBCopyInnovatebuild:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'任现职以来承担的本科教学改革及建设项目(教师系列需填写)删除','dcaBCopyInnovatebuild:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBCopyInnovatebuild 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBCopyInnovatebuild:view")
public Map<String, Object> List(QueryRequest request, DcaBCopyInnovatebuild dcaBCopyInnovatebuild){
        return getDataTable(this.iDcaBCopyInnovatebuildService.findDcaBCopyInnovatebuilds(request, dcaBCopyInnovatebuild));
        }

/**
 * 添加
 * @param  dcaBCopyInnovatebuild
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaBCopyInnovatebuild:add")
public void addDcaBCopyInnovatebuild(@Valid DcaBCopyInnovatebuild dcaBCopyInnovatebuild)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaBCopyInnovatebuild.setCreateUserId(currentUser.getUserId());
        this.iDcaBCopyInnovatebuildService.createDcaBCopyInnovatebuild(dcaBCopyInnovatebuild);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBCopyInnovatebuild
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBCopyInnovatebuild:update")
public void updateDcaBCopyInnovatebuild(@Valid DcaBCopyInnovatebuild dcaBCopyInnovatebuild)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBCopyInnovatebuild.setModifyUserId(currentUser.getUserId());
        this.iDcaBCopyInnovatebuildService.updateDcaBCopyInnovatebuild(dcaBCopyInnovatebuild);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBCopyInnovatebuild:delete")
public void deleteDcaBCopyInnovatebuilds(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBCopyInnovatebuildService.deleteDcaBCopyInnovatebuilds(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBCopyInnovatebuild:export")
public void export(QueryRequest request, DcaBCopyInnovatebuild dcaBCopyInnovatebuild, HttpServletResponse response) throws FebsException {
        try {
        List<DcaBCopyInnovatebuild> dcaBCopyInnovatebuilds = this.iDcaBCopyInnovatebuildService.findDcaBCopyInnovatebuilds(request, dcaBCopyInnovatebuild).getRecords();
        ExcelKit.$Export(DcaBCopyInnovatebuild.class, response).downXlsx(dcaBCopyInnovatebuilds, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBCopyInnovatebuild detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBCopyInnovatebuild dcaBCopyInnovatebuild=this.iDcaBCopyInnovatebuildService.getById(id);
        return dcaBCopyInnovatebuild;
        }
        }