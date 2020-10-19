package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.service.IDcaBTitleapplyDService;
import cc.mrbird.febs.dca.entity.DcaBTitleapplyD;

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
 * @since 2020-09-22
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBTitleapplyD")

public class DcaBTitleapplyDController extends BaseController{

private String message;
@Autowired
public IDcaBTitleapplyDService iDcaBTitleapplyDService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBTitleapplyD 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBTitleapplyD:view")
public Map<String, Object> List(QueryRequest request, DcaBTitleapplyD dcaBTitleapplyD){
        return getDataTable(this.iDcaBTitleapplyDService.findDcaBTitleapplyDs(request, dcaBTitleapplyD));
        }

/**
 * 添加
 * @param  dcaBTitleapplyD
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaBTitleapplyD:add")
public void addDcaBTitleapplyD(@Valid DcaBTitleapplyD dcaBTitleapplyD)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaBTitleapplyD.setCreateUserId(currentUser.getUserId());
        this.iDcaBTitleapplyDService.createDcaBTitleapplyD(dcaBTitleapplyD);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBTitleapplyD
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBTitleapplyD:update")
public void updateDcaBTitleapplyD(@Valid DcaBTitleapplyD dcaBTitleapplyD)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBTitleapplyD.setModifyUserId(currentUser.getUserId());
        this.iDcaBTitleapplyDService.updateDcaBTitleapplyD(dcaBTitleapplyD);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBTitleapplyD:delete")
public void deleteDcaBTitleapplyDs(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBTitleapplyDService.deleteDcaBTitleapplyDs(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBTitleapplyD:export")
public void export(QueryRequest request, DcaBTitleapplyD dcaBTitleapplyD, HttpServletResponse response) throws FebsException {
        try {
        List<DcaBTitleapplyD> dcaBTitleapplyDs = this.iDcaBTitleapplyDService.findDcaBTitleapplyDs(request, dcaBTitleapplyD).getRecords();
        ExcelKit.$Export(DcaBTitleapplyD.class, response).downXlsx(dcaBTitleapplyDs, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBTitleapplyD detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBTitleapplyD dcaBTitleapplyD=this.iDcaBTitleapplyDService.getById(id);
        return dcaBTitleapplyD;
        }
        }