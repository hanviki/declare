package cc.mrbird.febs.webService.ScmToGys;


import cc.mrbird.febs.common.utils.MD5Util;
import cc.mrbird.febs.scm.RFC.BackFromSAP_SubPlan;
import cc.mrbird.febs.scm.RFC.RfcNOC;
import cc.mrbird.febs.scm.dao.ScmBGysMaterPicMapper;
import cc.mrbird.febs.scm.dao.ScmBPurcharseorderMapper;
import cc.mrbird.febs.scm.dao.ScmBSupplyplanMapper;
import cc.mrbird.febs.scm.dao.ScmDVendorMapper;
import cc.mrbird.febs.scm.entity.*;
import cc.mrbird.febs.system.domain.User;
import cc.mrbird.febs.system.manager.UserManager;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@Service
@WebService(name = "gys", targetNamespace = "urn:ScmToGys.webService.febs.mrbird.cc",
        endpointInterface = "cc.mrbird.febs.webService.ScmToGys.ISCM_XHService"// 接口地址
)
public class SCM_XHImpl implements ISCM_XHService {

    @Autowired
    private ScmDVendorMapper scmDVendorMapper;

    @Autowired
    private ScmBPurcharseorderMapper scmBPurcharseorderMapper;

    @Autowired
    private ScmBSupplyplanMapper scmBSupplyplanMapper;

    @Autowired
    private UserManager userManager;
    @Autowired
    private ScmBGysMaterPicMapper scmBGysMaterPicMapper;
    public String HelloWorld() {
        return  "haha";
    }
    /**
     *
     * @param userName
     * @param password
     * @param startTime
     * @param endTime
     * @return
     */


    public WcfMess_XH ExportPurchasePlan(String userName, String password, String startTime, String endTime) {
        WcfMess_XH Msg = new WcfMess_XH();
        log.info("用户：" + userName+",password:"+password + "开始获取采购计划");
        if (!StringUtils.isNotBlank(userName) || !StringUtils.isNotBlank(password)) {
            Msg.setIsSuccess(false);
            Msg.setMess("参数有误");
            Msg.setPurchasePlans(null);
            return Msg;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate;
        try {
            startDate = sdf.parse(startTime);
        }
        catch (Exception ex)
        {
            Msg.setIsSuccess(false);
            Msg.setMess("开始时间参数有误");
            Msg.setPurchasePlans(null);
            return Msg;
        }
        Date endDate;
        try {
            endDate = sdf.parse(endTime);
        }
        catch (Exception ex)
        {
            Msg.setIsSuccess(false);
            Msg.setMess("结束时间参数有误");
            Msg.setPurchasePlans(null);
            return Msg;
        }
        try {
            String accountCode = userName.trim().replace("'", "");
            LambdaQueryWrapper<ScmDVendor> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ScmDVendor::getCode, accountCode);
            List<ScmDVendor> en_MDMs = this.scmDVendorMapper.selectList(queryWrapper);
            if (en_MDMs.size() > 0) {
                ScmDVendor en_MDM = en_MDMs.get(0);

                if (en_MDM != null && en_MDM.getJieKouState() == 1) {
                    String username = StringUtils.lowerCase(accountCode);
                    password = MD5Util.encrypt(username, password);

                    final String errorMessage = "用户名或密码错误";
                    User user = this.userManager.getUser(username);
                    if (user == null) {
                        Msg.setIsSuccess(false);
                        Msg.setMess("您的用户名或密码有误");
                        Msg.setPurchasePlans(null);
                        return Msg;
                    }

                    if (!StringUtils.equals(user.getPassword(), password)) {
                        Msg.setIsSuccess(false);
                        Msg.setMess("您的用户名或密码有误");
                        Msg.setPurchasePlans(null);
                        return Msg;
                    }
                    if (User.STATUS_LOCK.equals(user.getStatus())) {
                        Msg.setIsSuccess(false);
                        Msg.setMess("账号已被锁定,请联系管理员！");
                        Msg.setPurchasePlans(null);
                        return Msg;
                    }


                    LambdaQueryWrapper<ScmBPurcharseorder> queryOrderWrapper = new LambdaQueryWrapper<>();
                    queryOrderWrapper.eq(ScmBPurcharseorder::getLifnr, accountCode);
                    queryOrderWrapper.eq(ScmBPurcharseorder::getStatus, "1");
                    queryOrderWrapper.between(ScmBPurcharseorder::getBedat, startDate, endDate);


                    List<ScmBPurcharseorder> list = this.scmBPurcharseorderMapper.selectList(queryOrderWrapper);
                    list.sort(
                            new Comparator<ScmBPurcharseorder>() {
                                @Override
                                public int compare(ScmBPurcharseorder o1, ScmBPurcharseorder o2) {
                                    int i = o1.getEbeln().compareTo(o2.getEbeln());
                                    if (i == 0) {
                                        i = o1.getEbelp().compareTo(o2.getEbelp());
                                    }
                                    return i;
                                }
                            }
                    );

                    //   var list = rnc.GetPurcharseList("", userName.Trim().Replace("'", ""), "", startTime.ToString("yyyy-MM-dd"), endTime.ToString("yyyy-MM-dd"));
                    List<Purchase> reList = new ArrayList<>();
                    for (ScmBPurcharseorder item : list
                    ) {
                        String v_lgort = item.getLgort();//库位表述
                        String v_name = item.getName();
                        if (item.getBsart().equals("Z004") && item.getWerks().equals("2200"))// hsc 2019 07 05 去除限制
                        {
                            // var v_lgort2 = Ipedf.Web.Common.SendCode.GetDepartCode(item.SEND_DEAPRT_NAME ?? "");
                            v_name = item.getSendDeaprtName().equals("") ? "药库" : item.getSendDeaprtName();
                        }
                        Purchase purchase = new Purchase();
                        purchase.setId(item.getEbeln() + item.getEbelp());

                        purchase.setBedat(sdf.format(item.getBedat()));
                        purchase.setEbeln(item.getId());//把采购订单的ID存入采购订单号 新的修改
                        purchase.setEbelp(item.getEbelp());
                        purchase.setEindt(sdf.format(item.getEindt()));
                        purchase.setLgort(v_lgort);
                        purchase.setLifnr(item.getLifnr());
                        purchase.setMatnr(item.getMatnr());
                        purchase.setMeins(item.getMeins());
                        purchase.setMenge(item.getMenge());
                        purchase.setName1(v_name);
                        purchase.setMseht(item.getMseht());
                        purchase.setNetpr(item.getNetpr());
                        purchase.setTxz01(item.getTxz01());
                        purchase.setWerkst(item.getWerkst());
                        purchase.setWerks(item.getWerks());
                        reList.add(purchase);
                        log.info("传出参数:" + purchase.toString());

                    }
                    Msg.setPurchasePlans(reList);
                    Msg.setIsSuccess(true);
                    Msg.setMess("");
                    return Msg;
                } else {
                    Msg.setIsSuccess(false);
                    Msg.setMess("您的接口尚未开通");
                    Msg.setPurchasePlans(null);
                    return Msg;
                }
            }
            else {
                Msg.setIsSuccess(false);
                Msg.setMess("供应商不存在注册");
                Msg.setPurchasePlans(null);
            }
            return Msg;
        } catch (Exception ex) {
            Msg.setIsSuccess(false);
            Msg.setMess("SCM接口出错");
            Msg.setPurchasePlans(null);
            return Msg;

        }

    }

    /**
     * @param order
     * @param plan
     * @param id    保存供应商上传的ID 放在baseid中
     * @return
     */
    private ViewSupplyplan getViewSupplan(ScmBPurcharseorder order, ScmBSupplyplan plan, String id) {
        ViewSupplyplan vEn = new ViewSupplyplan();
        vEn.setBsart("0");
        vEn.setIsDeletemark(1);
        vEn.setGysaccount(order.getLifnr().trim());
        vEn.setBaseId(id);//。。。。。。。。。。。。。。。。重要
        vEn.setGysname(order.getGysname());
        vEn.setBsartD("0");
        vEn.setBedat(order.getBedat());
        vEn.setCharge(plan.getCharge());
        vEn.setEbeln(order.getEbeln());
        vEn.setEbelp(order.getEbelp());
        vEn.setEindt(order.getEindt());
        vEn.setFpbm(plan.getFpbm());
        vEn.setFphm(plan.getFphm());
        vEn.setFpjr(plan.getFpjr());
        vEn.setFprq(plan.getFprq());
        vEn.setgMenge(plan.getgMenge());
        vEn.setWerkst(order.getWerkst());
        vEn.setWerks(order.getWerks());
        vEn.setVfdat(plan.getVfdat());
        vEn.setTxz01(order.getTxz01());
        vEn.setSendDepart(plan.getSendDepart());
        vEn.setSendOrderCode(plan.getSendOrderCode());
        vEn.setPkgNumber(plan.getPkgNumber());
        vEn.setPkgAmount(plan.getPkgAmount());
        vEn.setOutCause(plan.getOutCause());
        if (plan.getOutDate() != null) {
            vEn.setOutDate(plan.getOutDate());
        }
        vEn.setNetpr(order.getNetpr());
        vEn.setName(order.getName());
        vEn.setMseht(order.getMseht());
        vEn.setMenge(order.getMenge());
        vEn.setMeins(order.getMeins());
        vEn.setMatnr(order.getMatnr());
        vEn.setLinkTelephone(plan.getLinkTelephone());
        vEn.setLinkPerson(plan.getLinkPerson());
        vEn.setLgortName(order.getLgortName());
        vEn.setLgort(order.getLgort());
        vEn.setId(plan.getId());
        vEn.setHsdat(plan.getHsdat());
        return vEn;

    }

    private WcfPlan_XH GenerateMsg(String ID, String Mess, boolean IsSuccess) {
        WcfPlan_XH wcfPlan_xh = new WcfPlan_XH();
        wcfPlan_xh.setId(ID);
        wcfPlan_xh.setMess(Mess);
        wcfPlan_xh.setIsSuccess(IsSuccess);
        return wcfPlan_xh;
    }

    private Boolean IsExistFphm(ScmBPurcharseorder entity, String id, String fphm, String gysAccount) {
        if (entity.getWerks().equals("2000") & (entity.getLgort() .equals("1001") || entity.getLgort().equals("1008"))) {
            return true;
        }
        if (entity.getWerks().equals("2200") & (entity.getLgort().equals("1002") || entity.getLgort().equals("1005"))) {
            return true;
        }
        if (entity.getWerks().equals("2100")){
            return true;
        }
        int count = this.scmBSupplyplanMapper.IsExistFphm(id, fphm, gysAccount);
        if (count > 0) {
            return false;
        }
        return true;
    }

    public List<WcfPlan_XH> ImportSupplyPlan(String userName, String password, String trueName, List<PlanDetail> PlanDetails) {
        log.info("用户：" + userName + "上传供应计划");
        List<WcfPlan_XH> ListMess = new ArrayList<>();
        int IsLimit = 1;//0是不限制是否上传 1限制
        //region 校验密码
        User user = new User();
        if (!StringUtils.isNotBlank(userName) || !StringUtils.isNotBlank(password)) {
            WcfPlan_XH Msg = new WcfPlan_XH();
            Msg.setIsSuccess(false);
            Msg.setMess("参数有误");
            Msg.setId("");
            ListMess.add(Msg);
            return ListMess;
        }
        String accountCode = userName.trim().replace("'", "");
        LambdaQueryWrapper<ScmDVendor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ScmDVendor::getCode, accountCode);
        List<ScmDVendor> en_MDMs = this.scmDVendorMapper.selectList(queryWrapper);
        if (en_MDMs.size() > 0) {
            ScmDVendor en_MDM = en_MDMs.get(0);

            if (en_MDM != null && en_MDM.getJieKouState() == 1) {
                String username = StringUtils.lowerCase(accountCode);
                password = MD5Util.encrypt(username, password);

                user = this.userManager.getUser(username);
                if (user == null) {
                    WcfPlan_XH Msg = new WcfPlan_XH();
                    Msg.setIsSuccess(false);
                    Msg.setMess("您的用户名或密码有误");
                    Msg.setId("");
                    ListMess.add(Msg);
                    return ListMess;
                }

                if (!StringUtils.equals(user.getPassword(), password)) {
                    WcfPlan_XH Msg = new WcfPlan_XH();
                    Msg.setIsSuccess(false);
                    Msg.setMess("您的用户名或密码有误");
                    Msg.setId("");
                    ListMess.add(Msg);
                    return ListMess;
                }
                if (User.STATUS_LOCK.equals(user.getStatus())) {
                    WcfPlan_XH Msg = new WcfPlan_XH();
                    Msg.setIsSuccess(false);
                    Msg.setMess("账号已被锁定,请联系管理员！");
                    Msg.setId("");
                    ListMess.add(Msg);
                    return ListMess;
                }
            } else {
                WcfPlan_XH Msg = new WcfPlan_XH();
                Msg.setIsSuccess(false);
                Msg.setMess("请通知管理员开通接口！");
                Msg.setId("");
                ListMess.add(Msg);
                return ListMess;
            }
            if (en_MDM != null && en_MDM.getFileState() == 1) {//是否限制文件上传
                IsLimit = 0;
            }
        }

        //endregionord.Trim().Replace("'", ""));

        List<String> orderIds = PlanDetails.stream().map(PlanDetail::getEBELN).collect(Collectors.toList());
        if (orderIds.size() <= 0) {
            WcfPlan_XH Msg = new WcfPlan_XH();
            Msg.setIsSuccess(false);
            Msg.setMess("无效的回填数据，EBELN必填，且不能替换");
            Msg.setId("");
            ListMess.add(Msg);
            return ListMess;
        }
        List<ScmBPurcharseorder> listOrder = this.scmBPurcharseorderMapper.getAllByIds(orderIds);//获取对应的采购订单
        if (listOrder.size() <= 0) {
            WcfPlan_XH Msg = new WcfPlan_XH();
            Msg.setIsSuccess(false);
            Msg.setMess("无效的回填数据，EBELN必填，且不能替换");
            Msg.setId("");
            ListMess.add(Msg);
            return ListMess;
        }
        List<ScmBSupplyplan> listPlans = new ArrayList<>();
        List<String> supplyPlanIds = PlanDetails.stream().filter(p -> "D,U".contains(p.getFLAG())).map(PlanDetail::getCODE).collect(Collectors.toList());
        if (supplyPlanIds.size() > 0) {
            listPlans = this.scmBSupplyplanMapper.getAllPlansByIds(supplyPlanIds);
        }
        List<ViewSupplyplan> list_supp_C = new ArrayList<>();
        List<ViewSupplyplan> list_supp_U = new ArrayList<>();
        List<ViewSupplyplan> list_supp_D = new ArrayList<>();

        for (PlanDetail item : PlanDetails) {
            //region 数据验证
            ScmBPurcharseorder order = listOrder.stream().filter(p -> p.getId().equals(item.getEBELN())).findFirst().get();
            if (order == null) {
                ListMess.add(GenerateMsg(item.getID(), "不存在的订单号", false));
                continue;
            }
            if (!StringUtils.isNotBlank(item.getID())) {
                ListMess.add(GenerateMsg(item.getID(), "ID字段不能为空", false));
                // ListMess.Add(new WcfPlan_XH { ID = item.ID, MESS = "ID字段不能为空", IsSuccess = false });
                continue;
            }
            if (!StringUtils.isNotBlank(item.getEBELN())) {
                ListMess.add(GenerateMsg(item.getID(), "EBELN字段不能为空", false));
                continue;
            }

            if (!StringUtils.isNotBlank(item.getGYJH())) {
                ListMess.add(GenerateMsg(item.getID(), "GYJH字段不能为空", false));
                continue;
            }
            if (item.getORDER_MENGE() == null || item.getORDER_MENGE().longValue() < 0) {
                ListMess.add(GenerateMsg(item.getID(), "ORDER_MENGE字段不能为空", false));
                continue;
            }
            if (item.getMENGE() == null || item.getMENGE().longValue() < 0) {
                ListMess.add(GenerateMsg(item.getID(), "MENGE字段不能为空", false));
                continue;
            }
            if (!StringUtils.isNotBlank(item.getFLAG()) || !"C U D".contains(item.getFLAG())) {
                ListMess.add(GenerateMsg(item.getID(), "FALG字段不能为空", false));
                continue;
            }
            if (item.getPKG_AMOUNT() == null && item.getPKG_AMOUNT().longValue() < 0) {
                ListMess.add(GenerateMsg(item.getID(), "PKG_AMOUNT数值必须大于等于0", false));
                continue;
            }
            if (item.getPKG_NUMBER() == null && item.getPKG_NUMBER().longValue() < 0) {
                ListMess.add(GenerateMsg(item.getID(), "PKG_NUMBER数值必须大于等于0", false));
                continue;
            }
            if (item.getFPJR() == null || item.getFPJR().longValue() < 0) {
                ListMess.add(GenerateMsg(item.getID(), "FPJR数值必须大于等于0", false));
                continue;
            }
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.MONTH, 6);
            Date m = c.getTime();
            if (item.getVFDAT().compareTo(m) < 0) {
                ListMess.add(GenerateMsg(item.getID(), "药品剩余效期不足6个月", false));
                continue;
            }

            if (!IsExistFphm(order, "", item.getFPHM(), userName.trim().replace("'", ""))) {
                ListMess.add(GenerateMsg(item.getID(), "发票号码已经存在", false));
                continue;
            }

            ScmBSupplyplan entity = new ScmBSupplyplan();
            if ("U,D".contains(item.getFLAG()))//修改删除
            {
                Long planId = Long.parseLong(item.getCODE());
                entity = listPlans.stream().filter(t -> t.getId() == planId).findFirst().get();

                if (entity == null) {
                    ListMess.add(GenerateMsg(item.getID(), "不存在的供应计划", false));
                    continue;
                }
            }
            //endregion
            //region 赋值数据
            entity.setCharge(item.getCHARG());
            entity.setComments(item.getCOMMENTS());

            entity.setFphm(item.getFPHM());
            entity.setFpbm(item.getFPBM());
            entity.setFpjr(item.getFPJR());
            entity.setFprq(item.getFPRQ());

            entity.setgMenge(item.getMENGE());

            entity.setGysaccount(user.getUsername());
            entity.setGysname(user.getRealname());
            entity.setHsdat(item.getHSDAT());
            // entity.ID = item.ID;

            entity.setBaseId(order.getId());


            entity.setPkgAmount(item.getPKG_AMOUNT());
            entity.setPkgNumber(item.getPKG_NUMBER());

            entity.setVfdat(item.getVFDAT());
            entity.setOutCause(item.getOUT_CAUSE());
            if (item.getOUT_DATE() != null) {
                entity.setOutDate(item.getOUT_DATE());
            }


            entity.setBsartD("0");//hsc 2017 06 08 如果总务需要做接口 需要做更多修改

            //endregion
            if (item.getFLAG().equals("C")) {
                entity.setStatus(0);// = "0";//0新建 1确认
                // list_supp_C.Add(entity);
                try {
                    Long isMenge = this.scmBSupplyplanMapper.IsOutMenge(entity);
                    if (isMenge != null && isMenge > 0) {
                        WcfPlan_XH Msg = new WcfPlan_XH();
                        Msg.setIsSuccess(false);
                        Msg.setMess("供应总数量超出订单数量！");
                        Msg.setId(item.getID());
                        Msg.setCode("");
                        ListMess.add(Msg);
                        continue;
                    }
                    if (IsLimit == 1) {
                        LambdaQueryWrapper<ScmBGysMaterPic> queryGysPic = new LambdaQueryWrapper<>();
                        queryGysPic.eq(ScmBGysMaterPic::getMaterId, order.getMatnr());
                        queryGysPic.eq(ScmBGysMaterPic::getGysaccount, user.getUsername());
                        queryGysPic.eq(ScmBGysMaterPic::getCharge, item.getCHARG());
                        int fileCount = this.scmBGysMaterPicMapper.selectCount(queryGysPic);
                        if (fileCount < 0) {
                            WcfPlan_XH Msg = new WcfPlan_XH();
                            Msg.setIsSuccess(false);
                            Msg.setMess("没有对应的药品验收报告！");
                            Msg.setId(item.getID());
                            Msg.setCode("");
                            ListMess.add(Msg);
                            continue;
                        }
                    }
                    entity.setCreateTime(new Date());
                    entity.setCreateUserId(user.getUserId());
                    this.scmBSupplyplanMapper.insert(entity);
                    if (entity.getId() != null) {
                        ViewSupplyplan addOrderEnrity = getViewSupplan(order, entity, item.getID());
                        list_supp_C.add(addOrderEnrity);//上传SAP
                    } else {
                        WcfPlan_XH Msg = new WcfPlan_XH();
                        Msg.setIsSuccess(false);
                        Msg.setMess("获取供应计划号失败");
                        Msg.setId(item.getID());
                        Msg.setCode("");
                        ListMess.add(Msg);
                        continue;
                    }
                } catch (Exception ex) {
                    WcfPlan_XH Msg = new WcfPlan_XH();
                    Msg.setIsSuccess(false);
                    Msg.setMess(ex.getMessage());
                    Msg.setId(item.getID());
                    Msg.setCode("");
                    ListMess.add(Msg);
                }
            } else if (item.getFLAG().equals("U")) {
                try {
                    Long isMenge = this.scmBSupplyplanMapper.IsOutMenge(entity);
                    if (isMenge != null && isMenge > 0) {
                        WcfPlan_XH Msg = new WcfPlan_XH();
                        Msg.setIsSuccess(false);
                        Msg.setMess("供应总数量超出订单数量！");
                        Msg.setId(item.getID());
                        Msg.setCode("");
                        ListMess.add(Msg);
                        continue;
                    }
                    if (IsLimit == 1) {
                        LambdaQueryWrapper<ScmBGysMaterPic> queryGysPic = new LambdaQueryWrapper<>();
                        queryGysPic.eq(ScmBGysMaterPic::getMaterId, order.getMatnr());
                        queryGysPic.eq(ScmBGysMaterPic::getGysaccount, user.getUsername());
                        queryGysPic.eq(ScmBGysMaterPic::getCharge, item.getCHARG());
                        int fileCount = this.scmBGysMaterPicMapper.selectCount(queryGysPic);
                        if (fileCount < 0) {
                            WcfPlan_XH Msg = new WcfPlan_XH();
                            Msg.setIsSuccess(false);
                            Msg.setMess("没有对应的药品验收报告！");
                            Msg.setId(item.getID());
                            Msg.setCode("");
                            ListMess.add(Msg);
                            continue;
                        }
                    }
                    entity.setModifyUserId(user.getUserId());
                    entity.setModifyTime(new Date());

                    this.scmBSupplyplanMapper.updateScmBSupplyplan(entity);
                    WcfPlan_XH Msg = new WcfPlan_XH();
                    Msg.setIsSuccess(false);
                    Msg.setMess("更新数据成功！");
                    Msg.setId(item.getID());
                    Msg.setCode("");
                    ListMess.add(Msg);
                    ViewSupplyplan orderEnrity = getViewSupplan(order, entity, item.getID());
                    list_supp_U.add(orderEnrity);
                } catch (Exception ex) {
                    WcfPlan_XH Msg = new WcfPlan_XH();
                    Msg.setIsSuccess(false);
                    Msg.setMess("更新数据有误,错误信息！" + ex.getMessage());
                    Msg.setId(item.getID());
                    Msg.setCode("");
                    ListMess.add(Msg);
                }

            } else {
                ViewSupplyplan deleteOrderEnrity = getViewSupplan(order, entity, item.getID());
                list_supp_D.add(deleteOrderEnrity);
            }

        }
        //endregion

        RfcNOC rnc = new RfcNOC();
        //region 删除供应计划
        if (list_supp_D.size() > 0) {
            List<BackFromSAP_SubPlan> List_Back = rnc.SendSupplyPlan_RFC("", list_supp_D, userName, trueName, "0", "D");//SAP返回的列表
            for (BackFromSAP_SubPlan back : List_Back
            ) {
                Long gyjh = Long.parseLong(back.getZGYJH());
                String deleteID = list_supp_D.stream().filter(p -> p.getId() == gyjh).findFirst().get().getBaseId();
                if (!back.getMSTYPE().equals("S")) {

                    WcfPlan_XH Msg = new WcfPlan_XH();
                    Msg.setIsSuccess(false);
                    Msg.setMess("发送SAP数据失败,删除失败");
                    Msg.setId(deleteID);
                    Msg.setCode("");
                    ListMess.add(Msg);
                } else {
                    ScmBSupplyplan deletesupplan = new ScmBSupplyplan();
                    deletesupplan.setId(gyjh);
                    deletesupplan.setIsDeletemark(0);
                    this.scmBSupplyplanMapper.updateScmBSupplyplan(deletesupplan);
                    WcfPlan_XH Msg = new WcfPlan_XH();
                    Msg.setIsSuccess(true);
                    Msg.setMess("删除成功");
                    Msg.setId(deleteID);
                    Msg.setCode(back.getZGYJH());
                    ListMess.add(Msg);
                }
            }

        }

        //endregion

        //region 增加供应计划
        if (list_supp_C.size() > 0) {
            //var msg = BizLogicObject_SCM_D_SUPPLYPLAN.Proxy.BatchSave(list_supp_C.ToArray());
            try {
                List<BackFromSAP_SubPlan> List_Back = rnc.SendSupplyPlan_RFC("", list_supp_C, userName, trueName, "0", "C");//SAP返回的列表


                for (BackFromSAP_SubPlan back : List_Back
                ) {

                    Long gyjh = Long.parseLong(back.getZGYJH().trim());
                    if(list_supp_C.size()>2) {
                        log.info(list_supp_C.get(1).getId().toString() + ":" + list_supp_C.get(1).getBaseId());
                        log.info(list_supp_C.get(2).getId().toString() + ":" + list_supp_C.get(2).getBaseId());
                    }
                    List<ViewSupplyplan> deList = list_supp_C.stream().filter(p -> p.getId().equals(gyjh)).collect(Collectors.toList());
                   String deleteID="";
                   if(deList.size()>0)
                   {
                       log.info("sssssss");
                       deleteID=deList.get(0).getBaseId();
                   }
                    WcfPlan_XH Msg = new WcfPlan_XH();
                    log.info("getMSTYPE:"+back.getMSTYPE());
                    if (!back.getMSTYPE().equals("S")) {
                        ScmBSupplyplan deletesupplan = new ScmBSupplyplan();
                        deletesupplan.setId(gyjh);
                        deletesupplan.setIsDeletemark(0);
                        this.scmBSupplyplanMapper.updateScmBSupplyplan(deletesupplan);
                        Msg.setIsSuccess(false);
                        Msg.setMess("发送SAP数据失败,新增失败");
                        Msg.setId(deleteID);
                        Msg.setCode("");
                        ListMess.add(Msg);
                    } else {
                        Msg.setIsSuccess(true);
                        Msg.setMess("新增成功");
                        Msg.setId(deleteID);
                        Msg.setCode(back.getZGYJH());
                        ListMess.add(Msg);
                    }
                }

            } catch (Exception ex) {
                for (ViewSupplyplan en : list_supp_C) {
                    ScmBSupplyplan deletesupplan = new ScmBSupplyplan();
                    deletesupplan.setId(en.getId());
                    deletesupplan.setIsDeletemark(0);
                    this.scmBSupplyplanMapper.updateScmBSupplyplan(deletesupplan);

                    log.error(ex.getMessage());
                    WcfPlan_XH Msg = new WcfPlan_XH();
                    Msg.setIsSuccess(false);
                    Msg.setMess("发送SAP数据失败,新增失败");
                    Msg.setId(en.getBaseId());
                    Msg.setCode("");
                    ListMess.add(Msg);
                }
            }

        }

        //endregion

        return ListMess;
    }
}
