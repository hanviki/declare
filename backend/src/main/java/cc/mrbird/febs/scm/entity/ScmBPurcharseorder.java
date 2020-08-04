package cc.mrbird.febs.scm.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * SCM_B_PURCHARSEORDER
 * </p>
 *
 * @author viki
 * @since 2019-11-21
 */

@Excel("scm_b_purcharseorder")
public class ScmBPurcharseorder implements Serializable{

private static final long serialVersionUID=1L;

    /**
     * 主键
     */
                    @TableId("ID")
                    @ExcelField(value ="主键")
private String id;
    /**
     * 编码
     */
    @TableField("CODE")
            @ExcelField(value ="编码")
private String code;
    /**
     * 名字
     */
    @TableField("NAME")
            @ExcelField(value ="名字")
private String name;
    /**
     * 名字
     */
    @TableField("GYSNAME")
    @ExcelField(value ="供应商名称")
    private String gysname;
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
     * 供应计划号
     */
            @ExcelField(value ="供应计划号")
private String lifnr;
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
            @DateTimeFormat(pattern = "yyyy-MM-dd")
private Date eindt;
    /**
     * 订单结束时间
     */
            @ExcelField(value ="订单结束时间")
            @DateTimeFormat(pattern = "yyyy-MM-dd")
private Date bedat;
    /**
     * 状态
     */
            @ExcelField(value ="状态")
private Integer status;
    /**
     * 备注
     */
            @ExcelField(value ="备注")
private String comments;
    /**
     * 供应数量
     */
            @ExcelField(value ="供应数量")
private BigDecimal allmenge;
    /**
     * 收货数量
     */
            @ExcelField(value ="收货数量")
private BigDecimal suremenge;
    /**
     * 类型
     */
            @ExcelField(value ="类型")
private String bsart;
    /**
     * 送货部门ID
     */
            @ExcelField(value ="送货部门ID")
private String sendDeaprtId;
    /**
     * 送货部门名称
     */
            @ExcelField(value ="送货部门名称")
private String sendDeaprtName;
    /**
     * 联系人
     */
            @ExcelField(value ="联系人")
private String sendDeaprtContact;
    /**
     * 联系电话
     */
            @ExcelField(value ="联系电话")
private String sendDeaprtPhone;
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

    @TableField(exist = false)
    public ScmBSupplyplan[] innerData;
    /**
     * 修改人
     */
    @TableField("MODIFY_USER_ID")
            @ExcelField(value ="修改人")
private Long modifyUserId;


    public String getId(){
            return id;
            }

        public void setId(String id) {
            this.id = id;
            }

    public String getCode(){
            return code;
            }

        public void setCode(String code) {
            this.code = code;
            }

    public String getGysname(){
            return gysname;
            }

        public void setGysname(String gysname) {
            this.gysname = gysname;
            }

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

    public String getLifnr(){
            return lifnr;
            }

        public void setLifnr(String lifnr) {
            this.lifnr = lifnr;
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

    public Integer getStatus(){
            return status;
            }

        public void setStatus(Integer status) {
            this.status = status;
            }

    public String getComments(){
            return comments;
            }

        public void setComments(String comments) {
            this.comments = comments;
            }

    public BigDecimal getAllmenge(){
            return allmenge;
            }

        public void setAllmenge(BigDecimal allmenge) {
            this.allmenge = allmenge;
            }

    public BigDecimal getSuremenge(){
            return suremenge;
            }

        public void setSuremenge(BigDecimal suremenge) {
            this.suremenge = suremenge;
            }

    public String getBsart(){
            return bsart;
            }

        public void setBsart(String bsart) {
            this.bsart = bsart;
            }

    public String getSendDeaprtId(){
            return sendDeaprtId;
            }

        public void setSendDeaprtId(String sendDeaprtId) {
            this.sendDeaprtId = sendDeaprtId;
            }

    public String getSendDeaprtName(){
            return sendDeaprtName;
            }

        public void setSendDeaprtName(String sendDeaprtName) {
            this.sendDeaprtName = sendDeaprtName;
            }

    public String getSendDeaprtContact(){
            return sendDeaprtContact;
            }

        public void setSendDeaprtContact(String sendDeaprtContact) {
            this.sendDeaprtContact = sendDeaprtContact;
            }

    public String getSendDeaprtPhone(){
            return sendDeaprtPhone;
            }

        public void setSendDeaprtPhone(String sendDeaprtPhone) {
            this.sendDeaprtPhone = sendDeaprtPhone;
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

    public static final String ID ="ID" ;

    public static final String CODE ="CODE" ;

    public static final String NAME ="NAME" ;

    public static final String EBELN ="ebeln" ;

    public static final String EBELP ="ebelp" ;

    public static final String LIFNR ="lifnr" ;

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

    public static final String STATUS ="status" ;

    public static final String COMMENTS ="comments" ;

    public static final String ALLMENGE ="allmenge" ;

    public static final String SUREMENGE ="suremenge" ;

    public static final String BSART ="bsart" ;

    public static final String SEND_DEAPRT_ID ="send_deaprt_id" ;

    public static final String SEND_DEAPRT_NAME ="send_deaprt_name" ;

    public static final String SEND_DEAPRT_CONTACT ="send_deaprt_contact" ;

    public static final String SEND_DEAPRT_PHONE ="send_deaprt_phone" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

@Override
public String toString() {
        return "ScmBPurcharseorder{" +
                ", id=" + id +
                ", code=" + code +
                ", name=" + name +
                ", ebeln=" + ebeln +
                ", ebelp=" + ebelp +
                ", lifnr=" + lifnr +
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
                ", status=" + status +
                ", comments=" + comments +
                ", allmenge=" + allmenge +
                ", suremenge=" + suremenge +
                ", bsart=" + bsart +
                ", sendDeaprtId=" + sendDeaprtId +
                ", sendDeaprtName=" + sendDeaprtName +
                ", sendDeaprtContact=" + sendDeaprtContact +
                ", sendDeaprtPhone=" + sendDeaprtPhone +
                ", isDeletemark=" + isDeletemark +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", createUserId=" + createUserId +
                ", modifyUserId=" + modifyUserId +
        "}";
        }
        }