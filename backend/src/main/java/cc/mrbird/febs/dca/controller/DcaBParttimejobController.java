package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.service.IDcaBParttimejobService;
import cc.mrbird.febs.dca.entity.DcaBParttimejob;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import cn.hutool.Hutool;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.druid.support.json.JSONUtils;
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
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author viki
 * @since 2020-08-04
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBParttimejob")

public class DcaBParttimejobController extends BaseController{

private String message;
@Autowired
public IDcaBParttimejobService iDcaBParttimejobService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBParttimejob 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBParttimejob:view")
public Map<String, Object> List(QueryRequest request, DcaBParttimejob dcaBParttimejob){
    User currentUser= FebsUtil.getCurrentUser();
    dcaBParttimejob.setUserAccount(currentUser.getUsername());
    dcaBParttimejob.setIsDeletemark(1);
    request.setPageSize(100);
    request.setSortField("state");
    request.setSortOrder("descend");
        return getDataTable(this.iDcaBParttimejobService.findDcaBParttimejobs(request, dcaBParttimejob));
        }
    @GetMapping("audit")
    public Map<String, Object> List2(QueryRequest request, DcaBParttimejob dcaBParttimejob){
        User currentUser= FebsUtil.getCurrentUser();
        dcaBParttimejob.setIsDeletemark(1);
        request.setSortField("state");
        request.setSortOrder("descend");
        return getDataTable(this.iDcaBParttimejobService.findDcaBParttimejobs(request, dcaBParttimejob));
    }

/**
 * 添加
 * @param  dcaBParttimejob
 * @return
 */
@Log("新增/按钮")
    @PostMapping
    @RequiresPermissions("dcaBParttimejob:add")
    public void addDcaBParttimejob(@Valid DcaBParttimejob dcaBParttimejob)throws FebsException{
        try{
            User currentUser= FebsUtil.getCurrentUser();
            dcaBParttimejob.setCreateUserId(currentUser.getUserId());
            dcaBParttimejob.setUserAccount(currentUser.getUsername());
            this.iDcaBParttimejobService.createDcaBParttimejob(dcaBParttimejob);
        }catch(Exception e){
            message="新增/按钮失败" ;
            log.error(message,e);
            throw new FebsException(message);
        }
    }
    @Log("审核/按钮")
    @PostMapping("updateNew")
    public void updateNewDcaBParttimejob(@Valid String jsonStr ,int state )throws FebsException{
        try{
            User currentUser= FebsUtil.getCurrentUser();
           DcaBParttimejob dcaBParttimejob= JSON.parseObject(jsonStr, new TypeReference<DcaBParttimejob>() {
            });
            dcaBParttimejob.setState(state);
            dcaBParttimejob.setAuditMan(currentUser.getUsername());
            dcaBParttimejob.setAuditManName(currentUser.getRealname());
            dcaBParttimejob.setAuditDate(DateUtil.date());
            this.iDcaBParttimejobService.updateDcaBParttimejob(dcaBParttimejob);

        }catch(Exception e){
            message="新增/按钮失败" ;
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    @Log("新增/按钮")
    @PostMapping("addNew")
    public void addNewDcaBParttimejob(@Valid String jsonStr ,int state )throws FebsException{
        try{
            User currentUser= FebsUtil.getCurrentUser();
            List<DcaBParttimejob> list= JSON.parseObject(jsonStr, new TypeReference<List<DcaBParttimejob>>() {
            });
            int countid=0;
            /**
             * 先删除数据，然后再添加
             */
            // List<DcaBParttimejob> listAdd=list.stream().filter(p->!p.getState().equals(3)).collect(Collectors.toList());
            this.iDcaBParttimejobService.deleteByuseraccount(currentUser.getUsername());
            for (DcaBParttimejob dcaBParttimejob:list
            ) {
                if(dcaBParttimejob.getState()!=null&&dcaBParttimejob.getState().equals(3)) {
                    dcaBParttimejob.setState(3);
                }
                else{
                    dcaBParttimejob.setState(state);
                }
                dcaBParttimejob.setCreateUserId(currentUser.getUserId());
                dcaBParttimejob.setUserAccount(currentUser.getUsername());
                dcaBParttimejob.setUserAccountName(currentUser.getRealname());
                this.iDcaBParttimejobService.createDcaBParttimejob(dcaBParttimejob);
            }
        }catch(Exception e){
            message="新增/按钮失败" ;
            log.error(message,e);
            throw new FebsException(message);
        }
    }
/**
 * 修改
 * @param dcaBParttimejob
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBParttimejob:update")
public void updateDcaBParttimejob(@Valid DcaBParttimejob dcaBParttimejob)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBParttimejob.setModifyUserId(currentUser.getUserId());
        this.iDcaBParttimejobService.updateDcaBParttimejob(dcaBParttimejob);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBParttimejob:delete")
public void deleteDcaBParttimejobs(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBParttimejobService.deleteDcaBParttimejobs(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBParttimejob:export")
public void export(QueryRequest request, DcaBParttimejob dcaBParttimejob, HttpServletResponse response) throws FebsException {
        try {
        List<DcaBParttimejob> dcaBParttimejobs = this.iDcaBParttimejobService.findDcaBParttimejobs(request, dcaBParttimejob).getRecords();
        ExcelKit.$Export(DcaBParttimejob.class, response).downXlsx(dcaBParttimejobs, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBParttimejob detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBParttimejob dcaBParttimejob=this.iDcaBParttimejobService.getById(id);
        return dcaBParttimejob;
        }
        }