package cc.mrbird.febs.dcacopy.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dcacopy.service.IDcaBCopyPolitalshowService;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyPolitalshow;

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
@RequestMapping("dcaBCopyPolitalshow")

public class DcaBCopyPolitalshowController extends BaseController{

private String message;
@Autowired
public IDcaBCopyPolitalshowService iDcaBCopyPolitalshowService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'个人思想政治表现','/dca/DcaBCopyPolitalshow/DcaBCopyPolitalshow','dca/DcaBCopyPolitalshow/DcaBCopyPolitalshow','dcaBCopyPolitalshow:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'个人思想政治表现新增','dcaBCopyPolitalshow:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'个人思想政治表现编辑','dcaBCopyPolitalshow:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'个人思想政治表现删除','dcaBCopyPolitalshow:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBCopyPolitalshow 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBCopyPolitalshow:view")
public Map<String, Object> List(QueryRequest request, DcaBCopyPolitalshow dcaBCopyPolitalshow){
        return getDataTable(this.iDcaBCopyPolitalshowService.findDcaBCopyPolitalshows(request, dcaBCopyPolitalshow));
        }

/**
 * 添加
 * @param  dcaBCopyPolitalshow
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaBCopyPolitalshow:add")
public void addDcaBCopyPolitalshow(@Valid DcaBCopyPolitalshow dcaBCopyPolitalshow)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaBCopyPolitalshow.setCreateUserId(currentUser.getUserId());
        this.iDcaBCopyPolitalshowService.createDcaBCopyPolitalshow(dcaBCopyPolitalshow);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBCopyPolitalshow
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBCopyPolitalshow:update")
public void updateDcaBCopyPolitalshow(@Valid DcaBCopyPolitalshow dcaBCopyPolitalshow)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBCopyPolitalshow.setModifyUserId(currentUser.getUserId());
        this.iDcaBCopyPolitalshowService.updateDcaBCopyPolitalshow(dcaBCopyPolitalshow);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBCopyPolitalshow:delete")
public void deleteDcaBCopyPolitalshows(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBCopyPolitalshowService.deleteDcaBCopyPolitalshows(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBCopyPolitalshow:export")
public void export(QueryRequest request, DcaBCopyPolitalshow dcaBCopyPolitalshow, HttpServletResponse response) throws FebsException {
        try {
        List<DcaBCopyPolitalshow> dcaBCopyPolitalshows = this.iDcaBCopyPolitalshowService.findDcaBCopyPolitalshows(request, dcaBCopyPolitalshow).getRecords();
        ExcelKit.$Export(DcaBCopyPolitalshow.class, response).downXlsx(dcaBCopyPolitalshows, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBCopyPolitalshow detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBCopyPolitalshow dcaBCopyPolitalshow=this.iDcaBCopyPolitalshowService.getById(id);
        return dcaBCopyPolitalshow;
        }
        }