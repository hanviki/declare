package cc.mrbird.febs.dcacopy.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dcacopy.service.IDcaBCopyScientificprizeService;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyScientificprize;

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
@RequestMapping("dcaBCopyScientificprize")

public class DcaBCopyScientificprizeController extends BaseController{

private String message;
@Autowired
public IDcaBCopyScientificprizeService iDcaBCopyScientificprizeService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'任现职以来科研获奖情况','/dca/DcaBCopyScientificprize/DcaBCopyScientificprize','dca/DcaBCopyScientificprize/DcaBCopyScientificprize','dcaBCopyScientificprize:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'任现职以来科研获奖情况新增','dcaBCopyScientificprize:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'任现职以来科研获奖情况编辑','dcaBCopyScientificprize:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'任现职以来科研获奖情况删除','dcaBCopyScientificprize:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBCopyScientificprize 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBCopyScientificprize:view")
public Map<String, Object> List(QueryRequest request, DcaBCopyScientificprize dcaBCopyScientificprize){
        return getDataTable(this.iDcaBCopyScientificprizeService.findDcaBCopyScientificprizes(request, dcaBCopyScientificprize));
        }

/**
 * 添加
 * @param  dcaBCopyScientificprize
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaBCopyScientificprize:add")
public void addDcaBCopyScientificprize(@Valid DcaBCopyScientificprize dcaBCopyScientificprize)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaBCopyScientificprize.setCreateUserId(currentUser.getUserId());
        this.iDcaBCopyScientificprizeService.createDcaBCopyScientificprize(dcaBCopyScientificprize);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBCopyScientificprize
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBCopyScientificprize:update")
public void updateDcaBCopyScientificprize(@Valid DcaBCopyScientificprize dcaBCopyScientificprize)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBCopyScientificprize.setModifyUserId(currentUser.getUserId());
        this.iDcaBCopyScientificprizeService.updateDcaBCopyScientificprize(dcaBCopyScientificprize);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBCopyScientificprize:delete")
public void deleteDcaBCopyScientificprizes(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBCopyScientificprizeService.deleteDcaBCopyScientificprizes(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBCopyScientificprize:export")
public void export(QueryRequest request, DcaBCopyScientificprize dcaBCopyScientificprize, HttpServletResponse response) throws FebsException {
        try {
        List<DcaBCopyScientificprize> dcaBCopyScientificprizes = this.iDcaBCopyScientificprizeService.findDcaBCopyScientificprizes(request, dcaBCopyScientificprize).getRecords();
        ExcelKit.$Export(DcaBCopyScientificprize.class, response).downXlsx(dcaBCopyScientificprizes, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBCopyScientificprize detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBCopyScientificprize dcaBCopyScientificprize=this.iDcaBCopyScientificprizeService.getById(id);
        return dcaBCopyScientificprize;
        }
        }