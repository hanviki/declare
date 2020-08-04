package cc.mrbird.febs.scm.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.scm.service.IVScmBSalereturnService;
import cc.mrbird.febs.scm.entity.VScmBSalereturn;

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
 * @since 2019-12-09
 */
@Slf4j
@Validated
@RestController
@RequestMapping("vScmBSalereturn")

public class VScmBSalereturnController extends BaseController{

private String message;
@Autowired
public IVScmBSalereturnService iVScmBSalereturnService;


/**
 * 分页查询数据
 *
 * @param bootStrapTable  分页信息
 * @param vScmBSalereturn 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("vScmBSalereturn:view")
public Map<String, Object> List(QueryRequest request, VScmBSalereturn vScmBSalereturn){
        return getDataTable(this.iVScmBSalereturnService.findVScmBSalereturns(request, vScmBSalereturn));
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
@RequiresPermissions("vScmBSalereturn:add")
public void addVScmBSalereturn(@Valid VScmBSalereturn vScmBSalereturn)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      //  vScmBSalereturn.setCreateUserId(currentUser.getUserId());
        this.iVScmBSalereturnService.createVScmBSalereturn(vScmBSalereturn);
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
@RequiresPermissions("vScmBSalereturn:update")
public void updateVScmBSalereturn(@Valid VScmBSalereturn vScmBSalereturn)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
     // vScmBSalereturn.setModifyUserId(currentUser.getUserId());
        this.iVScmBSalereturnService.updateVScmBSalereturn(vScmBSalereturn);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("vScmBSalereturn:delete")
public void deleteVScmBSalereturns(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iVScmBSalereturnService.deleteVScmBSalereturns(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("vScmBSalereturn:export")
public void export(QueryRequest request, VScmBSalereturn vScmBSalereturn, HttpServletResponse response) throws FebsException {
        try {
        List<VScmBSalereturn> vScmBSalereturns = this.iVScmBSalereturnService.findVScmBSalereturns(request, vScmBSalereturn).getRecords();
        ExcelKit.$Export(VScmBSalereturn.class, response).downXlsx(vScmBSalereturns, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public VScmBSalereturn detail(@NotBlank(message = "{required}") @PathVariable String id) {
    VScmBSalereturn vScmBSalereturn=this.iVScmBSalereturnService.getById(id);
        return vScmBSalereturn;
        }
        }