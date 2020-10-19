package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.service.IDcaUserMoudulesService;
import cc.mrbird.febs.dca.entity.DcaUserMoudules;

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
import java.util.stream.Collectors;

/**
 *
 * @author viki
 * @since 2020-08-10
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaUserMoudules")

public class DcaUserMoudulesController extends BaseController{

private String message;
@Autowired
public IDcaUserMoudulesService iDcaUserMoudulesService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaUserMoudules 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaUserMoudules:view")
public Map<String, Object> List(QueryRequest request, DcaUserMoudules dcaUserMoudules){
        return getDataTable(this.iDcaUserMoudulesService.findDcaUserMouduless(request, dcaUserMoudules));
        }

/**
 * 添加
 * @param  dcaUserMoudules
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaUserMoudules:add")
public void addDcaUserMoudules(@Valid DcaUserMoudules dcaUserMoudules)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
       // dcaUserMoudules.setCreateUserId(currentUser.getUserId());
        this.iDcaUserMoudulesService.createDcaUserMoudules(dcaUserMoudules);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaUserMoudules
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaUserMoudules:update")
public void updateDcaUserMoudules(@Valid DcaUserMoudules dcaUserMoudules)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      //dcaUserMoudules.setModifyUserId(currentUser.getUserId());
        this.iDcaUserMoudulesService.updateDcaUserMoudules(dcaUserMoudules);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaUserMoudules:delete")
public void deleteDcaUserMouduless(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaUserMoudulesService.deleteDcaUserMouduless(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaUserMoudules:export")
public void export(QueryRequest request, DcaUserMoudules dcaUserMoudules, HttpServletResponse response) throws FebsException {
        try {
        List<DcaUserMoudules> dcaUserMouduless = this.iDcaUserMoudulesService.findDcaUserMouduless(request, dcaUserMoudules).getRecords();
        ExcelKit.$Export(DcaUserMoudules.class, response).downXlsx(dcaUserMouduless, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaUserMoudules detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaUserMoudules dcaUserMoudules=this.iDcaUserMoudulesService.getById(id);
        return dcaUserMoudules;
        }

        @GetMapping("mudules/{userId}")
        public List<String> getRoleMenus(@NotBlank(message = "{required}") @PathVariable String userId) {
                List<DcaUserMoudules> list = this.iDcaUserMoudulesService.getMudulesByUserId(Integer.parseInt(userId));
                return list.stream().map(area -> String.valueOf(area.getMuduleId())).collect(Collectors.toList());
        }
        }