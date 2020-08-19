package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.service.IDcaBApplyjobService;
import cc.mrbird.febs.dca.entity.DcaBApplyjob;

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
 *
 * @author viki
 * @since 2020-08-04
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBApplyjob")

public class DcaBApplyjobController extends BaseController{

private String message;
@Autowired
public IDcaBApplyjobService iDcaBApplyjobService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBApplyjob 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBApplyjob:view")
public Map<String, Object> List(QueryRequest request, DcaBApplyjob dcaBApplyjob){
        return getDataTable(this.iDcaBApplyjobService.findDcaBApplyjobs(request, dcaBApplyjob));
        }
    @GetMapping("custom")
    public Map<String, Object> ListCustom(QueryRequest request, DcaBApplyjob dcaBApplyjob){
        User currentUser= FebsUtil.getCurrentUser();
        dcaBApplyjob.setUserAccount(currentUser.getUsername());
        dcaBApplyjob.setIsDeletemark(1);
        return getDataTable(this.iDcaBApplyjobService.findDcaBApplyjobs(request, dcaBApplyjob));
    }

/**
 * 添加
 * @param  dcaBApplyjob
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBApplyjob(@Valid DcaBApplyjob dcaBApplyjob)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaBApplyjob.setCreateUserId(currentUser.getUserId());
        dcaBApplyjob.setUserAccount(currentUser.getUsername());
        this.iDcaBApplyjobService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBApplyjobService.createDcaBApplyjob(dcaBApplyjob);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBApplyjob
 * @return
 */
@Log("修改")
@PutMapping
public void updateDcaBApplyjob(@Valid DcaBApplyjob dcaBApplyjob)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
            dcaBApplyjob.setUserAccount(currentUser.getUsername());
      dcaBApplyjob.setModifyUserId(currentUser.getUserId());
        this.iDcaBApplyjobService.updateDcaBApplyjob(dcaBApplyjob);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBApplyjob:delete")
public void deleteDcaBApplyjobs(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBApplyjobService.deleteDcaBApplyjobs(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBApplyjob:export")
public void export(QueryRequest request, DcaBApplyjob dcaBApplyjob, HttpServletResponse response) throws FebsException {
        try {
        List<DcaBApplyjob> dcaBApplyjobs = this.iDcaBApplyjobService.findDcaBApplyjobs(request, dcaBApplyjob).getRecords();
        ExcelKit.$Export(DcaBApplyjob.class, response).downXlsx(dcaBApplyjobs, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBApplyjob detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBApplyjob dcaBApplyjob=this.iDcaBApplyjobService.getById(id);
        return dcaBApplyjob;
        }
        }