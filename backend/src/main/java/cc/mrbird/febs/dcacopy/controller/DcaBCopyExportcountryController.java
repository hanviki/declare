package cc.mrbird.febs.dcacopy.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dcacopy.service.IDcaBCopyExportcountryService;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyExportcountry;

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
@RequestMapping("dcaBCopyExportcountry")

public class DcaBCopyExportcountryController extends BaseController{

private String message;
@Autowired
public IDcaBCopyExportcountryService iDcaBCopyExportcountryService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'出国情况','/dca/DcaBCopyExportcountry/DcaBCopyExportcountry','dca/DcaBCopyExportcountry/DcaBCopyExportcountry','dcaBCopyExportcountry:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'出国情况新增','dcaBCopyExportcountry:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'出国情况编辑','dcaBCopyExportcountry:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'出国情况删除','dcaBCopyExportcountry:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBCopyExportcountry 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBCopyExportcountry:view")
public Map<String, Object> List(QueryRequest request, DcaBCopyExportcountry dcaBCopyExportcountry){
        return getDataTable(this.iDcaBCopyExportcountryService.findDcaBCopyExportcountrys(request, dcaBCopyExportcountry));
        }

/**
 * 添加
 * @param  dcaBCopyExportcountry
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaBCopyExportcountry:add")
public void addDcaBCopyExportcountry(@Valid DcaBCopyExportcountry dcaBCopyExportcountry)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaBCopyExportcountry.setCreateUserId(currentUser.getUserId());
        this.iDcaBCopyExportcountryService.createDcaBCopyExportcountry(dcaBCopyExportcountry);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBCopyExportcountry
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBCopyExportcountry:update")
public void updateDcaBCopyExportcountry(@Valid DcaBCopyExportcountry dcaBCopyExportcountry)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBCopyExportcountry.setModifyUserId(currentUser.getUserId());
        this.iDcaBCopyExportcountryService.updateDcaBCopyExportcountry(dcaBCopyExportcountry);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBCopyExportcountry:delete")
public void deleteDcaBCopyExportcountrys(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBCopyExportcountryService.deleteDcaBCopyExportcountrys(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBCopyExportcountry:export")
public void export(QueryRequest request, DcaBCopyExportcountry dcaBCopyExportcountry, HttpServletResponse response) throws FebsException {
        try {
        List<DcaBCopyExportcountry> dcaBCopyExportcountrys = this.iDcaBCopyExportcountryService.findDcaBCopyExportcountrys(request, dcaBCopyExportcountry).getRecords();
        ExcelKit.$Export(DcaBCopyExportcountry.class, response).downXlsx(dcaBCopyExportcountrys, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBCopyExportcountry detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBCopyExportcountry dcaBCopyExportcountry=this.iDcaBCopyExportcountryService.getById(id);
        return dcaBCopyExportcountry;
        }
        }