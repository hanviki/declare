package cc.mrbird.febs.scm.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.scm.service.IViewSupplyplanService;
import cc.mrbird.febs.scm.entity.ViewSupplyplan;

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
import java.util.*;

/**
 *
 * @author viki
 * @since 2019-12-05
 */
@Slf4j
@Validated
@RestController
@RequestMapping("viewSupplyplan")

public class ViewSupplyplanController extends BaseController{

private String message;
@Autowired
public IViewSupplyplanService iViewSupplyplanService;


/**
 * 分页查询数据
 *
 * @param bootStrapTable  分页信息
 * @param viewSupplyplan 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("viewSupplyplan:view")
public Map<String, Object> List(QueryRequest request, ViewSupplyplan viewSupplyplan){
        return getDataTable(this.iViewSupplyplanService.findVPurcharseorder(request, viewSupplyplan));
        }
    @GetMapping("matnr")
    @RequiresPermissions("viewSupplyplan:matnr")
    public Map<String, Object> ListMatnr(QueryRequest request, ViewSupplyplan viewSupplyplan){
        viewSupplyplan.setBedat(new Date());
        Calendar c = Calendar.getInstance();
        //过去一个月
        c.setTime(new Date());
        c.add(Calendar.MONTH, - 6);
        Date preMonth = c.getTime();
        viewSupplyplan.setEindt(preMonth);
    viewSupplyplan.setStatus(1);
        return getDataTable(this.iViewSupplyplanService.findMatnrValid(request, viewSupplyplan));
    }

    @GetMapping("sendOrder")
    public Map<String, Object> ListOrder(QueryRequest request, ViewSupplyplan scmBSupplyplan) {
        scmBSupplyplan.setIsDeletemark(1);
        User currentUser = FebsUtil.getCurrentUser();
        scmBSupplyplan.setGysaccount(currentUser.getUsername());

        return getDataTable(this.iViewSupplyplanService.findViewSupplyplans2(request, scmBSupplyplan));
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
@RequiresPermissions("viewSupplyplan:add")
public void addViewSupplyplan(@Valid ViewSupplyplan viewSupplyplan)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        viewSupplyplan.setCreateUserId(currentUser.getUserId());
        this.iViewSupplyplanService.createViewSupplyplan(viewSupplyplan);
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
@RequiresPermissions("viewSupplyplan:update")
public void updateViewSupplyplan(@Valid ViewSupplyplan viewSupplyplan)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      viewSupplyplan.setModifyUserId(currentUser.getUserId());
        this.iViewSupplyplanService.updateViewSupplyplan(viewSupplyplan);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("viewSupplyplan:delete")
public void deleteViewSupplyplans(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iViewSupplyplanService.deleteViewSupplyplans(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("viewSupplyplan:export")
public void export(QueryRequest request, ViewSupplyplan viewSupplyplan, HttpServletResponse response) throws FebsException {
        try {
        List<ViewSupplyplan> viewSupplyplans = this.iViewSupplyplanService.findViewSupplyplans(request, viewSupplyplan).getRecords();
        ExcelKit.$Export(ViewSupplyplan.class, response).downXlsx(viewSupplyplans, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public ViewSupplyplan detail(@NotBlank(message = "{required}") @PathVariable String id) {
    ViewSupplyplan viewSupplyplan=this.iViewSupplyplanService.getById(id);
        return viewSupplyplan;
        }

    @GetMapping("/forPadScan/{id}")
    public ViewSupplyplan forpda(@NotBlank(message = "{required}") @PathVariable String id) {
        List<ViewSupplyplan> ListviewSupplyplan=this.iViewSupplyplanService.getViewSupplyPlanByPdaId(id);
        ViewSupplyplan viewSupplyplan =new ViewSupplyplan();
        if(ListviewSupplyplan.size()>0){
            viewSupplyplan=ListviewSupplyplan.get(0);
        }
        return viewSupplyplan;
    }
    @GetMapping("doneStatus/{statusType}")
    public Map<String, Object> List(QueryRequest request,@PathVariable String statusType,ViewSupplyplan viewSupplyplan){
        User currentUser= FebsUtil.getCurrentUser();
        viewSupplyplan.setEbelp(currentUser.getUserId().toString());

        return getDataTable(this.iViewSupplyplanService.findDoneViewSupplyplans(request, viewSupplyplan,statusType));
    }
        }