package cc.mrbird.febs.scm.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;

/**
 * <p>
 * 送货单
 * </p>
 *
 * @author viki
 * @since 2019-11-26
 */

@Excel("scm_b_sendinfo")
public class ScmBSendinfo implements Serializable{

private static final long serialVersionUID=1L;

    /**
     * 主键
     */
                    @TableId(value = "ID" , type = IdType.AUTO)
                    @ExcelField(value ="主键")
private Long id;
    /**
     * 编码
     */
    @TableField("CODE")
            @ExcelField(value ="编码")
private String code;
    /**
     * 姓名
     */
    @TableField("NAME")
            @ExcelField(value ="姓名")
private String name;
    /**
     * 物料ID
     */
    @TableField("MATNR")
            @ExcelField(value ="物料ID")
private String matnr;
    /**
     * 供应计划ID
     */
    @TableField("GYJH")
            @ExcelField(value ="供应计划ID")
private Long gyjh;
    /**
     * 计量单位编码
     */
    @TableField("MEINS")
            @ExcelField(value ="计量单位编码")
private String meins;
    /**
     * 计量单位
     */
    @TableField("MSEHT")
            @ExcelField(value ="计量单位")
private String mseht;
    /**
     * 物料描述
     */
    @TableField("TXZ01")
            @ExcelField(value ="物料描述")
private String txz01;
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
     * 商品条码
     */
    @TableField("MATER_CODE")
            @ExcelField(value ="商品条码")
private String materCode;
    /**
     * 送货清单号
     */
    @TableField("SEND_ORDER_CODE")
            @ExcelField(value ="送货清单号")
private String sendOrderCode;
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
    /**
     * 供应商名称
     */
    @TableField("GYSNAME")
            @ExcelField(value ="供应商名称")
private String gysname;
    /**
     * 供应商账号
     */
    @TableField("GYSACCOUNT")
            @ExcelField(value ="供应商账号")
private String gysaccount;
    /**
     * 送货数量
     */
    @TableField("AMOUNT")
            @ExcelField(value ="送货数量")
private BigDecimal amount;
    /**
     * 送货金额
     */
    @TableField("MONEY")
            @ExcelField(value ="送货金额")
private BigDecimal money;

    /**
     * 单价
     */
    @TableField("PRICE")
    @ExcelField(value ="单价")
    private BigDecimal price;

    /**
     * 院区ID
     */
    @TableField("WERKS")
            @ExcelField(value ="院区ID")
private String werks;
    /**
     * 院区名称
     */
    @TableField("WERKST")
            @ExcelField(value ="院区名称")
private String werkst;


    public Long getId(){
            return id;
            }

        public void setId(Long id) {
            this.id = id;
            }

    public String getCode(){
            return code;
            }

        public void setCode(String code) {
            this.code = code;
            }

    public String getName(){
            return name;
            }

        public void setName(String name) {
            this.name = name;
            }

    public String getMatnr(){
            return matnr;
            }

        public void setMatnr(String matnr) {
            this.matnr = matnr;
            }

    public Long getGyjh(){
            return gyjh;
            }

        public void setGyjh(Long gyjh) {
            this.gyjh = gyjh;
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

    public String getTxz01(){
            return txz01;
            }

        public void setTxz01(String txz01) {
            this.txz01 = txz01;
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

    public String getMaterCode(){
            return materCode;
            }

        public void setMaterCode(String materCode) {
            this.materCode = materCode;
            }

    public String getSendOrderCode(){
            return sendOrderCode;
            }

        public void setSendOrderCode(String sendOrderCode) {
            this.sendOrderCode = sendOrderCode;
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

    public String getGysname(){
            return gysname;
            }

        public void setGysname(String gysname) {
            this.gysname = gysname;
            }

    public String getGysaccount(){
            return gysaccount;
            }

        public void setGysaccount(String gysaccount) {
            this.gysaccount = gysaccount;
            }

    public BigDecimal getAmount(){
            return amount;
            }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
            }


    public BigDecimal getMoney(){
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getPrice(){
            return price;
            }

        public void setPrice(BigDecimal price) {
            this.price = price;
            }

    public String getWerks(){
            return werks;
            }

        public void setWerks(String werks) {
            this.werks = werks;
            }

    public String getWerkst(){
            return werkst;
            }

        public void setWerkst(String werkst) {
            this.werkst = werkst;
            }

    public static final String ID ="ID" ;

    public static final String CODE ="CODE" ;

    public static final String NAME ="NAME" ;

    public static final String MATNR ="MATNR" ;

    public static final String GYJH ="GYJH" ;

    public static final String MEINS ="MEINS" ;

    public static final String MSEHT ="MSEHT" ;

    public static final String TXZ01 ="TXZ01" ;

    public static final String LINK_PERSON ="LINK_PERSON" ;

    public static final String SEND_DEPART ="SEND_DEPART" ;

    public static final String LINK_TELEPHONE ="LINK_TELEPHONE" ;

    public static final String MATER_CODE ="MATER_CODE" ;

    public static final String SEND_ORDER_CODE ="SEND_ORDER_CODE" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

    public static final String GYSNAME ="GYSNAME" ;

    public static final String GYSACCOUNT ="GYSACCOUNT" ;

    public static final String AMOUNT ="AMOUNT" ;

    public static final String MONEY ="MONEY" ;

    public static final String PRICE ="PRICE" ;

    public static final String WERKS ="WERKS" ;

    public static final String WERKST ="WERKST" ;

@Override
public String toString() {
        return "ScmBSendinfo{" +
                ", id=" + id +
                ", code=" + code +
                ", name=" + name +
                ", matnr=" + matnr +
                ", gyjh=" + gyjh +
                ", meins=" + meins +
                ", mseht=" + mseht +
                ", txz01=" + txz01 +
                ", linkPerson=" + linkPerson +
                ", sendDepart=" + sendDepart +
                ", linkTelephone=" + linkTelephone +
                ", materCode=" + materCode +
                ", sendOrderCode=" + sendOrderCode +
                ", isDeletemark=" + isDeletemark +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", createUserId=" + createUserId +
                ", modifyUserId=" + modifyUserId +
                ", gysname=" + gysname +
                ", gysaccount=" + gysaccount +
                ", amount=" + amount +
                ", money=" + money +
                ", price=" + price +
                ", werks=" + werks +
                ", werkst=" + werkst +
        "}";
        }
        }