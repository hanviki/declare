package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.service.IDcaDAuditinfoService;
import cc.mrbird.febs.dca.entity.DcaDAuditinfo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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

/**
 *
 * @author viki
 * @since 2020-10-23
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaDAuditinfo")

public class DcaDAuditinfoController extends BaseController{

private String message;
@Autowired
public IDcaDAuditinfoService iDcaDAuditinfoService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaDAuditinfo 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaDAuditinfo:view")
public Map<String, Object> List(QueryRequest request, DcaDAuditinfo dcaDAuditinfo){
        return getDataTable(this.iDcaDAuditinfoService.findDcaDAuditinfos(request, dcaDAuditinfo));
        }
    @GetMapping("tree")
    public Map<String, Object> treeList() {
        return this.iDcaDAuditinfoService.findDepts();
    }

    @GetMapping("treeByUserId")
    public Map<String, Object> treeList2() {
        User currentUser= FebsUtil.getCurrentUser();
        return this.iDcaDAuditinfoService.findDeptsByUserId(currentUser.getUserId());
    }
/**
 * 添加
 * @param  dcaDAuditinfo
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaDAuditinfo(@Valid DcaDAuditinfo dcaDAuditinfo)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaDAuditinfo.setCreateUserId(currentUser.getUserId());
        this.iDcaDAuditinfoService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaDAuditinfoService.createDcaDAuditinfo(dcaDAuditinfo);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaDAuditinfo
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaDAuditinfo:update")
public void updateDcaDAuditinfo(@Valid DcaDAuditinfo dcaDAuditinfo)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaDAuditinfo.setModifyUserId(currentUser.getUserId());
        this.iDcaDAuditinfoService.updateDcaDAuditinfo(dcaDAuditinfo);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaDAuditinfo:delete")
public void deleteDcaDAuditinfos(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaDAuditinfoService.deleteDcaDAuditinfos(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaDAuditinfo:export")
public void export(QueryRequest request, DcaDAuditinfo dcaDAuditinfo,HttpServletResponse response)throws FebsException{
        try{
        List<DcaDAuditinfo> dcaDAuditinfos=this.iDcaDAuditinfoService.findDcaDAuditinfos(request, dcaDAuditinfo).getRecords();
        ExcelKit.$Export(DcaDAuditinfo.class,response).downXlsx(dcaDAuditinfos,false);
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaDAuditinfo detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaDAuditinfo dcaDAuditinfo=this.iDcaDAuditinfoService.getById(id);
        return dcaDAuditinfo;
        }

    @GetMapping("userAudit")
    public List<DcaDAuditinfo> ListByUseraccount(){
        User currentUser=FebsUtil.getCurrentUser();
        LambdaQueryWrapper<DcaDAuditinfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.apply("dca_d_auditinfo.id in (SELECT audit_id  from dca_user_audit where userId ="+currentUser.getUserId()+")");//
        List<DcaDAuditinfo> list = this.iDcaDAuditinfoService.list(queryWrapper);
        return list;
    }
        }