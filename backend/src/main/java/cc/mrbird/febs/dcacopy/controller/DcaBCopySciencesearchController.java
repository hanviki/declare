package cc.mrbird.febs.dcacopy.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dcacopy.service.IDcaBCopySciencesearchService;
import cc.mrbird.febs.dcacopy.entity.DcaBCopySciencesearch;

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
@RequestMapping("dcaBCopySciencesearch")

public class DcaBCopySciencesearchController extends BaseController{

private String message;
@Autowired
public IDcaBCopySciencesearchService iDcaBCopySciencesearchService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'任现职以来承担的科研项目','/dca/DcaBCopySciencesearch/DcaBCopySciencesearch','dca/DcaBCopySciencesearch/DcaBCopySciencesearch','dcaBCopySciencesearch:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'任现职以来承担的科研项目新增','dcaBCopySciencesearch:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'任现职以来承担的科研项目编辑','dcaBCopySciencesearch:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'任现职以来承担的科研项目删除','dcaBCopySciencesearch:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBCopySciencesearch 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBCopySciencesearch:view")
public Map<String, Object> List(QueryRequest request, DcaBCopySciencesearch dcaBCopySciencesearch){
        return getDataTable(this.iDcaBCopySciencesearchService.findDcaBCopySciencesearchs(request, dcaBCopySciencesearch));
        }

/**
 * 添加
 * @param  dcaBCopySciencesearch
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaBCopySciencesearch:add")
public void addDcaBCopySciencesearch(@Valid DcaBCopySciencesearch dcaBCopySciencesearch)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaBCopySciencesearch.setCreateUserId(currentUser.getUserId());
        this.iDcaBCopySciencesearchService.createDcaBCopySciencesearch(dcaBCopySciencesearch);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBCopySciencesearch
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBCopySciencesearch:update")
public void updateDcaBCopySciencesearch(@Valid DcaBCopySciencesearch dcaBCopySciencesearch)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBCopySciencesearch.setModifyUserId(currentUser.getUserId());
        this.iDcaBCopySciencesearchService.updateDcaBCopySciencesearch(dcaBCopySciencesearch);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBCopySciencesearch:delete")
public void deleteDcaBCopySciencesearchs(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBCopySciencesearchService.deleteDcaBCopySciencesearchs(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBCopySciencesearch:export")
public void export(QueryRequest request, DcaBCopySciencesearch dcaBCopySciencesearch, HttpServletResponse response) throws FebsException {
        try {
        List<DcaBCopySciencesearch> dcaBCopySciencesearchs = this.iDcaBCopySciencesearchService.findDcaBCopySciencesearchs(request, dcaBCopySciencesearch).getRecords();
        ExcelKit.$Export(DcaBCopySciencesearch.class, response).downXlsx(dcaBCopySciencesearchs, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBCopySciencesearch detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBCopySciencesearch dcaBCopySciencesearch=this.iDcaBCopySciencesearchService.getById(id);
        return dcaBCopySciencesearch;
        }
        }