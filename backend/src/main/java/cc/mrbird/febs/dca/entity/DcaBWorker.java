package cc.mrbird.febs.dca.entity;

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
 * 协和医院合同制职工信息确认表
 * </p>
 *
 * @author viki
 * @since 2021-05-24
 */

@Excel("dca_b_worker")
@Data
@Accessors(chain = true)
public class DcaBWorker implements Serializable{

private static final long serialVersionUID=1L;

    /**
     * 主键
     */
                            
        @ExcelField(value ="主键")
    private String id;

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
     * 博士学校
     */
        
        @ExcelField(value ="博士学校")
    private String doctorSchool;

    /**
     * 博士毕业时间
     */
        
        @ExcelField(value ="博士毕业时间")
    private String doctorDate;

    /**
     * 博士专业
     */
        
        @ExcelField(value ="博士专业")
    private String doctorZy;

    /**
     * 硕士学校
     */
        
        @ExcelField(value ="硕士学校")
    private String graduateSchool;

    /**
     * 硕士毕业时间
     */
        
        @ExcelField(value ="硕士毕业时间")
    private String graduateDate;

    /**
     * 硕士专业
     */
        
        @ExcelField(value ="硕士专业")
    private String graduateZy;

    /**
     * 本科学校
     */
        
        @ExcelField(value ="本科学校")
    private String bkSchool;

    /**
     * 本科毕业时间
     */
        
        @ExcelField(value ="本科毕业时间")
    private String bkDate;

    /**
     * 本科专业
     */
        
        @ExcelField(value ="本科专业")
    private String bkZy;

    /**
     * 大专学校
     */
        
        @ExcelField(value ="大专学校")
    private String dzSchool;

    /**
     * 大专毕业时间
     */
        
        @ExcelField(value ="大专毕业时间")
    private String dzDate;

    /**
     * 大专专业
     */
        
        @ExcelField(value ="大专专业")
    private String dzZy;

    /**
     * 中专学校
     */
        
        @ExcelField(value ="中专学校")
    private String zzSchool;

    /**
     * 中专毕业时间
     */
        
        @ExcelField(value ="中专毕业时间")
    private String zzDate;

    /**
     * 中专专业
     */
        
        @ExcelField(value ="中专专业")
    private String zzZy;

    /**
     * 现任专业技术职务
     */
        
        @ExcelField(value ="现任专业技术职务")
    private String xrzyzw;

    /**
     * 聘任时间
     */
        
        @ExcelField(value ="聘任时间")
    private String prDate;

    /**
     * 职员类别
     */
        
        @ExcelField(value ="职员类别")
    private String zylb;

    /**
     * 现任职员职级
     */
        
        @ExcelField(value ="现任职员职级")
    private String zrzyjb;

    /**
     * 聘任时间
     */
        
        @ExcelField(value ="聘任时间")
    private String zrprDate;

    /**
     * 签订劳动合同时间
     */
        
        @ExcelField(value ="签订劳动合同时间")
    private String comployDate;

    /**
     * 确认时间
     */
        
        @ExcelField(value ="确认时间", writeConverter = DateConverter.class)
    private Date confirmDate;
    private transient String confirmDateFrom;
    private transient String confirmDateTo;

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

    public static final String USER_ACCOUNT_NAME ="user_account_name" ;

    public static final String USER_ACCOUNT ="user_account" ;

    public static final String DOCTOR_SCHOOL ="doctor_school" ;

    public static final String DOCTOR_DATE ="doctor_date" ;

    public static final String DOCTOR_ZY ="doctor_zy" ;

    public static final String GRADUATE_SCHOOL ="graduate_school" ;

    public static final String GRADUATE_DATE ="graduate_date" ;

    public static final String GRADUATE_ZY ="graduate_zy" ;

    public static final String BK_SCHOOL ="bk_school" ;

    public static final String BK_DATE ="bk_date" ;

    public static final String BK_ZY ="bk_zy" ;

    public static final String DZ_SCHOOL ="dz_school" ;

    public static final String DZ_DATE ="dz_date" ;

    public static final String DZ_ZY ="dz_zy" ;

    public static final String ZZ_SCHOOL ="zz_school" ;

    public static final String ZZ_DATE ="zz_date" ;

    public static final String ZZ_ZY ="zz_zy" ;

    public static final String XRZYZW ="xrzyzw" ;

    public static final String PR_DATE ="pr_date" ;

    public static final String ZYLB ="zylb" ;

    public static final String ZRZYJB ="zrzyjb" ;

    public static final String ZRPR_DATE ="zrpr_date" ;

    public static final String COMPLOY_DATE ="comploy_date" ;

    public static final String CONFIRM_DATE ="confirm_date" ;

    public static final String STATE ="state" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

        }