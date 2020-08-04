package cc.mrbird.febs.scm.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.FebsResponse;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.scm.entity.ComFile;
import cc.mrbird.febs.scm.service.IComFileService;
import cc.mrbird.febs.scm.service.IScmDVendoruserService;
import cc.mrbird.febs.scm.entity.ScmDVendoruser;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
 * @since 2020-06-03
 */
@Slf4j
@Validated
@RestController
@RequestMapping("scmDVendoruser")

public class ScmDVendoruserController extends BaseController{

private String message;
@Autowired
    public IScmDVendoruserService iScmDVendoruserService;
    @Autowired
    public IComFileService iComFileService;


/**
 * 分页查询数据
 *
 * @param bootStrapTable  分页信息
 * @param scmDVendoruser 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("scmDVendoruser:view")
public Map<String, Object> List(QueryRequest request, ScmDVendoruser scmDVendoruser){
        return getDataTable(this.iScmDVendoruserService.findScmDVendorusers(request, scmDVendoruser));
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
@RequiresPermissions("scmDVendoruser:add")
public void addScmDVendoruser(@Valid ScmDVendoruser scmDVendoruser)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        scmDVendoruser.setCreateUserId(currentUser.getUserId());
        this.iScmDVendoruserService.createScmDVendoruser(scmDVendoruser);
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
@RequiresPermissions("scmDVendoruser:update")
public void updateScmDVendoruser(@Valid ScmDVendoruser scmDVendoruser)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      scmDVendoruser.setModifyUserId(currentUser.getUserId());
        this.iScmDVendoruserService.updateScmDVendoruser(scmDVendoruser);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("scmDVendoruser:delete")
public void deleteScmDVendorusers(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iScmDVendoruserService.deleteScmDVendorusers(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("scmDVendoruser:export")
public void export(QueryRequest request, ScmDVendoruser scmDVendoruser, HttpServletResponse response) throws FebsException {
        try {
        List<ScmDVendoruser> scmDVendorusers = this.iScmDVendoruserService.findScmDVendorusers(request, scmDVendoruser).getRecords();
        ExcelKit.$Export(ScmDVendoruser.class, response).downXlsx(scmDVendorusers, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public ScmDVendoruser detail(@NotBlank(message = "{required}") @PathVariable String id) {
    ScmDVendoruser scmDVendoruser=this.iScmDVendoruserService.getById(id);
        return scmDVendoruser;
        }

    public class CustomerUser {
        public ScmDVendoruser scmDVendoruser;
        public ComFile fileAgent;
        public ComFile filehead;
        public ComFile fileback;
        public ComFile filefront;
    }
    @GetMapping("user/{id}")
    public FebsResponse user(@PathVariable String id) {
        CustomerUser user =new CustomerUser();
        if(StringUtils.isNotBlank(id)){
            User currentUser= FebsUtil.getCurrentUser();
            id=currentUser.getUsername();
        }
        ScmDVendoruser scmDVendoruser=this.iScmDVendoruserService.getUserInfo(id);
        user.scmDVendoruser=scmDVendoruser;
        if(StringUtils.isNotBlank(scmDVendoruser.getAgentImage())) {
            ComFile fileAgent = this.iComFileService.getById(scmDVendoruser.getAgentImage());
            user.fileAgent=fileAgent;
        }
        if(StringUtils.isNotBlank(scmDVendoruser.getHeadImage())) {
            ComFile filehead = this.iComFileService.getById(scmDVendoruser.getHeadImage());
            user.filehead=filehead;
        }
        if(StringUtils.isNotBlank(scmDVendoruser.getIdcardBack())) {
            ComFile fileback = this.iComFileService.getById(scmDVendoruser.getIdcardBack());
            user.fileback=fileback;
        }
        if(StringUtils.isNotBlank(scmDVendoruser.getIdcardFront())) {
            ComFile filefront = this.iComFileService.getById(scmDVendoruser.getIdcardFront());
            user.filefront=filefront;
        }
       return  new FebsResponse().data(user);
    }
    @GetMapping("user2/{id}") //供应商注册是编辑
    public FebsResponse user2(@PathVariable String id) {
        CustomerUser user =new CustomerUser();
        LambdaQueryWrapper<ScmDVendoruser> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(ScmDVendoruser::getBaseId,id);
        ScmDVendoruser scmDVendoruser=this.iScmDVendoruserService.getOne(queryWrapper);
        user.scmDVendoruser=scmDVendoruser;
        if(StringUtils.isNotBlank(scmDVendoruser.getAgentImage())) {
            ComFile fileAgent = this.iComFileService.getById(scmDVendoruser.getAgentImage());
            user.fileAgent=fileAgent;
        }
        if(StringUtils.isNotBlank(scmDVendoruser.getHeadImage())) {
            ComFile filehead = this.iComFileService.getById(scmDVendoruser.getHeadImage());
            user.filehead=filehead;
        }
        if(StringUtils.isNotBlank(scmDVendoruser.getIdcardBack())) {
            ComFile fileback = this.iComFileService.getById(scmDVendoruser.getIdcardBack());
            user.fileback=fileback;
        }
        if(StringUtils.isNotBlank(scmDVendoruser.getIdcardFront())) {
            ComFile filefront = this.iComFileService.getById(scmDVendoruser.getIdcardFront());
            user.filefront=filefront;
        }
        return  new FebsResponse().data(user);
    }
        }