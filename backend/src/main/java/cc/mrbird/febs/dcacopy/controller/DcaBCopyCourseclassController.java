package cc.mrbird.febs.dcacopy.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dcacopy.service.IDcaBCopyCourseclassService;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyCourseclass;

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
 * @since 2020-11-26
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBCopyCourseclass")

public class DcaBCopyCourseclassController extends BaseController{

private String message;
@Autowired
public IDcaBCopyCourseclassService iDcaBCopyCourseclassService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'精品课程','/dca/DcaBCopyCourseclass/DcaBCopyCourseclass','dca/DcaBCopyCourseclass/DcaBCopyCourseclass','dcaBCopyCourseclass:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'精品课程新增','dcaBCopyCourseclass:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'精品课程编辑','dcaBCopyCourseclass:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'精品课程删除','dcaBCopyCourseclass:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBCopyCourseclass 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBCopyCourseclass:view")
public Map<String, Object> List(QueryRequest request, DcaBCopyCourseclass dcaBCopyCourseclass){
        return getDataTable(this.iDcaBCopyCourseclassService.findDcaBCopyCourseclasss(request, dcaBCopyCourseclass));
        }

/**
 * 添加
 * @param  dcaBCopyCourseclass
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaBCopyCourseclass:add")
public void addDcaBCopyCourseclass(@Valid DcaBCopyCourseclass dcaBCopyCourseclass)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaBCopyCourseclass.setCreateUserId(currentUser.getUserId());
        this.iDcaBCopyCourseclassService.createDcaBCopyCourseclass(dcaBCopyCourseclass);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBCopyCourseclass
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBCopyCourseclass:update")
public void updateDcaBCopyCourseclass(@Valid DcaBCopyCourseclass dcaBCopyCourseclass)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBCopyCourseclass.setModifyUserId(currentUser.getUserId());
        this.iDcaBCopyCourseclassService.updateDcaBCopyCourseclass(dcaBCopyCourseclass);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBCopyCourseclass:delete")
public void deleteDcaBCopyCourseclasss(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBCopyCourseclassService.deleteDcaBCopyCourseclasss(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBCopyCourseclass:export")
public void export(QueryRequest request, DcaBCopyCourseclass dcaBCopyCourseclass, HttpServletResponse response) throws FebsException {
        try {
        List<DcaBCopyCourseclass> dcaBCopyCourseclasss = this.iDcaBCopyCourseclassService.findDcaBCopyCourseclasss(request, dcaBCopyCourseclass).getRecords();
        ExcelKit.$Export(DcaBCopyCourseclass.class, response).downXlsx(dcaBCopyCourseclasss, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBCopyCourseclass detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBCopyCourseclass dcaBCopyCourseclass=this.iDcaBCopyCourseclassService.getById(id);
        return dcaBCopyCourseclass;
        }
        }