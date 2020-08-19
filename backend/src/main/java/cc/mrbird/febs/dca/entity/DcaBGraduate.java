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
 * 研究生情况
 * </p>
 *
 * @author viki
 * @since 2020-08-11
 */

@Excel("dca_b_graduate")
@Data
@Accessors(chain = true)
public class DcaBGraduate implements Serializable{

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
     * 博士在读人数
     */
            @ExcelField(value ="博士在读人数")
    private Integer doctorNumber;

    /**
     * 硕士在读人数
     */
            @ExcelField(value ="硕士在读人数")
    private Integer graduateNumber;

    /**
     * 博士在读人数
     */
            @ExcelField(value ="博士在读人数")
    private Integer doctorDoneNumber;

    /**
     * 硕士在读人数
     */
            @ExcelField(value ="硕士在读人数")
    private Integer graduateDoneNumber;

    /**
     * 已毕业获奖情况
     */
            @ExcelField(value ="已毕业获奖情况")
    private String prizeContent;

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

    public static final String DOCTOR_NUMBER ="doctor_number" ;

    public static final String GRADUATE_NUMBER ="graduate_number" ;

    public static final String DOCTOR_DONE_NUMBER ="doctor_done_number" ;

    public static final String GRADUATE_DONE_NUMBER ="graduate_done_number" ;

    public static final String PRIZE_CONTENT ="prize_content" ;

    public static final String STATE ="state" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

        }