package cc.mrbird.febs.scm.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.util.Date;

import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;

/**
 * <p>
 * 资质文件记录表
 * </p>
 *
 * @author viki
 * @since 2019-11-21
 */

@Excel("scm_b_gys_mater_pic")
public class ScmBGysMaterPic implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("ID")
    @ExcelField(value = "主键")
    private String id;
    /**
     * 编码
     */
    @TableField("CODE")
    @ExcelField(value = "编码")
    private String code;
    /**
     * 姓名
     */
    @TableField("NAME")
    @ExcelField(value = "姓名")
    private String name;
    /**
     * 供应商账号
     */
    @TableField("GYSACCOUNT")
    @ExcelField(value = "供应商账号")
    private String gysaccount;
    /**
     * 药品编码ID
     */
    @TableField("MATER_ID")
    @ExcelField(value = "药品编码ID")
    private String materId;

    /**
     * 药品编码
     */
    @TableField("MATNR")
    @ExcelField(value = "药品编码")
    private String matnr;
    /**
     * 批次号
     */
    @TableField("CHARGE")
    @ExcelField(value = "批次号")
    private String charge;
    /**
     * 备注
     */
    @TableField("COMMENTS")
    @ExcelField(value = "备注")
    private String comments;
    /**
     * 状态
     */
    @TableField("STATE")
    @ExcelField(value = "状态")
    private Integer state;
    /**
     * 附件ID
     */
    @TableField("FILE_ID")
    @ExcelField(value = "附件ID")
    private String fileId;

    /**
     * 院区
     */
    @TableField("werks")
    @ExcelField(value ="院区ID")
    private String werks;

    /**
     * 库房
     */
    @TableField("lgort")
    @ExcelField(value ="库房ID")
    private String lgort;

    /**
     * 院区
     */
    @TableField("werkt")
    @ExcelField(value ="院区")
    private String werkt;

    /**
     * 库房
     */
    @TableField("lgortName")
    @ExcelField(value ="库房")
    private String lgortName;
    /**
     * 是否删除
     */
    @TableField("IS_DELETEMARK")
    @ExcelField(value = "是否删除")
    private Integer isDeletemark;
    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    @ExcelField(value = "创建时间")
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField("MODIFY_TIME")
    @ExcelField(value = "修改时间")
    private Date modifyTime;
    /**
     * 创建人
     */
    @TableField("CREATE_USER_ID")
    @ExcelField(value = "创建人")
    private Long createUserId;
    /**
     * 修改人
     */
    @TableField("MODIFY_USER_ID")
    @ExcelField(value = "修改人")
    private Long modifyUserId;

    /**
     * 单位描述
     */
    @TableField("MSEHT")
    @ExcelField(value = "单位描述")
    private String mseht;
    /**
     * 物料描述
     */
    @TableField("TXZ01")
    @ExcelField(value = "物料描述")
    private String txz01;
    /**
     * 生产厂家
     */
    @TableField("PRODUCE_AREA")
    @ExcelField(value = "生产厂家")
    private String produceArea;
    /**
     * 规格
     */
    @TableField("SPEC")
    @ExcelField(value = "规格")
    private String spec;

    @TableField("AUDITCAUSE")
    private  String auditCause;

    private  transient  String userid;

    public String getAuditCause() {
        return auditCause;
    }

    public void setAuditCause(String auditCause) {
        this.auditCause = auditCause;
    }
    /**
     * 拼音码
     */
    @TableField("SPELL_CODE")
    @ExcelField(value = "拼音码")
    private String spellCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setLgort(String lgort){
        this.lgort = lgort;
    }
    public String getLgort(){
       return this.lgort;
    }

    public void setLgortName(String lgortName){
        this.lgortName = lgortName;
    }
    public String getLgortName(){
        return this.lgortName;
    }
    public void setWerks(String werks){
        this.werks = werks;
    }

    public String getWerks(){
        return this.werks;
    }
    public void setWerkt(String werkt){
        this.werkt = werkt ;
    }
    public String getWerkt(){
        return this.werkt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGysaccount() {
        return gysaccount;
    }

    public void setGysaccount(String gysaccount) {
        this.gysaccount = gysaccount;
    }

    public String getSpellCode() {
        return spellCode;
    }

    public void setSpellCode(String spellCode) {
        this.spellCode = spellCode;
    }


    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr;
    }

    public String getMaterId() {
        return materId;
    }

    public void setMaterId(String materId) {
        this.materId = materId;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getMseht() {
        return mseht;
    }

    public void setMseht(String mseht) {
        this.mseht = mseht;
    }

    public String getTxz01() {
        return txz01;
    }

    public void setTxz01(String txz01) {
        this.txz01 = txz01;
    }

    public String getProduceArea() {
        return produceArea;
    }

    public void setProduceArea(String produceArea) {
        this.produceArea = produceArea;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public Integer getIsDeletemark() {
        return isDeletemark;
    }

    public void setIsDeletemark(Integer isDeletemark) {
        this.isDeletemark = isDeletemark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Long getModifyUserId() {
        return modifyUserId;
    }

    public void setModifyUserId(Long modifyUserId) {
        this.modifyUserId = modifyUserId;
    }

    public static final String ID = "ID";

    public static final String CODE = "CODE";

    public static final String NAME = "NAME";

    public static final String GYSACCOUNT = "GYSACCOUNT";

    public static final String MATER_ID = "MATER_ID";

    public static final String CHARGE = "CHARGE";

    public static final String COMMENTS = "COMMENTS";

    public static final String STATE = "STATE";

    public static final String FILE_ID = "FILE_ID";

    public static final String IS_DELETEMARK = "IS_DELETEMARK";

    public static final String CREATE_TIME = "CREATE_TIME";

    public static final String MODIFY_TIME = "MODIFY_TIME";

    public static final String CREATE_USER_ID = "CREATE_USER_ID";

    public static final String MODIFY_USER_ID = "MODIFY_USER_ID";

    @Override
    public String toString() {
        return "ScmBGysMaterPic{" +
                ", id=" + id +
                ", code=" + code +
                ", name=" + name +
                ", gysaccount=" + gysaccount +
                ", materId=" + materId +
                ", charge=" + charge +
                ", comments=" + comments +
                ", state=" + state +
                ", fileId=" + fileId +
                ", isDeletemark=" + isDeletemark +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", createUserId=" + createUserId +
                ", modifyUserId=" + modifyUserId +
                "}";
    }
}