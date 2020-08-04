package cc.mrbird.febs.scm.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.FebsResponse;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.scm.service.IScmDAreaService;
import cc.mrbird.febs.scm.entity.ScmDArea;

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
 * @since 2019-11-11
 */
@Slf4j
@Validated
@RestController
@RequestMapping("scmDArea")

public class ScmDAreaController extends BaseController {

    private String message;
    @Autowired
    public IScmDAreaService iScmDAreaService;


    /**
     *
     * @param request
     * @param scmDArea
     * @return
     */
    @GetMapping
    @RequiresPermissions("scmDArea:view")
    public Map<String, Object> List(QueryRequest request, ScmDArea scmDArea) {
        return this.iScmDAreaService.findScmDAreas(request, scmDArea);
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
    @RequiresPermissions("scmDArea:add")
    public void addScmDArea(@Valid ScmDArea scmDArea) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            scmDArea.setCreateUserId(currentUser.getUserId());
            this.iScmDAreaService.createScmDArea(scmDArea);
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
    @RequiresPermissions("scmDArea:update")
    public void updateScmDArea(@Valid ScmDArea scmDArea) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            scmDArea.setModifyUserId(currentUser.getUserId());
            this.iScmDAreaService.updateScmDArea(scmDArea);
        } catch (Exception e) {
            message = "修改成功";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @Log("删除")
    @DeleteMapping("/{ids}")
    @RequiresPermissions("scmDArea:delete")
    public void deleteScmDAreas(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            this.iScmDAreaService.deleteScmDAreas(arr_ids);
        } catch (Exception e) {
            message = "删除成功";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @GetMapping("/{id}")
    public ScmDArea detail(@NotBlank(message = "{required}") @PathVariable String id) {
        ScmDArea scmDArea = this.iScmDAreaService.getById(id);
        return scmDArea;
    }

    @GetMapping("userArea")
    public FebsResponse areaUser() {
        User currentUser = FebsUtil.getCurrentUser();
        Long userid = currentUser.getUserId();
        FebsResponse febs = new FebsResponse();
        if(currentUser.getRoleId().equals("3") ||currentUser.getRoleId().equals("4")) {
            febs.data(this.iScmDAreaService.getAreasAll());
        }
        else
        {
            febs.data(this.iScmDAreaService.getAreasByUserId(userid));
        }
        return febs;
    }
    @GetMapping("getAreas")
    public FebsResponse getAreas() {
        FebsResponse febs = new FebsResponse();
        febs.data(this.iScmDAreaService.getAreasAll());
        return febs;
    }
}