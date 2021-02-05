package cc.mrbird.febs.check.controller;

import cc.mrbird.febs.check.entity.CheckBAuditresult;
import cc.mrbird.febs.check.entity.TotalResultNum;
import cc.mrbird.febs.check.service.ICheckBAuditresultService;
import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.check.service.ICheckDReportService;
import cc.mrbird.febs.check.entity.CheckDReport;

import cc.mrbird.febs.common.utils.ExportExcelUtils;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import cn.hutool.core.util.NumberUtil;
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
import java.util.stream.Collectors;

/**
 *
 * @author viki
 * @since 2021-01-27
 */
@Slf4j
@Validated
@RestController
@RequestMapping("checkDReport")

public class CheckDReportController extends BaseController{

private String message;
@Autowired
public ICheckDReportService iCheckDReportService;
    @Autowired
    private ICheckBAuditresultService iCheckBAuditresultService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/dca/CheckDReport/CheckDReport','dca/CheckDReport/CheckDReport','checkDReport:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'新增','checkDReport:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'编辑','checkDReport:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'删除','checkDReport:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param checkDReport 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, CheckDReport checkDReport){
        return getDataTable(this.iCheckDReportService.findCheckDReports(request, checkDReport));
        }
    @GetMapping("getPersonInfo")
    public List<CheckDReport> List2(String userAccount,String dcaYear){
        List<CheckDReport> checkDReportList=this.iCheckDReportService.getAll(userAccount,dcaYear);

        CheckBAuditresult checkBAuditresult = new CheckBAuditresult();
        checkBAuditresult.setUserAccount(userAccount);
        checkBAuditresult.setDcaYear(dcaYear);
        List<TotalResultNum> checkBAuditresultTotalList = this.iCheckBAuditresultService.findTotalResult(checkBAuditresult);
        List<TotalResultNum> str_checkBAuditresultTotalList = this.iCheckBAuditresultService.findStrResult(checkBAuditresult);
        if (checkDReportList.size() > 0) {
            for (CheckDReport checkDReport : checkDReportList
            ) {
                List<TotalResultNum> cl=checkBAuditresultTotalList.stream().filter(p->checkDReport.getTitles().contains(p.getAuditTitletype())).collect(Collectors.toList());
               if(cl.size()>0) {
                   double f = cl.stream().mapToDouble(p -> p.getAuditResult()).sum();
                   checkDReport.setTitleResult(NumberUtil.roundStr(f, 2));
               }
               else {
                   List<TotalResultNum> sl=checkBAuditresultTotalList.stream().filter(p->checkDReport.getTitles().contains(p.getAuditTitletype())).collect(Collectors.toList());
                  if(sl.size()>0) {
                      checkDReport.setTitleResult(sl.get(0).getStrAuditResult());
                  }
               }
            }
        }
        return  checkDReportList;
    }
/**
 * 添加
 * @param  checkDReport
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("checkDReport:add")
public void addCheckDReport(@Valid CheckDReport checkDReport)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        checkDReport.setCreateUserId(currentUser.getUserId());
        this.iCheckDReportService.createCheckDReport(checkDReport);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param checkDReport
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("checkDReport:update")
public void updateCheckDReport(@Valid CheckDReport checkDReport)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      checkDReport.setModifyUserId(currentUser.getUserId());
        this.iCheckDReportService.updateCheckDReport(checkDReport);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("checkDReport:delete")
public void deleteCheckDReports(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iCheckDReportService.deleteCheckDReports(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
    @PostMapping("excel")
    public void export(QueryRequest request, String userAccount,String dcaYear,String userAccountName,String zyjsgw,String ks,String dataJson,HttpServletResponse response)throws FebsException{
        try{
            request.setPageNum(1);
            request.setPageSize(10000);
            User currentUser = FebsUtil.getCurrentUser();


            List<CheckDReport> checkDReportList=this.iCheckDReportService.getAll(userAccount,dcaYear);

            CheckBAuditresult checkBAuditresult = new CheckBAuditresult();
            checkBAuditresult.setUserAccount(userAccount);
            checkBAuditresult.setDcaYear(dcaYear);
            List<TotalResultNum> checkBAuditresultTotalList = this.iCheckBAuditresultService.findTotalResult(checkBAuditresult);
            List<TotalResultNum> str_checkBAuditresultTotalList = this.iCheckBAuditresultService.findStrResult(checkBAuditresult);
            if (checkDReportList.size() > 0) {
                for (CheckDReport checkDReport : checkDReportList
                ) {
                    List<TotalResultNum> cl=checkBAuditresultTotalList.stream().filter(p->checkDReport.getTitles().contains(p.getAuditTitletype())).collect(Collectors.toList());
                    if(cl.size()>0) {
                        double f = cl.stream().mapToDouble(p -> p.getAuditResult()).sum();
                        checkDReport.setTitleResult(NumberUtil.roundStr(f, 2));
                    }
                    else {
                        List<TotalResultNum> sl=checkBAuditresultTotalList.stream().filter(p->checkDReport.getTitles().contains(p.getAuditTitletype())).collect(Collectors.toList());
                        if(sl.size()>0) {
                            checkDReport.setTitleResult(sl.get(0).getStrAuditResult());
                        }
                    }
                }
            }
            //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
            ExportExcelUtils.exportCustomExcel_person(response, checkDReportList,dataJson,"",dcaYear,userAccountName,zyjsgw,ks);
        }catch(Exception e){
            message="导出Excel失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

@GetMapping("/{id}")
public CheckDReport detail(@NotBlank(message = "{required}") @PathVariable String id) {
    CheckDReport checkDReport=this.iCheckDReportService.getById(id);
        return checkDReport;
        }
        }