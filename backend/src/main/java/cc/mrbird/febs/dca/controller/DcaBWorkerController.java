package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.service.IDcaBWorkerService;
import cc.mrbird.febs.dca.entity.DcaBWorker;

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
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author viki
 * @since 2021-05-24
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBWorker")

public class DcaBWorkerController extends BaseController {

    private String message;
    @Autowired
    public IDcaBWorkerService iDcaBWorkerService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'协和医院合同制职工信息确认表','/dca/DcaBWorker/DcaBWorker','dca/DcaBWorker/DcaBWorker','dcaBWorker:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'协和医院合同制职工信息确认表新增','dcaBWorker:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'协和医院合同制职工信息确认表编辑','dcaBWorker:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'协和医院合同制职工信息确认表删除','dcaBWorker:delete',1,1,NOW())
 */


    /**
     * 分页查询数据
     *
     * @param request    分页信息
     * @param dcaBWorker 查询条件
     * @return
     */
    @GetMapping
    @RequiresPermissions("dcaBWorker:view")
    public Map<String, Object> List(QueryRequest request, DcaBWorker dcaBWorker) {
        return getDataTable(this.iDcaBWorkerService.findDcaBWorkers(request, dcaBWorker));
    }

    /**
     * 添加
     *
     * @param dcaBWorker
     * @return
     */
    @Log("新增/按钮")
    @PostMapping
    @RequiresPermissions("dcaBWorker:add")
    public void addDcaBWorker(@Valid DcaBWorker dcaBWorker) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            dcaBWorker.setCreateUserId(currentUser.getUserId());
            this.iDcaBWorkerService.createDcaBWorker(dcaBWorker);
        } catch (Exception e) {
            message = "新增/按钮失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改
     *
     * @param dcaBWorker
     * @return
     */
    @Log("修改")
    @PutMapping
    @RequiresPermissions("dcaBWorker:update")
    public void updateDcaBWorker(@Valid DcaBWorker dcaBWorker) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            dcaBWorker.setModifyUserId(currentUser.getUserId());
            this.iDcaBWorkerService.updateDcaBWorker(dcaBWorker);
        } catch (Exception e) {
            message = "修改失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @Log("删除")
    @DeleteMapping("/{ids}")
    public void deleteDcaBWorkers(@NotBlank(message = "{required}") @PathVariable String ids,int state) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            for (String id : arr_ids
            ) {
                DcaBWorker dcaBWorker = new DcaBWorker();
                dcaBWorker.setId(id);
                dcaBWorker.setState(state);
                if(state==1){
                    dcaBWorker.setConfirmDate(new Date());
                }
                this.iDcaBWorkerService.updateDcaBWorker(dcaBWorker);
            }
        } catch (Exception e) {
            message = "退回失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel")
    @RequiresPermissions("dcaBWorker:export")
    public void export(QueryRequest request, DcaBWorker dcaBWorker, HttpServletResponse response) throws FebsException {
        try {
            List<DcaBWorker> dcaBWorkers = this.iDcaBWorkerService.findDcaBWorkers(request, dcaBWorker).getRecords();
            ExcelKit.$Export(DcaBWorker.class, response).downXlsx(dcaBWorkers, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @GetMapping("/{id}")
    public DcaBWorker detail(@NotBlank(message = "{required}") @PathVariable String id) {
        DcaBWorker dcaBWorker = this.iDcaBWorkerService.getById(id);
        return dcaBWorker;
    }
}