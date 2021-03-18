package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.service.IDcaBAssitantService;
import cc.mrbird.febs.dca.entity.DcaBAssitant;

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
 * @since 2021-03-02
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBAssitant")

public class DcaBAssitantController extends BaseController{

private String message;
@Autowired
public IDcaBAssitantService iDcaBAssitantService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'支援情况','/dca/DcaBAssitant/DcaBAssitant','dca/DcaBAssitant/DcaBAssitant','dcaBAssitant:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'支援情况新增','dcaBAssitant:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'支援情况编辑','dcaBAssitant:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'支援情况删除','dcaBAssitant:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBAssitant 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBAssitant:view")
public Map<String, Object> List(QueryRequest request, DcaBAssitant dcaBAssitant){
        return getDataTable(this.iDcaBAssitantService.findDcaBAssitants(request, dcaBAssitant));
        }

/**
 * 添加
 * @param  dcaBAssitant
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaBAssitant:add")
public void addDcaBAssitant(@Valid DcaBAssitant dcaBAssitant)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaBAssitant.setCreateUserId(currentUser.getUserId());
        this.iDcaBAssitantService.createDcaBAssitant(dcaBAssitant);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBAssitant
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBAssitant:update")
public void updateDcaBAssitant(@Valid DcaBAssitant dcaBAssitant)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBAssitant.setModifyUserId(currentUser.getUserId());
        this.iDcaBAssitantService.updateDcaBAssitant(dcaBAssitant);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBAssitant:delete")
public void deleteDcaBAssitants(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBAssitantService.deleteDcaBAssitants(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBAssitant:export")
public void export(QueryRequest request, DcaBAssitant dcaBAssitant, HttpServletResponse response) throws FebsException {
        try {
        List<DcaBAssitant> dcaBAssitants = this.iDcaBAssitantService.findDcaBAssitants(request, dcaBAssitant).getRecords();
        ExcelKit.$Export(DcaBAssitant.class, response).downXlsx(dcaBAssitants, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBAssitant detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBAssitant dcaBAssitant=this.iDcaBAssitantService.getById(id);
        return dcaBAssitant;
        }
        }