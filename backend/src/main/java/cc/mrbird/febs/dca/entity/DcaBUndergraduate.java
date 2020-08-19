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
 * 本科教学情况
 * </p>
 *
 * @author viki
 * @since 2020-08-11
 */

@Excel("dca_b_undergraduate")
@Data
@Accessors(chain = true)
public class DcaBUndergraduate implements Serializable{

private static final long serialVersionUID=1L;

    /**
     * 主键
     */
                                @ExcelField(value ="主键")
    private String id;

    /**
     * 人事编号
     */
            @ExcelField(value ="人事编号")
    private String userAccount;

    /**
     * 课程名称
     */
            @ExcelField(value ="课程名称")
    private String courseName;

    /**
     * 自何年月
     */
            @ExcelField(value ="自何年月")
    private Date ugStartDate;
    private transient String ugStartDateFrom;
    private transient String ugStartDateTo;

    /**
     * 至何年月
     */
            @ExcelField(value ="至何年月")
    private Date ugEndDate;
    private transient String ugEndDateFrom;
    private transient String ugEndDateTo;

    /**
     * 课程类别
     */
            @ExcelField(value ="课程类别")
    private String courseType;

    /**
     * 学生人数
     */
            @ExcelField(value ="学生人数")
    private Integer studentNumber;

    /**
     * 总学时
     */
            @ExcelField(value ="总学时")
    private Integer totalTime;

    /**
     * 个人承担学时
     */
            @ExcelField(value ="个人承担学时")
    private Integer personTime;

    /**
     * 教学评分
     */
            @ExcelField(value ="教学评分")
    private String teachScore;

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



    public static final String ID ="id" ;

    public static final String USER_ACCOUNT ="user_account" ;

    public static final String COURSE_NAME ="course_name" ;

    public static final String UG_START_DATE ="ug_start_date" ;

    public static final String UG_END_DATE ="ug_end_date" ;

    public static final String COURSE_TYPE ="course_type" ;

    public static final String STUDENT_NUMBER ="student_number" ;

    public static final String TOTAL_TIME ="total_time" ;

    public static final String PERSON_TIME ="person_time" ;

    public static final String TEACH_SCORE ="teach_score" ;

    public static final String STATE ="state" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

        }