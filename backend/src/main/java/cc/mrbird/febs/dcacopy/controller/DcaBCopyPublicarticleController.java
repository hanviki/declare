package cc.mrbird.febs.dcacopy.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dcacopy.service.IDcaBCopyPublicarticleService;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyPublicarticle;

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
@RequestMapping("dcaBCopyPublicarticle")

public class DcaBCopyPublicarticleController extends BaseController{

private String message;
@Autowired
public IDcaBCopyPublicarticleService iDcaBCopyPublicarticleService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/dca/DcaBCopyPublicarticle/DcaBCopyPublicarticle','dca/DcaBCopyPublicarticle/DcaBCopyPublicarticle','dcaBCopyPublicarticle:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'新增','dcaBCopyPublicarticle:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'编辑','dcaBCopyPublicarticle:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'删除','dcaBCopyPublicarticle:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBCopyPublicarticle 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBCopyPublicarticle:view")
public Map<String, Object> List(QueryRequest request, DcaBCopyPublicarticle dcaBCopyPublicarticle){
        return getDataTable(this.iDcaBCopyPublicarticleService.findDcaBCopyPublicarticles(request, dcaBCopyPublicarticle));
        }

/**
 * 添加
 * @param  dcaBCopyPublicarticle
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaBCopyPublicarticle:add")
public void addDcaBCopyPublicarticle(@Valid DcaBCopyPublicarticle dcaBCopyPublicarticle)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaBCopyPublicarticle.setCreateUserId(currentUser.getUserId());
        this.iDcaBCopyPublicarticleService.createDcaBCopyPublicarticle(dcaBCopyPublicarticle);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBCopyPublicarticle
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBCopyPublicarticle:update")
public void updateDcaBCopyPublicarticle(@Valid DcaBCopyPublicarticle dcaBCopyPublicarticle)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBCopyPublicarticle.setModifyUserId(currentUser.getUserId());
        this.iDcaBCopyPublicarticleService.updateDcaBCopyPublicarticle(dcaBCopyPublicarticle);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBCopyPublicarticle:delete")
public void deleteDcaBCopyPublicarticles(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBCopyPublicarticleService.deleteDcaBCopyPublicarticles(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBCopyPublicarticle:export")
public void export(QueryRequest request, DcaBCopyPublicarticle dcaBCopyPublicarticle, HttpServletResponse response) throws FebsException {
        try {
        List<DcaBCopyPublicarticle> dcaBCopyPublicarticles = this.iDcaBCopyPublicarticleService.findDcaBCopyPublicarticles(request, dcaBCopyPublicarticle).getRecords();
        ExcelKit.$Export(DcaBCopyPublicarticle.class, response).downXlsx(dcaBCopyPublicarticles, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBCopyPublicarticle detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBCopyPublicarticle dcaBCopyPublicarticle=this.iDcaBCopyPublicarticleService.getById(id);
        return dcaBCopyPublicarticle;
        }
        }