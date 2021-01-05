package cc.mrbird.febs.dca.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import cc.mrbird.febs.common.converter.*;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;


import lombok.Data;
import lombok.experimental.Accessors;
import java.util.Date;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;

/**
 * <p>
 * 
 * </p>
 *
 * @author viki
 * @since 2020-12-24
 */

@Excel("dca_b_userapplyzc")
@Data
@Accessors(chain = true)
public class DcaBUserapplyzc implements Serializable{

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
     * 系列
     */
            @ExcelField(value ="系列")
    private String xl;

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
     * 申请岗位
     */
            @ExcelField(value ="申请岗位")
    private String npPositionName;

    /**
     * 申请岗位等级
     */
            @ExcelField(value ="申请岗位等级")
    private String gwdj;

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
     * 论文
     */
            @ExcelField(value ="论文")
    private String fileId;

    /**
     * 论文地址
     */
            @ExcelField(value ="论文地址")
    private String fileUrl;

    /**
     * 住院医师规范化培训证书
     */
            @ExcelField(value ="住院医师规范化培训证书")
    private String zcFileId;

    /**
     * 证书地址
     */
            @ExcelField(value ="证书地址")
    private String zcFileUrl;

    /**
     * 申报年度
     */
            @ExcelField(value ="申报年度")
    private String dcaYear;

    /**
     * 状态
     */
            @ExcelField(value ="状态", writeConverter = UserState.class)
    private Integer state;

    /**
     * 是否通过初级考核
     */
            @ExcelField(value ="是否通过初级考核", writeConverter = BooleanConverter.class)
    private Boolean isChujikh;

    /**
     * 通过初级考核时间
     */
            @ExcelField(value ="通过初级考核时间", writeConverter = DateConverter.class)
    private Date chujikhDate;
    private transient String chujikhDateFrom;
    private transient String chujikhDateTo;

    /**
     * 是否通过中级考核
     */
            @ExcelField(value ="是否通过中级考核", writeConverter = BooleanConverter.class)
    private Boolean isZhongjikh;

    /**
     * 通过中级考核时间
     */
            @ExcelField(value ="通过中级考核时间", writeConverter = DateConverter.class)
    private Date zhongjikhDate;
    private transient String zhongjikhDateFrom;
    private transient String zhongjikhDateTo;

    /**
     * 是否补考
     */
            @ExcelField(value ="是否补考", writeConverter = BooleanConverter.class)
    private Boolean isBukao;

    /**
     * 补考科目
     */
            @ExcelField(value ="补考科目")
    private String bukaokemu;

    /**
     * 理论考试科目
     */
            @ExcelField(value ="理论考试科目")
    private String lilunbukao;

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

    public static final String KS ="ks" ;

    public static final String XL ="xl" ;

    public static final String USER_ACCOUNT ="user_account" ;

    public static final String USER_ACCOUNT_NAME ="user_account_name" ;

    public static final String SEX_NAME ="sex_name" ;

    public static final String BIRTHDAY ="birthday" ;

    public static final String TELEPHONE ="telephone" ;

    public static final String ZYJSGW ="zyjsgw" ;

    public static final String APPOINTED_DATE ="appointed_date" ;

    public static final String NP_POSITION_NAME ="np_position_name" ;

    public static final String GWDJ ="gwdj" ;

    public static final String DEPT_NAME ="dept_name" ;

    public static final String POSITION_NAME ="position_name" ;

    public static final String SCHOOL_DATE ="school_date" ;

    public static final String XCSZYJZC ="xcszyjzc" ;

    public static final String FILE_ID ="file_id" ;

    public static final String FILE_URL ="file_url" ;

    public static final String ZC_FILE_ID ="zc_file_id" ;

    public static final String ZC_FILE_URL ="zc_file_url" ;

    public static final String DCA_YEAR ="dca_year" ;

    public static final String STATE ="state" ;

    public static final String IS_CHUJIKH ="is_chujikh" ;

    public static final String CHUJIKH_DATE ="chujikh_date" ;

    public static final String IS_ZHONGJIKH ="is_zhongjikh" ;

    public static final String ZHONGJIKH_DATE ="zhongjikh_date" ;

    public static final String IS_BUKAO ="is_bukao" ;

    public static final String BUKAOKEMU ="bukaokemu" ;

    public static final String LILUNBUKAO ="lilunbukao" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

        }