package cc.mrbird.febs.scm.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.scm.service.IScmBPurcharseorderService;
import cc.mrbird.febs.scm.entity.ScmBPurcharseorder;

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
 * @since 2019-11-21
 */
@Slf4j
@Validated
@RestController
@RequestMapping("scmBPurcharseorder")

public class ScmBPurcharseorderController extends BaseController{

private String message;
@Autowired
public IScmBPurcharseorderService iScmBPurcharseorderService;


/**
 * 分页查询数据
 *
 * @param bootStrapTable  分页信息
 * @param scmBPurcharseorder 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("scmBPurcharseorder:view")
public Map<String, Object> List(QueryRequest request, ScmBPurcharseorder scmBPurcharseorder){
        return getDataTable(this.iScmBPurcharseorderService.findScmBPurcharseorders(request, scmBPurcharseorder));
        }

/**
 * 跳转添加页面
 * @param request
 * @param response
 * @param model
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("scmBPurcharseorder:add")
public void addScmBPurcharseorder(@Valid ScmBPurcharseorder scmBPurcharseorder)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        scmBPurcharseorder.setCreateUserId(currentUser.getUserId());
        this.iScmBPurcharseorderService.createScmBPurcharseorder(scmBPurcharseorder);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 跳转修改页面
 * @param request
 * @param id  实体ID
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("scmBPurcharseorder:update")
public void updateScmBPurcharseorder(@Valid ScmBPurcharseorder scmBPurcharseorder)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      scmBPurcharseorder.setModifyUserId(currentUser.getUserId());
        this.iScmBPurcharseorderService.updateScmBPurcharseorder(scmBPurcharseorder);
        }catch(Exception e){
        message="修改成功" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("scmBPurcharseorder:delete")
public void deleteScmBPurcharseorders(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iScmBPurcharseorderService.deleteScmBPurcharseorders(arr_ids);
        }catch(Exception e){
        message="删除成功" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("scmBPurcharseorder:export")
public void export(QueryRequest request, ScmBPurcharseorder scmBPurcharseorder, HttpServletResponse response) throws FebsException {
        try {
        List<ScmBPurcharseorder> scmBPurcharseorders = this.iScmBPurcharseorderService.findScmBPurcharseorders(request, scmBPurcharseorder).getRecords();
        ExcelKit.$Export(ScmBPurcharseorder.class, response).downXlsx(scmBPurcharseorders, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public ScmBPurcharseorder detail(@NotBlank(message = "{required}") @PathVariable String id) {
    ScmBPurcharseorder scmBPurcharseorder=this.iScmBPurcharseorderService.getOrderById(id);
        return scmBPurcharseorder;
        }
        }