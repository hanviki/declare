package cc.mrbird.febs.scm.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.scm.service.IScmBQuerypriceProductService;
import cc.mrbird.febs.scm.entity.ScmBQuerypriceProduct;

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
 * @since 2019-12-27
 */
@Slf4j
@Validated
@RestController
@RequestMapping("scmBQuerypriceProduct")

public class ScmBQuerypriceProductController extends BaseController{

private String message;
@Autowired
public IScmBQuerypriceProductService iScmBQuerypriceProductService;


/**
 * 分页查询数据
 *
 * @param bootStrapTable  分页信息
 * @param scmBQuerypriceProduct 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("scmBQuerypriceProduct:view")
public Map<String, Object> List(QueryRequest request, ScmBQuerypriceProduct scmBQuerypriceProduct){
        return getDataTable(this.iScmBQuerypriceProductService.findScmBQuerypriceProducts(request, scmBQuerypriceProduct));
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
@RequiresPermissions("scmBQuerypriceProduct:add")
public void addScmBQuerypriceProduct(@Valid ScmBQuerypriceProduct scmBQuerypriceProduct)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        scmBQuerypriceProduct.setCreateUserId(currentUser.getUserId());
        this.iScmBQuerypriceProductService.createScmBQuerypriceProduct(scmBQuerypriceProduct);
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
@RequiresPermissions("scmBQuerypriceProduct:update")
public void updateScmBQuerypriceProduct(@Valid ScmBQuerypriceProduct scmBQuerypriceProduct)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      scmBQuerypriceProduct.setModifyUserId(currentUser.getUserId());
        this.iScmBQuerypriceProductService.updateScmBQuerypriceProduct(scmBQuerypriceProduct);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("scmBQuerypriceProduct:delete")
public void deleteScmBQuerypriceProducts(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iScmBQuerypriceProductService.deleteScmBQuerypriceProducts(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("scmBQuerypriceProduct:export")
public void export(QueryRequest request, ScmBQuerypriceProduct scmBQuerypriceProduct, HttpServletResponse response) throws FebsException {
        try {
        List<ScmBQuerypriceProduct> scmBQuerypriceProducts = this.iScmBQuerypriceProductService.findScmBQuerypriceProducts(request, scmBQuerypriceProduct).getRecords();
        ExcelKit.$Export(ScmBQuerypriceProduct.class, response).downXlsx(scmBQuerypriceProducts, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public ScmBQuerypriceProduct detail(@NotBlank(message = "{required}") @PathVariable String id) {
    ScmBQuerypriceProduct scmBQuerypriceProduct=this.iScmBQuerypriceProductService.getById(id);
        return scmBQuerypriceProduct;
        }
        }