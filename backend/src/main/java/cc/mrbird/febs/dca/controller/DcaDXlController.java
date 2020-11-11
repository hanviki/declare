package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.service.IDcaDXlService;
import cc.mrbird.febs.dca.entity.DcaDXl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
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
 * @since 2020-10-29
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaDXl")

public class DcaDXlController extends BaseController{

private String message;
@Autowired
public IDcaDXlService iDcaDXlService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaDXl 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaDXl:view")
public Map<String, Object> List(QueryRequest request, DcaDXl dcaDXl){
        return getDataTable(this.iDcaDXlService.findDcaDXls(request, dcaDXl));
        }

/**
 * 添加
 * @param  dcaDXl
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaDXl(@Valid DcaDXl dcaDXl)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaDXl.setCreateUserId(currentUser.getUserId());

        this.iDcaDXlService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaDXlService.createDcaDXl(dcaDXl);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaDXl
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaDXl:update")
public void updateDcaDXl(@Valid DcaDXl dcaDXl)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaDXl.setModifyUserId(currentUser.getUserId());
        this.iDcaDXlService.updateDcaDXl(dcaDXl);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaDXl:delete")
public void deleteDcaDXls(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaDXlService.deleteDcaDXls(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaDXl:export")
public void export(QueryRequest request, DcaDXl dcaDXl,HttpServletResponse response)throws FebsException{
        try{
        List<DcaDXl> dcaDXls=this.iDcaDXlService.findDcaDXls(request, dcaDXl).getRecords();
        ExcelKit.$Export(DcaDXl.class,response).downXlsx(dcaDXls,false);
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaDXl detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaDXl dcaDXl=this.iDcaDXlService.getById(id);
        return dcaDXl;
        }
    @GetMapping("tree")
    public Map<String, Object> treeList() {
        return this.iDcaDXlService.findDepts();
    }

    @GetMapping("treeByUserId")
    public Map<String, Object> treeList2() {
        User currentUser= FebsUtil.getCurrentUser();
        return this.iDcaDXlService.findDeptsByUserId(currentUser.getUserId());
    }

}