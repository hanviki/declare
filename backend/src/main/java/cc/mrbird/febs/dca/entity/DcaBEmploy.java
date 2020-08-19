package cc.mrbird.febs.dca.entity;

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
 * 任职培养
 * </p>
 *
 * @author viki
 * @since 2020-08-11
 */

@Excel("dca_b_employ")
@Data
@Accessors(chain = true)
public class DcaBEmploy implements Serializable{

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
     * 自何年月
     */
    @TableField("em_start_TIME")
            @ExcelField(value ="自何年月")
    private Date emStartTime;
    private transient String emStartTimeFrom;
    private transient String emStartTimeTo;

    /**
     * 至何年月
     */
    @TableField("em_end_TIME")
            @ExcelField(value ="至何年月")
    private Date emEndTime;
    private transient String emEndTimeFrom;
    private transient String emEndTimeTo;

    /**
     * 讲授课程名称
     */
            @ExcelField(value ="讲授课程名称")
    private String emCoursename;

    /**
     * 其他教学任务
     */
            @ExcelField(value ="其他教学任务")
    private String emOtherwork;

    /**
     * 学生人数
     */
            @ExcelField(value ="学生人数")
    private Integer emStudentcount;

    /**
     * 周学时数
     */
            @ExcelField(value ="周学时数")
    private Integer emWeektime;

    /**
     * 总学时数
     */
            @ExcelField(value ="总学时数")
    private Integer emTotaltime;

    /**
     * 备注
     */
            @ExcelField(value ="备注")
    private String emContent;

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

    public static final String EM_START_TIME ="em_start_TIME" ;

    public static final String EM_END_TIME ="em_end_TIME" ;

    public static final String EM_COURSENAME ="em_coursename" ;

    public static final String EM_OTHERWORK ="em_otherwork" ;

    public static final String EM_STUDENTCOUNT ="em_studentcount" ;

    public static final String EM_WEEKTIME ="em_weektime" ;

    public static final String EM_TOTALTIME ="em_totaltime" ;

    public static final String EM_CONTENT ="em_content" ;

    public static final String STATE ="state" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

        }