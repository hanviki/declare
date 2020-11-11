package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.service.IDcaUserXlService;
import cc.mrbird.febs.dca.entity.DcaUserXl;

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
import java.util.stream.Collectors;

/**
 *
 * @author viki
 * @since 2020-10-29
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaUserXl")

public class DcaUserXlController extends BaseController{

private String message;
@Autowired
public IDcaUserXlService iDcaUserXlService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaUserXl 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaUserXl:view")
public Map<String, Object> List(QueryRequest request, DcaUserXl dcaUserXl){
        return getDataTable(this.iDcaUserXlService.findDcaUserXls(request, dcaUserXl));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaUserXl dcaUserXl){
        User currentUser= FebsUtil.getCurrentUser();

        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaUserXlService.findDcaUserXls(request, dcaUserXl));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaUserXl dcaUserXl){
        User currentUser= FebsUtil.getCurrentUser();

        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaUserXlService.findDcaUserXls(request, dcaUserXl));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaUserXlCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaUserXl> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaUserXl>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaUserXlService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaUserXlService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaUserXl dcaUserXl:list
        ){
        this.iDcaUserXlService.createDcaUserXl(dcaUserXl);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaUserXl(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaUserXl dcaUserXl= JSON.parseObject(jsonStr, new TypeReference<DcaUserXl>() {
        });

        this.iDcaUserXlService.updateDcaUserXl(dcaUserXl);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaUserXl
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaUserXl(@Valid DcaUserXl dcaUserXl)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();

        this.iDcaUserXlService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaUserXlService.createDcaUserXl(dcaUserXl);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaUserXl
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaUserXl:update")
public void updateDcaUserXl(@Valid DcaUserXl dcaUserXl)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();

        this.iDcaUserXlService.updateDcaUserXl(dcaUserXl);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaUserXl:delete")
public void deleteDcaUserXls(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaUserXlService.deleteDcaUserXls(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaUserXl:export")
public void export(QueryRequest request, DcaUserXl dcaUserXl,HttpServletResponse response)throws FebsException{
        try{
        List<DcaUserXl> dcaUserXls=this.iDcaUserXlService.findDcaUserXls(request, dcaUserXl).getRecords();
        ExcelKit.$Export(DcaUserXl.class,response).downXlsx(dcaUserXls,false);
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaUserXl detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaUserXl dcaUserXl=this.iDcaUserXlService.getById(id);
        return dcaUserXl;
        }
    @GetMapping("mudules/{userId}")
    public List<String> getRoleMenus(@NotBlank(message = "{required}") @PathVariable String userId) {
        List<DcaUserXl> list = this.iDcaUserXlService.getMudulesByUserId(Integer.parseInt(userId));
        return list.stream().map(area -> String.valueOf(area.getXlId())).collect(Collectors.toList());
    }
        }