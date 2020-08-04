package cc.mrbird.febs.scm.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.scm.service.IScmDMaterService;
import cc.mrbird.febs.scm.entity.ScmDMater;

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
 * @since 2019-11-11
 */
@Slf4j
@Validated
@RestController
@RequestMapping("scmDMater")

public class ScmDMaterController extends BaseController{

private String message;
@Autowired
public IScmDMaterService iScmDMaterService;


/**
 * 分页查询数据
 *
 * @param bootStrapTable  分页信息
 * @param scmDMater 查询条件
 * @return
 */
@GetMapping
//@RequiresPermissions("scmDMater:view")
public Map<String, Object> List(QueryRequest request, ScmDMater scmDMater,String keyWord){
        log.error("关键字"+keyWord);
        scmDMater.keyword=keyWord;
    User currentUser= FebsUtil.getCurrentUser();
    scmDMater.setGysaccount(currentUser.getUsername());
        return getDataTable(this.iScmDMaterService.findScmDMaters(request, scmDMater));
        }
    @GetMapping("list")
    public Map<String, Object> ListMater(QueryRequest request, ScmDMater scmDMater,String keyWord){
        log.error("关键字"+keyWord);
        scmDMater.keyword=keyWord;
        return getDataTable(this.iScmDMaterService.findScmDMaters(request, scmDMater));
    }
    @GetMapping("send")
    public Map<String, Object> ListMater2(QueryRequest request, ScmDMater scmDMater,String keyWord){
        log.error("关键字"+keyWord);
        scmDMater.keyword=keyWord;
        return getDataTable(this.iScmDMaterService.findScmDMaters_send(request, scmDMater));
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
@RequiresPermissions("scmDMater:add")
public void addScmDMater(@Valid ScmDMater scmDMater)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        scmDMater.setCreateUserId(currentUser.getUserId());
        this.iScmDMaterService.createScmDMater(scmDMater);
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
@RequiresPermissions("scmDMater:update")
public void updateScmDMater(@Valid ScmDMater scmDMater)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      scmDMater.setModifyUserId(currentUser.getUserId());
        this.iScmDMaterService.updateScmDMater(scmDMater);
        }catch(Exception e){
        message="修改成功" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("scmDMater:delete")
public void deleteScmDMaters(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iScmDMaterService.deleteScmDMaters(arr_ids);
        }catch(Exception e){
        message="删除成功" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("scmDMater:export")
public void export(QueryRequest request, ScmDMater scmDMater, HttpServletResponse response) throws FebsException {
        try {
        List<ScmDMater> scmDMaters = this.iScmDMaterService.findScmDMaters(request, scmDMater).getRecords();
        ExcelKit.$Export(ScmDMater.class, response).downXlsx(scmDMaters, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public ScmDMater detail(@NotBlank(message = "{required}") @PathVariable String id) {
    ScmDMater scmDMater=this.iScmDMaterService.getById(id);
        return scmDMater;
        }
        }