package cc.mrbird.febs.scm.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.scm.entity.ScmBQuerypriceD;
import cc.mrbird.febs.scm.service.IScmBQuerypriceDService;
import cc.mrbird.febs.scm.service.IScmBQuerypriceService;
import cc.mrbird.febs.scm.entity.ScmBQueryprice;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
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
@RequestMapping("scmBQueryprice")

public class ScmBQuerypriceController extends BaseController {

    private String message;
    @Autowired
    public IScmBQuerypriceService iScmBQuerypriceService;
    @Autowired
    public IScmBQuerypriceDService iScmBQuerypriceDService;

    /**
     * @param request
     * @param scmBQueryprice
     * @return
     */

    @GetMapping
    @RequiresPermissions("scmBQueryprice:view")
    public Map<String, Object> List(QueryRequest request, ScmBQueryprice scmBQueryprice) {
        return getDataTable(this.iScmBQuerypriceService.findScmBQueryprices(request, scmBQueryprice));
    }
    @GetMapping("gysList")
    @RequiresPermissions("scmBQueryprice:gysView")
    public Map<String, Object> gysList(QueryRequest request, ScmBQueryprice scmBQueryprice) {
        User currentUser = FebsUtil.getCurrentUser();
        scmBQueryprice.setGysaccount(currentUser.getUsername());
        return getDataTable(this.iScmBQuerypriceService.getQueryPriceByGys(request, scmBQueryprice));
    }

    /**
     *
     * @param maters
     * @param gys
     * @throws FebsException
     */
    @Log("新增询价/按钮")
    @PutMapping("xjEdit")
    @RequiresPermissions("scmBQuerypriceNew:edit")
    public void editScmBQuerypriceNew(ScmBQueryprice scmBQueryprice,String gys) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            Long userId=currentUser.getUserId();
            Long deptId=currentUser.getDeptId();
            this.iScmBQuerypriceService.updateScmBQueryprice(scmBQueryprice);
            List<ScmBQuerypriceD> list_ScmBQuerypriceD = JSON.parseObject(gys, new TypeReference<List<ScmBQuerypriceD>>() {
            });
            this.iScmBQuerypriceService.updateScmBQuerypriceNew(scmBQueryprice.getId(),list_ScmBQuerypriceD);
        } catch (Exception e) {
            message = "修改询价/按钮失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("新增询价/按钮")
    @PostMapping("xjAdd")
    @RequiresPermissions("scmBQuerypriceNew:add")
    public void addScmBQuerypriceNew(String maters,String gys,int state) throws FebsException {
        try {
            log.info(maters);
            User currentUser = FebsUtil.getCurrentUser();
            Long userId=currentUser.getUserId();
            Long deptId=currentUser.getDeptId();
            List<ScmBQueryprice> list_ScmBQueryprice = JSON.parseObject(maters, new TypeReference<List<ScmBQueryprice>>() {
            });
            List<ScmBQuerypriceD> list_ScmBQuerypriceD = JSON.parseObject(gys, new TypeReference<List<ScmBQuerypriceD>>() {
            });
            this.iScmBQuerypriceService.createScmBQuerypriceNew(list_ScmBQueryprice,list_ScmBQuerypriceD,userId,deptId,state);
        } catch (Exception e) {
            message = "新增询价/按钮失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
    @Log("新增/按钮")
    @PostMapping
    @RequiresPermissions("scmBQueryprice:add")
    public void addScmBQueryprice(@Valid ScmBQueryprice scmBQueryprice) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            scmBQueryprice.setCreateUserId(currentUser.getUserId());
            this.iScmBQuerypriceService.createScmBQueryprice(scmBQueryprice);
        } catch (Exception e) {
            message = "新增/按钮失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
    /**
     * @param scmBQueryprice
     * @throws FebsException
     */
    @Log("修改")
    @PutMapping
    @RequiresPermissions("scmBQueryprice:update")
    public void updateScmBQueryprice(@Valid ScmBQueryprice scmBQueryprice) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            scmBQueryprice.setModifyUserId(currentUser.getUserId());
            this.iScmBQuerypriceService.updateScmBQueryprice(scmBQueryprice);
        } catch (Exception e) {
            message = "修改失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @Log("删除")
    @DeleteMapping("/{ids}")
    @RequiresPermissions("scmBQueryprice:delete")
    public void deleteScmBQueryprices(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
          //  String[] arr_ids = ids.split(StringPool.COMMA);
            this.iScmBQuerypriceService.deleteScmBQueryprices(ids);
        } catch (Exception e) {
            message = "删除失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel")
    @RequiresPermissions("scmBQueryprice:export")
    public void export(QueryRequest request, ScmBQueryprice scmBQueryprice, HttpServletResponse response) throws FebsException {
        try {
            List<ScmBQueryprice> scmBQueryprices = this.iScmBQuerypriceService.findScmBQueryprices(request, scmBQueryprice).getRecords();
            ExcelKit.$Export(ScmBQueryprice.class, response).downXlsx(scmBQueryprices, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @GetMapping("/{id}")
    public ScmBQueryprice detail(@NotBlank(message = "{required}") @PathVariable String id) {
        ScmBQueryprice scmBQueryprice = this.iScmBQuerypriceService.getById(id);
        return scmBQueryprice;
    }

    @Log("结束询价")
    @PutMapping("stop")
    @RequiresPermissions("scmBQueryprice:stop")
    public void stopScmBQueryprice(@Valid String ids) throws FebsException {
        try {
            //  String[] arr_ids = ids.split(StringPool.COMMA);
            this.iScmBQuerypriceService.updateQueryState(ids,"stop");
        } catch (Exception e) {
            message = "操作失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
    @Log("撤销结束询价")
    @PutMapping("cancle")
    @RequiresPermissions("scmBQueryprice:cancle")
    public void cancelScmBQueryprice(@Valid String ids) throws FebsException {
        try {
            //  String[] arr_ids = ids.split(StringPool.COMMA);
            this.iScmBQuerypriceService.updateQueryState(ids,"cancle");
        } catch (Exception e) {
            message = "操作失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
}