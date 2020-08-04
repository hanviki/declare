package cc.mrbird.febs.scm.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.scm.service.IScmDSenddepartService;
import cc.mrbird.febs.scm.entity.ScmDSenddepart;

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
@RequestMapping("scmDSenddepart")

public class ScmDSenddepartController extends BaseController{

private String message;
@Autowired
public IScmDSenddepartService iScmDSenddepartService;


/**
 * 分页查询数据
 *
 * @param bootStrapTable  分页信息
 * @param scmDSenddepart 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, ScmDSenddepart scmDSenddepart){
        return getDataTable(this.iScmDSenddepartService.findScmDSenddeparts(request, scmDSenddepart));
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
@RequiresPermissions("scmDSenddepart:add")
public void addScmDSenddepart(@Valid ScmDSenddepart scmDSenddepart)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        scmDSenddepart.setCreateUserId(currentUser.getUserId());
        this.iScmDSenddepartService.createScmDSenddepart(scmDSenddepart);
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
@RequiresPermissions("scmDSenddepart:update")
public void updateScmDSenddepart(@Valid ScmDSenddepart scmDSenddepart)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      scmDSenddepart.setModifyUserId(currentUser.getUserId());
        this.iScmDSenddepartService.updateScmDSenddepart(scmDSenddepart);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("scmDSenddepart:delete")
public void deleteScmDSenddeparts(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iScmDSenddepartService.deleteScmDSenddeparts(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("scmDSenddepart:export")
public void export(QueryRequest request, ScmDSenddepart scmDSenddepart, HttpServletResponse response) throws FebsException {
        try {
        List<ScmDSenddepart> scmDSenddeparts = this.iScmDSenddepartService.findScmDSenddeparts(request, scmDSenddepart).getRecords();
        ExcelKit.$Export(ScmDSenddepart.class, response).downXlsx(scmDSenddeparts, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public ScmDSenddepart detail(@NotBlank(message = "{required}") @PathVariable String id) {
    ScmDSenddepart scmDSenddepart=this.iScmDSenddepartService.getById(id);
        return scmDSenddepart;
        }
        }