package cc.mrbird.febs.scm.entity;

import java.math.BigDecimal;

import cc.mrbird.febs.common.annotation.Log;
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
 * 药品的送货清单
 * </p>
 *
 * @author viki
 * @since 2019-11-26
 */

@Excel("scm_b_sendorder")
public class ScmBSendorder implements Serializable{

private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value="ID",type = IdType.AUTO)
                    @ExcelField(value ="主键")
private Long id;

    @TableField(exist = false)
    public String supplyPlanIds;
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

    public String getWerks() {
        return werks;
    }

    public void setWerks(String werks) {
        this.werks = werks;
    }

    public String getWerkst() {
        return werkst;
    }

    public void setWerkst(String werkst) {
        this.werkst = werkst;
    }

    public String getLgort() {
        return lgort;
    }

    public void setLgort(String lgort) {
        this.lgort = lgort;
    }

    public String getLgortname() {
        return lgortname;
    }

    public void setLgortname(String lgortname) {
        this.lgortname = lgortname;
    }

    private String werks;
    private String werkst;
    private String lgort;
    private String lgortname;
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
     * 备注
     */
    @TableField("COMMENTS")
            @ExcelField(value ="备注")
private String comments;
    /**
     * 状态
     */
    @TableField("Status")
            @ExcelField(value ="状态")
private Integer Status;

    /**
     * 送货单类型 0是药品 1是物资
     */
    @TableField("BSART")
    @ExcelField(value ="送货单类型")
    private String bsart;


    public Date getFprq() {
        return fprq;
    }

    public void setFprq(Date fprq) {
        this.fprq = fprq;
    }

    /**
     * 送货时间
     */
    @TableField("FPRQ")
    @ExcelField(value ="发票日期")
    private Date fprq;

    /**
     * 送货时间
     */
    @TableField("SEND_DATE")
    @ExcelField(value ="送货日期")
    private Date sendDate;
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



    @TableField(exist = false)
    public ViewSupplyplan[] innerData;


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

    public String getBsart(){
            return bsart;
            }

        public void setBsart(String bsart) {
            this.bsart = bsart;
            }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getComments(){
            return comments;
            }

        public void setComments(String comments) {
            this.comments = comments;
            }

    public Integer getStatus(){
            return Status;
            }

        public void setStatus(Integer Status) {
            this.Status = Status;
            }

    public Integer getIsDeletemark(){
            return isDeletemark;
            }

        public void setIsDeletemark(Integer isDeletemark) {
            this.isDeletemark = isDeletemark;
            }

    public Date getSendDate(){
            return sendDate;
            }

        public void setSendDate(Date sendDate) {
            this.sendDate = sendDate;
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

    public  static  final  String BSART="BSART";

    public static final String NAME ="NAME" ;

    public static final String FPHM ="FPHM" ;

    public static final String FPJR ="FPJR" ;

    public static final String GYSACCOUNT ="GYSACCOUNT" ;

    public static final String GYSNAME ="GYSNAME" ;

    public static final String COMMENTS ="COMMENTS" ;

    public static final String STATUS ="Status" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

@Override
public String toString() {
        return "ScmBSendorder{" +
                ", id=" + id +
                ", code=" + code +
                ", name=" + name +
                ", fphm=" + fphm +
                ", fpjr=" + fpjr +
                ", gysaccount=" + gysaccount +
                ", gysname=" + gysname +
                ", comments=" + comments +
                ", Status=" + Status +
                ", bsart=" + bsart +
                ", sendDate=" + sendDate +
                ", isDeletemark=" + isDeletemark +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", createUserId=" + createUserId +
                ", modifyUserId=" + modifyUserId +
        "}";
        }
        }