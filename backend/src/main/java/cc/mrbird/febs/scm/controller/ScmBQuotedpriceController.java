package cc.mrbird.febs.scm.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.FebsResponse;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.scm.entity.ComFile;
import cc.mrbird.febs.scm.entity.ScmBQuerypriceD;
import cc.mrbird.febs.scm.entity.ScmBQuotedpriceD;
import cc.mrbird.febs.scm.service.IComFileService;
import cc.mrbird.febs.scm.service.IScmBQuerypriceDService;
import cc.mrbird.febs.scm.service.IScmBQuotedpriceDService;
import cc.mrbird.febs.scm.service.IScmBQuotedpriceService;
import cc.mrbird.febs.scm.entity.ScmBQuotedprice;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
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
import java.util.UUID;

/**
 * @author viki
 * @since 2019-12-27
 */
@Slf4j
@Validated
@RestController
@RequestMapping("scmBQuotedprice")

public class ScmBQuotedpriceController extends BaseController {

    private String message;
    @Autowired
    public IScmBQuotedpriceService iScmBQuotedpriceService;
    @Autowired
    public IScmBQuotedpriceDService iScmBQuotedpriceDService;
    @Autowired
    public IComFileService iComFileService;

    @Autowired
    public IScmBQuerypriceDService iScmBQuerypriceDService;

    /**
     * 分页查询数据
     *
     * @param bootStrapTable  分页信息
     * @param scmBQuotedprice 查询条件
     * @return
     */
    @GetMapping
    @RequiresPermissions("scmBQuotedprice:view")
    public Map<String, Object> List(QueryRequest request, ScmBQuotedprice scmBQuotedprice) {
        return getDataTable(this.iScmBQuotedpriceService.findScmBQuotedprices(request, scmBQuotedprice));
    }

    /**
     * 跳转添加页面
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @Log("新增/按钮")
    @PostMapping
    @RequiresPermissions("scmBQuotedprice:add")
    public void addScmBQuotedprice(@Valid ScmBQuotedprice scmBQuotedprice) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            scmBQuotedprice.setCreateUserId(currentUser.getUserId());
            this.iScmBQuotedpriceService.createScmBQuotedprice(scmBQuotedprice);
        } catch (Exception e) {
            message = "新增/按钮失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("新增报价")
    @PostMapping("add")
    @RequiresPermissions("scmBQuotedprice:add")
    public void insetScmBQuoteprice(String jsonString, String baseid, int state) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            log.info("baseid:" + baseid);
            log.info(jsonString);
            List<ScmBQuotedprice> list = JSON.parseObject(jsonString, new TypeReference<List<ScmBQuotedprice>>() {
            });
            this.iScmBQuotedpriceService.deleteScmBQuotedprices(baseid);
            for (ScmBQuotedprice scmBQuotedprice : list
            ) {
                if (StringUtils.isNotBlank(scmBQuotedprice.getProductName())) {
                    scmBQuotedprice.setGysaccount(currentUser.getUsername());
                    scmBQuotedprice.setGysname(currentUser.getRealname());
                    scmBQuotedprice.setState(state);
                    Long baseidL = Long.parseLong(baseid);
                    scmBQuotedprice.setBaseId(baseidL);
                    this.iScmBQuotedpriceService.createScmBQuotedprice(scmBQuotedprice);

                    List<ScmBQuotedpriceD> listD = scmBQuotedprice.getHospital();
                    for (ScmBQuotedpriceD scmBQuotedpriceD : listD
                    ) {
                        if (StringUtils.isNotBlank(scmBQuotedpriceD.getHospitalName())) {
                            scmBQuotedpriceD.setBaseId(scmBQuotedprice.getId());
                            this.iScmBQuotedpriceDService.createScmBQuotedpriceD(scmBQuotedpriceD);
                        }
                    }
                    if(state==1)
                    {
                        this.iScmBQuerypriceDService.updateScmBQuotedpriceDState(currentUser.getUsername(),baseidL);
                    }
                }
            }

        } catch (Exception e) {
            message = "新增/按钮失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 跳转修改页面
     *
     * @param request
     * @param id      实体ID
     * @return
     */
    @Log("修改")
    @PutMapping
    @RequiresPermissions("scmBQuotedprice:update")
    public void updateScmBQuotedprice(@Valid ScmBQuotedprice scmBQuotedprice) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            scmBQuotedprice.setModifyUserId(currentUser.getUserId());
            this.iScmBQuotedpriceService.updateScmBQuotedprice(scmBQuotedprice);
        } catch (Exception e) {
            message = "修改失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @Log("删除")
    @DeleteMapping("/{ids}")
    @RequiresPermissions("scmBQuotedprice:delete")
    public void deleteScmBQuotedprices(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            this.iScmBQuotedpriceService.deleteScmBQuotedprices(arr_ids);
        } catch (Exception e) {
            message = "删除失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel")
    @RequiresPermissions("scmBQuotedprice:export")
    public void export(QueryRequest request, ScmBQuotedprice scmBQuotedprice, HttpServletResponse response) throws FebsException {
        try {
            List<ScmBQuotedprice> scmBQuotedprices = this.iScmBQuotedpriceService.findScmBQuotedprices(request, scmBQuotedprice).getRecords();
            ExcelKit.$Export(ScmBQuotedprice.class, response).downXlsx(scmBQuotedprices, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @GetMapping("/{id}")
    public ScmBQuotedprice detail(@NotBlank(message = "{required}") @PathVariable String id) {
        ScmBQuotedprice scmBQuotedprice = this.iScmBQuotedpriceService.getById(id);
        return scmBQuotedprice;
    }

    @GetMapping("GetList/{baseid}")
    public FebsResponse getQuoteList(@PathVariable String baseid)
    {
        User currentUser = FebsUtil.getCurrentUser();
        Long l_baseid=Long.parseLong(baseid);
        LambdaQueryWrapper<ScmBQuotedprice> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(ScmBQuotedprice::getBaseId,l_baseid);
        queryWrapper.eq(ScmBQuotedprice::getIsDeletemark,1);
        queryWrapper.eq(ScmBQuotedprice::getGysaccount,currentUser.getUsername());
        List<ScmBQuotedprice> list=this.iScmBQuotedpriceService.list(queryWrapper);
        for (ScmBQuotedprice scmBQuoteprice:list
             ) {
            LambdaQueryWrapper<ScmBQuotedpriceD> queryWrapperD=new LambdaQueryWrapper<>();
            queryWrapperD.eq(ScmBQuotedpriceD::getBaseId,scmBQuoteprice.getId());
            List<ScmBQuotedpriceD> listD=this.iScmBQuotedpriceDService.list(queryWrapperD);
            for (ScmBQuotedpriceD scmBQuotepriceD:listD
                 ) {
                if(StringUtils.isNotBlank( scmBQuotepriceD.getComFileId()))
                {
                    ComFile comFile=this.iComFileService.getById(scmBQuotepriceD.getComFileId());

                    scmBQuotepriceD.setClientName(comFile.getClientName());
                    scmBQuotepriceD.setServerName(comFile.getServerName());
                }
            }
            scmBQuoteprice.setHospital(listD);
        }
        FebsResponse feb=new FebsResponse();
        feb.data(list);
        return  feb;
    }
}