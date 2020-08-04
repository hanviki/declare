package cc.mrbird.febs.scm.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;

/**
 * <p>
 * 供应商表
 * </p>
 *
 * @author viki
 * @since 2019-11-14
 */

@Excel("scm_d_vendor")
public class ScmDVendor implements Serializable{

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
     * 地址
     */
    @TableField("ADDRESS")
    @ExcelField(value ="地址")
    private String address;
    /**
     * 法人代表
     */
    @TableField("LAW_PERSON")
    @ExcelField(value ="法人代表")
    private String lawPerson;
    /**
     * 联系人
     */
    @TableField("LINK_PERSON")
    @ExcelField(value ="联系人")
    private String linkPerson;
    /**
     * 联系电话
     */
    @TableField("PHONE")
    @ExcelField(value ="联系电话")
    private String phone;
    /**
     * 邮件
     */
    @TableField("EMAIL")
    @ExcelField(value ="邮件")
    private String email;

    /**
     * beizhu
     */
    @TableField("NOTE")
    @ExcelField(value ="备注")
    private String note;

    /**
     * 审核原因
     */
    @TableField("audit_cause")
    @ExcelField(value ="审核原因")
    private String auditCause;
    /**
     * 状态
     */
    @TableField("STATE")
    @ExcelField(value ="状态")
    private Integer state;
    /**
     * 供应商类别（0是药品1是物资）
     */
    @TableField("LB")
    @ExcelField(value ="供应商类别（0是药品1是物资）")
    private Integer lb;
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
     * 接口是否可用状态
     */
    @TableField("JIEKOUSTATE")
    @ExcelField(value ="状态")
    private Integer jiekouState;

    /**
     * 资质文件上传限制
     */
    @TableField("FILESTATE")
    @ExcelField(value ="状态")
    private Integer fileState;

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

    public String getNote(){
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    public String getAuditCause(){
        return auditCause;
    }

    public void setAuditCause(String auditCause) {
        this.auditCause = auditCause;
    }


    public String getName(){
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getAddress(){
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLawPerson(){
        return lawPerson;
    }

    public void setLawPerson(String lawPerson) {
        this.lawPerson = lawPerson;
    }

    public String getLinkPerson(){
        return linkPerson;
    }

    public void setLinkPerson(String linkPerson) {
        this.linkPerson = linkPerson;
    }

    public String getPhone(){
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getState(){
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getJieKouState(){
        return jiekouState;
    }

    public void setJieKouState(Integer jieKouState) {
        this.jiekouState = jieKouState;
    }

    public Integer getFileState(){
        return fileState;
    }

    public void setFileState(Integer fileState) {
        this.fileState = fileState;
    }

    public Integer getLb(){
        return lb;
    }

    public void setLb(Integer lb) {
        this.lb = lb;
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

    public static final String ADDRESS ="ADDRESS" ;

    public static final String LAW_PERSON ="LAW_PERSON" ;

    public static final String LINK_PERSON ="LINK_PERSON" ;

    public static final String PHONE ="PHONE" ;

    public static final String EMAIL ="EMAIL" ;

    public static final String STATE ="STATE" ;

    public static final String LB ="LB" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

    @Override
    public String toString() {
        return "ScmDVendor{" +
                ", id=" + id +
                ", code=" + code +
                ", name=" + name +
                ", address=" + address +
                ", lawPerson=" + lawPerson +
                ", linkPerson=" + linkPerson +
                ", phone=" + phone +
                ", email=" + email +
                ", state=" + state +
                ", lb=" + lb +
                ", isDeletemark=" + isDeletemark +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", createUserId=" + createUserId +
                ", modifyUserId=" + modifyUserId +
                "}";
    }
}