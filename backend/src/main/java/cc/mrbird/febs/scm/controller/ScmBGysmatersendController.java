package cc.mrbird.febs.scm.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.scm.service.IScmBGysmatersendService;
import cc.mrbird.febs.scm.entity.ScmBGysmatersend;

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
 * @author viki
 * @since 2020-06-03
 */
@Slf4j
@Validated
@RestController
@RequestMapping("scmBGysmatersend")

public class ScmBGysmatersendController extends BaseController {

    private String message;
    @Autowired
    public IScmBGysmatersendService iScmBGysmatersendService;


    /**
     * 分页查询数据
     *
     * @param bootStrapTable   分页信息
     * @param scmBGysmatersend 查询条件
     * @return
     */
    @GetMapping
    @RequiresPermissions("scmBGysmatersend:view")
    public Map<String, Object> List(QueryRequest request, ScmBGysmatersend scmBGysmatersend) {
        User currentUser = FebsUtil.getCurrentUser();
        scmBGysmatersend.setGysaccount(currentUser.getUsername());
        return getDataTable(this.iScmBGysmatersendService.findScmBGysmatersends(request, scmBGysmatersend, "", ""));
    }

    @GetMapping("audit")
    @RequiresPermissions("scmBGysmatersend:view")
    public Map<String, Object> List2(QueryRequest request, ScmBGysmatersend scmBGysmatersend, String keyword_mater, String keyword_gys) {
        return getDataTable(this.iScmBGysmatersendService.findScmBGysmatersends(request, scmBGysmatersend, keyword_mater, keyword_gys));
    }

    /**
     * 跳转添加页面
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @Log("新增/按钮")
    @PostMapping
    @RequiresPermissions("scmBGysmatersend:add")
    public void addScmBGysmatersend(@Valid ScmBGysmatersend scmBGysmatersend) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            scmBGysmatersend.setCreateUserId(currentUser.getUserId());
            scmBGysmatersend.setGysaccount(currentUser.getUsername());
            scmBGysmatersend.setName(currentUser.getRealname());
            scmBGysmatersend.setState(0);
            scmBGysmatersend.setIsDeletemark(1);
            this.iScmBGysmatersendService.createScmBGysmatersend(scmBGysmatersend);
        } catch (Exception e) {
            message = "新增/按钮失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 跳转修改页面
     *
     * @param request
     * @param id      实体ID
     * @return
     */
    @Log("修改")
    @PutMapping
    @RequiresPermissions("scmBGysmatersend:update")
    public void updateScmBGysmatersend(@Valid ScmBGysmatersend scmBGysmatersend) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            scmBGysmatersend.setModifyUserId(currentUser.getUserId());
            this.iScmBGysmatersendService.updateScmBGysmatersend(scmBGysmatersend);
        } catch (Exception e) {
            message = "修改失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @Log("删除")
    @DeleteMapping("/{ids}")
    @RequiresPermissions("scmBGysmatersend:delete")
    public void deleteScmBGysmatersends(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            this.iScmBGysmatersendService.deleteScmBGysmatersends(arr_ids);
        } catch (Exception e) {
            message = "删除失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel")
    @RequiresPermissions("scmBGysmatersend:export")
    public void export(QueryRequest request, ScmBGysmatersend scmBGysmatersend, HttpServletResponse response) throws FebsException {
        try {
            List<ScmBGysmatersend> scmBGysmatersends = this.iScmBGysmatersendService.findScmBGysmatersends(request, scmBGysmatersend, "", "").getRecords();
            ExcelKit.$Export(ScmBGysmatersend.class, response).downXlsx(scmBGysmatersends, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @GetMapping("/{id}")
    public ScmBGysmatersend detail(@NotBlank(message = "{required}") @PathVariable String id) {
        ScmBGysmatersend scmBGysmatersend = this.iScmBGysmatersendService.getById(id);
        return scmBGysmatersend;
    }

    @Log("审核配送")
    @PostMapping("auditAdmin")
    public void auditAdmin(@Valid ScmBGysmatersend scmBGysmatersend) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();

            this.iScmBGysmatersendService.updateScmBGysmatersend(scmBGysmatersend);
        } catch (Exception e) {
            message = "修改失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
}