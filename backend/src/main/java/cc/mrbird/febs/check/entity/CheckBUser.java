package cc.mrbird.febs.check.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;


import lombok.Data;
import lombok.experimental.Accessors;
import java.util.Date;
import java.util.List;

import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import cc.mrbird.febs.common.converter.*;

/**
 * <p>
 * 待审核用户
 * </p>
 *
 * @author viki
 * @since 2021-01-25
 */

@Excel("check_b_user")
@Data
@Accessors(chain = true)
public class CheckBUser implements Serializable{

private static final long serialVersionUID=1L;

    /**
     * 主键
     */
                            
        @ExcelField(value ="主键")
    private String id;

    /**
     * 科室
     */
        
        @ExcelField(value ="科室")
    private String ks;

    /**
     * 专技类别
     */
        
        @ExcelField(value ="专技类别")
    private String zjlb;

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
     * 性别
     */
        
        @ExcelField(value ="性别")
    private String sexName;

    /**
     * 出生年月
     */
        
        @ExcelField(value ="出生年月", writeConverter = DateConverter.class)
    private Date birthday;
    private transient String birthdayFrom;
    private transient String birthdayTo;

    /**
     * 手机号
     */
        
        @ExcelField(value ="手机号")
    private String telephone;

    /**
     * 身份证号
     */
        
        @ExcelField(value ="身份证号")
    private String idCard;

    /**
     * 专业技术职务
     */
        
        @ExcelField(value ="专业技术职务")
    private String zyjsgw;

    /**
     * 专业技术职务聘任时间
     */
        
        @ExcelField(value ="专业技术职务聘任时间")
    private String appointedDate;

    /**
     * 科室负责人发薪号申请岗位
     */
        
        @ExcelField(value ="科室负责人发薪号申请岗位")
    private String ksLeaderPernr;

    /**
     * 主管院领导发薪号
     */
        
        @ExcelField(value ="主管院领导发薪号")
    private String zgLeaderPernr;

    /**
     * 所在院系
     */
        
        @ExcelField(value ="所在院系")
    private String deptName;

    /**
     * 现岗位职务
     */
        
        @ExcelField(value ="现岗位职务")
    private String positionName;

    /**
     * 来校工作时间
     */
        
        @ExcelField(value ="来校工作时间", writeConverter = DateConverter.class)
    private Date schoolDate;
    private transient String schoolDateFrom;
    private transient String schoolDateTo;

    /**
     * 现从事专业及专长
     */
        
        @ExcelField(value ="现从事专业及专长")
    private String xcszyjzc;

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
     * 申报年度
     */
        
        @ExcelField(value ="申报年度")
    private String dcaYear;

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

    private transient List<CheckBAuditresult> checkBAuditresultList;

    /**
     * 每个人每年度得分总和
     */
    private transient String totalNum;

    public static final String ID ="id" ;

    public static final String KS ="ks" ;

    public static final String ZJLB ="zjlb" ;

    public static final String USER_ACCOUNT ="user_account" ;

    public static final String USER_ACCOUNT_NAME ="user_account_name" ;

    public static final String SEX_NAME ="sex_name" ;

    public static final String BIRTHDAY ="birthday" ;

    public static final String TELEPHONE ="telephone" ;

    public static final String ID_CARD ="id_card" ;

    public static final String ZYJSGW ="zyjsgw" ;

    public static final String APPOINTED_DATE ="appointed_date" ;

    public static final String KS_LEADER_PERNR ="ks_leader_pernr" ;

    public static final String ZG_LEADER_PERNR ="zg_leader_pernr" ;

    public static final String DEPT_NAME ="dept_name" ;

    public static final String POSITION_NAME ="position_name" ;

    public static final String SCHOOL_DATE ="school_date" ;

    public static final String XCSZYJZC ="xcszyjzc" ;

    public static final String FILE_ID ="file_id" ;

    public static final String FILE_URL ="file_url" ;

    public static final String DCA_YEAR ="dca_year" ;

    public static final String STATE ="state" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

        }