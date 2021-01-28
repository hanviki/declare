package cc.mrbird.febs.doctor.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
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
 * 科研论文
 * </p>
 *
 * @author viki
 * @since 2021-01-13
 */

@Excel("dca_b_doc_sciencepublish")
@Data
@Accessors(chain = true)
public class DcaBDocSciencepublish implements Serializable{

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
     * 人事编号
     */
        
        @ExcelField(value ="人事编号")
    private String userAccount;

    /**
     * 论文名
     */
        
        @ExcelField(value ="论文名")
    private String paperName;

    /**
     * 期刊名
     */
        
        @ExcelField(value ="期刊名")
    private String journalName;

    /**
     * 期刊号
     */
        
        @ExcelField(value ="期刊号")
    private String journalCode;

    /**
     * 发表年月
     */
        
        @ExcelField(value ="发表年月", writeConverter = DateConverter.class)
    private Date paperPublishdate;
    private transient String paperPublishdateFrom;
    private transient String paperPublishdateTo;

    /**
     * 文章类型
     */
        
        @ExcelField(value ="文章类型")
    private String wzlx;

    /**
     * 第几作者
     */
        
        @ExcelField(value ="第几作者")
    private String djzz;

    /**
     * 第一作者第一单位
     */
        
        @ExcelField(value ="第一作者第一单位")
    private String firstUnitAuthor;

    /**
     * 期刊级别
     */
        
        @ExcelField(value ="期刊级别")
    private String qkjb;

    /**
     * 承担字数（万）
     */
        
        @ExcelField(value ="承担字数（万）")
    private BigDecimal cdzs;

    /**
     * 收录情况
     */
        
        @ExcelField(value ="收录情况")
    private String paperShoulu;

    /**
     * 影响因子
     */
        
        @ExcelField(value ="影响因子")
    private String paperCause;

    /**
     * 是否一流期刊
     */
        
        @ExcelField(value ="是否一流期刊")
    private String isBest;

    /**
     * 第一或通讯作者
     */
        
        @ExcelField(value ="第一或通讯作者")
    private String authorRank;

    /**
     * 他引次数
     */
        
        @ExcelField(value ="他引次数")
    private String otherTimes;

    /**
     * 是否能用于教学职称申报
     */
        
        @ExcelField(value ="是否能用于教学职称申报")
    private String isJxzcsb;

    /**
     * 是否能用于临床职称申报
     */
        
        @ExcelField(value ="是否能用于临床职称申报")
    private String isLczcsb;

    /**
     * 期刊级别
     */
        
        @ExcelField(value ="期刊级别")
    private String auditQkjb;

    /**
     * 教学职称数量
     */
        
        @ExcelField(value ="教学职称数量")
    private String jxzcsl;

    /**
     * 临床职称数量
     */
        
        @ExcelField(value ="临床职称数量")
    private String lczcsl;

        
    private Integer auditXuhao;

        
    private Integer auditTotalnum;

        
    private Boolean auditIsfirst;

    /**
     * 状态
     */
        
        @ExcelField(value ="状态")
    private Integer state;

    /**
     * 审核状态
     */
        
        @ExcelField(value ="审核状态")
    private Integer auditState;

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
     * 排序
     */
        
        @ExcelField(value ="排序")
    private Integer displayIndex;

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
        
        @ExcelField(value ="审核时间", writeConverter = DateConverter.class)
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
        
        @ExcelField(value ="是否用于本次评审")
    private Boolean IsUse;



    public static final String ID ="id" ;

    public static final String FILE_ID ="file_id" ;

    public static final String FILE_URL ="file_url" ;

    public static final String USER_ACCOUNT_NAME ="user_account_name" ;

    public static final String USER_ACCOUNT ="user_account" ;

    public static final String PAPER_NAME ="paper_name" ;

    public static final String JOURNAL_NAME ="journal_name" ;

    public static final String JOURNAL_CODE ="journal_code" ;

    public static final String PAPER_PUBLISHDATE ="paper_publishdate" ;

    public static final String WZLX ="wzlx" ;

    public static final String DJZZ ="djzz" ;

    public static final String FIRST_UNIT_AUTHOR ="first_unit_author" ;

    public static final String QKJB ="qkjb" ;

    public static final String CDZS ="cdzs" ;

    public static final String PAPER_SHOULU ="paper_shoulu" ;

    public static final String PAPER_CAUSE ="paper_cause" ;

    public static final String IS_BEST ="is_best" ;

    public static final String AUTHOR_RANK ="author_rank" ;

    public static final String OTHER_TIMES ="other_times" ;

    public static final String IS_JXZCSB ="is_jxzcsb" ;

    public static final String IS_LCZCSB ="is_lczcsb" ;

    public static final String AUDIT_QKJB ="audit_qkjb" ;

    public static final String JXZCSL ="jxzcsl" ;

    public static final String LCZCSL ="lczcsl" ;

    public static final String AUDIT_XUHAO ="audit_xuhao" ;

    public static final String AUDIT_TOTALNUM ="audit_totalnum" ;

    public static final String AUDIT_ISFIRST ="audit_isfirst" ;

    public static final String STATE ="state" ;

    public static final String AUDIT_STATE ="audit_state" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String DISPLAY_INDEX ="display_index" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

    public static final String AUDITMAN ="auditMan" ;

    public static final String AUDITMANNAME ="auditManName" ;

    public static final String AUDITDATE ="auditDate" ;

    public static final String AUDITSUGGESTION ="auditSuggestion" ;

    public static final String ISUSE ="IsUse" ;

        }