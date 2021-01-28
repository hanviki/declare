package cc.mrbird.febs.doctor.entity;

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
 * 任现职以来完成教学、人才培养情况
 * </p>
 *
 * @author viki
 * @since 2021-01-12
 */

@Excel("dca_b_doc_teachtalent")
@Data
@Accessors(chain = true)
public class DcaBDocTeachtalent implements Serializable{

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
     * 讲授课程名称及其它教学任务
     */
            @ExcelField(value ="讲授课程名称及其它教学任务")
    private String teachtaletName;

    /**
     * 自何年月
     */
            @ExcelField(value ="自何年月")
    private Date teachtalentStartDate;
    private transient String teachtalentStartDateFrom;
    private transient String teachtalentStartDateTo;

    /**
     * 至何年月
     */
            @ExcelField(value ="至何年月")
    private Date teachtalentEndDate;
    private transient String teachtalentEndDateFrom;
    private transient String teachtalentEndDateTo;

    /**
     * 学生人数
     */
            @ExcelField(value ="学生人数")
    private Integer studentNumber;

    /**
     * 周学时数
     */
            @ExcelField(value ="周学时数")
    private Integer weekTime;

    /**
     * 总学时
     */
            @ExcelField(value ="总学时")
    private Integer totalTime;

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
            @ExcelField(value ="创建时间")
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
            @ExcelField(value ="是否用于本次评审")
    private Boolean IsUse;

    /**
     * 备注
     */
            @ExcelField(value ="备注")
    private String note;



    public static final String ID ="id" ;

    public static final String FILE_ID ="file_id" ;

    public static final String FILE_URL ="file_url" ;

    public static final String USER_ACCOUNT_NAME ="user_account_name" ;

    public static final String USER_ACCOUNT ="user_account" ;

    public static final String TEACHTALET_NAME ="teachtalet_name" ;

    public static final String TEACHTALENT_START_DATE ="teachtalent_start_date" ;

    public static final String TEACHTALENT_END_DATE ="teachtalent_end_date" ;

    public static final String STUDENT_NUMBER ="student_number" ;

    public static final String WEEK_TIME ="week_time" ;

    public static final String TOTAL_TIME ="total_time" ;

    public static final String STATE ="state" ;

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

    public static final String NOTE ="note" ;

        }