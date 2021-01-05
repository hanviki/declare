package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.common.utils.ExportExcelUtils;
import cc.mrbird.febs.dca.service.IDcaBUserapplyzcService;
import cc.mrbird.febs.dca.entity.DcaBUserapplyzc;

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
 * @since 2020-12-22
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBUserapplyzc")

public class DcaBUserapplyzcController extends BaseController{

private String message;
@Autowired
public IDcaBUserapplyzcService iDcaBUserapplyzcService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/dca/DcaBUserapplyzc/DcaBUserapplyzc','dca/DcaBUserapplyzc/DcaBUserapplyzc','dcaBUserapplyzc:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'新增','dcaBUserapplyzc:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'编辑','dcaBUserapplyzc:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'删除','dcaBUserapplyzc:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBUserapplyzc 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBUserapplyzc:view")
public Map<String, Object> List(QueryRequest request, DcaBUserapplyzc dcaBUserapplyzc){
        return getDataTable(this.iDcaBUserapplyzcService.findDcaBUserapplyzcs(request, dcaBUserapplyzc));
        }
    @GetMapping("person")
    @RequiresPermissions("dcaBUserapplyzc:view")
    public Map<String, Object> List2(QueryRequest request, DcaBUserapplyzc dcaBUserapply){
        User currentUser= FebsUtil.getCurrentUser();
        dcaBUserapply.setUserAccount(currentUser.getUsername());
        return getDataTable(this.iDcaBUserapplyzcService.findDcaBUserapplyzcs(request, dcaBUserapply));
    }
/**
 * 添加
 * @param  dcaBUserapplyzc
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaBUserapplyzc:add")
public void addDcaBUserapplyzc(@Valid DcaBUserapplyzc dcaBUserapplyzc)throws FebsException{
        try{
            User currentUser= FebsUtil.getCurrentUser();
            dcaBUserapplyzc.setCreateUserId(currentUser.getUserId());
            dcaBUserapplyzc.setUserAccount(currentUser.getUsername());
            dcaBUserapplyzc.setUserAccountName(currentUser.getRealname());
            if(this.iDcaBUserapplyzcService.IsExistApply(dcaBUserapplyzc)){
                this.iDcaBUserapplyzcService.createDcaBUserapplyzc(dcaBUserapplyzc);
            }else {
                throw new FebsException("当前年度已经有申报记录");
            }

        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
            throw new FebsException(e.getMessage());
        }
        }

/**
 * 修改
 * @param dcaBUserapplyzc
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBUserapplyzc:update")
public void updateDcaBUserapplyzc(@Valid DcaBUserapplyzc dcaBUserapplyzc)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBUserapplyzc.setModifyUserId(currentUser.getUserId());
            if(this.iDcaBUserapplyzcService.IsExistApply(dcaBUserapplyzc)){
                this.iDcaBUserapplyzcService.updateDcaBUserapplyzc(dcaBUserapplyzc);}
            else {
                throw new FebsException("当前年度已经有申报记录");
            }
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
            throw new FebsException(e.getMessage());
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBUserapplyzc:delete")
public void deleteDcaBUserapplyzcs(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBUserapplyzcService.deleteDcaBUserapplyzcs(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
    @PostMapping("excel")
    public void export(QueryRequest request, DcaBUserapplyzc dcaBSciencepublish,String dataJson,HttpServletResponse response)throws FebsException{
        try{
            request.setPageNum(1);
            request.setPageSize(10000);
            User currentUser = FebsUtil.getCurrentUser();

            dcaBSciencepublish.setIsDeletemark(1);
            request.setSortField("user_account asc,state asc");
            request.setSortOrder("ascend");
            List<DcaBUserapplyzc> dcaBSciencepublishList=  this.iDcaBUserapplyzcService.findDcaBUserapplyzcs(request, dcaBSciencepublish).getRecords();
            //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
            ExportExcelUtils.exportCustomExcel_han(response, dcaBSciencepublishList,dataJson,"");
        }catch(Exception e){
            message="导出Excel失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }
    @Log("修改")
    @PutMapping("state")
    public void updateDcaBUserapplyState(@Valid DcaBUserapplyzc dcaBUserapply)throws FebsException{
        try{
            User currentUser= FebsUtil.getCurrentUser();
            dcaBUserapply.setModifyUserId(currentUser.getUserId());
            // dcaBUserapply.setUserAccount(currentUser.getUsername());
            this.iDcaBUserapplyzcService.updateDcaBUserapplyzc(dcaBUserapply);

        }catch(Exception e){
            message="修改失败" ;
            log.error(message,e);
            throw new FebsException(message);
        }
    }
@GetMapping("/{id}")
public DcaBUserapplyzc detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBUserapplyzc dcaBUserapplyzc=this.iDcaBUserapplyzcService.getById(id);
        return dcaBUserapplyzc;
        }
        }