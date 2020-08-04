package cc.mrbird.febs.scm.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.FebsResponse;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.scm.service.IScmDVendorDService;
import cc.mrbird.febs.scm.entity.ScmDVendorD;

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
 * @since 2019-11-14
 */
@Slf4j
@Validated
@RestController
@RequestMapping("scmDVendorD")

public class ScmDVendorDController extends BaseController{

    private String message;
    @Autowired
    public IScmDVendorDService iScmDVendorDService;


    /**
     * 分页查询数据
     *
     * @param bootStrapTable  分页信息
     * @param scmDVendorD 查询条件
     * @return
     */
    @GetMapping
    @RequiresPermissions("scmDVendorD:view")
    public Map<String, Object> List(QueryRequest request, ScmDVendorD scmDVendorD){
        return getDataTable(this.iScmDVendorDService.findScmDVendorDs(request, scmDVendorD));
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
    @RequiresPermissions("scmDVendorD:add")
    public void addScmDVendorD(@Valid ScmDVendorD scmDVendorD)throws FebsException{
        try{
            User currentUser= FebsUtil.getCurrentUser();
            scmDVendorD.setCreateUserId(currentUser.getUserId());
            this.iScmDVendorDService.createScmDVendorD(scmDVendorD);
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
    @RequiresPermissions("scmDVendorD:update")
    public void updateScmDVendorD(@Valid ScmDVendorD scmDVendorD)throws FebsException{
        try{
            User currentUser= FebsUtil.getCurrentUser();
            scmDVendorD.setModifyUserId(currentUser.getUserId());
            this.iScmDVendorDService.updateScmDVendorD(scmDVendorD);
        }catch(Exception e){
            message="修改成功" ;
            log.error(message,e);
            throw new FebsException(message);
        }
    }


    @Log("删除")
    @DeleteMapping("/{ids}")
    @RequiresPermissions("scmDVendorD:delete")
    public void deleteScmDVendorDs(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
            String[]arr_ids=ids.split(StringPool.COMMA);
            this.iScmDVendorDService.deleteScmDVendorDs(arr_ids);
        }catch(Exception e){
            message="删除成功" ;
            log.error(message,e);
            throw new FebsException(message);
        }
    }
    @PostMapping("excel")
    @RequiresPermissions("scmDVendorD:export")
    public void export(QueryRequest request, ScmDVendorD scmDVendorD, HttpServletResponse response) throws FebsException {
        try {
            List<ScmDVendorD> scmDVendorDs = this.iScmDVendorDService.findScmDVendorDs(request, scmDVendorD).getRecords();
            ExcelKit.$Export(ScmDVendorD.class, response).downXlsx(scmDVendorDs, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @GetMapping("/{id}")
    public ScmDVendorD detail(@NotBlank(message = "{required}") @PathVariable String id) {
        ScmDVendorD scmDVendorD=this.iScmDVendorDService.getById(id);
        return scmDVendorD;
    }
    @GetMapping("/attach/{id}")
    public FebsResponse GetAttach(@NotBlank(message = "{required}") @PathVariable String id) {
        List<ScmDVendorD> scmDVendorDs=this.iScmDVendorDService.findScmDVendorDByBaseId(id);
        Collections.sort(scmDVendorDs,Comparator.comparing(ScmDVendorD::getFileIndex));
        return new FebsResponse().data(scmDVendorDs);
    }

}
