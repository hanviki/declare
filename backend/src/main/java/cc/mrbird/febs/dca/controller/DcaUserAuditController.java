package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.common.utils.ExportExcelUtils;
import cc.mrbird.febs.dca.entity.DcaBAuditdynamic;
import cc.mrbird.febs.dca.entity.DcaBUser;
import cc.mrbird.febs.dca.service.IDcaBAuditdynamicService;
import cc.mrbird.febs.dca.service.IDcaBUserService;
import cc.mrbird.febs.dca.service.IDcaUserAuditService;
import cc.mrbird.febs.dca.entity.DcaUserAudit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import cn.hutool.core.date.DateUtil;
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
 * @author viki
 * @since 2020-10-23
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaUserAudit")

public class DcaUserAuditController extends BaseController {

    private String message;
    @Autowired
    public IDcaUserAuditService iDcaUserAuditService;
    @Autowired
    public IDcaBUserService iDcaBUserService;
    @Autowired
    public IDcaBAuditdynamicService iDcaBAuditdynamicService;

    /**
     * 分页查询数据
     *
     * @param request      分页信息
     * @param dcaUserAudit 查询条件
     * @return
     */
    @GetMapping
    @RequiresPermissions("dcaUserAudit:view")
    public Map<String, Object> List(QueryRequest request, DcaUserAudit dcaUserAudit) {
        return getDataTable(this.iDcaUserAuditService.findDcaUserAudits(request, dcaUserAudit));
    }


    /**
     * 添加
     *
     * @param dcaUserAudit
     * @return
     */
    @Log("新增/按钮")
    @PostMapping
    public void addDcaUserAudit(@Valid DcaUserAudit dcaUserAudit) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();

            this.iDcaUserAuditService.deleteByuseraccount(currentUser.getUsername());
            this.iDcaUserAuditService.createDcaUserAudit(dcaUserAudit);
        } catch (Exception e) {
            message = "新增/按钮失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改
     *
     * @param dcaUserAudit
     * @return
     */
    @Log("修改")
    @PutMapping
    @RequiresPermissions("dcaUserAudit:update")
    public void updateDcaUserAudit(@Valid DcaUserAudit dcaUserAudit) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();

            this.iDcaUserAuditService.updateDcaUserAudit(dcaUserAudit);
        } catch (Exception e) {
            message = "修改失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @Log("删除")
    @DeleteMapping("/{ids}")
    @RequiresPermissions("dcaUserAudit:delete")
    public void deleteDcaUserAudits(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            this.iDcaUserAuditService.deleteDcaUserAudits(arr_ids);
        } catch (Exception e) {
            message = "删除失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel")
    @RequiresPermissions("dcaUserAudit:export")
    public void export(QueryRequest request, DcaUserAudit dcaUserAudit, HttpServletResponse response) throws FebsException {
        try {
            List<DcaUserAudit> dcaUserAudits = this.iDcaUserAuditService.findDcaUserAudits(request, dcaUserAudit).getRecords();
            ExcelKit.$Export(DcaUserAudit.class, response).downXlsx(dcaUserAudits, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
    @PostMapping("excel2")
    public void export(QueryRequest request, DcaBUser dcaBUser,String dataJson,HttpServletResponse response)throws FebsException{
        try{
            request.setPageNum(1);
            request.setPageSize(10000);
            User currentUser = FebsUtil.getCurrentUser();
            dcaBUser.setCreateUserId(currentUser.getUserId());
            List<DcaBUser> dcaBAuditdynamics=this.iDcaBUserService.findDcaBUsersAuditCustom(request, dcaBUser).getRecords();
          //  LambdaQueryWrapper<DcaBAuditdynamic> queryWrapperDynamic = new LambdaQueryWrapper<>();

            List<DcaBAuditdynamic> listDynamic= this.iDcaBAuditdynamicService.list();
            //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
            ExportExcelUtils.exportCustomExcelCutome(response, dcaBAuditdynamics,dataJson,listDynamic,"");
        }catch(Exception e){
            message="导出Excel失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }
    @GetMapping("/{id}")
    public DcaUserAudit detail(@NotBlank(message = "{required}") @PathVariable String id) {
        DcaUserAudit dcaUserAudit = this.iDcaUserAuditService.getById(id);
        return dcaUserAudit;
    }

    @GetMapping("mudules/{userId}")
    public List<String> getRoleMenus(@NotBlank(message = "{required}") @PathVariable String userId) {
        LambdaQueryWrapper<DcaUserAudit> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaUserAudit::getUserId, Integer.parseInt(userId));//
        List<DcaUserAudit> list = this.iDcaUserAuditService.list(queryWrapper);
        return list.stream().map(area -> String.valueOf(area.getAuditId())).collect(Collectors.toList());
    }

    @GetMapping("user")
    public Map<String, Object> List(QueryRequest request, DcaBUser dcaUserAudit, int state) {
        User currentUser = FebsUtil.getCurrentUser();
        dcaUserAudit.setCreateUserId(currentUser.getUserId());
        return getDataTable(this.iDcaBUserService.findDcaBUsersAudit(request, dcaUserAudit, state));
    }
    @GetMapping("userList")
    public Map<String, Object> ListUser(QueryRequest request, DcaBUser dcaUserAudit) {
        User currentUser = FebsUtil.getCurrentUser();
       // dcaUserAudit.setCreateUserId(currentUser.getUserId());
        return getDataTable(this.iDcaBUserService.findDcaBUsersAll(request, dcaUserAudit));
    }

    @GetMapping("userAuditResult")
    public Map<String, Object> List(QueryRequest request, DcaBUser dcaUserAudit) {
        User currentUser = FebsUtil.getCurrentUser();
        dcaUserAudit.setCreateUserId(currentUser.getUserId());
        return getDataTable(this.iDcaBUserService.findDcaBUsersAuditResult(request, dcaUserAudit));
    }
}