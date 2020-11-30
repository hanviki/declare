package cc.mrbird.febs.dcacopy.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dcacopy.service.IDcaBCopyYoungprizeService;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyYoungprize;

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
@RequestMapping("dcaBCopyYoungprize")

public class DcaBCopyYoungprizeController extends BaseController{

private String message;
@Autowired
public IDcaBCopyYoungprizeService iDcaBCopyYoungprizeService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'青年教师教学竞赛获奖','/dca/DcaBCopyYoungprize/DcaBCopyYoungprize','dca/DcaBCopyYoungprize/DcaBCopyYoungprize','dcaBCopyYoungprize:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'青年教师教学竞赛获奖新增','dcaBCopyYoungprize:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'青年教师教学竞赛获奖编辑','dcaBCopyYoungprize:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'青年教师教学竞赛获奖删除','dcaBCopyYoungprize:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBCopyYoungprize 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBCopyYoungprize:view")
public Map<String, Object> List(QueryRequest request, DcaBCopyYoungprize dcaBCopyYoungprize){
        return getDataTable(this.iDcaBCopyYoungprizeService.findDcaBCopyYoungprizes(request, dcaBCopyYoungprize));
        }

/**
 * 添加
 * @param  dcaBCopyYoungprize
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaBCopyYoungprize:add")
public void addDcaBCopyYoungprize(@Valid DcaBCopyYoungprize dcaBCopyYoungprize)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaBCopyYoungprize.setCreateUserId(currentUser.getUserId());
        this.iDcaBCopyYoungprizeService.createDcaBCopyYoungprize(dcaBCopyYoungprize);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBCopyYoungprize
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBCopyYoungprize:update")
public void updateDcaBCopyYoungprize(@Valid DcaBCopyYoungprize dcaBCopyYoungprize)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBCopyYoungprize.setModifyUserId(currentUser.getUserId());
        this.iDcaBCopyYoungprizeService.updateDcaBCopyYoungprize(dcaBCopyYoungprize);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBCopyYoungprize:delete")
public void deleteDcaBCopyYoungprizes(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBCopyYoungprizeService.deleteDcaBCopyYoungprizes(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBCopyYoungprize:export")
public void export(QueryRequest request, DcaBCopyYoungprize dcaBCopyYoungprize, HttpServletResponse response) throws FebsException {
        try {
        List<DcaBCopyYoungprize> dcaBCopyYoungprizes = this.iDcaBCopyYoungprizeService.findDcaBCopyYoungprizes(request, dcaBCopyYoungprize).getRecords();
        ExcelKit.$Export(DcaBCopyYoungprize.class, response).downXlsx(dcaBCopyYoungprizes, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBCopyYoungprize detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBCopyYoungprize dcaBCopyYoungprize=this.iDcaBCopyYoungprizeService.getById(id);
        return dcaBCopyYoungprize;
        }
        }