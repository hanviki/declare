package cc.mrbird.febs.dca.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;


import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;

/**
 * <p>
 * 任现职以来科研获奖情况
 * </p>
 *
 * @author viki
 * @since 2020-11-06
 */

@Excel("dca_b_scientificprize")
@Data
@Accessors(chain = true)
public class DcaBScientificprize implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelField(value = "主键")
    private String id;

    /**
     * 附件
     */
    @ExcelField(value = "附件")
    private String fileId;

    /**
     * 排序
     */
    @ExcelField(value = "排序")
    private Integer displayIndex;

    /**
     * 附件地址
     */
    @ExcelField(value = "附件地址")
    private String fileUrl;

    /**
     * 姓名
     */
    @ExcelField(value = "姓名")
    private String userAccountName;

    /**
     * 人事编号
     */
    @ExcelField(value = "人事编号")
    private String userAccount;

    /**
     * 获奖项目名称
     */
    @ExcelField(value = "获奖项目名称")
    private String spProjectName;

    /**
     * 奖项级别
     */
    @ExcelField(value = "奖项级别")
    private String srProjectGrade;

    /**
     * 奖项等级
     */
    @ExcelField(value = "奖项等级")
    private String srProjectLevel;

    /**
     * 授奖部门
     */
    @ExcelField(value = "授奖部门")
    private String srPrizeDept;

    /**
     * 批准年月
     */
    @ExcelField(value = "批准年月")
    private Date srPrizeDate;
    private transient String srPrizeDateFrom;
    private transient String srPrizeDateTo;

    /**
     * 本人排名
     */
    @ExcelField(value = "本人排名")
    private Integer srPrizeRanknum;

    /**
     * 名称
     */
    @ExcelField(value = "名称")
    private String auditName;

    /**
     * 等级
     */
    @ExcelField(value = "等级")
    private String auditGrade;

    /**
     * 排名
     */
    @ExcelField(value = "排名")
    private String auditRank;

    /**
     * 审核状态
     */
    @ExcelField(value = "审核状态")
    private Integer auditState;

    /**
     * 状态
     */
    @ExcelField(value = "状态")
    private Integer state;

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
    private transient String createTimeFrom;
    private transient String createTimeTo;

    /**
     * 修改时间
     */
    @TableField("MODIFY_TIME")
    @ExcelField(value = "修改时间")
    private Date modifyTime;
    private transient String modifyTimeFrom;
    private transient String modifyTimeTo;

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
     * 审核人
     */
    @TableField("auditMan")
    @ExcelField(value = "审核人")
    private String auditMan;

    /**
     * 审核人姓名
     */
    @TableField("auditManName")
    @ExcelField(value = "审核人姓名")
    private String auditManName;

    /**
     * 审核时间
     */
    @TableField("auditDate")
    @ExcelField(value = "审核时间")
    private Date auditDate;
    private transient String auditDateFrom;
    private transient String auditDateTo;

    /**
     * 审核意见
     */
    @TableField("auditSuggestion")
    @ExcelField(value = "审核意见")
    private String auditSuggestion;

    /**
     * 是否用于本次评审
     */
    @TableField("IsUse")
    @ExcelField(value = "是否用于本次评审")
    private Boolean IsUse;

    private transient Integer auditXuhao;
    private transient Integer auditXuhaoS;
    private transient Integer auditXuhaoE;

    public static final String ID = "id";

    public static final String FILE_ID = "file_id";

    public static final String DISPLAY_INDEX = "display_index";

    public static final String FILE_URL = "file_url";

    public static final String USER_ACCOUNT_NAME = "user_account_name";

    public static final String USER_ACCOUNT = "user_account";

    public static final String SP_PROJECT_NAME = "sp_project_name";

    public static final String SR_PROJECT_GRADE = "sr_project_grade";

    public static final String SR_PROJECT_LEVEL = "sr_project_level";

    public static final String SR_PRIZE_DEPT = "sr_prize_dept";

    public static final String SR_PRIZE_DATE = "sr_prize_date";

    public static final String SR_PRIZE_RANKNUM = "sr_prize_ranknum";

    public static final String AUDIT_NAME = "audit_name";

    public static final String AUDIT_GRADE = "audit_grade";

    public static final String AUDIT_RANK = "audit_rank";

    public static final String AUDIT_STATE = "audit_state";

    public static final String STATE = "state";

    public static final String IS_DELETEMARK = "IS_DELETEMARK";

    public static final String CREATE_TIME = "CREATE_TIME";

    public static final String MODIFY_TIME = "MODIFY_TIME";

    public static final String CREATE_USER_ID = "CREATE_USER_ID";

    public static final String MODIFY_USER_ID = "MODIFY_USER_ID";

    public static final String AUDITMAN = "auditMan";

    public static final String AUDITMANNAME = "auditManName";

    public static final String AUDITDATE = "auditDate";

    public static final String AUDITSUGGESTION = "auditSuggestion";

    public static final String ISUSE = "IsUse";

}