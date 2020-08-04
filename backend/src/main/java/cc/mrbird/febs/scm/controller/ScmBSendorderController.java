package cc.mrbird.febs.scm.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.config.FebsConfig;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.FebsResponse;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.common.properties.FebsProperties;
import cc.mrbird.febs.common.utils.BarCodeUtil;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.scm.RFC.BackFromSAP_SubPlan;
import cc.mrbird.febs.scm.RFC.RfcNOC;
import cc.mrbird.febs.scm.entity.ScmBSupplyplan;
import cc.mrbird.febs.scm.entity.ViewSupplyplan;
import cc.mrbird.febs.scm.service.IScmBSendorderService;
import cc.mrbird.febs.scm.entity.ScmBSendorder;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.scm.service.IViewSupplyplanService;
import cc.mrbird.febs.system.domain.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author viki
 * @since 2019-11-26
 */
@Slf4j
@Validated
@RestController
@RequestMapping("scmBSendorder")

public class ScmBSendorderController extends BaseController {

    private String message;
    @Autowired
    public IScmBSendorderService iScmBSendorderService;
    @Autowired
    public IViewSupplyplanService iViewSupplyplanService;
    @Autowired
    private FebsProperties febsProperties;
    /**
     * 分页查询数据
     *
     * @param bootStrapTable 分页信息
     * @param scmBSendorder  查询条件
     * @return
     */
    @GetMapping
    @RequiresPermissions("scmBSendorder:view")
    public Map<String, Object> List(QueryRequest request, ScmBSendorder scmBSendorder) {
        scmBSendorder.setIsDeletemark(1);
        User currentUser = FebsUtil.getCurrentUser();
        scmBSendorder.setGysaccount(currentUser.getUsername());
        log.error("art:"+scmBSendorder.getBsart());
        return getDataTable(this.iScmBSendorderService.findScmBSendorders(request, scmBSendorder));
    }
    @GetMapping("admin")
    @RequiresPermissions("scmBSendorder:view")
    public Map<String, Object> List2(QueryRequest request, ScmBSendorder scmBSendorder) {
        scmBSendorder.setIsDeletemark(1);
        return getDataTable(this.iScmBSendorderService.findScmBSendorders(request, scmBSendorder));
    }
    @GetMapping("planIds/{sendCode}")
    public  FebsResponse GetPlanIds(@PathVariable(value = "sendCode") String sendCode)
    {
        FebsResponse febsResponse=new FebsResponse();
        febsResponse.data(this.iScmBSendorderService.findPlanIds(sendCode));
        return  febsResponse;
    }
    /**
     *
     * @param scmBSendorder
     * @param supplyPlanIds
     * @throws FebsException
     */
    @Log("物资送货清单新增/按钮")
    @PostMapping
    @RequiresPermissions("scmBSendorder:add")
    public void addScmBSendorder(@Valid ScmBSendorder scmBSendorder, String supplyPlanIds) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            scmBSendorder.setCreateUserId(currentUser.getUserId());
            scmBSendorder.setGysaccount(currentUser.getUsername());
            scmBSendorder.setGysname(currentUser.getRealname());
            scmBSendorder.supplyPlanIds = supplyPlanIds;
            this.iScmBSendorderService.createScmBSendorder(scmBSendorder);

            if(StringUtils.isNotBlank(supplyPlanIds)) {



                List<ViewSupplyplan> list = new ArrayList<>();
                list.addAll(this.iViewSupplyplanService.getViewSupplyPlanByIds(supplyPlanIds));
                RfcNOC rfc = new RfcNOC();
                List<BackFromSAP_SubPlan> backMsg = rfc.SendSupplyPlan_RFC(currentUser.getUserId().toString(), list, currentUser.getUsername(), currentUser.getRealname(), "0", "U");
                if (!backMsg.get(0).getMSTYPE().equals("S")) {
                    log.error("修改送货订单,SAP端接收失败");
                    throw new FebsException("修改送货订单,SAP端接收失败");
                }
            }
        } catch (Exception e) {
            message = "新增/按钮失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
    @Log("药品新增/按钮")
    @PostMapping("OrderAdd")
    @RequiresPermissions("sendorder:add")
    public void addSendorder(@Valid ScmBSendorder scmBSendorder, String supplyPlanIds) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            scmBSendorder.setCreateUserId(currentUser.getUserId());
            scmBSendorder.setGysaccount(currentUser.getUsername());
            scmBSendorder.setGysname(currentUser.getRealname());
            scmBSendorder.supplyPlanIds = supplyPlanIds;
            this.iScmBSendorderService.createScmBSendorder(scmBSendorder);

            if(StringUtils.isNotBlank(supplyPlanIds)) {
                String ids="'"+supplyPlanIds.replace(",","','")+"'";

                List<ViewSupplyplan> list = new ArrayList<>();
                list.addAll(this.iViewSupplyplanService.getViewSupplyPlanByIds(supplyPlanIds));

                scmBSendorder.setWerks(list.get(0).getWerks());
                scmBSendorder.setWerkst(list.get(0).getWerkst());
                scmBSendorder.setLgort(list.get(0).getLgort());
                scmBSendorder.setLgortname(list.get(0).getLgortName());

                RfcNOC rfc = new RfcNOC();
                List<BackFromSAP_SubPlan> backMsg = rfc.SendSupplyPlan_RFC(currentUser.getUserId().toString(), list, currentUser.getUsername(), currentUser.getRealname(), "0", "U");
                if (!backMsg.get(0).getMSTYPE().equals("S")) {
                    log.error("修改送货订单,SAP端接收失败");
                    throw new FebsException("修改送货订单,SAP端接收失败");
                }
                else {
                    this.iScmBSendorderService.updateScmBSendorder(scmBSendorder);
                    //this.iScmBSendorderService.updateFpjr(scmBSendorder.getId().toString());
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
    @Log("物资修改")
    @PutMapping
    @RequiresPermissions("scmBSendorder:update")
    public void updateScmBSendorder(@Valid ScmBSendorder scmBSendorder, String supplyPlanIds) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            scmBSendorder.setModifyUserId(currentUser.getUserId());
            scmBSendorder.supplyPlanIds = supplyPlanIds;


            if(StringUtils.isNotBlank(supplyPlanIds)) {
                String ids="'"+supplyPlanIds.replace(",","','")+"'";

                List<ViewSupplyplan> list = new ArrayList<>();
                list.addAll(this.iViewSupplyplanService.getViewSupplyPlanByIds(supplyPlanIds));

                scmBSendorder.setWerks(list.get(0).getWerks());
                scmBSendorder.setWerkst(list.get(0).getWerkst());
                scmBSendorder.setLgort(list.get(0).getLgort());
                scmBSendorder.setLgortname(list.get(0).getLgortName());
                this.iScmBSendorderService.updateScmBSendorder(scmBSendorder);

                RfcNOC rfc = new RfcNOC();
                List<BackFromSAP_SubPlan> backMsg = rfc.SendSupplyPlan_RFC(currentUser.getUserId().toString(), list, currentUser.getUsername(), currentUser.getRealname(), "0", "U");
                if (!backMsg.get(0).getMSTYPE().equals("S")) {
                    log.error("修改送货订单,SAP端接收失败");
                    throw new FebsException("修改送货订单,SAP端接收失败");
                }
                else {
                    //this.iScmBSendorderService.updateFpjr(scmBSendorder.getId().toString());
                }
            }
            else
            {
                this.iScmBSendorderService.updateScmBSendorder(scmBSendorder);
            }
        } catch (Exception e) {
            message = "修改失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("药品修改")
    @PutMapping("orderEdit")
    @RequiresPermissions("sendorder:update")
    public void updateSendorder(@Valid ScmBSendorder scmBSendorder, String supplyPlanIds) throws FebsException {
        try {
            message="";
            User currentUser = FebsUtil.getCurrentUser();
            scmBSendorder.setModifyUserId(currentUser.getUserId());
            scmBSendorder.supplyPlanIds = supplyPlanIds;

            List<ViewSupplyplan> list = new ArrayList<>();
            List<ViewSupplyplan> listVp1 =this.iViewSupplyplanService.getViewSupplyPlanByOrderId(scmBSendorder.getId().toString());
            String werks="";
            String lgort="";

            for ( ViewSupplyplan vp:listVp1
                 ) {
                werks= vp.getWerks();
                lgort=vp.getLgort();
                if(vp.getStatus().equals(1)){
                    message="送货清单已经入库，不能修改";
                    throw new FebsException("送货清单已经入库，不能修改");
                }
                if(StringUtils.isNotBlank(supplyPlanIds)) {
                    if (!supplyPlanIds.contains(vp.getId().toString()) && vp.getStatus().equals(0)) {
                        vp.setSendOrderCode("");
                        list.add(vp);
                    }
                }
                else {
                    if(vp.getStatus().equals(0)) {
                        vp.setSendOrderCode("");
                        list.add(vp);
                    }
                }
            }
            if(StringUtils.isNotBlank(supplyPlanIds)) {
                String ids = "'" + supplyPlanIds.replace(",", "','") + "'";
                List<ViewSupplyplan> listvp = this.iViewSupplyplanService.getViewSupplyPlanByIds(supplyPlanIds);
                for (ViewSupplyplan vp2 : listvp
                ) {
                    if(werks.equals("")){
                        werks=vp2.getWerks();
                        lgort=vp2.getLgort();
                    }
                    else {
                        if (!(werks.equals(vp2.getWerks()) && lgort.equals(vp2.getLgort()))) {
                            throw new FebsException(vp2.getId().toString() + ":" + vp2.getWerkst() + "-" + vp2.getLgortName() + "不一致");
                        }
                    }
                    vp2.setSendOrderCode(scmBSendorder.getId().toString());
                    list.add(vp2);
                }
            }
            if(list.size()>0) {
                RfcNOC rfc = new RfcNOC();
                List<BackFromSAP_SubPlan> backMsg = rfc.SendSupplyPlan_RFC(currentUser.getUserId().toString(), list, currentUser.getUsername(), currentUser.getRealname(), "0", "U");
                if (!backMsg.get(0).getMSTYPE().equals("S")) {
                    message="修改送货订单,SAP端接收失败";
                    throw new FebsException("修改送货订单,SAP端接收失败");
                } else {
                    this.iScmBSendorderService.updateScmBSendorder(scmBSendorder);
                    // this.iScmBSendorderService.updateFpjr(scmBSendorder.getId().toString());
                }
            }
            else {
                this.iScmBSendorderService.updateScmBSendorder(scmBSendorder);
            }

        } catch (Exception e) {
           // message = "修改失败";
            log.error(message, e);
            throw new FebsException(e.getMessage());
        }
    }
    @Log("删除")
    @DeleteMapping("/{ids}")
    @RequiresPermissions("scmBSendorder:delete")
    public void deleteScmBSendorders(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
            message="";
            String[] arr_ids = ids.split(StringPool.COMMA);
            for (String orderid:arr_ids
                 ) {
                List<ViewSupplyplan> listVp1 =this.iViewSupplyplanService.getViewSupplyPlanByOrderId(orderid);
                if(listVp1.size()>0) {
                   message+="送货清单"+orderid +"存在供应计划不能删除!";
                }
            }
            if(message!=""){
                throw new FebsException(message);
            }
            this.iScmBSendorderService.deleteScmBSendorders(arr_ids);
        } catch (Exception e) {
            log.error(message, e);
            throw new FebsException(e.getMessage());
        }
    }

    @PostMapping("excel")
    @RequiresPermissions("scmBSendorder:export")
    public void export(QueryRequest request, ScmBSendorder scmBSendorder, HttpServletResponse response) throws FebsException {
        try {
            List<ScmBSendorder> scmBSendorders = this.iScmBSendorderService.findScmBSendorders(request, scmBSendorder).getRecords();
            ExcelKit.$Export(ScmBSendorder.class, response).downXlsx(scmBSendorders, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @GetMapping("/{id}")
    public ScmBSendorder detail(@NotBlank(message = "{required}") @PathVariable String id) {
        ScmBSendorder scmBSendorder = this.iScmBSendorderService.getById(id);
        return scmBSendorder;
    }
    //region 打印 清单和供应计划
    @PostMapping("print")
    public FebsResponse Generate(@NotBlank(message = "{required}") String id ,String bsart) {
        FebsResponse feb=new FebsResponse();
        LambdaQueryWrapper<ViewSupplyplan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ViewSupplyplan::getSendOrderCode,id);
        List<ViewSupplyplan> entitys= iViewSupplyplanService.list(queryWrapper);
        StringBuilder sb = new StringBuilder();
        String markCode = GenerateMark(id);//生成送货清单二维码

        if(bsart.equals("1"))
        {
            GenerMater(sb,entitys,id,markCode);
        }
        else
        {
            GenerYaoPin(sb,entitys,id,markCode);
        }

        feb.data(sb.toString());
        return  feb;
    }
    private void GenerYaoPin(StringBuilder sb,List<ViewSupplyplan> entitys ,String id ,String markCode ){
        sb.append("<table cellpadding=\"0\" cellspacing=\"0\">");

        String gysName = "";
        BigDecimal FPJR = entitys.stream().map(ViewSupplyplan::getFpjr).reduce(BigDecimal.ZERO,BigDecimal::add);
        for (int i = 0; i < entitys.size(); i++)
        {
            ViewSupplyplan entity = entitys.get(i);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (i == 0)
            {
                gysName = entity.getGysname();
                sb.append(String.format("<tr><td colspan=\"14\" style=\"height:50px;font-family:宋体;text-align:center;font-size: 20px;\" >%1$s</td></tr>",  "武汉协和医院药品送货清单"));
                sb.append(String.format("<tr><td colspan=\"4\" style=\"height:40px;font-family:宋体;text-align:left;font-size: 12px;\" >供应商：%1$s</td>", entity.getGysaccount()));
                sb.append(String.format("<td colspan=\"1\" style=\"height:40px;font-family:宋体;text-align:left;font-size: 12px;\" >院区：</td><td colspan=\"5\" style=\"height:40px;font-family:宋体;text-align:left;font-size: 12px;\" >发票号码：%1$s</td><td colspan=\"3\" style=\"height:40px;font-family:宋体;font-size: 12px;\" >送货清单号：</td><td style=\"height:40px;font-family:宋体;font-size: 12px;\" rowspan=\"2\"> <img alt=\"显示出错\" id=\"im_1\" src=\"%4$s\"  style=\" width:80px; height:80px;\"/></td></tr>", entity.getFphm(), FPJR, id, markCode, "协和"));
                sb.append(String.format("<tr><td colspan=\"4\" style=\"height:40px;font-family:宋体;text-align:left;font-size: 12px;\" >%1$s</td>", entity.getGysname()));
                sb.append(String.format("<td colspan=\"1\" style=\"height:40px;font-family:宋体;text-align:left;font-size: 12px;\" >%5$s</td><td colspan=\"5\" style=\"height:40px;font-family:宋体;font-size: 12px;\" >发票金额：%2$.2f</td><td colspan=\"3\" style=\"height:40px;font-family:宋体;font-size: 12px;\" >%3$s</td></tr>", entity.getFphm(), FPJR, id, markCode, entity.getWerkst()));
                GenerateHeadCode(sb, "订单日期", "采购订单", "行项目", "药品编码", "药品描述", "采购数量", "送货数量", "批次", "单价", "发票金额", "有效日期", "开票日期", "包装数量", "箱数");
            }
            // var data = GenerateMark(entity.CODE);

            GenerateCode(sb, sdf.format(entity.getBedat()), entity.getEbeln(), entity.getId().toString(), entity.getMatnr(), entity.getTxz01(), entity.getMenge().toString(), entity.getgMenge().toString(), entity.getCharge(), entity.getNetpr().toString(), entity.getFpjr().toString(), sdf.format(entity.getHsdat()), sdf.format(entity.getFprq()), entity.getPkgAmount()==null?"":entity.getPkgAmount().toString(), entity.getPkgNumber()==null?"":entity.getPkgNumber().toString());
        }
        //GenerateBottomCode(sb, "", "", "", "", "", "", "", "", "", "", "", "", "", "");//最后一行空着
        sb.append(String.format("<tr><td colspan=\"5\" style=\"height:30px;font-family:宋体;border-top:solid 1px black;text-align:left;font-size: 12px;\" >供应商(盖章)：%1$s</td><td colspan=\"5\" style=\"height:30px;font-family:宋体;border-top:solid 1px black;font-size: 12px;\" >采购中心(签字)：</td><td colspan=\"4\" style=\"height:30px;border-top:solid 1px black;font-family:宋体;font-size: 12px;\" >打印日期：</td></tr>", gysName));
        sb.append("</table>");
    }
    private void GenerMater(StringBuilder sb,List<ViewSupplyplan> entitys ,String id ,String markCode ){
        sb.append("<table cellpadding=\"0\" cellspacing=\"0\">");

        String gysName = "";
        BigDecimal FPJR = entitys.stream().map(ViewSupplyplan::getFpjr).reduce(BigDecimal.ZERO,BigDecimal::add);
        for (int i = 0; i < entitys.size(); i++)
        {
            ViewSupplyplan entity = entitys.get(i);
            if (i == 0)
            {
                gysName = entity.getGysname();
                sb.append(String.format("<tr><td colspan=\"14\" style=\"height:50px;font-family:宋体;text-align:center;font-size: 20px;\" >%1$s</td></tr>",  "武汉协和医院总务物资送货清单"));
                sb.append(String.format("<tr><td colspan=\"4\" style=\"height:40px;font-family:宋体;text-align:left;font-size: 12px;\" >供应商：%1$s</td>", entity.getGysaccount()));
                sb.append(String.format("<td colspan=\"1\" style=\"height:40px;font-family:宋体;text-align:left;font-size: 12px;\" >库房：</td><td colspan=\"5\" style=\"height:40px;font-family:宋体;text-align:left;font-size: 12px;\" >发票号码：%1$s</td><td colspan=\"3\" style=\"height:40px;font-family:宋体;font-size: 12px;\" >送货清单号：</td><td style=\"height:40px;font-family:宋体;font-size: 12px;\" rowspan=\"2\"> <img alt=\"显示出错\" id=\"im_1\" src=\"%4$s\"  style=\" width:80px; height:80px;\"/></td></tr>", entity.getFphm(), FPJR, id, markCode, entity.getWerkst()+entity.getLgortName()));
                sb.append(String.format("<tr><td colspan=\"4\" style=\"height:40px;font-family:宋体;text-align:left;font-size: 12px;\" >%1$s</td>", entity.getGysname()));
                sb.append(String.format("<td colspan=\"1\" style=\"height:40px;font-family:宋体;text-align:left;font-size: 12px;\" >%5$s</td><td colspan=\"5\" style=\"height:40px;font-family:宋体;font-size: 12px;\" >发票金额：%2$.2f</td><td colspan=\"3\" style=\"height:40px;font-family:宋体;font-size: 12px;\" >%3$s</td></tr>", entity.getFphm(), FPJR, id, markCode, entity.getWerkst()));
                GenerateHeadCode(sb, "订单日期", "采购订单", "行项目", "物资编码", "物资描述", "采购数量", "送货数量", "基本单位", "单价", "金额", "送达科室", "负责人", "联系电话", "商品条码");
            }
            // var data = GenerateMark(entity.CODE);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            GenerateCode(sb, sdf.format(entity.getBedat()), entity.getEbeln(), entity.getId().toString(), entity.getMatnr(), entity.getTxz01(), entity.getMenge().toString(), entity.getgMenge().toString(), entity.getMseht(), entity.getNetpr().toString(), entity.getFpjr().toString(), entity.getSendDepart(), entity.getLinkPerson(), entity.getLinkTelephone(), entity.getMatnr());
        }
        //GenerateBottomCode(sb, "", "", "", "", "", "", "", "", "", "", "", "", "", "");//最后一行空着
        sb.append(String.format("<tr><td colspan=\"5\" style=\"height:30px;font-family:宋体;border-top:solid 1px black;text-align:left;font-size: 12px;\" >供应商(盖章)：%1$s</td><td colspan=\"5\" style=\"height:30px;font-family:宋体;border-top:solid 1px black;font-size: 12px;\" >采购中心(签字)：</td><td colspan=\"4\" style=\"height:30px;border-top:solid 1px black;font-family:宋体;font-size: 12px;\" >打印日期：</td></tr>", gysName));
        sb.append("</table>");
    }
    public void GenerateCode(StringBuilder sb, String BEDAT, String EBELN, String EBELP, String MATNR, String TXZ01, String order_menge, String MENGE, String MSEHT, String PRICE, String MONEY, String SEND_DEPART_NAME, String LINK_PERSON, String LINK_TELEPHONE, String MATER_CODE)
    {
        String replaceStr = GenerateStr();
        sb.append(String.format(replaceStr, BEDAT, EBELN, EBELP, MATNR, TXZ01, order_menge, MENGE, MSEHT, PRICE, MONEY, SEND_DEPART_NAME, LINK_PERSON, LINK_TELEPHONE, MATER_CODE));
    }
    public String GenerateStr()
    {
        String reStr =
                "<tr>" +
                        "<td style=\"width: 80px;border-left:solid 1px black;border-top:solid 1px black;text-align:center;height:30px;font-family:宋体;font-size: 12px;\">" +
                        "%1$s" +
                        "</td>" +
                        "<td style=\"width: 60px;border-left:solid 1px black;border-top:solid 1px black;height:30px;font-family:宋体;font-size: 12px;\">" +
                        "%2$s" +
                        "</td>" +
                        "<td style=\"width: 60px;border-left:solid 1px black;border-top:solid 1px black;height:30px;font-family:宋体;font-size: 12px;\">" +
                        "%3$s" +
                        "</td>" +
                        "<td style=\"width: 60px;border-left:solid 1px black;border-top:solid 1px black;text-align:center;height:30px;font-family:宋体;font-size: 12px;\">" +
                        "%4$s" +
                        "</td>" +
                        "<td style=\"width: 240px;border-left:solid 1px black;border-top:solid 1px black;text-align:center;height:30px;font-family:宋体;font-size: 12px;\">" +
                        "%5$s" +
                        "</td>" +
                        "<td style=\"width: 60px;border-left:solid 1px black;border-top:solid 1px black;text-align:center;height:30px;font-family:宋体;font-size: 12px;\">" +
                        "%6$s" +
                        "</td>" +
                        "<td style=\"width: 60px;border-left:solid 1px black;border-top:solid 1px black;text-align:center;height:30px;font-family:宋体;font-size: 12px;\">" +
                        "%7$s" +
                        "</td>" +
                        "<td style=\"width: 60px;border-left:solid 1px black;border-top:solid 1px black;text-align:center;height:30px;font-family:宋体;font-size: 12px;\">" +
                        "%8$s" +
                        "</td>" +
                        "<td style=\"width: 60px;border-left:solid 1px black;border-top:solid 1px black;text-align:center;height:30px;font-family:宋体;font-size: 12px;\">" +
                        "%9$s" +
                        "</td>" +
                        "<td style=\"width: 60px;border-left:solid 1px black;border-top:solid 1px black;text-align:center;height:30px;font-family:宋体;font-size: 12px;\">" +
                        "%10$s" +
                        "</td>" +
                        "<td style=\"width: 60px;border-left:solid 1px black;border-top:solid 1px black;text-align:center;height:30px;font-family:宋体;font-size: 12px;\">" +
                        "%11$s" +
                        "</td>" +
                        "<td style=\"width: 60px;border-left:solid 1px black;border-top:solid 1px black;text-align:center;height:30px;font-family:宋体;font-size: 12px;\">" +
                        "%12$s" +
                        "</td>" +
                        "<td style=\"width: 80px;border-left:solid 1px black;border-top:solid 1px black;text-align:center;height:30px;font-family:宋体;font-size: 12px;\">" +
                        "%13$s" +
                        "</td>" +
                        "<td style=\"width: 100px;border-left:solid 1px black;border-top:solid 1px black;border-right:solid 1px black;height:30px;font-family:宋体;font-size: 12px;\">" +
                        "%14$s" +
                        "</td>" +
                        "</tr>";

        return reStr;

    }

    public void GenerateHeadCode(StringBuilder sb, String BEDAT, String EBELN, String EBELP, String MATNR, String TXZ01, String order_menge, String MENGE, String MSEHT, String PRICE, String MONEY, String SEND_DEPART_NAME, String LINK_PERSON, String LINK_TELEPHONE, String MATER_CODE)
    {
        String replaceStr = GenerateHeadStr();
        sb.append(String.format(replaceStr, BEDAT, EBELN, EBELP, MATNR, TXZ01, order_menge, MENGE, MSEHT, PRICE, MONEY, SEND_DEPART_NAME, LINK_PERSON, LINK_TELEPHONE, MATER_CODE));
    }
    public String GenerateHeadStr()
    {
        String reStr =
                "<tr>" +
                        "<td style=\"width: 80px;border-left:solid 1px black;border-top:solid 1px black;text-align:center;height:30px;font-family:宋体;font-size: 12px;\">" +
                        "%1$s" +
                        "</td>" +
                        "<td style=\"width: 60px;border-left:solid 1px black;border-top:solid 1px black;text-align:center;height:30px;font-family:宋体;font-size: 12px;\">" +
                        "%2$s" +
                        "</td>" +
                        "<td style=\"width: 60px;border-left:solid 1px black;border-top:solid 1px black;text-align:center;height:30px;font-family:宋体;font-size: 12px;\">" +
                        "%3$s" +
                        "</td>" +
                        "<td style=\"width: 60px;border-left:solid 1px black;border-top:solid 1px black;text-align:center;height:30px;font-family:宋体;font-size: 12px;\">" +
                        "%4$s" +
                        "</td>" +
                        "<td style=\"width: 240px;border-left:solid 1px black;border-top:solid 1px black;text-align:center;height:30px;font-family:宋体;font-size: 12px;\">" +
                        "%5$s" +
                        "</td>" +
                        "<td style=\"width: 60px;border-left:solid 1px black;border-top:solid 1px black;text-align:center;height:30px;font-family:宋体;font-size: 12px;\">" +
                        "%6$s" +
                        "</td>" +
                        "<td style=\"width: 60px;border-left:solid 1px black;border-top:solid 1px black;text-align:center;height:30px;font-family:宋体;font-size: 12px;\">" +
                        "%7$s" +
                        "</td>" +
                        "<td style=\"width: 60px;border-left:solid 1px black;border-top:solid 1px black;text-align:center;height:30px;font-family:宋体;font-size: 12px;\">" +
                        "%8$s" +
                        "</td>" +
                        "<td style=\"width: 60px;border-left:solid 1px black;border-top:solid 1px black;text-align:center;height:30px;font-family:宋体;font-size: 12px;\">" +
                        "%9$s" +
                        "</td>" +
                        "<td style=\"width: 60px;border-left:solid 1px black;border-top:solid 1px black;text-align:center;height:30px;font-family:宋体;font-size: 12px;\">" +
                        "%10$s" +
                        "</td>" +
                        "<td style=\"width: 60px;border-left:solid 1px black;border-top:solid 1px black;text-align:center;height:30px;font-family:宋体;font-size: 12px;\">" +
                        "%11$s" +
                        "</td>" +
                        "<td style=\"width: 60px;border-left:solid 1px black;border-top:solid 1px black;text-align:center;height:30px;font-family:宋体;font-size: 12px;\">" +
                        "%12$s" +
                        "</td>" +
                        "<td style=\"width: 80px;border-left:solid 1px black;border-top:solid 1px black;text-align:center;height:30px;font-family:宋体;font-size: 12px;\">" +
                        "%13$s" +
                        "</td>" +
                        "<td style=\"width: 100px;border-left:solid 1px black;border-top:solid 1px black;border-right:solid 1px black;text-align:center;height:30px;font-family:宋体;font-size: 12px;\">" +
                        "%14$s" +
                        "</td>" +
                        "</tr>";

        return reStr;

    }


    public ResponseEntity<byte[]> getQRImage(String id) {
        byte[] qrcode = null;
        try {
            qrcode = GetQRCodeImage(id, 64, 64);
        } catch (WriterException e) {
            System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
        }

        // Set headers
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<byte[]> (qrcode, headers, HttpStatus.CREATED);
    }

    public  byte[] GetQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();
        return pngData;
    }
    private  String GenerateMark(String id)
    {
        String filename = UUID.randomUUID().toString() + ".png";
        final String projectPath = febsProperties.getUploadPath();
        SimpleDateFormat sdf = new SimpleDateFormat("MM");

        Calendar cal = Calendar.getInstance();
        cal.add(cal.MONTH, -1);

        String preMonth = sdf.format(cal.getTime());

        String fileMonthPath = projectPath + sdf.format(new Date());
        String filePreMonthPath = projectPath + preMonth;
        File file = new File(fileMonthPath);
        File file_p = new File(filePreMonthPath);
        if (!file.exists())
        {
            file.mkdirs();
        }
        if (file_p.exists())
        {
           file_p.delete();
        }

        String filepath = fileMonthPath + "\\" + filename;
        try {
            BarCodeUtil.generateQRCodeImage(id,64,64,filepath);
        } catch (WriterException e) {
            System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
        }

        return febsProperties.getBaseUrl()+"/uploadFile/"+sdf.format(new Date())+"/"+filename;

    }
  //endregion

//    @GetMapping("phoneSendOrder")
//    @RequiresPermissions("phoneSendOrder:view")
//    public Map<String, Object> phoneSendOrderList(QueryRequest request, ViewSupplyplan viewSupplyplan) {
//        viewSupplyplan.setIsDeletemark(1);
//        viewSupplyplan.setBsart("0");
//        return getDataTable(this.iScmBSendorderService.findPhoneSendorders(request, viewSupplyplan));
//    }
//
//    @GetMapping("phoneGysSendOrder")
//    @RequiresPermissions("phoneGysSendOrder:view")
//    public Map<String, Object> phoneGysSendOrderList(QueryRequest request, ViewSupplyplan viewSupplyplan) {
//        viewSupplyplan.setIsDeletemark(1);
//        User currentUser = FebsUtil.getCurrentUser();
//        viewSupplyplan.setGysaccount(currentUser.getUsername());
//        viewSupplyplan.setBsart("0");
//        return getDataTable(this.iScmBSendorderService.findPhoneSendorders(request, viewSupplyplan));
//    }

    @GetMapping("phoneGysSendOrder")
    @RequiresPermissions("phoneGysSendOrder:view")
    public Map<String, Object> phoneSendOrderList2(QueryRequest request, ScmBSendorder scmBSendorder, ViewSupplyplan viewSupplyplan) {
        scmBSendorder.setIsDeletemark(1);

        User currentUser = FebsUtil.getCurrentUser();
        scmBSendorder.setGysaccount(currentUser.getUsername());
        log.error("art:"+scmBSendorder.getBsart());
        IPage<ScmBSendorder> list= this.iScmBSendorderService.findScmBSendorders_phone(request, scmBSendorder);
        List<ScmBSendorder> records=list.getRecords();

        List<String> listCodes=records.stream().map(t->t.getId().toString()).collect(Collectors.toList());
        String sendCodes="("+String.join(",", listCodes)+")";
        viewSupplyplan.setIsDeletemark(1);
        viewSupplyplan.setSendCodes(sendCodes);
        viewSupplyplan.setGysaccount(currentUser.getUsername());
        List<ViewSupplyplan> dataAll= this.iViewSupplyplanService.findPurcharseSendOrder(viewSupplyplan);
        viewSupplyplan.setId(0L);
        //此代码 可以优化
        for (ScmBSendorder order:records
             ) {

            List<ViewSupplyplan> data =dataAll.stream().filter(t->t.getSendOrderCode().equals(order.getId().toString())).collect(Collectors.toList());
            /**
            viewSupplyplan.setSendOrderCode(order.getId().toString());
            viewSupplyplan.setIsDeletemark(1);



            LambdaQueryWrapper<ViewSupplyplan> queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.eq(ViewSupplyplan::getSendOrderCode,order.getId().toString());
            queryWrapper.eq(ViewSupplyplan::getIsDeletemark,1);
            if ( viewSupplyplan.getStatus()!=null) {
                queryWrapper.eq(ViewSupplyplan::getStatus,viewSupplyplan.getStatus());
            }
            if(StringUtils.isNotBlank(viewSupplyplan.getWerks())) {
                queryWrapper.eq(ViewSupplyplan::getWerks,viewSupplyplan.getWerks());
            }
            if(StringUtils.isNotBlank(viewSupplyplan.getEbeln())) {
                queryWrapper.eq(ViewSupplyplan::getEbeln,viewSupplyplan.getEbeln());
            }
            if(StringUtils.isNotBlank(viewSupplyplan.getTxz01())) {
                queryWrapper.like(ViewSupplyplan::getTxz01,viewSupplyplan.getTxz01());
            }
            if(StringUtils.isNotBlank(viewSupplyplan.getMatnr())) {
                queryWrapper.eq(ViewSupplyplan::getMatnr,viewSupplyplan.getMatnr());
            }
            if(StringUtils.isNotBlank(viewSupplyplan.getLgortName())) {
                queryWrapper.like(ViewSupplyplan::getLgortName,viewSupplyplan.getLgortName());
            }


          // List<ViewSupplyplan> data= this.iViewSupplyplanService.list(queryWrapper);
            List<ViewSupplyplan> data= this.iViewSupplyplanService.findPurcharseSendOrder(viewSupplyplan);
           **/
             order.innerData=data.toArray(new ViewSupplyplan[data.size()]);
        }
        return getDataTable(list);
    }

    @GetMapping("phoneSendOrder")
    public Map<String, Object> phoneSendOrderList3(QueryRequest request, ScmBSendorder scmBSendorder, ViewSupplyplan viewSupplyplan) {
        User currentUser = FebsUtil.getCurrentUser();
        scmBSendorder.setIsDeletemark(1);
        scmBSendorder.setBsart("0");
        scmBSendorder.setName(currentUser.getUserId().toString());

        IPage<ScmBSendorder> list= this.iScmBSendorderService.findScmBSendorders_phone(request, scmBSendorder);
        List<ScmBSendorder> records=list.getRecords();

        if(records.size()>0){
            List<String> listCodes=records.stream().map(t->t.getId().toString()).collect(Collectors.toList());
            String sendCodes="("+String.join(",", listCodes)+")";



            viewSupplyplan.setIsDeletemark(1);
            if(viewSupplyplan.getStatus()!=null &&viewSupplyplan.getStatus().equals(0)) {
                viewSupplyplan.setDoneMenge(new BigDecimal(0));//当设置为0 时，就是取带入库的数据
            }

            // viewSupplyplan.setDoneMenge(new BigDecimal(1));//当设置为1 时，就是取已入库的数据
            viewSupplyplan.setEbelp(currentUser.getUserId().toString());

            viewSupplyplan.setId(0L);
            viewSupplyplan.setSendCodes(sendCodes);
            List<ViewSupplyplan> dataAll= this.iViewSupplyplanService.findPurcharseSendOrder(viewSupplyplan);
            //此代码 可以优化
            for (ScmBSendorder order:records
            ) {
                //  viewSupplyplan.setSendOrderCode(order.getId().toString());
                List<ViewSupplyplan> data =dataAll.stream().filter(t->t.getSendOrderCode().equals(order.getId().toString())).collect(Collectors.toList());
                /**
                 LambdaQueryWrapper<ViewSupplyplan> queryWrapper=new LambdaQueryWrapper<>();
                 queryWrapper.eq(ViewSupplyplan::getSendOrderCode,order.getId().toString());
                 queryWrapper.eq(ViewSupplyplan::getIsDeletemark,1);
                 if(viewSupplyplan.getStatus()!=null) {
                 queryWrapper.eq(ViewSupplyplan::getStatus,viewSupplyplan.getStatus());
                 }
                 if(StringUtils.isNotBlank(viewSupplyplan.getWerks())) {
                 queryWrapper.eq(ViewSupplyplan::getWerks,viewSupplyplan.getWerks());
                 }
                 if(StringUtils.isNotBlank(viewSupplyplan.getEbeln())) {
                 queryWrapper.eq(ViewSupplyplan::getEbeln,viewSupplyplan.getEbeln());
                 }
                 if(StringUtils.isNotBlank(viewSupplyplan.getTxz01())) {
                 queryWrapper.like(ViewSupplyplan::getTxz01,viewSupplyplan.getTxz01());
                 }
                 if(StringUtils.isNotBlank(viewSupplyplan.getLgortName())) {
                 queryWrapper.like(ViewSupplyplan::getLgortName,viewSupplyplan.getLgortName());
                 }
                 if(StringUtils.isNotBlank(viewSupplyplan.getMatnr())) {
                 queryWrapper.eq(ViewSupplyplan::getMatnr,viewSupplyplan.getMatnr());
                 }

                 List<ViewSupplyplan> data= this.iViewSupplyplanService.list(queryWrapper);
                 //  List<ViewSupplyplan> data= this.iViewSupplyplanService.findPurcharseSendOrder(viewSupplyplan);
                 **/
                order.innerData=data.toArray(new ViewSupplyplan[data.size()]);
            }
        }
        return getDataTable(list);
    }

    @GetMapping("pdaSendOrder")
    public Map<String, Object> phoneSendOrderList4(QueryRequest request, ScmBSendorder scmBSendorder, ViewSupplyplan viewSupplyplan) {
        User currentUser = FebsUtil.getCurrentUser();
        scmBSendorder.setIsDeletemark(1);
        scmBSendorder.setBsart("0");
       // scmBSendorder.setName(currentUser.getUserId().toString());

        IPage<ScmBSendorder> list= this.iScmBSendorderService.findScmBSendorders_phone(request, scmBSendorder);
        List<ScmBSendorder> records=list.getRecords();

        if(records.size()>0){
            List<String> listCodes=records.stream().map(t->t.getId().toString()).collect(Collectors.toList());
            String sendCodes="("+String.join(",", listCodes)+")";


            viewSupplyplan.setIsDeletemark(1);
            if(viewSupplyplan.getStatus()!=null &&viewSupplyplan.getStatus().equals(0)) {
                viewSupplyplan.setDoneMenge(new BigDecimal(0));//当设置为0 时，就是取带入库的数据
            }

            viewSupplyplan.setId(0L);
            viewSupplyplan.setSendCodes(sendCodes);
            List<ViewSupplyplan> dataAll= this.iViewSupplyplanService.findPurcharseSendOrder(viewSupplyplan);
            //此代码 可以优化
            for (ScmBSendorder order:records
            ) {
                //  viewSupplyplan.setSendOrderCode(order.getId().toString());
                List<ViewSupplyplan> data =dataAll.stream().filter(t->t.getSendOrderCode().equals(order.getId().toString())).collect(Collectors.toList());
                order.innerData=data.toArray(new ViewSupplyplan[data.size()]);
            }
        }
        return getDataTable(list);
    }

}