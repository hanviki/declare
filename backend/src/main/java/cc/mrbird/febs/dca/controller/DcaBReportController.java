package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.FebsResponse;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.service.IDcaBReportService;
import cc.mrbird.febs.dca.entity.DcaBReport;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
 * @since 2020-11-13
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBReport")

public class DcaBReportController extends BaseController {

    private String message;
    @Autowired
    public IDcaBReportService iDcaBReportService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/dca/DcaBReport/DcaBReport','dca/DcaBReport/DcaBReport','dcaBReport:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'新增','dcaBReport:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'编辑','dcaBReport:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'删除','dcaBReport:delete',1,1,NOW())
 */


    /**
     * 分页查询数据
     *
     * @param request    分页信息
     * @param dcaBReport 查询条件
     * @return
     */
    @GetMapping
    public Map<String, Object> List(QueryRequest request, DcaBReport dcaBReport) {
        return getDataTable(this.iDcaBReportService.findDcaBReports(request, dcaBReport));
    }

    /**
     * 添加
     *
     * @param dcaBReport
     * @return
     */
    @Log("新增/按钮")
    @PostMapping
    public FebsResponse addDcaBReport(@Valid DcaBReport dcaBReport) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            dcaBReport.setCreateUserId(currentUser.getUserId());
            if(!StringUtils.isNotBlank(dcaBReport.getIfshuangbao()))
            {
                dcaBReport.setIfshuangbao("否");
            }
            if(!StringUtils.isNotBlank(dcaBReport.getIffuhediyi()))
            {
                dcaBReport.setIffuhediyi("否");
            }
            if(!StringUtils.isNotBlank(dcaBReport.getIffuhekeyan()))
            {
                dcaBReport.setIffuhekeyan("否");
            }
            if(!StringUtils.isNotBlank(dcaBReport.getIfdaitou()))
            {
                dcaBReport.setIfdaitou("否");
            }
            if (StringUtils.isNotBlank(dcaBReport.getId()) && !dcaBReport.getId().contains(dcaBReport.getUserAccount())) {
                this.iDcaBReportService.updateDcaBReport(dcaBReport);
            } else {
                this.iDcaBReportService.createDcaBReport(dcaBReport);
            }
            return  new FebsResponse().data(dcaBReport.getId());
        } catch (Exception e) {
            message = "新增或提交失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改
     *
     * @param dcaBReport
     * @return
     */
    @Log("修改")
    @PutMapping
    public void updateDcaBReport(@Valid DcaBReport dcaBReport) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            dcaBReport.setModifyUserId(currentUser.getUserId());
            this.iDcaBReportService.updateDcaBReport(dcaBReport);
        } catch (Exception e) {
            message = "修改失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @Log("删除")
    @DeleteMapping("/{ids}")
    @RequiresPermissions("dcaBReport:delete")
    public void deleteDcaBReports(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            this.iDcaBReportService.deleteDcaBReports(arr_ids);
        } catch (Exception e) {
            message = "删除失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel")
    @RequiresPermissions("dcaBReport:export")
    public void export(QueryRequest request, DcaBReport dcaBReport, HttpServletResponse response) throws FebsException {
        try {
            List<DcaBReport> dcaBReports = this.iDcaBReportService.findDcaBReports(request, dcaBReport).getRecords();
            ExcelKit.$Export(DcaBReport.class, response).downXlsx(dcaBReports, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @GetMapping("/{id}")
    public DcaBReport detail(@NotBlank(message = "{required}") @PathVariable String id) {
        DcaBReport dcaBReport = this.iDcaBReportService.getById(id);
        return dcaBReport;
    }
}