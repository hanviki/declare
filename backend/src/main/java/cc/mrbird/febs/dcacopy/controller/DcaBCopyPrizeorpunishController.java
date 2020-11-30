package cc.mrbird.febs.dcacopy.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dcacopy.service.IDcaBCopyPrizeorpunishService;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyPrizeorpunish;

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
@RequestMapping("dcaBCopyPrizeorpunish")

public class DcaBCopyPrizeorpunishController extends BaseController{

private String message;
@Autowired
public IDcaBCopyPrizeorpunishService iDcaBCopyPrizeorpunishService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'何时受奖励处分','/dca/DcaBCopyPrizeorpunish/DcaBCopyPrizeorpunish','dca/DcaBCopyPrizeorpunish/DcaBCopyPrizeorpunish','dcaBCopyPrizeorpunish:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'何时受奖励处分新增','dcaBCopyPrizeorpunish:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'何时受奖励处分编辑','dcaBCopyPrizeorpunish:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'何时受奖励处分删除','dcaBCopyPrizeorpunish:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBCopyPrizeorpunish 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBCopyPrizeorpunish:view")
public Map<String, Object> List(QueryRequest request, DcaBCopyPrizeorpunish dcaBCopyPrizeorpunish){
        return getDataTable(this.iDcaBCopyPrizeorpunishService.findDcaBCopyPrizeorpunishs(request, dcaBCopyPrizeorpunish));
        }

/**
 * 添加
 * @param  dcaBCopyPrizeorpunish
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaBCopyPrizeorpunish:add")
public void addDcaBCopyPrizeorpunish(@Valid DcaBCopyPrizeorpunish dcaBCopyPrizeorpunish)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaBCopyPrizeorpunish.setCreateUserId(currentUser.getUserId());
        this.iDcaBCopyPrizeorpunishService.createDcaBCopyPrizeorpunish(dcaBCopyPrizeorpunish);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBCopyPrizeorpunish
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBCopyPrizeorpunish:update")
public void updateDcaBCopyPrizeorpunish(@Valid DcaBCopyPrizeorpunish dcaBCopyPrizeorpunish)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBCopyPrizeorpunish.setModifyUserId(currentUser.getUserId());
        this.iDcaBCopyPrizeorpunishService.updateDcaBCopyPrizeorpunish(dcaBCopyPrizeorpunish);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBCopyPrizeorpunish:delete")
public void deleteDcaBCopyPrizeorpunishs(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBCopyPrizeorpunishService.deleteDcaBCopyPrizeorpunishs(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBCopyPrizeorpunish:export")
public void export(QueryRequest request, DcaBCopyPrizeorpunish dcaBCopyPrizeorpunish, HttpServletResponse response) throws FebsException {
        try {
        List<DcaBCopyPrizeorpunish> dcaBCopyPrizeorpunishs = this.iDcaBCopyPrizeorpunishService.findDcaBCopyPrizeorpunishs(request, dcaBCopyPrizeorpunish).getRecords();
        ExcelKit.$Export(DcaBCopyPrizeorpunish.class, response).downXlsx(dcaBCopyPrizeorpunishs, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBCopyPrizeorpunish detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBCopyPrizeorpunish dcaBCopyPrizeorpunish=this.iDcaBCopyPrizeorpunishService.getById(id);
        return dcaBCopyPrizeorpunish;
        }
        }