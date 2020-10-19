package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.service.IDcaDDeclaremodulesService;
import cc.mrbird.febs.dca.entity.DcaDDeclaremodules;

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
@RequestMapping("dcaDDeclaremodules")

public class DcaDDeclaremodulesController extends BaseController{

private String message;
@Autowired
public IDcaDDeclaremodulesService iDcaDDeclaremodulesService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaDDeclaremodules 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaDDeclaremodules:view")
public Map<String, Object> List(QueryRequest request, DcaDDeclaremodules dcaDDeclaremodules){
        return getDataTable(this.iDcaDDeclaremodulesService.findDcaDDeclaremoduless(request, dcaDDeclaremodules));
        }

/**
 * 添加
 * @param  dcaDDeclaremodules
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaDDeclaremodules:add")
public void addDcaDDeclaremodules(@Valid DcaDDeclaremodules dcaDDeclaremodules)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaDDeclaremodules.setCreateUserId(currentUser.getUserId());
        this.iDcaDDeclaremodulesService.createDcaDDeclaremodules(dcaDDeclaremodules);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaDDeclaremodules
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaDDeclaremodules:update")
public void updateDcaDDeclaremodules(@Valid DcaDDeclaremodules dcaDDeclaremodules)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaDDeclaremodules.setModifyUserId(currentUser.getUserId());
        this.iDcaDDeclaremodulesService.updateDcaDDeclaremodules(dcaDDeclaremodules);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaDDeclaremodules:delete")
public void deleteDcaDDeclaremoduless(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaDDeclaremodulesService.deleteDcaDDeclaremoduless(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaDDeclaremodules:export")
public void export(QueryRequest request, DcaDDeclaremodules dcaDDeclaremodules, HttpServletResponse response) throws FebsException {
        try {
        List<DcaDDeclaremodules> dcaDDeclaremoduless = this.iDcaDDeclaremodulesService.findDcaDDeclaremoduless(request, dcaDDeclaremodules).getRecords();
        ExcelKit.$Export(DcaDDeclaremodules.class, response).downXlsx(dcaDDeclaremoduless, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaDDeclaremodules detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaDDeclaremodules dcaDDeclaremodules=this.iDcaDDeclaremodulesService.getById(id);
        return dcaDDeclaremodules;
        }
        }