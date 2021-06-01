package cc.mrbird.febs.dca.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import cc.mrbird.febs.common.converter.AuditStateConverter;
import cc.mrbird.febs.common.converter.BooleanConverter;
import cc.mrbird.febs.common.converter.DateConverter;
import cc.mrbird.febs.common.converter.StateConverter;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;


import lombok.Data;
import lombok.experimental.Accessors;
import java.util.Date;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;

/**
 * <p>
 * 任现职以来承担的科研项目
 * </p>
 *
 * @author viki
 * @since 2020-11-06
 */

@Excel("dca_b_sciencesearch")
@Data
@Accessors(chain = true)
public class DcaBSciencesearch implements Serializable{

private static final long serialVersionUID=1L;

    /**
     * 主键
     */
                                @ExcelField(value ="主键")
    private String id;

    /**
     * 附件
     */
            @ExcelField(value ="附件")
    private String fileId;

    /**
     * 排序
     */
            @ExcelField(value ="排序")
    private Integer displayIndex;

    /**
     * 附件地址
     */
            @ExcelField(value ="附件地址")
    private String fileUrl;

    /**
     * 姓名
     */
            @ExcelField(value ="姓名")
    private String userAccountName;

    /**
     * 用户名
     */
            @ExcelField(value ="用户名")
    private String userAccount;

    /**
     * 项目名称
     */
            @ExcelField(value ="项目名称")
    private String projectName;

    /**
     * 项目性质
     */
            @ExcelField(value ="项目性质")
    private String projectType;

    /**
     * 项目来源
     */
            @ExcelField(value ="项目来源")
    private String projectSource;

    /**
     * 合同经费
     */
            @ExcelField(value ="合同经费")
    private BigDecimal contractFund;

    /**
     * 实到经费
     */
            @ExcelField(value ="实到经费")
    private BigDecimal realFund;

    /**
     * 批准年月
     */
            @ExcelField(value ="批准年月", writeConverter = DateConverter.class)
            @TableField("audit_date")
    private Date auditDate2;
    private transient String auditDate2From;
    private transient String auditDate2To;

    /**
     * 起始日期
     */
            @ExcelField(value ="起始日期", writeConverter = DateConverter.class)
    private Date startDate;
    private transient String startDateFrom;
    private transient String startDateTo;

    /**
     * 终止日期
     */
            @ExcelField(value ="终止日期", writeConverter = DateConverter.class)
    private Date endDate;
    private transient String endDateFrom;
    private transient String endDateTo;

    /**
     * 本人排名
     */
            @ExcelField(value ="本人排名")
    private Integer rankNum;

    /**
     *  类型
     */
            @ExcelField(value ="临床类型")
    private String auditTypetp;

    /**
     *  类型
     */
    @ExcelField(value ="教学类型")
    private String auditTypetpjx;

    /**
     * 类别
     */
            @ExcelField(value ="类别")
    private String auditLb;

    /**
     * 经费（万）
     */
            @ExcelField(value ="经费（万）")
    private BigDecimal auditFund;

    /**
     * 排名
     */
            @ExcelField(value ="排名")
    private Integer auditRank;

    /**
     * 审核状态
     */
    @ExcelField(value ="审核状态", writeConverter = AuditStateConverter.class)
    private Integer auditState;

    /**
     * 状态
     */
    @ExcelField(value ="状态", writeConverter = StateConverter.class)
    private Integer state;

    /**
     * 序号
     */
    @ExcelField(value ="序号")
    private  Integer auditXuhao;
    private  transient  Integer auditXuhaoS;
    private  transient  Integer auditXuhaoE;
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
    private transient String createTimeFrom;
    private transient String createTimeTo;

    /**
     * 修改时间
     */
    @TableField("MODIFY_TIME")
            @ExcelField(value ="修改时间")
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
     * 审核人
     */
    @TableField("auditMan")
            @ExcelField(value ="审核人")
    private String auditMan;

    /**
     * 审核人姓名
     */
    @TableField("auditManName")
            @ExcelField(value ="审核人姓名")
    private String auditManName;

    /**
     * 审核时间
     */
    @TableField("auditDate")
            @ExcelField(value ="审核时间")
    private Date auditDate;
    private transient String auditDateFrom;
    private transient String auditDateTo;

    /**
     * 审核意见
     */
    @TableField("auditSuggestion")
            @ExcelField(value ="审核意见")
    private String auditSuggestion;

    /**
     * 是否用于本次评审
     */
    @TableField("IsUse")
            @ExcelField(value ="是否用于本次评审",writeConverter = BooleanConverter.class)
    private Boolean isUse;



    public static final String ID ="id" ;

    public static final String FILE_ID ="file_id" ;

    public static final String DISPLAY_INDEX ="display_index" ;

    public static final String FILE_URL ="file_url" ;

    public static final String USER_ACCOUNT_NAME ="user_account_name" ;

    public static final String USER_ACCOUNT ="user_account" ;

    public static final String PROJECT_NAME ="project_name" ;

    public static final String PROJECT_TYPE ="project_type" ;

    public static final String PROJECT_SOURCE ="project_source" ;

    public static final String CONTRACT_FUND ="contract_fund" ;

    public static final String REAL_FUND ="real_fund" ;

    public static final String AUDIT_DATE ="audit_date" ;

    public static final String START_DATE ="start_date" ;

    public static final String END_DATE ="end_date" ;

    public static final String RANK_NUM ="rank_num" ;

    public static final String AUDIT_TYPETP ="audit_typetp" ;

    public static final String AUDIT_LB ="audit_lb" ;

    public static final String AUDIT_FUND ="audit_fund" ;

    public static final String AUDIT_RANK ="audit_rank" ;

    public static final String AUDIT_STATE ="audit_state" ;

    public static final String STATE ="state" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

    public static final String AUDITMAN ="auditMan" ;

    public static final String AUDITMANNAME ="auditManName" ;

    public static final String AUDITDATE ="auditDate" ;

    public static final String AUDITSUGGESTION ="auditSuggestion" ;

    public static final String ISUSE ="IsUse" ;

        }