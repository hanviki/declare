package cc.mrbird.febs.check.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * 指标配置表
 * </p>
 *
 * @author viki
 * @since 2021-01-25
 */

@Excel("check_b_setting")
@Data
@Accessors(chain = true)
public class CheckBSetting implements Serializable{

private static final long serialVersionUID=1L;

                    @TableId(value = "id" , type = IdType.AUTO)
                
    private Long id;

    /**
     * 科室
     */
        
        @ExcelField(value ="科室")
    private String ks;

    /**
     * 人事编号
     */
        
        @ExcelField(value ="人事编号")
    private String userAccount;

    /**
     * 姓名
     */
        
        @ExcelField(value ="姓名")
    private String userAccountName;

    /**
     * 指标ID
     */
        
        @ExcelField(value ="指标ID")
    private Integer titleId;

    /**
     * 指标编码
     */
        
        @ExcelField(value ="指标编码")
    private String titleCode;

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
     * 年度
     */
        
        @ExcelField(value ="年度")
    private String dcaYear;

    /**
     * 指标考核类别
     */
        
        @ExcelField(value ="指标考核类别")
    private String lb;

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



    public static final String ID ="id" ;

    public static final String KS ="ks" ;

    public static final String USER_ACCOUNT ="user_account" ;

    public static final String USER_ACCOUNT_NAME ="user_account_name" ;

    public static final String TITLE_ID ="title_id" ;

    public static final String TITLE_CODE ="title_code" ;

    public static final String AUDIT_TITLE ="audit_title" ;

    public static final String AUDIT_TITLETYPE ="audit_titletype" ;

    public static final String DCA_YEAR ="dca_year" ;

    public static final String LB ="lb" ;

    public static final String STATE ="state" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

        }