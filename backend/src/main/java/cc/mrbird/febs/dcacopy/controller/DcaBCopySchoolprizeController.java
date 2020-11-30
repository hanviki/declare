package cc.mrbird.febs.dcacopy.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dcacopy.service.IDcaBCopySchoolprizeService;
import cc.mrbird.febs.dcacopy.entity.DcaBCopySchoolprize;

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
@RequestMapping("dcaBCopySchoolprize")

public class DcaBCopySchoolprizeController extends BaseController{

private String message;
@Autowired
public IDcaBCopySchoolprizeService iDcaBCopySchoolprizeService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'校教学质量奖、校教学成果奖','/dca/DcaBCopySchoolprize/DcaBCopySchoolprize','dca/DcaBCopySchoolprize/DcaBCopySchoolprize','dcaBCopySchoolprize:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'校教学质量奖、校教学成果奖新增','dcaBCopySchoolprize:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'校教学质量奖、校教学成果奖编辑','dcaBCopySchoolprize:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'校教学质量奖、校教学成果奖删除','dcaBCopySchoolprize:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBCopySchoolprize 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBCopySchoolprize:view")
public Map<String, Object> List(QueryRequest request, DcaBCopySchoolprize dcaBCopySchoolprize){
        return getDataTable(this.iDcaBCopySchoolprizeService.findDcaBCopySchoolprizes(request, dcaBCopySchoolprize));
        }

/**
 * 添加
 * @param  dcaBCopySchoolprize
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaBCopySchoolprize:add")
public void addDcaBCopySchoolprize(@Valid DcaBCopySchoolprize dcaBCopySchoolprize)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaBCopySchoolprize.setCreateUserId(currentUser.getUserId());
        this.iDcaBCopySchoolprizeService.createDcaBCopySchoolprize(dcaBCopySchoolprize);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBCopySchoolprize
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBCopySchoolprize:update")
public void updateDcaBCopySchoolprize(@Valid DcaBCopySchoolprize dcaBCopySchoolprize)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBCopySchoolprize.setModifyUserId(currentUser.getUserId());
        this.iDcaBCopySchoolprizeService.updateDcaBCopySchoolprize(dcaBCopySchoolprize);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBCopySchoolprize:delete")
public void deleteDcaBCopySchoolprizes(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBCopySchoolprizeService.deleteDcaBCopySchoolprizes(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBCopySchoolprize:export")
public void export(QueryRequest request, DcaBCopySchoolprize dcaBCopySchoolprize, HttpServletResponse response) throws FebsException {
        try {
        List<DcaBCopySchoolprize> dcaBCopySchoolprizes = this.iDcaBCopySchoolprizeService.findDcaBCopySchoolprizes(request, dcaBCopySchoolprize).getRecords();
        ExcelKit.$Export(DcaBCopySchoolprize.class, response).downXlsx(dcaBCopySchoolprizes, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBCopySchoolprize detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBCopySchoolprize dcaBCopySchoolprize=this.iDcaBCopySchoolprizeService.getById(id);
        return dcaBCopySchoolprize;
        }
        }