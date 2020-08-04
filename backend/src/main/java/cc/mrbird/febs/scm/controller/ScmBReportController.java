package cc.mrbird.febs.scm.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.scm.service.IScmBReportService;
import cc.mrbird.febs.scm.entity.ScmBReport;

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
@RequestMapping("scmBReport")

public class ScmBReportController extends BaseController{

private String message;
@Autowired
public IScmBReportService iScmBReportService;


/**
 * 分页查询数据
 *
 * @param bootStrapTable  分页信息
 * @param scmBReport 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("scmBReport:view")
public Map<String, Object> List(QueryRequest request, ScmBReport scmBReport){
        return getDataTable(this.iScmBReportService.findScmBReports(request, scmBReport));
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
@RequiresPermissions("scmBReport:add")
public void addScmBReport(@Valid ScmBReport scmBReport)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        scmBReport.setCreateUserId(currentUser.getUserId());
        this.iScmBReportService.createScmBReport(scmBReport);
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
@RequiresPermissions("scmBReport:update")
public void updateScmBReport(@Valid ScmBReport scmBReport)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      scmBReport.setModifyUserId(currentUser.getUserId());
        this.iScmBReportService.updateScmBReport(scmBReport);
        }catch(Exception e){
        message="修改成功" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("scmBReport:delete")
public void deleteScmBReports(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iScmBReportService.deleteScmBReports(arr_ids);
        }catch(Exception e){
        message="删除成功" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("scmBReport:export")
public void export(QueryRequest request, ScmBReport scmBReport, HttpServletResponse response) throws FebsException {
        try {
        List<ScmBReport> scmBReports = this.iScmBReportService.findScmBReports(request, scmBReport).getRecords();
        ExcelKit.$Export(ScmBReport.class, response).downXlsx(scmBReports, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public ScmBReport detail(@NotBlank(message = "{required}") @PathVariable String id) {
    ScmBReport scmBReport=this.iScmBReportService.getById(id);
        return scmBReport;
        }
        }