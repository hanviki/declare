package cc.mrbird.febs.scm.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.scm.service.IScmDHrpmaterService;
import cc.mrbird.febs.scm.entity.ScmDHrpmater;

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
 * @since 2019-11-26
 */
@Slf4j
@Validated
@RestController
@RequestMapping("scmDHrpmater")

public class ScmDHrpmaterController extends BaseController{

private String message;
@Autowired
public IScmDHrpmaterService iScmDHrpmaterService;


/**
 * 分页查询数据
 *
 * @param bootStrapTable  分页信息
 * @param scmDHrpmater 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, ScmDHrpmater scmDHrpmater,String keyWord){
        scmDHrpmater.keyword=keyWord;
        return getDataTable(this.iScmDHrpmaterService.findScmDHrpmaters(request, scmDHrpmater));
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
@RequiresPermissions("scmDHrpmater:add")
public void addScmDHrpmater(@Valid ScmDHrpmater scmDHrpmater)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        scmDHrpmater.setCreateUserId(currentUser.getUserId());
        this.iScmDHrpmaterService.createScmDHrpmater(scmDHrpmater);
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
@RequiresPermissions("scmDHrpmater:update")
public void updateScmDHrpmater(@Valid ScmDHrpmater scmDHrpmater)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      scmDHrpmater.setModifyUserId(currentUser.getUserId());
        this.iScmDHrpmaterService.updateScmDHrpmater(scmDHrpmater);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("scmDHrpmater:delete")
public void deleteScmDHrpmaters(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iScmDHrpmaterService.deleteScmDHrpmaters(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("scmDHrpmater:export")
public void export(QueryRequest request, ScmDHrpmater scmDHrpmater, HttpServletResponse response) throws FebsException {
        try {
        List<ScmDHrpmater> scmDHrpmaters = this.iScmDHrpmaterService.findScmDHrpmaters(request, scmDHrpmater).getRecords();
        ExcelKit.$Export(ScmDHrpmater.class, response).downXlsx(scmDHrpmaters, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public ScmDHrpmater detail(@NotBlank(message = "{required}") @PathVariable String id) {
    ScmDHrpmater scmDHrpmater=this.iScmDHrpmaterService.getById(id);
        return scmDHrpmater;
        }
        }