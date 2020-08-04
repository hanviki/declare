package cc.mrbird.febs.scm.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.scm.service.IScmBQuerypriceDService;
import cc.mrbird.febs.scm.entity.ScmBQuerypriceD;

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
 * @since 2019-12-26
 */
@Slf4j
@Validated
@RestController
@RequestMapping("scmBQuerypriceD")

public class ScmBQuerypriceDController extends BaseController {

    private String message;
    @Autowired
    public IScmBQuerypriceDService iScmBQuerypriceDService;


    /**
     * @param request
     * @param scmBQuerypriceD
     * @return
     */
    @GetMapping
    public Map<String, Object> List(QueryRequest request, ScmBQuerypriceD scmBQuerypriceD) {
        return getDataTable(this.iScmBQuerypriceDService.findScmBQuerypriceDs(request, scmBQuerypriceD));
    }

    /**
     * @param scmBQuerypriceD
     * @throws FebsException
     */
    @Log("新增/按钮")
    @PostMapping
    @RequiresPermissions("scmBQuerypriceD:add")
    public void addScmBQuerypriceD(@Valid ScmBQuerypriceD scmBQuerypriceD) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            scmBQuerypriceD.setCreateUserId(currentUser.getUserId());
            this.iScmBQuerypriceDService.createScmBQuerypriceD(scmBQuerypriceD);
        } catch (Exception e) {
            message = "新增/按钮失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * @param scmBQuerypriceD
     * @throws FebsException
     */
    @Log("修改")
    @PutMapping
    @RequiresPermissions("scmBQuerypriceD:update")
    public void updateScmBQuerypriceD(@Valid ScmBQuerypriceD scmBQuerypriceD) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            scmBQuerypriceD.setModifyUserId(currentUser.getUserId());
            this.iScmBQuerypriceDService.updateScmBQuerypriceD(scmBQuerypriceD);
        } catch (Exception e) {
            message = "修改失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @Log("删除")
    @DeleteMapping("/{ids}")
    @RequiresPermissions("scmBQuerypriceD:delete")
    public void deleteScmBQuerypriceDs(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            this.iScmBQuerypriceDService.deleteScmBQuerypriceDs(arr_ids);
        } catch (Exception e) {
            message = "删除失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel")
    @RequiresPermissions("scmBQuerypriceD:export")
    public void export(QueryRequest request, ScmBQuerypriceD scmBQuerypriceD, HttpServletResponse response) throws FebsException {
        try {
            List<ScmBQuerypriceD> scmBQuerypriceDs = this.iScmBQuerypriceDService.findScmBQuerypriceDs(request, scmBQuerypriceD).getRecords();
            ExcelKit.$Export(ScmBQuerypriceD.class, response).downXlsx(scmBQuerypriceDs, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @GetMapping("/{id}")
    public ScmBQuerypriceD detail(@NotBlank(message = "{required}") @PathVariable String id) {
        ScmBQuerypriceD scmBQuerypriceD = this.iScmBQuerypriceDService.getById(id);
        return scmBQuerypriceD;
    }
}