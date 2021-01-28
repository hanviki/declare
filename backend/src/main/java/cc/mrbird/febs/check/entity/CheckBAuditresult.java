package cc.mrbird.febs.check.entity;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;


import lombok.Data;
import lombok.experimental.Accessors;
import java.util.Date;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import cc.mrbird.febs.common.converter.*;

/**
 * <p>
 * 指标结果表
 * </p>
 *
 * @author viki
 * @since 2021-01-25
 */

@Excel("check_b_auditresult")
@Data
@Accessors(chain = true)
public class CheckBAuditresult implements Serializable{

private static final long serialVersionUID=1L;

    /**
     * 主键
     */
                            
        @ExcelField(value ="主键")
    private String id;

    /**
     * 姓名
     */
        
        @ExcelField(value ="姓名")
    private String userAccountName;

    /**
     * 人事编号
     */
        
        @ExcelField(value ="人事编号")
    private String userAccount;

    /**
     * 用户id
     */
        
        @ExcelField(value ="用户id")
    private String checkUserId;

    /**
     * 当前年度
     */
        
        @ExcelField(value ="当前年度")
    private String dcaYear;

    /**
     * 审核内容
     */
        
        @ExcelField(value ="审核内容")
    private String auditTitle;

    /**
     * 显示标题id
     */
        
        @ExcelField(value ="显示标题id")
    private String auditTitletype;

    /**
     * 显示结果
     */
        
        @ExcelField(value ="显示结果")
    private String auditResult;

    /**
     * 备注
     */
        
        @ExcelField(value ="备注")
    private String auditNote;

    /**
     * 状态
     */
        
        @ExcelField(value ="状态")
    private Integer state;

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
        
        @ExcelField(value ="创建时间", writeConverter = DateConverter.class)
    private Date createTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;

    /**
     * 修改时间
     */
    @TableField("MODIFY_TIME")
        
        @ExcelField(value ="修改时间", writeConverter = DateConverter.class)
    private Date modifyTime;
    private transient String modifyTimeFrom;
    private transient String modifyTimeTo;

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
     * 科室
     */
        
        @ExcelField(value ="科室")
    private String ks;

    /**
     * 正负
     */
        
        @ExcelField(value ="正负")
    private Boolean isOria;

    private  transient Boolean showType;



    public static final String ID ="id" ;

    public static final String USER_ACCOUNT_NAME ="user_account_name" ;

    public static final String USER_ACCOUNT ="user_account" ;

    public static final String CHECK_USER_ID ="check_user_id" ;

    public static final String DCA_YEAR ="dca_year" ;

    public static final String AUDIT_TITLE ="audit_title" ;

    public static final String AUDIT_TITLETYPE ="audit_titletype" ;

    public static final String AUDIT_RESULT ="audit_result" ;

    public static final String AUDIT_NOTE ="audit_note" ;

    public static final String STATE ="state" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

    public static final String KS ="ks" ;

    public static final String IS_ORIA ="is_oria" ;

        }