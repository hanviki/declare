package cc.mrbird.febs.scm.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 供应商对应的资质文件表
 * </p>
 *
 * @author viki
 * @since 2019-11-14
 */

@Excel("scm_d_vendor_d")
public class ScmDVendorD implements Serializable{

private static final long serialVersionUID=1L;

    /**
     * 主键
     */
                    @TableId("ID")
                    @ExcelField(value ="主键")
private String id;
    /**
     * 资质类别
     */
    @TableField("TITLE")
            @ExcelField(value ="资质类别")
private String title;
    /**
     * 资质名称
     */
    @TableField("FILENAME")
            @ExcelField(value ="资质名称")
private String filename;
    /**
     * 有效期开始时间
     */
    @TableField("VALID_DATESTART")
            @ExcelField(value ="有效期开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
private Date validDatestart;
    /**
     * 有效期
     */
    @TableField("VALID_DATE")
            @ExcelField(value ="有效期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
private Date validDate;
    /**
     * 是否有效
     */
    @TableField("IS_VALID")
            @ExcelField(value ="是否有效")
private Boolean isValid;
    /**
     * 是否删除
     */
    @TableField("IS_DELETEMARK")
            @ExcelField(value ="是否删除")
private Boolean isDeletemark;
    /**
     * 附件ID
     */
    @TableField("FILE_ID")
            @ExcelField(value ="附件ID")
private String fileId;
    /**
     * 附件对应值
     */
    @TableField("FILE_INDEX")
            @ExcelField(value ="附件对应值")
private Integer fileIndex;
    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
            @ExcelField(value ="创建时间")
private Date createTime;
    /**
     * beizhu
     */
    @TableField("NOTED")
    @ExcelField(value ="备注")
    private String noted;
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
     * 供应商主表ID
     */
    @TableField("BASE_ID")
            @ExcelField(value ="供应商主表ID")
private String baseId;
    @TableField(exist = false)
    public  ComFile attachfile=new ComFile();



    public String getId(){
        return id;
    }

        public void setId(String id) {
            this.id = id;
            }

    public String getTitle(){
            return title;
            }

        public void setTitle(String title) {
            this.title = title;
            }

    public String getNoted(){
            return noted;
            }

        public void setNoted(String noted) {
            this.noted = noted;
            }

    public String getFilename(){
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Date getValidDatestart(){
            return validDatestart;
            }

        public void setValidDatestart(Date validDatestart) {
            this.validDatestart = validDatestart;
            }

    public Date getValidDate(){
            return validDate;
            }

        public void setValidDate(Date validDate) {
            this.validDate = validDate;
            }

    public Boolean getValid(){
            return isValid;
            }

        public void setValid(Boolean isValid) {
            this.isValid = isValid;
            }

    public Boolean getDeletemark(){
            return isDeletemark;
            }

        public void setDeletemark(Boolean isDeletemark) {
            this.isDeletemark = isDeletemark;
            }

    public String getFileId(){
            return fileId;
            }

        public void setFileId(String fileId) {
            this.fileId = fileId;
            }

    public Integer getFileIndex(){
            return fileIndex;
            }

        public void setFileIndex(Integer fileIndex) {
            this.fileIndex = fileIndex;
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

    public String getBaseId(){
            return baseId;
            }

        public void setBaseId(String baseId) {
            this.baseId = baseId;
            }

    public static final String ID ="ID" ;

    public static final String TITLE ="TITLE" ;

    public static final String FILENAME ="FILENAME" ;

    public static final String VALID_DATESTART ="VALID_DATESTART" ;

    public static final String VALID_DATE ="VALID_DATE" ;

    public static final String IS_VALID ="IS_VALID" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String FILE_ID ="FILE_ID" ;

    public static final String FILE_INDEX ="FILE_INDEX" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

    public static final String BASE_ID ="BASE_ID" ;

@Override
public String toString() {
        return "ScmDVendorD{" +
                ", id=" + id +
                ", title=" + title +
                ", filename=" + filename +
                ", validDatestart=" + validDatestart +
                ", validDate=" + validDate +
                ", isValid=" + isValid +
                ", isDeletemark=" + isDeletemark +
                ", fileId=" + fileId +
                ", fileIndex=" + fileIndex +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", createUserId=" + createUserId +
                ", modifyUserId=" + modifyUserId +
                ", baseId=" + baseId +
        "}";
        }


        }