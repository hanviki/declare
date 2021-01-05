package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.service.IDcaBUserapplyService;
import cc.mrbird.febs.dca.entity.DcaBUserapply;

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
 * @since 2020-11-05
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBUserapply")

public class DcaBUserapplyController extends BaseController{

private String message;
@Autowired
public IDcaBUserapplyService iDcaBUserapplyService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/dca/DcaBUserapply/DcaBUserapply','dca/DcaBUserapply/DcaBUserapply','dcaBUserapply:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'新增','dcaBUserapply:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'编辑','dcaBUserapply:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'删除','dcaBUserapply:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBUserapply 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBUserapply:view")
public Map<String, Object> List(QueryRequest request, DcaBUserapply dcaBUserapply){
        return getDataTable(this.iDcaBUserapplyService.findDcaBUserapplys(request, dcaBUserapply));
        }

    @GetMapping("person")
    @RequiresPermissions("dcaBUserapply:view")
    public Map<String, Object> List2(QueryRequest request, DcaBUserapply dcaBUserapply){
        User currentUser= FebsUtil.getCurrentUser();
        dcaBUserapply.setUserAccount(currentUser.getUsername());
        return getDataTable(this.iDcaBUserapplyService.findDcaBUserapplys(request, dcaBUserapply));
    }
    @GetMapping("audit")
    public Map<String, Object> List4(QueryRequest request, DcaBUserapply dcaBUserapply){
        return getDataTable(this.iDcaBUserapplyService.findDcaBUserapplyAudit(request, dcaBUserapply));
    }
/**
 * 添加
 * @param  dcaBUserapply
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaBUserapply:add")
public void addDcaBUserapply(@Valid DcaBUserapply dcaBUserapply)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaBUserapply.setCreateUserId(currentUser.getUserId());
        dcaBUserapply.setUserAccount(currentUser.getUsername());
            dcaBUserapply.setUserAccountName(currentUser.getRealname());
           if(this.iDcaBUserapplyService.IsExistApply(dcaBUserapply)){
        this.iDcaBUserapplyService.createDcaBUserapply(dcaBUserapply);
           }else {
               throw new FebsException("当前年度已经有申报记录");
           }

        }catch(Exception e){
        //message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(e.getMessage());
        }
        }

/**
 * 修改
 * @param dcaBUserapply
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBUserapply:update")
public void updateDcaBUserapply(@Valid DcaBUserapply dcaBUserapply)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBUserapply.setModifyUserId(currentUser.getUserId());
            dcaBUserapply.setUserAccount(currentUser.getUsername());
            if(this.iDcaBUserapplyService.IsExistApply(dcaBUserapply)){
        this.iDcaBUserapplyService.updateDcaBUserapply(dcaBUserapply);}
         else {
                    throw new FebsException("当前年度已经有申报记录");
                }
        }catch(Exception e){
       // message="修改失败" ;
        log.error(message,e);
        throw new FebsException(e.getMessage());
        }
        }

    @Log("修改")
    @PutMapping("state")
    public void updateDcaBUserapplyState(@Valid DcaBUserapply dcaBUserapply)throws FebsException{
        try{
            User currentUser= FebsUtil.getCurrentUser();
            dcaBUserapply.setModifyUserId(currentUser.getUserId());
           // dcaBUserapply.setUserAccount(currentUser.getUsername());
            this.iDcaBUserapplyService.updateDcaBUserapplyState(dcaBUserapply);

        }catch(Exception e){
            message="修改失败" ;
            log.error(message,e);
            throw new FebsException(message);
        }
    }
@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBUserapply:delete")
public void deleteDcaBUserapplys(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBUserapplyService.deleteDcaBUserapplys(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
public void export(QueryRequest request, DcaBUserapply dcaBUserapply, HttpServletResponse response) throws FebsException {
        try {
            request.setPageNum(1);
            request.setPageSize(10000);
        List<DcaBUserapply> dcaBUserapplys = this.iDcaBUserapplyService.findDcaBUserapplys(request, dcaBUserapply).getRecords();
        ExcelKit.$Export(DcaBUserapply.class, response).downXlsx(dcaBUserapplys, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBUserapply detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBUserapply dcaBUserapply=this.iDcaBUserapplyService.getById(id);
        return dcaBUserapply;
        }
        }