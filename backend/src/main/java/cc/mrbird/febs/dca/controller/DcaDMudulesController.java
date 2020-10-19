package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.entity.DcaUserMoudules;
import cc.mrbird.febs.dca.service.IDcaDMudulesService;
import cc.mrbird.febs.dca.entity.DcaDMudules;

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
 * @since 2020-08-10
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaDMudules")

public class DcaDMudulesController extends BaseController{

private String message;
@Autowired
public IDcaDMudulesService iDcaDMudulesService;



/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaDMudules 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaDMudules:view")
public Map<String, Object> List(QueryRequest request, DcaDMudules dcaDMudules){
        return getDataTable(this.iDcaDMudulesService.findDcaDMuduless(request, dcaDMudules));
        }

    @GetMapping("tree")
    public Map<String, Object> treeList() {
        return this.iDcaDMudulesService.findDepts();
    }

    @GetMapping("treeByUserId")
    public Map<String, Object> treeList2() {
        User currentUser= FebsUtil.getCurrentUser();
        return this.iDcaDMudulesService.findDeptsByUserId(currentUser.getUserId());
    }

/**
 * 添加
 * @param  dcaDMudules
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaDMudules:add")
public void addDcaDMudules(@Valid DcaDMudules dcaDMudules)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaDMudules.setCreateUserId(currentUser.getUserId());
        this.iDcaDMudulesService.createDcaDMudules(dcaDMudules);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaDMudules
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaDMudules:update")
public void updateDcaDMudules(@Valid DcaDMudules dcaDMudules)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaDMudules.setModifyUserId(currentUser.getUserId());
        this.iDcaDMudulesService.updateDcaDMudules(dcaDMudules);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaDMudules:delete")
public void deleteDcaDMuduless(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaDMudulesService.deleteDcaDMuduless(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaDMudules:export")
public void export(QueryRequest request, DcaDMudules dcaDMudules, HttpServletResponse response) throws FebsException {
        try {
        List<DcaDMudules> dcaDMuduless = this.iDcaDMudulesService.findDcaDMuduless(request, dcaDMudules).getRecords();
        ExcelKit.$Export(DcaDMudules.class, response).downXlsx(dcaDMuduless, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaDMudules detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaDMudules dcaDMudules=this.iDcaDMudulesService.getById(id);
        return dcaDMudules;
        }
        }