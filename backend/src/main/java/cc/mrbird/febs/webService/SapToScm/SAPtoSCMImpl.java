package cc.mrbird.febs.webService.SapToScm;

import cc.mrbird.febs.scm.dao.*;
import cc.mrbird.febs.scm.entity.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jdk.net.SocketFlow;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.WebService;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
@Service
@WebService(name = "sap", targetNamespace = "urn:SapToScm.webService.febs.mrbird.cc",
        endpointInterface = "cc.mrbird.febs.webService.SapToScm.ISAPtoSCMService"// 接口地址
)
public class SAPtoSCMImpl implements ISAPtoSCMService {

    @Autowired
    private ScmDSenddepartMapper scmDSenddepartMapper;
    @Autowired
    private ScmBPurcharseorderMapper scmBPurcharseorderMapper;

    @Autowired
    private ScmDMaterMapper scmDMaterMapper;

    @Autowired
    private ScmBSupplyplanMapper scmBSupplyplanMapper;

    @Autowired
    private ScmDHrpmaterMapper scmDHrpmaterMapper;

    @Autowired
    private ScmDVendorMapper scmDVendorMapper;

    @Autowired
    private ScmDAreaMapper scmDAreaMapper;

    public String HelloWorld() {
        return "haha";
    }

    @Override
    public Boolean GetPucharseFromSap(List<Sap_PurchasePlan> purcharseList, String Flag) {
        log.info("从SAP获取订单开始");


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<ScmDSenddepart> listDepart = this.scmDSenddepartMapper.selectList(null);
        List<GysEntity> listVendors = this.scmDVendorMapper.getGysNameAndCode();
        // List<ScmDArea> listArea=this.scmDAreaMapper.selectList(null);//在传递订单的时候，检测库房是否存在
        if (purcharseList.size() > 0) {
            log.info("从SAP更新订单开始");


            List<ScmBPurcharseorder> list_Update = new ArrayList<>();
            List<ScmBPurcharseorder> list_Delete = new ArrayList<>();
            List<ScmBPurcharseorder> list_purchase_C = new ArrayList<>();
            for (Sap_PurchasePlan item : purcharseList) {

                String gysName = "";
                List<GysEntity> listvf = listVendors.stream().filter((GysEntity e0) -> e0.getCode().equals(item.getLifnr())).collect(Collectors.toList());
                if (listvf.size() > 0) {
                    gysName = listvf.get(0).getName();
                }

                LambdaQueryWrapper<ScmBPurcharseorder> queryWrapperOrder = new LambdaQueryWrapper<>();
                queryWrapperOrder.eq(ScmBPurcharseorder::getEbelp, item.getEbelp());
                queryWrapperOrder.eq(ScmBPurcharseorder::getEbeln, item.getEbeln());
                ScmBPurcharseorder order = scmBPurcharseorderMapper.selectOne(queryWrapperOrder);
                Flag = (order == null ? "C" : "U");


                if (Flag == "U") {

                    if (StringUtils.equals(item.getLoekz(), "L")) {
                        if (order != null) {
                            list_Delete.add(order);
                        }
                    } else {
                        if (order != null) {
                            order.setEbeln(item.getEbeln());
                            try {
                                order.setBedat(sdf.parse(item.getBedat()));
                            } catch (Exception ex) {
                            }
                            order.setEbelp(item.getEbelp());
                            try {
                                order.setEindt(sdf.parse(item.getEindt()));
                            } catch (Exception ex) {
                            }
                            order.setLgort(item.getLgort());
                            order.setLifnr(item.getLifnr());
                            order.setMatnr(item.getMatnr());
                            order.setMenge(item.getMenge());
                            order.setMseht(item.getMseht());
                            order.setName(item.getName());
                            order.setLgortName(item.getName());
                            order.setGysname(gysName);
                            order.setStatus(1);
                            order.setNetpr(new BigDecimal(item.getNetpr().replace(",", "")));
                            order.setWerks(item.getWerks());
                            order.setWerkst(item.getWerkst());
                            order.setTxz01(item.getTxz01());
                            order.setMeins(item.getMeins());
                            order.setBsart(StringUtils.equals(item.getBsart().trim(), "Z004") ? "0" : "1");

                            order.setSendDeaprtContact(item.getContact());
                            order.setSendDeaprtPhone(item.getPhone());
                            order.setComments(item.getComments());

                            if (StringUtils.equals(item.getBsart().trim(), "Z004")) {
                                order.setSendDeaprtName(item.getRemark());//西院的药品订单 接收科室放在订单备注里
                            } else {
                                if (StringUtils.isNotBlank(item.getKostl()))//采购订单含有送货科室编码
                                {
                                    List<ScmDSenddepart> departs = listDepart.stream().filter(p -> p.CODE == item.getKostl()).collect(Collectors.toList());
                                    if (departs != null) {
                                        order.setSendDeaprtId(departs.get(0).getCode());
                                        order.setSendDeaprtName(departs.get(0).getName());
                                    }
                                }
                            }
                            list_Update.add(order);

                        }
                    }
                } else if (Flag == "C") {
                    ScmBPurcharseorder entity = new ScmBPurcharseorder();

                    entity.setEbeln(item.getEbeln());
                    try {
                        entity.setBedat(sdf.parse(item.getBedat()));
                    } catch (Exception ex) {
                    }
                    entity.setEbelp(item.getEbelp());
                    try {
                        entity.setEindt(sdf.parse(item.getEindt()));
                    } catch (Exception ex) {
                    }

                    entity.setLgort(item.getLgort());
                    entity.setLifnr(item.getLifnr());
                    entity.setMatnr(item.getMatnr());
                    entity.setMenge(item.getMenge());
                    entity.setMseht(item.getMseht());
                    entity.setName(item.getName());
                    entity.setLgortName(item.getName());
                    entity.setStatus(1);
                    entity.setNetpr(new BigDecimal(item.getNetpr().replace(",", "")));

                    entity.setWerks(item.getWerks());
                    entity.setWerkst(item.getWerkst());
                    entity.setTxz01(item.getTxz01());
                    entity.setMeins(item.getMeins());
                    entity.setGysname(gysName);
                    entity.setBsart(StringUtils.equals(item.getBsart().trim(), "Z004") ? "0" : "1");

                    entity.setSendDeaprtContact(item.getContact());
                    entity.setSendDeaprtPhone(item.getPhone());
                    entity.setComments(item.getComments());
                    entity.setId(UUID.randomUUID().toString());

                    if (StringUtils.equals(item.getBsart().trim(), "Z004")) {
                        entity.setSendDeaprtName(item.getRemark());//西院的药品订单 接收科室放在订单备注里
                    } else {
                        if (StringUtils.isNotBlank(item.getKostl()))//采购订单含有送货科室编码
                        {
                            List<ScmDSenddepart> departs = listDepart.stream().filter(p -> p.CODE == item.getKostl()).collect(Collectors.toList());
                            if (departs != null) {
                                entity.setSendDeaprtId(departs.get(0).getCode());
                                entity.setSendDeaprtName(departs.get(0).getName());
                            }
                        }
                    }

                    list_purchase_C.add(entity);
                }

            }

            if (list_purchase_C.size() > 0) {
                try {
                    for (ScmBPurcharseorder itemOrder : list_purchase_C) {
                        itemOrder.setCreateTime(new Date());
                        this.scmBPurcharseorderMapper.insert(itemOrder);
                    }
                    //return msg.Succeed;
                } catch (Exception ex) {
                    log.info("新增采购订单报错");
                }
            }
            if (list_Update.size() > 0) {
                for (ScmBPurcharseorder itemOrder : list_Update) {
                    itemOrder.setModifyTime(new Date());
                    this.scmBPurcharseorderMapper.updateScmBPurcharseorder(itemOrder);
                }
            }

            if (list_Delete.size() > 0) {
                for (ScmBPurcharseorder deleteOrder : list_Delete) {
                    this.scmBPurcharseorderMapper.deleteById(deleteOrder.getId());
                }
            }
            return true;

        }
        return false;
    }

    @Override
    public Boolean GetMaterFromSap(List<SAP_MATER> materList, String Flag) {
        log.info("从SAP获取物料开始");

        if (materList.size() > 0) {

            log.info("从SAP更新物料开始");

            List<ScmDMater> list_Update = new ArrayList<>();
            List<ScmDMater> list_purchase_C = new ArrayList<>();
            List<String> list_matnr = new ArrayList<>();
            for (SAP_MATER item : materList) {
                if (list_matnr.contains(item.getMatnr().trim())) {
                    continue;
                }
                list_matnr.add(item.getMatnr().trim());
                LambdaQueryWrapper<ScmDMater> queryWrapperMater = new LambdaQueryWrapper<>();

                queryWrapperMater.eq(ScmDMater::getGysaccount, item.getGysAccount().trim());

                queryWrapperMater.eq(ScmDMater::getMatnr, item.getMatnr().trim());

                List<ScmDMater> ensList = scmDMaterMapper.selectList(queryWrapperMater);
                ScmDMater ens = null;
                if (ensList.size() > 0) {
                    ens = ensList.get(0);
                }
                if (ens != null && StringUtils.isNotBlank(ens.getMatnr())) {
                    ens.setMatnr(item.getMatnr().trim());
                    if (StringUtils.isNotBlank(item.getGysAccount())) {
                        ens.setGysaccount(item.getGysAccount().trim());
                    }

                    ens.setProduceArea(item.getProduceArea() == null ? "" : item.getProduceArea().trim());
                    ens.setSpec(item.getSpec() == null ? "" : item.getSpec().trim());
                    ens.setSpellCode(item.getSpellCode() == null ? "" : item.getSpellCode().trim());
                    ens.setBklas(item.getBklas() == null ? "" : item.getBklas().trim());
                    ens.setTxz01(item.getTxz01() == null ? "" : item.getTxz01().trim());
                    list_Update.add(ens);
                } else {

                    ScmDMater entity = new ScmDMater();
                    entity.setMatnr(item.getMatnr().trim());
                    entity.setGysaccount(item.getGysAccount().trim());

                    entity.setProduceArea(item.getProduceArea() == null ? "" : item.getProduceArea().trim());
                    entity.setSpec(item.getSpec() == null ? "" : item.getSpec().trim());
                    entity.setSpellCode(item.getSpellCode() == null ? "" : item.getSpellCode().trim());
                    entity.setBklas(item.getBklas() == null ? "" : item.getBklas().trim());

                    entity.setTxz01(item.getTxz01() == null ? "" : item.getTxz01().trim());
                    entity.setId(UUID.randomUUID().toString());

                    list_purchase_C.add(entity);

                }
            }
            try {
                if (list_Update.size() > 0) {
                    for (ScmDMater m : list_Update) {
                        scmDMaterMapper.updateScmDMater(m);
                    }
                }
                if (list_purchase_C.size() > 0) {
                    for (ScmDMater am : list_purchase_C) {
                        scmDMaterMapper.insert(am);
                    }
                }
                return true;
            } catch (Exception ex) {
                log.info("新增药品报错" + ex.getMessage());
                return false;
            }
        }
        return false;

    }

    @Override
    public Boolean ChangeStausFromSap(List<String> codeList, String status) {
        log.info("从SAP更改供应计划状态");
        try {
            if (codeList.size() > 0) {
                LambdaQueryWrapper<ScmBSupplyplan> queryWrapperPlan = new LambdaQueryWrapper<>();
                List<Long> arr = new ArrayList<>();
                for (String code : codeList) {
                    ScmBSupplyplan plan = new ScmBSupplyplan();
                    plan.setStatus(Integer.parseInt(status.trim()));
                    plan.setId(Long.parseLong(code.trim()));
                    scmBSupplyplanMapper.updateScmBSupplyplan(plan);
                }
                log.info("从SAP更改供应计划状态成功！");
                return true;

            }
        } catch (Exception ex) {
            log.error("从SAP更改供应计划状态失败！");
            log.error(ex.getMessage());
        }
        return false;
    }

    public Boolean GetHrpMaterFromSap(List<HRP_MATER> materList, String Flag) {
        log.info("从SAP获取物料开始");
        if (StringUtils.equals("D", Flag)) {

            List<ScmDHrpmater> delete_Update = new ArrayList<>();
            if (materList.size() > 0) {
                for (HRP_MATER item : materList) {
                    scmDHrpmaterMapper.deleteById(item.getMATNR());
                }
            }
        } else {
            if (materList.size() > 0) {

                log.info("从SAP更新订单开始");

                try {
                    for (HRP_MATER item : materList) {
                        ScmDHrpmater ens = scmDHrpmaterMapper.selectById(item.getMATNR());
                        if (ens != null) {
                            ens.setMaktx(item.getMAKTX() == null ? "" : item.getMAKTX().trim());
                            ens.setMeins(item.getMEINS() == null ? "" : item.getMEINS().trim());
                            ens.setMseht(item.getMSEHT() == null ? "" : item.getMSEHT().trim());
                            ens.setMtart(item.getMTART() == null ? "" : item.getMTART().trim());
                            ens.setNormt(item.getNORMT() == null ? "" : item.getNORMT().trim());
                            ens.setZeinr(item.getZEINR() == null ? "" : item.getZEINR().trim());
                            ens.setBklas(item.getBKLAS() == null ? "" : item.getBKLAS().trim());
                            scmDHrpmaterMapper.updateScmDHrpmater(ens);
                        } else {
                            ScmDHrpmater entity = new ScmDHrpmater();

                            entity.setMatnr(item.getMATNR().trim());
                            entity.setMaktx(item.getMAKTX() == null ? "" : item.getMAKTX().trim());
                            entity.setMeins(item.getMEINS() == null ? "" : item.getMEINS().trim());
                            entity.setMseht(item.getMSEHT() == null ? "" : item.getMSEHT().trim());
                            entity.setMtart(item.getMTART() == null ? "" : item.getMTART().trim());
                            entity.setNormt(item.getNORMT() == null ? "" : item.getNORMT().trim());
                            entity.setZeinr(item.getZEINR() == null ? "" : item.getZEINR().trim());
                            entity.setBklas(item.getBKLAS() == null ? "" : item.getBKLAS().trim());
                            entity.setId(item.getMATNR().trim());
                            scmDHrpmaterMapper.insert(entity);
                        }
                    }
                    return true;
                } catch (Exception ex) {
                    log.error("获取物资信息失败!" + ex.getMessage());
                }
            }

        }

        return false;

    }
}
