package cc.mrbird.febs.scm.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.scm.entity.VScmBSalereturn;
import cc.mrbird.febs.scm.service.IScmBSalereturnService;
import cc.mrbird.febs.scm.entity.ScmBSalereturn;

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
 * @since 2019-12-09
 */
@Slf4j
@Validated
@RestController
@RequestMapping("scmBSalereturn")

public class ScmBSalereturnController extends BaseController {

    private String message;
    @Autowired
    public IScmBSalereturnService iScmBSalereturnService;


    /**
     * 分页查询数据
     *
     * @param bootStrapTable 分页信息
     * @param scmBSalereturn 查询条件
     * @return
     */
    @GetMapping
    @RequiresPermissions("scmBSalereturn:view")
    public Map<String, Object> List(QueryRequest request, VScmBSalereturn scmBSalereturn) {
        return getDataTable(this.iScmBSalereturnService.findScmBSalereturns(request, scmBSalereturn));
    }
    @GetMapping("setting/{type}")
    @RequiresPermissions("scmBSalereturn:view")
    public Map<String, Object> ListSetting(QueryRequest request, VScmBSalereturn scmBSalereturn ,@PathVariable int type) {
        scmBSalereturn.setState(type);
        return getDataTable(this.iScmBSalereturnService.findScmBSalereturns(request, scmBSalereturn));
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
    @RequiresPermissions("scmBSalereturn:add")
    public void addScmBSalereturn(@Valid ScmBSalereturn scmBSalereturn) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            scmBSalereturn.setCreateUserId(currentUser.getUserId());
            scmBSalereturn.setState(0);//未提交
            this.iScmBSalereturnService.createScmBSalereturn(scmBSalereturn);
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
    @RequiresPermissions("scmBSalereturn:update")
    public void updateScmBSalereturn(@Valid ScmBSalereturn scmBSalereturn) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            scmBSalereturn.setModifyUserId(currentUser.getUserId());
            this.iScmBSalereturnService.updateScmBSalereturn(scmBSalereturn);
        } catch (Exception e) {
            message = "修改失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @DeleteMapping("submit/{ids}")
    @RequiresPermissions("scmBSalereturn:submit")
    public void submitScmBSalereturns(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            for (String id:arr_ids
            ) {
                ScmBSalereturn sbs=new ScmBSalereturn();
                sbs.setId(id);
                sbs.setState(1);
                this.iScmBSalereturnService.updateScmBSalereturn(sbs);
            }
        } catch (Exception e) {
            message = "提交失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @DeleteMapping("back/{ids}")
    @RequiresPermissions("scmBSalereturn:back")
    public void settingScmBSalereturns(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            for (String id:arr_ids
                 ) {
                ScmBSalereturn sbs=new ScmBSalereturn();
                sbs.setId(id);
                sbs.setState(4);
                this.iScmBSalereturnService.updateScmBSalereturn(sbs);
            }
        } catch (Exception e) {
            message = "确认失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
    @DeleteMapping("audit/{ids}")
    @RequiresPermissions("scmBSalereturn:audit")
    public void auditScmBSalereturns(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            for (String id:arr_ids
            ) {
                ScmBSalereturn sbs=new ScmBSalereturn();
                sbs.setId(id);
                sbs.setState(3);
                this.iScmBSalereturnService.updateScmBSalereturn(sbs);
            }
        } catch (Exception e) {
            message = "审核失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @DeleteMapping("fpsubmit/{ids}")
    @RequiresPermissions("scmBSalereturn:fpSubmit")
    public void fpScmBSalereturns(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            for (String id:arr_ids
            ) {
                ScmBSalereturn sbs=new ScmBSalereturn();
                sbs.setId(id);
                sbs.setState(5);
                this.iScmBSalereturnService.updateScmBSalereturn(sbs);
            }
        } catch (Exception e) {
            message = "发票失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("删除")
    @DeleteMapping("/{ids}")
    @RequiresPermissions("scmBSalereturn:delete")
    public void deleteScmBSalereturns(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            this.iScmBSalereturnService.deleteScmBSalereturns(arr_ids);
        } catch (Exception e) {
            message = "删除失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel")
    @RequiresPermissions("scmBSalereturn:export")
    public void export(QueryRequest request, VScmBSalereturn scmBSalereturn, HttpServletResponse response) throws FebsException {
        try {
            List<VScmBSalereturn> scmBSalereturns = this.iScmBSalereturnService.findScmBSalereturns(request, scmBSalereturn).getRecords();
            ExcelKit.$Export(ScmBSalereturn.class, response).downXlsx(scmBSalereturns, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @GetMapping("/{id}")
    public ScmBSalereturn detail(@NotBlank(message = "{required}") @PathVariable String id) {
        ScmBSalereturn scmBSalereturn = this.iScmBSalereturnService.getById(id);
        return scmBSalereturn;
    }
}