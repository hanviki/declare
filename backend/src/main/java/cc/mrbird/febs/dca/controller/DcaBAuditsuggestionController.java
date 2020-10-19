package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.service.IDcaBAuditsuggestionService;
import cc.mrbird.febs.dca.entity.DcaBAuditsuggestion;

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
 * @since 2020-09-22
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBAuditsuggestion")

public class DcaBAuditsuggestionController extends BaseController{

private String message;
@Autowired
public IDcaBAuditsuggestionService iDcaBAuditsuggestionService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBAuditsuggestion 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBAuditsuggestion:view")
public Map<String, Object> List(QueryRequest request, DcaBAuditsuggestion dcaBAuditsuggestion){
        return getDataTable(this.iDcaBAuditsuggestionService.findDcaBAuditsuggestions(request, dcaBAuditsuggestion));
        }

/**
 * 添加
 * @param  dcaBAuditsuggestion
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaBAuditsuggestion:add")
public void addDcaBAuditsuggestion(@Valid DcaBAuditsuggestion dcaBAuditsuggestion)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaBAuditsuggestion.setCreateUserId(currentUser.getUserId());
        this.iDcaBAuditsuggestionService.createDcaBAuditsuggestion(dcaBAuditsuggestion);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBAuditsuggestion
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBAuditsuggestion:update")
public void updateDcaBAuditsuggestion(@Valid DcaBAuditsuggestion dcaBAuditsuggestion)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBAuditsuggestion.setModifyUserId(currentUser.getUserId());
        this.iDcaBAuditsuggestionService.updateDcaBAuditsuggestion(dcaBAuditsuggestion);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBAuditsuggestion:delete")
public void deleteDcaBAuditsuggestions(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBAuditsuggestionService.deleteDcaBAuditsuggestions(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBAuditsuggestion:export")
public void export(QueryRequest request, DcaBAuditsuggestion dcaBAuditsuggestion, HttpServletResponse response) throws FebsException {
        try {
        List<DcaBAuditsuggestion> dcaBAuditsuggestions = this.iDcaBAuditsuggestionService.findDcaBAuditsuggestions(request, dcaBAuditsuggestion).getRecords();
        ExcelKit.$Export(DcaBAuditsuggestion.class, response).downXlsx(dcaBAuditsuggestions, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBAuditsuggestion detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBAuditsuggestion dcaBAuditsuggestion=this.iDcaBAuditsuggestionService.getById(id);
        return dcaBAuditsuggestion;
        }
        }