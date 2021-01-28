package cc.mrbird.febs.dca.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import cc.mrbird.febs.common.utils.DateUtil;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyAuditdynamic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;


import lombok.Data;
import lombok.experimental.Accessors;
import java.util.Date;
import java.util.List;

import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;

/**
 * <p>
 * 
 * </p>
 *
 * @author viki
 * @since 2020-11-05
 */

@Excel("dca_b_userapply")
@Data
@Accessors(chain = true)
public class DcaBUserapply implements Serializable{

private static final long serialVersionUID=1L;

    /**
     * 主键
     */

    private String id;

    /**
     * 科室
     */
            @ExcelField(value ="科室")
    private String ks;

    /**
     * 申报年度
     */
    @ExcelField(value ="申报年度")
    private String dcaYear;
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

    private Date birthday;
    private transient String birthdayFrom;
    private transient String birthdayTo;

    @ExcelField(value ="出生日期")
    private transient String birthdaystr;
    public  String getBirthdaystr (){
        String na="";
        try {
            if (birthday != null) {
                na=  DateUtil.formatCSTTime(birthday.toString(), "yyyy-MM-dd");
            }
        }catch (Exception ex) {

        }
        return  na;
    }
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

    private String deptName;

    /**
     * 现岗位职务
     */

    private String positionName;

    /**
     * 来校工作时间
     */

    private Date schoolDate;
    private transient String schoolDateFrom;
    private transient String schoolDateTo;

    /**
     * 现从事专业及专长
     */

    private String xcszyjzc;

    /**
     * 附件
     */

    private String fileId;

    /**
     * 附件地址
     */

    private String fileUrl;



    /**
     * 状态
     */

    private Integer state;

    /**
     * 是否删除
     */
    @TableField("IS_DELETEMARK")

    private Integer isDeletemark;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")

    private Date createTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;

    /**
     * 修改时间
     */
    @TableField("MODIFY_TIME")

    private Date modifyTime;
    private transient String modifyTimeFrom;
    private transient String modifyTimeTo;

    /**
     * 创建人
     */
    @TableField("CREATE_USER_ID")

    private Long createUserId;

    /**
     * 修改人
     */
    @TableField("MODIFY_USER_ID")

    private Long modifyUserId;

    public  String getXl (){
        String name = "";
        if(npPositionName==null) {
            return "";
        }

        switch (npPositionName) {
            case "教授主任医师":
                name = "医师";
                break;
            case "教授":
                name = "医师";
                break;
            case "主任医师":
                name = "医师";
                break;
            case "研究员":
                name = "研究";
                break;
            case "主任护师":
                name = "护理";
                break;
            case "主任技师":
                name = "医技";
                break;
            case "主任药师":
                name = "药技";
                break;
            case "教授级高级工程师":
                name = "技术工程";
                break;
            case "编审":
                name = "技术编辑";
                break;
            case "副教授副主任医师":
                name = "医师";
                break;
            case "副教授":
                name = "医师";
                break;
            case "副主任医师":
                name = "医师";
                break;
            case "副研究员":
                name = "研究";
                break;
            case "副主任护师":
                name = "护理";
                break;
            case "副主任技师":
                name = "医技";
                break;
            case "副主任药师":
                name = "药技";
                break;
            case "高级工程师":
                name = "技术工程";
                break;
            case "副编审":
                name = "技术编辑";
                break;
            case "主治医师":
                name = "医师";
                break;
            case "主管药师":
                name = "药技";
                break;
            case "主管护师":
                name = "护理";
                break;
            case "主管技师":
                name = "医技";
                break;
            case "编辑":
                name = "技术编辑";
                break;
            case "工程师":
                name = "技术工程";
                break;
            case "馆员":
                name = "其他";
                break;
            case "会计师":
                name = "其他";
                break;
            case "审计师":
                name = "其他";
                break;
            case "住院医师":
                name = "医师";
                break;
            case "药师":
                name = "药技";
                break;
            case "护师":
                name = "护理";
                break;
            case "技师":
                name = "医技";
                break;
            case "图书管理员":
                name = "其他";
                break;
            case "会计员":
                name = "其他";
                break;
            case "助理编辑":
                name = "编辑";
                break;
            case "助理工程师":
                name = "技术工程";
                break;
            case "助理馆员":
                name = "其他";
                break;
            case "助理会计师":
                name = "其他";
                break;
        }
        return name;
    }
    private transient List<DcaBCopyAuditdynamic> dcaBAuditdynamicList;
    private  transient List<DcaBAuditdynamic> dcaBAuditdynamics;

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

    public static final String DCA_YEAR ="dca_year" ;

    public static final String STATE ="state" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

        }