package cc.mrbird.febs.scm.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;

/**
 * <p>
 * 附件
 * </p>
 *
 * @author viki
 * @since 2019-11-14
 */

@Excel("com_file")
public class ComFile implements Serializable{

private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableField("ID")
            @ExcelField(value ="主键")
private String id;
    /**
     * 文件名称
     */
    @TableField("CLIENT_NAME")
            @ExcelField(value ="文件名称")
private String clientName;
    /**
     * 服务器地址
     */
    @TableField("SERVER_NAME")
            @ExcelField(value ="服务器地址")
private String serverName;
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
     * 记录ID
     */
    @TableField("REF_TAB_ID")
            @ExcelField(value ="记录ID")
private String refTabId;
    /**
     * 表名
     */
    @TableField("REF_TAB_TABLE")
            @ExcelField(value ="表名")
private String refTabTable;


    public String getId(){
            return id;
            }

        public void setId(String id) {
            this.id = id;
            }

    public String getClientName(){
            return clientName;
            }

        public void setClientName(String clientName) {
            this.clientName = clientName;
            }

    public String getServerName(){
            return serverName;
            }

        public void setServerName(String serverName) {
            this.serverName = serverName;
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

    public String getRefTabId(){
            return refTabId;
            }

        public void setRefTabId(String refTabId) {
            this.refTabId = refTabId;
            }

    public String getRefTabTable(){
            return refTabTable;
            }

        public void setRefTabTable(String refTabTable) {
            this.refTabTable = refTabTable;
            }

    public static final String ID ="ID" ;

    public static final String CLIENT_NAME ="CLIENT_NAME" ;

    public static final String SERVER_NAME ="SERVER_NAME" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

    public static final String REF_TAB_ID ="REF_TAB_ID" ;

    public static final String REF_TAB_TABLE ="REF_TAB_TABLE" ;

@Override
public String toString() {
        return "ComFile{" +
                ", id=" + id +
                ", clientName=" + clientName +
                ", serverName=" + serverName +
                ", isDeletemark=" + isDeletemark +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", createUserId=" + createUserId +
                ", modifyUserId=" + modifyUserId +
                ", refTabId=" + refTabId +
                ", refTabTable=" + refTabTable +
        "}";
        }
        }