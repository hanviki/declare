package cc.mrbird.febs.scm.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;

/**
 * <p>
 * VIEW
 * </p>
 *
 * @author viki
 * @since 2019-12-05
 */

@Excel("view_supplyplan")
public class ViewSupplyplan implements Serializable{

private static final long serialVersionUID=1L;

    /**
     * 订单号
     */
            @ExcelField(value ="订单号")
private String ebeln;
    /**
     * 项目号
     */
            @ExcelField(value ="项目号")
private String ebelp;
    /**
     * 物料ID
     */
            @ExcelField(value ="物料ID")
private String matnr;
    /**
     * 物料描述
     */
            @ExcelField(value ="物料描述")
private String txz01;
    /**
     * 院区名称
     */
            @ExcelField(value ="院区名称")
private String werkst;
    /**
     * 院区ID
     */
            @ExcelField(value ="院区ID")
private String werks;
    /**
     * 库房ID
     */
            @ExcelField(value ="库房ID")
private String lgort;
    /**
     * 库房名称
     */
    @TableField("lgortName")
            @ExcelField(value ="库房名称")
private String lgortName;
    /**
     * 订单数量
     */
            @ExcelField(value ="订单数量")
private BigDecimal menge;
    /**
     * 计量单位ID
     */
            @ExcelField(value ="计量单位ID")
private String meins;
    /**
     * 计量单位
     */
            @ExcelField(value ="计量单位")
private String mseht;
    /**
     * 单价
     */
            @ExcelField(value ="单价")
private BigDecimal netpr;
    /**
     * 订单开始时间
     */
            @ExcelField(value ="订单开始时间")
private Date eindt;
    /**
     * 订单结束时间
     */
            @ExcelField(value ="订单结束时间")
private Date bedat;
    /**
     * 类型
     */
            @ExcelField(value ="类型")
private String bsart;
    /**
     * 供应数量
     */
    @TableField("G_MENGE")
            @ExcelField(value ="供应数量")
private BigDecimal gMenge;
    /**
     * 收货数量
     */
    @TableField("DONEMENGE")
    @ExcelField(value ="已收货数量")
    private BigDecimal doneMenge;
    /**
     * 批号
     */
    @TableField("CHARGE")
            @ExcelField(value ="批号")
private String charge;

    /**
     *  所送库房
     */
    @TableField("NAME")
    @ExcelField(value ="未知")
    private String name;
    /**
     * 有效期
     */
    @TableField("VFDAT")
            @ExcelField(value ="有效期")
private Date vfdat;
    /**
     * 生产日期
     */
    @TableField("HSDAT")
            @ExcelField(value ="生产日期")
private Date hsdat;
    /**
     * 发票号码
     */
    @TableField("FPHM")
            @ExcelField(value ="发票号码")
private String fphm;
    /**
     * 发票金额
     */
    @TableField("FPJR")
            @ExcelField(value ="发票金额")
private BigDecimal fpjr;
    /**
     * 发票日期
     */
    @TableField("FPRQ")
            @ExcelField(value ="发票日期")
private Date fprq;
    /**
     * 父ID
     */
    @TableField("BASE_ID")
            @ExcelField(value ="父ID")
private String baseId;
    /**
     * 主键
     */
    @TableField("ID")
            @ExcelField(value ="主键")
private Long id;
    /**
     * 状态
     */
    @TableField("STATUS")
            @ExcelField(value ="状态")
private Integer status;
    /**
     * 发票编码
     */
    @TableField("FPBM")
            @ExcelField(value ="发票编码")
private String fpbm;
    /**
     * 供应商账号
     */
    @TableField("GYSACCOUNT")
            @ExcelField(value ="供应商账号")
private String gysaccount;
    /**
     * 供应商名称
     */
    @TableField("GYSNAME")
            @ExcelField(value ="供应商名称")
private String gysname;
    /**
     * 包装规格
     */
    @TableField("PKG_AMOUNT")
            @ExcelField(value ="包装规格")
private BigDecimal pkgAmount;

    /**
     * 包装数量
     */
    @TableField("PKG_NUMBER")
            @ExcelField(value ="包装数量")
private BigDecimal pkgNumber;
    @TableField("UNINFORMED_STATE")
        private String uninformedState;
    @TableField("INFORMED_STATE")
        private String informedState;
    /**
     * 订单类型
     */
    @TableField("BSART_D")
            @ExcelField(value ="订单类型")
private String bsartD;
    /**
     * 联系人
     */
    @TableField("LINK_PERSON")
            @ExcelField(value ="联系人")
private String linkPerson;
    /**
     * 送达科室
     */
    @TableField("SEND_DEPART")
            @ExcelField(value ="送达科室")
private String sendDepart;
    /**
     * 联系方式
     */
    @TableField("LINK_TELEPHONE")
            @ExcelField(value ="联系方式")
private String linkTelephone;
    /**
     * 送货清单号
     */
    @TableField("SEND_ORDER_CODE")
            @ExcelField(value ="送货清单号")
private String sendOrderCode;
    /**
     * 缺货原因
     */
    @TableField("OUT_CAUSE")
            @ExcelField(value ="缺货原因")
private String outCause;


    /**
     * 补货日期
     */
    @TableField("OUT_DATE")
            @ExcelField(value ="补货日期")
private Date outDate;
    /**
     * 是否删除
     */
    @TableField("IS_DELETEMARK")
            @ExcelField(value ="是否删除")
private Integer isDeletemark;
    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
            @ExcelField(value ="创建时间")
private Date createTime;
    /**
     * 修改时间
     */
    @TableField("MODIFY_TIME")
            @ExcelField(value ="修改时间")
private Date modifyTime;
    /**
     * 创建人
     */
    @TableField("CREATE_USER_ID")
            @ExcelField(value ="创建人")
private Long createUserId;
    /**
     * 修改人
     */
    @TableField("MODIFY_USER_ID")
            @ExcelField(value ="修改人")
private Long modifyUserId;

    private transient BigDecimal sub_menge;

    private transient Integer sub_state;

    public String getNoOrder() {
        return noOrder;
    }

    public void setNoOrder(String noOrder) {
        this.noOrder = noOrder;
    }

    private  transient String  noOrder;

    public String getKeyword_gys() {
        return keyword_gys;
    }

    public void setKeyword_gys(String keyword_gys) {
        this.keyword_gys = keyword_gys;
    }

    private transient String keyword_gys;

    public String getKeyword_mater() {
        return keyword_mater;
    }

    public void setKeyword_mater(String keyword_mater) {
        this.keyword_mater = keyword_mater;
    }

    private transient String keyword_mater;

  public String getSendCodes() {
        return sendCodes;
    }

    public void setSendCodes(String sendCodes) {
        this.sendCodes = sendCodes;
    }

    private  transient String  sendCodes;



    public String getName(){
            return name;
            }

        public void setName(String name) {
            this.name = name;
            }

    public String getEbeln(){
        return ebeln;
    }

    public void setEbeln(String ebeln) {
        this.ebeln = ebeln;
    }

    public String getEbelp(){
            return ebelp;
            }

        public void setEbelp(String ebelp) {
            this.ebelp = ebelp;
            }

    public String getMatnr(){
            return matnr;
            }

        public void setMatnr(String matnr) {
            this.matnr = matnr;
            }

    public String getTxz01(){
            return txz01;
            }

        public void setTxz01(String txz01) {
            this.txz01 = txz01;
            }

    public String getWerkst(){
            return werkst;
            }

        public void setWerkst(String werkst) {
            this.werkst = werkst;
            }

    public String getWerks(){
            return werks;
            }

        public void setWerks(String werks) {
            this.werks = werks;
            }

    public String getLgort(){
            return lgort;
            }

        public void setLgort(String lgort) {
            this.lgort = lgort;
            }

    public String getLgortName(){
            return lgortName;
            }

        public void setLgortName(String lgortName) {
            this.lgortName = lgortName;
            }

    public BigDecimal getMenge(){
            return menge;
            }

        public void setMenge(BigDecimal menge) {
            this.menge = menge;
            }

    public String getMeins(){
            return meins;
            }

        public void setMeins(String meins) {
            this.meins = meins;
            }

    public String getMseht(){
            return mseht;
            }

        public void setMseht(String mseht) {
            this.mseht = mseht;
            }

    public BigDecimal getNetpr(){
            return netpr;
            }

        public void setNetpr(BigDecimal netpr) {
            this.netpr = netpr;
            }

    public Date getEindt(){
            return eindt;
            }

        public void setEindt(Date eindt) {
            this.eindt = eindt;
            }

    public Date getBedat(){
            return bedat;
            }

        public void setBedat(Date bedat) {
            this.bedat = bedat;
            }

    public String getBsart(){
            return bsart;
            }

        public void setBsart(String bsart) {
            this.bsart = bsart;
            }

    public BigDecimal getgMenge(){
            return gMenge;
            }

        public void setgMenge(BigDecimal gMenge) {
            this.gMenge = gMenge;
            }

    public String getCharge(){
            return charge;
            }

        public void setCharge(String charge) {
            this.charge = charge;
            }

    public Date getVfdat(){
            return vfdat;
            }

        public void setVfdat(Date vfdat) {
            this.vfdat = vfdat;
            }

    public Date getHsdat(){
            return hsdat;
            }

        public void setHsdat(Date hsdat) {
            this.hsdat = hsdat;
            }

    public String getFphm(){
            return fphm;
            }

        public void setFphm(String fphm) {
            this.fphm = fphm;
            }

    public BigDecimal getFpjr(){
            return fpjr;
            }

        public void setFpjr(BigDecimal fpjr) {
            this.fpjr = fpjr;
            }

    public Date getFprq(){
            return fprq;
            }

        public void setFprq(Date fprq) {
            this.fprq = fprq;
            }

    public String getBaseId(){
            return baseId;
            }

        public void setBaseId(String baseId) {
            this.baseId = baseId;
            }

    public Long getId(){
            return id;
            }

        public void setId(Long id) {
            this.id = id;
            }

    public Integer getStatus(){
            return status;
            }

        public void setStatus(Integer status) {
            this.status = status;
            }

    public String getFpbm(){
            return fpbm;
            }

        public void setFpbm(String fpbm) {
            this.fpbm = fpbm;
            }

    public String getGysaccount(){
            return gysaccount;
            }

        public void setGysaccount(String gysaccount) {
            this.gysaccount = gysaccount;
            }

    public String getGysname(){
            return gysname;
            }

        public void setGysname(String gysname) {
            this.gysname = gysname;
            }

    public BigDecimal getPkgAmount(){
            return pkgAmount;
            }

        public void setPkgAmount(BigDecimal pkgAmount) {
            this.pkgAmount = pkgAmount;
            }

    public BigDecimal getPkgNumber(){
            return pkgNumber;
            }

        public void setPkgNumber(BigDecimal pkgNumber) {
            this.pkgNumber = pkgNumber;
            }

    public String getUninformedState(){
            return uninformedState;
            }

        public void setUninformedState(String uninformedState) {
            this.uninformedState = uninformedState;
            }

    public String getInformedState(){
            return informedState;
            }

        public void setInformedState(String informedState) {
            this.informedState = informedState;
            }

    public String getBsartD(){
            return bsartD;
            }

        public void setBsartD(String bsartD) {
            this.bsartD = bsartD;
            }

    public String getLinkPerson(){
            return linkPerson;
            }

        public void setLinkPerson(String linkPerson) {
            this.linkPerson = linkPerson;
            }

    public String getSendDepart(){
            return sendDepart;
            }

        public void setSendDepart(String sendDepart) {
            this.sendDepart = sendDepart;
            }

    public String getLinkTelephone(){
            return linkTelephone;
            }

        public void setLinkTelephone(String linkTelephone) {
            this.linkTelephone = linkTelephone;
            }

    public String getSendOrderCode(){
            return sendOrderCode;
            }

        public void setSendOrderCode(String sendOrderCode) {
            this.sendOrderCode = sendOrderCode;
            }

    public String getOutCause(){
            return outCause;
            }

        public void setOutCause(String outCause) {
            this.outCause = outCause;
            }

    public Date getOutDate(){
            return outDate;
            }

        public void setOutDate(Date outDate) {
            this.outDate = outDate;
            }

    public Integer getIsDeletemark(){
            return isDeletemark;
            }

        public void setIsDeletemark(Integer isDeletemark) {
            this.isDeletemark = isDeletemark;
            }

    public Date getCreateTime(){
            return createTime;
            }

        public void setCreateTime(Date createTime) {
            this.createTime = createTime;
            }

    public Date getModifyTime(){
            return modifyTime;
            }

        public void setModifyTime(Date modifyTime) {
            this.modifyTime = modifyTime;
            }

    public Long getCreateUserId(){
            return createUserId;
            }

        public void setCreateUserId(Long createUserId) {
            this.createUserId = createUserId;
            }

    public Long getModifyUserId(){
            return modifyUserId;
            }

        public void setModifyUserId(Long modifyUserId) {
            this.modifyUserId = modifyUserId;
            }

    public BigDecimal getDoneMenge() {
            return doneMenge;
    }

    public void setDoneMenge(BigDecimal doneMenge) {
        this.doneMenge = doneMenge;
    }

    public static final String EBELN ="ebeln" ;

    public static final String EBELP ="ebelp" ;

    public static final String MATNR ="matnr" ;

    public static final String TXZ01 ="txz01" ;

    public static final String WERKST ="werkst" ;

    public static final String WERKS ="werks" ;

    public static final String LGORT ="lgort" ;

    public static final String LGORTNAME ="lgortName" ;

    public static final String MENGE ="menge" ;

    public static final String MEINS ="meins" ;

    public static final String MSEHT ="mseht" ;

    public static final String NETPR ="netpr" ;

    public static final String EINDT ="eindt" ;

    public static final String BEDAT ="bedat" ;

    public static final String BSART ="bsart" ;

    public static final String G_MENGE ="G_MENGE" ;

    public static final String CHARGE ="CHARGE" ;

    public static final String VFDAT ="VFDAT" ;

    public static final String HSDAT ="HSDAT" ;

    public static final String FPHM ="FPHM" ;

    public static final String FPJR ="FPJR" ;

    public static final String FPRQ ="FPRQ" ;

    public static final String BASE_ID ="BASE_ID" ;

    public static final String ID ="ID" ;

    public static final String STATUS ="STATUS" ;

    public static final String FPBM ="FPBM" ;

    public static final String GYSACCOUNT ="GYSACCOUNT" ;

    public static final String GYSNAME ="GYSNAME" ;

    public static final String PKG_AMOUNT ="PKG_AMOUNT" ;

    public static final String PKG_NUMBER ="PKG_NUMBER" ;

    public static final String UNINFORMED_STATE ="UNINFORMED_STATE" ;

    public static final String INFORMED_STATE ="INFORMED_STATE" ;

    public static final String BSART_D ="BSART_D" ;

    public static final String LINK_PERSON ="LINK_PERSON" ;

    public static final String SEND_DEPART ="SEND_DEPART" ;

    public static final String LINK_TELEPHONE ="LINK_TELEPHONE" ;

    public static final String SEND_ORDER_CODE ="SEND_ORDER_CODE" ;

    public static final String OUT_CAUSE ="OUT_CAUSE" ;

    public static final String OUT_DATE ="OUT_DATE" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;



@Override
public String toString() {
        return "ViewSupplyplan{" +
                ", ebeln=" + ebeln +
                ", ebelp=" + ebelp +
                ", matnr=" + matnr +
                ", txz01=" + txz01 +
                ", werkst=" + werkst +
                ", werks=" + werks +
                ", lgort=" + lgort +
                ", lgortName=" + lgortName +
                ", menge=" + menge +
                ", meins=" + meins +
                ", mseht=" + mseht +
                ", netpr=" + netpr +
                ", eindt=" + eindt +
                ", bedat=" + bedat +
                ", bsart=" + bsart +
                ", gMenge=" + gMenge +
                ", charge=" + charge +
                ", vfdat=" + vfdat +
                ", hsdat=" + hsdat +
                ", fphm=" + fphm +
                ", fpjr=" + fpjr +
                ", fprq=" + fprq +
                ", baseId=" + baseId +
                ", id=" + id +
                ", status=" + status +
                ", fpbm=" + fpbm +
                ", gysaccount=" + gysaccount +
                ", gysname=" + gysname +
                ", pkgAmount=" + pkgAmount +
                ", pkgNumber=" + pkgNumber +
                ", uninformedState=" + uninformedState +
                ", informedState=" + informedState +
                ", bsartD=" + bsartD +
                ", linkPerson=" + linkPerson +
                ", sendDepart=" + sendDepart +
                ", linkTelephone=" + linkTelephone +
                ", sendOrderCode=" + sendOrderCode +
                ", outCause=" + outCause +
                ", outDate=" + outDate +
                ", isDeletemark=" + isDeletemark +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", createUserId=" + createUserId +
                ", modifyUserId=" + modifyUserId +
        "}";
        }
        }