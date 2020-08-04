package cc.mrbird.febs.scm.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;

/**
 * <p>
 * 
 * </p>
 *
 * @author viki
 * @since 2019-12-09
 */

@Excel("scm_b_salereturn")
public class ScmBSalereturn implements Serializable{

private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
                    @TableId("ID")
                    @ExcelField(value ="主键ID")
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
     * 物料ID
     */
    @TableField("MATNR_ID")
            @ExcelField(value ="物料ID")
private String matnrId;
    /**
     * 批次号
     */
    @TableField("CHARG")
            @ExcelField(value ="批次号")
private String charg;

    /**
     * 退货原因
     */
    @TableField("SALE_CAUSE")
    @ExcelField(value ="退货原因")
    private String saleCause;
    /**
     * 退货数量
     */
    @TableField("AMOUNT")
            @ExcelField(value ="退货数量")
private BigDecimal amount;
    /**
     * 备注
     */
    @TableField("COMMENTS")
            @ExcelField(value ="备注")
private String comments;
    /**
     * 0是未提交1是提交2是审核完成3是供应商确认4退货完成
     */
    @TableField("STATE")
            @ExcelField(value ="0是未提交1是提交2是审核完成3是供应商确认4退货完成")
private Integer state;
    /**
     * 退货部门
     */
    @TableField("DEPT_ID")
            @ExcelField(value ="退货部门")
private Long deptId;
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

    public String getName(){
            return name;
            }

        public void setName(String name) {
            this.name = name;
            }

    public String getSaleCause(){
        return saleCause;
    }

    public void setSaleCause(String saleCause) {
        this.saleCause = saleCause;
    }

    public String getMatnrId(){
            return matnrId;
            }

        public void setMatnrId(String matnrId) {
            this.matnrId = matnrId;
            }

    public String getCharg(){
            return charg;
            }

        public void setCharg(String charg) {
            this.charg = charg;
            }

    public BigDecimal getAmount(){
            return amount;
            }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
            }

    public String getComments(){
            return comments;
            }

        public void setComments(String comments) {
            this.comments = comments;
            }

    public Integer getState(){
            return state;
            }

        public void setState(Integer state) {
            this.state = state;
            }

    public Long getDeptId(){
            return deptId;
            }

        public void setDeptId(Long deptId) {
            this.deptId = deptId;
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

    public static final String MATNR_ID ="MATNR_ID" ;

    public static final String CHARG ="CHARG" ;

    public static final String AMOUNT ="AMOUNT" ;

    public static final String COMMENTS ="COMMENTS" ;

    public static final String STATE ="STATE" ;

    public static final String DEPT_ID ="DEPT_ID" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

@Override
public String toString() {
        return "ScmBSalereturn{" +
                ", id=" + id +
                ", code=" + code +
                ", name=" + name +
                ", matnrId=" + matnrId +
                ", charg=" + charg +
                ", amount=" + amount +
                ", comments=" + comments +
                ", state=" + state +
                ", deptId=" + deptId +
                ", isDeletemark=" + isDeletemark +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", createUserId=" + createUserId +
                ", modifyUserId=" + modifyUserId +
        "}";
        }
        }