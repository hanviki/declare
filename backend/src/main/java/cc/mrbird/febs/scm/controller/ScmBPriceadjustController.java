package cc.mrbird.febs.scm.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.scm.service.IScmBPriceadjustService;
import cc.mrbird.febs.scm.entity.ScmBPriceadjust;

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
@RequestMapping("scmBPriceadjust")

public class ScmBPriceadjustController extends BaseController{

private String message;
@Autowired
public IScmBPriceadjustService iScmBPriceadjustService;


/**
 * 分页查询数据
 *
 * @param bootStrapTable  分页信息
 * @param scmBPriceadjust 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("scmBPriceadjust:view")
public Map<String, Object> List(QueryRequest request, ScmBPriceadjust scmBPriceadjust){
        return getDataTable(this.iScmBPriceadjustService.findScmBPriceadjusts(request, scmBPriceadjust));
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
@RequiresPermissions("scmBPriceadjust:add")
public void addScmBPriceadjust(@Valid ScmBPriceadjust scmBPriceadjust)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        scmBPriceadjust.setCreateUserId(currentUser.getUserId());
        this.iScmBPriceadjustService.createScmBPriceadjust(scmBPriceadjust);
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
@RequiresPermissions("scmBPriceadjust:update")
public void updateScmBPriceadjust(@Valid ScmBPriceadjust scmBPriceadjust)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      scmBPriceadjust.setModifyUserId(currentUser.getUserId());
        this.iScmBPriceadjustService.updateScmBPriceadjust(scmBPriceadjust);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("scmBPriceadjust:delete")
public void deleteScmBPriceadjusts(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iScmBPriceadjustService.deleteScmBPriceadjusts(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("scmBPriceadjust:export")
public void export(QueryRequest request, ScmBPriceadjust scmBPriceadjust, HttpServletResponse response) throws FebsException {
        try {
        List<ScmBPriceadjust> scmBPriceadjusts = this.iScmBPriceadjustService.findScmBPriceadjusts(request, scmBPriceadjust).getRecords();
        ExcelKit.$Export(ScmBPriceadjust.class, response).downXlsx(scmBPriceadjusts, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public ScmBPriceadjust detail(@NotBlank(message = "{required}") @PathVariable String id) {
    ScmBPriceadjust scmBPriceadjust=this.iScmBPriceadjustService.getById(id);
        return scmBPriceadjust;
        }
        }