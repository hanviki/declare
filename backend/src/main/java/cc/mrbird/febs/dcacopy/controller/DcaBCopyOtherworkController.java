package cc.mrbird.febs.dcacopy.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dcacopy.service.IDcaBCopyOtherworkService;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyOtherwork;

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
@RequestMapping("dcaBCopyOtherwork")

public class DcaBCopyOtherworkController extends BaseController{

private String message;
@Autowired
public IDcaBCopyOtherworkService iDcaBCopyOtherworkService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'其他工作及成果，拟聘岗位工作思路及预期目标，个人总结','/dca/DcaBCopyOtherwork/DcaBCopyOtherwork','dca/DcaBCopyOtherwork/DcaBCopyOtherwork','dcaBCopyOtherwork:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'其他工作及成果，拟聘岗位工作思路及预期目标，个人总结新增','dcaBCopyOtherwork:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'其他工作及成果，拟聘岗位工作思路及预期目标，个人总结编辑','dcaBCopyOtherwork:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'其他工作及成果，拟聘岗位工作思路及预期目标，个人总结删除','dcaBCopyOtherwork:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBCopyOtherwork 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBCopyOtherwork:view")
public Map<String, Object> List(QueryRequest request, DcaBCopyOtherwork dcaBCopyOtherwork){
        return getDataTable(this.iDcaBCopyOtherworkService.findDcaBCopyOtherworks(request, dcaBCopyOtherwork));
        }

/**
 * 添加
 * @param  dcaBCopyOtherwork
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaBCopyOtherwork:add")
public void addDcaBCopyOtherwork(@Valid DcaBCopyOtherwork dcaBCopyOtherwork)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaBCopyOtherwork.setCreateUserId(currentUser.getUserId());
        this.iDcaBCopyOtherworkService.createDcaBCopyOtherwork(dcaBCopyOtherwork);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBCopyOtherwork
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBCopyOtherwork:update")
public void updateDcaBCopyOtherwork(@Valid DcaBCopyOtherwork dcaBCopyOtherwork)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBCopyOtherwork.setModifyUserId(currentUser.getUserId());
        this.iDcaBCopyOtherworkService.updateDcaBCopyOtherwork(dcaBCopyOtherwork);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBCopyOtherwork:delete")
public void deleteDcaBCopyOtherworks(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBCopyOtherworkService.deleteDcaBCopyOtherworks(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBCopyOtherwork:export")
public void export(QueryRequest request, DcaBCopyOtherwork dcaBCopyOtherwork, HttpServletResponse response) throws FebsException {
        try {
        List<DcaBCopyOtherwork> dcaBCopyOtherworks = this.iDcaBCopyOtherworkService.findDcaBCopyOtherworks(request, dcaBCopyOtherwork).getRecords();
        ExcelKit.$Export(DcaBCopyOtherwork.class, response).downXlsx(dcaBCopyOtherworks, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBCopyOtherwork detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBCopyOtherwork dcaBCopyOtherwork=this.iDcaBCopyOtherworkService.getById(id);
        return dcaBCopyOtherwork;
        }
        }