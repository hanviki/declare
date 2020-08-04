package cc.mrbird.febs.scm.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.scm.service.IScmBQuotedpriceDService;
import cc.mrbird.febs.scm.entity.ScmBQuotedpriceD;

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
@RequestMapping("scmBQuotedpriceD")

public class ScmBQuotedpriceDController extends BaseController{

private String message;
@Autowired
public IScmBQuotedpriceDService iScmBQuotedpriceDService;


/**
 * 分页查询数据
 *
 * @param bootStrapTable  分页信息
 * @param scmBQuotedpriceD 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("scmBQuotedpriceD:view")
public Map<String, Object> List(QueryRequest request, ScmBQuotedpriceD scmBQuotedpriceD){
        return getDataTable(this.iScmBQuotedpriceDService.findScmBQuotedpriceDs(request, scmBQuotedpriceD));
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
@RequiresPermissions("scmBQuotedpriceD:add")
public void addScmBQuotedpriceD(@Valid ScmBQuotedpriceD scmBQuotedpriceD)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        scmBQuotedpriceD.setCreateUserId(currentUser.getUserId());
        this.iScmBQuotedpriceDService.createScmBQuotedpriceD(scmBQuotedpriceD);
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
@RequiresPermissions("scmBQuotedpriceD:update")
public void updateScmBQuotedpriceD(@Valid ScmBQuotedpriceD scmBQuotedpriceD)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      scmBQuotedpriceD.setModifyUserId(currentUser.getUserId());
        this.iScmBQuotedpriceDService.updateScmBQuotedpriceD(scmBQuotedpriceD);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("scmBQuotedpriceD:delete")
public void deleteScmBQuotedpriceDs(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iScmBQuotedpriceDService.deleteScmBQuotedpriceDs(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("scmBQuotedpriceD:export")
public void export(QueryRequest request, ScmBQuotedpriceD scmBQuotedpriceD, HttpServletResponse response) throws FebsException {
        try {
        List<ScmBQuotedpriceD> scmBQuotedpriceDs = this.iScmBQuotedpriceDService.findScmBQuotedpriceDs(request, scmBQuotedpriceD).getRecords();
        ExcelKit.$Export(ScmBQuotedpriceD.class, response).downXlsx(scmBQuotedpriceDs, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public ScmBQuotedpriceD detail(@NotBlank(message = "{required}") @PathVariable String id) {
    ScmBQuotedpriceD scmBQuotedpriceD=this.iScmBQuotedpriceDService.getById(id);
        return scmBQuotedpriceD;
        }
        }