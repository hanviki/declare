package cc.mrbird.febs.scm.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.FebsResponse;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.scm.service.IScmBSendinfoService;
import cc.mrbird.febs.scm.entity.ScmBSendinfo;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
 * @author viki
 * @since 2019-11-26
 */
@Slf4j
@Validated
@RestController
@RequestMapping("scmBSendinfo")
public class ScmBSendinfoController extends BaseController {

    private String message;
    @Autowired
    public IScmBSendinfoService iScmBSendinfoService;


    /**
     * 分页查询数据
     *
     * @param bootStrapTable 分页信息
     * @param scmBSendinfo   查询条件
     * @return
     */
    @GetMapping
    @RequiresPermissions("scmBSendinfo:view")
    public Map<String, Object> List(QueryRequest request, ScmBSendinfo scmBSendinfo) {
        User currentUser = FebsUtil.getCurrentUser();
        scmBSendinfo.setGysaccount(currentUser.getUsername());
        //scmBSendinfo.setGysname(currentUser.getRealname());
        return getDataTable(this.iScmBSendinfoService.findScmBSendinfos(request, scmBSendinfo));
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
    @RequiresPermissions("scmBSendinfo:add")
    public void addScmBSendinfo(@Valid ScmBSendinfo scmBSendinfo) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            scmBSendinfo.setCreateUserId(currentUser.getUserId());

            scmBSendinfo.setGysaccount(currentUser.getUsername());
            scmBSendinfo.setGysname(currentUser.getRealname());
            this.iScmBSendinfoService.createScmBSendinfo(scmBSendinfo);
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
    @RequiresPermissions("scmBSendinfo:update")
    public void updateScmBSendinfo(@Valid ScmBSendinfo scmBSendinfo) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            scmBSendinfo.setModifyUserId(currentUser.getUserId());
            this.iScmBSendinfoService.updateScmBSendinfo(scmBSendinfo);
        } catch (Exception e) {
            message = "修改失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @Log("删除")
    @DeleteMapping("/{ids}")
    @RequiresPermissions("scmBSendinfo:delete")
    public void deleteScmBSendinfos(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            this.iScmBSendinfoService.deleteScmBSendinfos(arr_ids);
        } catch (Exception e) {
            message = "删除失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel")
    @RequiresPermissions("scmBSendinfo:export")
    public void export(QueryRequest request, ScmBSendinfo scmBSendinfo, HttpServletResponse response) throws FebsException {
        try {
            List<ScmBSendinfo> scmBSendinfos = this.iScmBSendinfoService.findScmBSendinfos(request, scmBSendinfo).getRecords();
            ExcelKit.$Export(ScmBSendinfo.class, response).downXlsx(scmBSendinfos, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @PostMapping("print")
    public FebsResponse Generate(@NotBlank(message = "{required}") String ids) {
        log.error("ids:"+ids);
        LambdaQueryWrapper<ScmBSendinfo> queryWrapper = new LambdaQueryWrapper<>();
       String[] arrIds= ids.split(StringPool.COMMA);
       List<Long> arr=new ArrayList<>();
        for (String id:arrIds
             ) {
            arr.add(Long.parseLong(id));
        }
        queryWrapper.in(ScmBSendinfo::getId,arr);
        List<ScmBSendinfo> list=this.iScmBSendinfoService.list(queryWrapper);
        StringBuilder sb = new StringBuilder();
        String gysName = "";
        sb.append("<table cellpadding=\"0\" cellspacing=\"0\">");
        for (int i = 0; i < list.size(); i++)
        {
            ScmBSendinfo entity=list.get(i);
            if (i == 0)
            {
                gysName = entity.getGysname();
                sb.append(String.format("<tr><td colspan=\"8\" style=\"height:50px;font-family:宋体;text-align:center;font-size: 20px;\" >%1$s</td></tr>", entity.getGysname() + "送货单"));
                sb.append(String.format("<tr><td colspan=\"4\" style=\"height:30px;font-family:宋体;text-align:left;font-size: 12px;\" >送货科室：%1$s</td><td colspan=\"3\" style=\"height:30px;font-family:宋体;font-size: 12px;\" >送货日期：</td><td></td></tr>", entity.getSendDepart()));
                GenerateHeadCode(sb, "序号", "物资名称", "物资编码", "送货数量", "单位", "单价", "金额", "备注说明");
            }
            // var data = GenerateMark(entity.CODE);
            GenerateCode(sb,  Integer.toString(i+1), entity.getTxz01(), entity.getMatnr(), entity.getAmount().toString(), entity.getMseht(), entity.getPrice().toString(), entity.getMoney().toString(), "");
        }
        sb.append(String.format("<tr><td colspan=\"4\" style=\"height:30px;font-family:宋体;border-top:solid 1px black;text-align:left;font-size: 12px;\" >供应商(盖章)：%1$s</td><td colspan=\"2\" style=\"height:30px;font-family:宋体;border-top:solid 1px black;font-size: 12px;\" >收货人：</td><td colspan=\"2\" style=\"border-top:solid 1px black;\"></td></tr>", gysName));
        sb.append("</table>");
        FebsResponse feb=new FebsResponse();
        feb.data(sb.toString());
        return  feb;
    }
    @GetMapping("/{id}")
    public ScmBSendinfo detail(@NotBlank(message = "{required}") @PathVariable String id) {
        ScmBSendinfo scmBSendinfo = this.iScmBSendinfoService.getById(id);
        return scmBSendinfo;
    }
    public void GenerateCode(StringBuilder sb, String k, String TXZ01, String MATNR, String MENGE, String MSEHT, String PRICE, String MONEY, String COMMENTS)
    {
        String replaceStr = GenerateStr();
        sb.append(String.format(replaceStr, k, MATNR, TXZ01, MENGE, MSEHT, PRICE, MONEY, COMMENTS));
    }
   /*
   行样式
    */
    public String GenerateStr()
    {
        String reStr =
                "<tr>" +
                        "<td style=\"width: 60px;border-left:solid 1px black;border-top:solid 1px black;text-align:center;height:30px;font-family:宋体;font-size: 12px;\">" +
                        "%1$s" +
                        "</td>" +
                        "<td style=\"width: 80px;border-left:solid 1px black;border-top:solid 1px black;height:30px;font-family:宋体;font-size: 12px;\">" +
                        "%2$s" +
                        "</td>" +
                        "<td style=\"width: 240px;border-left:solid 1px black;border-top:solid 1px black;height:30px;font-family:宋体;font-size: 12px;\">" +
                        "%3$s" +
                        "</td>" +
                        "<td style=\"width: 60px;border-left:solid 1px black;border-top:solid 1px black;text-align:center;height:30px;font-family:宋体;font-size: 12px;\">" +
                        "%4$s" +
                        "</td>" +
                        "<td style=\"width: 60px;border-left:solid 1px black;border-top:solid 1px black;text-align:center;height:30px;font-family:宋体;font-size: 12px;\">" +
                        "%5$s" +
                        "</td>" +
                        "<td style=\"width: 60px;border-left:solid 1px black;border-top:solid 1px black;text-align:center;height:30px;font-family:宋体;font-size: 12px;\">" +
                        "%6$s" +
                        "</td>" +
                        "<td style=\"width: 60px;border-left:solid 1px black;border-top:solid 1px black;text-align:center;height:30px;font-family:宋体;font-size: 12px;\">" +
                        "%7$s" +
                        "</td>" +
                        "<td style=\"width: 100px;border-left:solid 1px black;border-top:solid 1px black;border-right:solid 1px black;height:30px;font-family:宋体;font-size: 12px;\">" +
                        "%8$s" +
                        "</td>" +
                        "</tr>";

        return reStr;

    }

    public void GenerateHeadCode(StringBuilder sb, String k, String TXZ01, String MATNR, String MENGE, String MSEHT, String PRICE, String MONEY, String COMMENTS)
    {
        String replaceStr = GenerateHeadStr();
        sb.append(String.format(replaceStr, k, MATNR, TXZ01, MENGE, MSEHT, PRICE, MONEY, COMMENTS));
    }
    public String GenerateHeadStr()
    {
        String reStr =
                "<tr>" +
                        "<td style=\"width: 60px;border-left:solid 1px black;border-top:solid 1px black;text-align:center;height:30px;font-family:宋体;font-size: 12px;\">" +
                        "%1$s" +
                        "</td>" +
                        "<td style=\"width: 80px;border-left:solid 1px black;border-top:solid 1px black;text-align:center;height:30px;font-family:宋体;font-size: 12px;\">" +
                        "%2$s" +
                        "</td>" +
                        "<td style=\"width: 240px;border-left:solid 1px black;border-top:solid 1px black;text-align:center;height:30px;font-family:宋体;font-size: 12px;\">" +
                        "%3$s" +
                        "</td>" +
                        "<td style=\"width: 60px;border-left:solid 1px black;border-top:solid 1px black;text-align:center;height:30px;font-family:宋体;font-size: 12px;\">" +
                        "%4$s" +
                        "</td>" +
                        "<td style=\"width: 60px;border-left:solid 1px black;border-top:solid 1px black;text-align:center;height:30px;font-family:宋体;font-size: 12px;\">" +
                        "%5$s" +
                        "</td>" +
                        "<td style=\"width: 60px;border-left:solid 1px black;border-top:solid 1px black;text-align:center;height:30px;font-family:宋体;font-size: 12px;\">" +
                        "%6$s" +
                        "</td>" +
                        "<td style=\"width: 60px;border-left:solid 1px black;border-top:solid 1px black;text-align:center;height:30px;font-family:宋体;font-size: 12px;\">" +
                        "%7$s" +
                        "</td>" +
                        "<td style=\"width: 100px;border-left:solid 1px black;border-top:solid 1px black;border-right:solid 1px black;text-align:center;height:30px;font-family:宋体;font-size: 12px;\">" +
                        "%8$s" +
                        "</td>" +
                        "</tr>";

        return reStr;

    }
}