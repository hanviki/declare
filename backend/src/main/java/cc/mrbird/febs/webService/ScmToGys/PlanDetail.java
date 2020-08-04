package cc.mrbird.febs.webService.ScmToGys;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

public class PlanDetail {
    public String getEBELN() {
        return EBELN;
    }

    public void setEBELN(String EBELN) {
        this.EBELN = EBELN;
    }

    public String getGYJH() {
        return GYJH;
    }

    public void setGYJH(String GYJH) {
        this.GYJH = GYJH;
    }

    public String getGYSACCOUNT() {
        return GYSACCOUNT;
    }

    public void setGYSACCOUNT(String GYSACCOUNT) {
        this.GYSACCOUNT = GYSACCOUNT;
    }

    public String getGYSNAME() {
        return GYSNAME;
    }

    public void setGYSNAME(String GYSNAME) {
        this.GYSNAME = GYSNAME;
    }

    public String getMATNR() {
        return MATNR;
    }

    public void setMATNR(String MATNR) {
        this.MATNR = MATNR;
    }

    public String getTXZ01() {
        return TXZ01;
    }

    public void setTXZ01(String TXZ01) {
        this.TXZ01 = TXZ01;
    }

    public String getWERKS() {
        return WERKS;
    }

    public void setWERKS(String WERKS) {
        this.WERKS = WERKS;
    }

    public String getLGORT() {
        return LGORT;
    }

    public void setLGORT(String LGORT) {
        this.LGORT = LGORT;
    }

    public BigDecimal getMENGE() {
        return MENGE;
    }

    public void setMENGE(BigDecimal MENGE) {
        this.MENGE = MENGE;
    }

    public String getMEINS() {
        return MEINS;
    }

    public void setMEINS(String MEINS) {
        this.MEINS = MEINS;
    }

    public String getNETPR() {
        return NETPR;
    }

    public void setNETPR(String NETPR) {
        this.NETPR = NETPR;
    }

    public String getCHARG() {
        return CHARG;
    }

    public void setCHARG(String CHARG) {
        this.CHARG = CHARG;
    }

    public Date getHSDAT() {
        return HSDAT;
    }

    public void setHSDAT(Date HSDAT) {
        this.HSDAT = HSDAT;
    }

    public String getMSEHT() {
        return MSEHT;
    }

    public void setMSEHT(String MSEHT) {
        this.MSEHT = MSEHT;
    }

    public String getWERKST() {
        return WERKST;
    }

    public void setWERKST(String WERKST) {
        this.WERKST = WERKST;
    }

    public String getCODE() {
        return CODE;
    }

    public void setCODE(String CODE) {
        this.CODE = CODE;
    }

    public Date getVFDAT() {
        return VFDAT;
    }

    public void setVFDAT(Date VFDAT) {
        this.VFDAT = VFDAT;
    }

    public String getFPHM() {
        return FPHM;
    }

    public void setFPHM(String FPHM) {
        this.FPHM = FPHM;
    }

    public BigDecimal getFPJR() {
        return FPJR;
    }

    public void setFPJR(BigDecimal FPJR) {
        this.FPJR = FPJR;
    }

    public Date getFPRQ() {
        return FPRQ;
    }

    public void setFPRQ(Date FPRQ) {
        this.FPRQ = FPRQ;
    }

    public String getCOMMENTS() {
        return COMMENTS;
    }

    public void setCOMMENTS(String COMMENTS) {
        this.COMMENTS = COMMENTS;
    }

    public String getFPBM() {
        return FPBM;
    }

    public void setFPBM(String FPBM) {
        this.FPBM = FPBM;
    }

    public Date getEEIND() {
        return EEIND;
    }

    public void setEEIND(Date EEIND) {
        this.EEIND = EEIND;
    }

    public Date getBEDAT() {
        return BEDAT;
    }

    public void setBEDAT(Date BEDAT) {
        this.BEDAT = BEDAT;
    }

    public BigDecimal getPKG_AMOUNT() {
        return PKG_AMOUNT;
    }

    public void setPKG_AMOUNT(BigDecimal PKG_AMOUNT) {
        this.PKG_AMOUNT = PKG_AMOUNT;
    }

    public BigDecimal getPKG_NUMBER() {
        return PKG_NUMBER;
    }

    public void setPKG_NUMBER(BigDecimal PKG_NUMBER) {
        this.PKG_NUMBER = PKG_NUMBER;
    }

    public BigDecimal getORDER_MENGE() {
        return ORDER_MENGE;
    }

    public void setORDER_MENGE(BigDecimal ORDER_MENGE) {
        this.ORDER_MENGE = ORDER_MENGE;
    }

    public String getOUT_CAUSE() {
        return OUT_CAUSE;
    }

    public void setOUT_CAUSE(String OUT_CAUSE) {
        this.OUT_CAUSE = OUT_CAUSE;
    }

    public Date getOUT_DATE() {
        return OUT_DATE;
    }

    public void setOUT_DATE(Date OUT_DATE) {
        this.OUT_DATE = OUT_DATE;
    }

    public String getFLAG() {
        return FLAG;
    }

    public void setFLAG(String FLAG) {
        this.FLAG = FLAG;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    /// <summary>
    /// 采购凭证号
    /// </summary>
    private String EBELN ;
    /// <summary>
    /// 采购凭证项目编号
    /// </summary>
    private String GYJH ;
    /// <summary>
    /// 供应商账户
    /// </summary>
    private String GYSACCOUNT ;
    /// <summary>
    /// 供应商表述
    /// </summary>
    private String GYSNAME ;
    /// <summary>
    /// 物料号
    /// </summary>
    private String MATNR ;
    /// <summary>
    /// 物料描述
    /// </summary>
    private String TXZ01 ;
    /// <summary>
    /// 院区
    /// </summary>
    private String WERKS ;
    /// <summary>
    /// 库存地点
    /// </summary>
    private String LGORT ;

    /// <summary>
    /// 计划数量
    /// </summary>
    private BigDecimal MENGE ;

    /// <summary>
    /// 计量单位
    /// </summary>
    private String MEINS ;
    /// <summary>
    /// 竞价
    /// </summary>
    private String NETPR ;

    /// <summary>
    /// 批次
    /// </summary>
    private String CHARG ;

    /// <summary>
    /// 生产日期
    /// </summary>
    private Date HSDAT ;

    /// <summary>
    /// 计量单位描述
    /// </summary>
    private String MSEHT ;

    /// <summary>
    /// 工厂描述
    /// </summary>
    private String WERKST ;

    /// <summary>
    /// 供应计划号
    /// </summary>
    private String CODE ;
    /// <summary>
    /// 有效日期
    /// </summary>
    private Date VFDAT ;

    /// <summary>
    /// 发票号码
    /// </summary>
    private String FPHM ;

    /// <summary>
    /// 发票金额
    /// </summary>
    private BigDecimal FPJR ;
    /// <summary>
    /// 发票日期
    /// </summary>
    private Date FPRQ ;

    /// <summary>
    /// 备注
    /// </summary>
    private String COMMENTS ;
    /// <summary>
    /// 发票编码
    /// </summary>
    private String FPBM ;


    /// <summary>
    ///  交货日期       
    /// </summary>
    private Date EEIND ;

    /// <summary>
    /// 凭证日期
    /// </summary>
    private Date BEDAT ;

    /// <summary>
    /// 包装数量
    /// </summary>
    private BigDecimal PKG_AMOUNT ;

    /// <summary>
    /// 箱数
    /// </summary>
    private BigDecimal PKG_NUMBER ;

    /// <summary>
    /// 采购订单数量
    /// </summary>
    private BigDecimal ORDER_MENGE ;
    /// <summary>
    /// 缺货原因
    /// </summary>
    private String OUT_CAUSE ;
    /// <summary>
    /// 下次补货日期
    /// </summary>
    private Date OUT_DATE ;
    /// <summary>
    /// 标志 
    /// </summary>
    private String FLAG ;

    private String ID ;
}
