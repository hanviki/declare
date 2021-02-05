package cc.mrbird.febs.dca.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import cc.mrbird.febs.common.converter.DateConverter;
import cc.mrbird.febs.common.converter.TimeConverter;
//import cc.mrbird.febs.common.utils.DateUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;


import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
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
 * @since 2020-10-27
 */

@Excel("用户申报")
@Data
@Accessors(chain = true)
public class DcaBUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;


    /**
     * 人事编号
     */
    @ExcelField(value = "人事编号")
    private String userAccount;
    /**
     * 姓名
     */
    @ExcelField(value = "姓名")
    private String userAccountName;


    /**
     * 科室
     */
    @ExcelField(value = "科室")
    private String ks;


    /**
     * 系列
     */
    @ExcelField(value = "系列")
    private String xl;

    /**
     * 现岗位职务
     */
    @ExcelField(value = "现岗位职务")
    private String positionName;

    public String getPositionName() {
        return (zyjsgw == null ? "" : zyjsgw) +(zyjsgw == null||zyjsgw.equals("")?"":"/")+ (zyjsgwLc == null ? "" : zyjsgwLc);
    }

    /**
     * 专业技术岗位时间  appointedDate+ appointedDateLc
     */
    @ExcelField(value = "现职务聘任时间")
    private transient String zygwDate;

    public String getZygwDate() {
        String na = "";
        try {
            if (appointedDate != null) {
                na = DateUtil.format(appointedDate, "yyyyMM");
            }
            if (appointedDateLc != null) {
                na += (na == "" ? "" : "/") + DateUtil.format(appointedDateLc, "yyyyMM");
            }
        } catch (Exception ex) {

        }
        return na;
    }

    /**
     * 申报年度
     */
    @ExcelField(value = "申报年度")
    private String dcaYear;


    /**
     * 所在院系
     */
    private String deptName;


    /**
     * 拟聘岗位职务
     */
    @ExcelField(value = "拟聘岗位职务")
    private String npPositionName;


    /**
     * 手机号
     */
    @ExcelField(value = "手机号")
    private String telephone;
    /**
     * 性别
     */
    @ExcelField(value = "性别")
    private String sexName;


    /**
     * 出生年月
     */
    @ExcelField(value ="出生年月", writeConverter = DateConverter.class)
    private Date birthday;
    private transient String birthdayFrom;
    private transient String birthdayTo;

    @ExcelField(value = "出生日期")
    private transient String birthdaystr;

    public String getBirthdaystr() {
        String na = "";
        try {
            if (birthday != null) {
                na = DateUtil.format(birthday, "yyyyMM");
            }
        } catch (Exception ex) {

        }
        return na;
    }

    private transient String age;

    public String getAge() {
        int age2 = DateUtil.age(birthday, DateUtil.parse(DateUtil.year(DateUtil.date()) + "-10-31"));
        return String.valueOf(age2);
    }

    /**
     * 来校工作时间
     */

    private Date schoolDate;
    private transient String schoolDateFrom;
    private transient String schoolDateTo;

    /**
     * 现专业技术岗位
     */

    private String zyjsgw;

    /**
     * 现专业技术岗位（临床）
     */

    private String zyjsgwLc;


    /**
     * 现从事专业及专长
     */

    private String xcszyjzc;

    /**
     * 聘任时间
     */

    private Date appointedDate;
    private transient String appointedDateFrom;
    private transient String appointedDateTo;

    /**
     * 本人排名
     */

    private Integer patentRanknum;

    /**
     * 是否授权
     */

    private String isAuthority;

    /**
     * 附件
     */

    private String fileId;

    /**
     * 是否转让
     */

    private String isZhuanrang;

    /**
     * 附件地址
     */

    private String fileUrl;

    /**
     * 转让效益
     */

    private String patentGood;


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

    /**
     * 审核人
     */
    @TableField("auditMan")

    private String auditMan;

    /**
     * 审核人姓名
     */
    @TableField("auditManName")

    private String auditManName;

    /**
     * 审核时间
     */
    @TableField("auditDate")

    private Date auditDate;
    private transient String auditDateFrom;
    private transient String auditDateTo;

    /**
     * 审核意见
     */

    @TableField("auditSuggestion")
    private String auditSuggestion;

    /**
     * 是否用于本次评审
     */
    @TableField("IsUse")

    private Boolean IsUse;

    /**
     * 聘任时间（临床）
     */

    private Date appointedDateLc;
    private transient String appointedDateLcFrom;
    private transient String appointedDateLcTo;

    /**
     * 附件（临床）
     */

    private String fileIdLc;

    /**
     * 附件地址（临床）
     */

    private String fileUrlLc;

    /**
     * 员工组
     */
    private String yuangongzu;

    /**
     * 现任岗位级别
     */
    private String xrgwjb;

    /**
     * 现任岗位级别聘任时间
     */
    private Date xrgwjbprsj;

    /**
     * 担(兼)任党政职务
     */
    private String djrdzzw;

    /**
     * 是否通过初级考核
     */
    @ExcelField(value ="是否通过初级考核")
    private Boolean isChujikh;

    /**
     * 通过初级考核时间
     */
    @ExcelField(value ="通过初级考核时间")
    private Date chujikhDate;
    private transient String chujikhDateFrom;
    private transient String chujikhDateTo;

    /**
     * 是否通过中级考核
     */
    @ExcelField(value ="是否通过中级考核")
    private Boolean isZhongjikh;

    /**
     * 通过中级考核时间
     */
    @ExcelField(value ="通过中级考核时间")
    private Date zhongjikhDate;
    private transient String zhongjikhDateFrom;
    private transient String zhongjikhDateTo;



    private transient String doctorDesc;

    private transient List<DcaBAuditdynamic> dcaBAuditdynamicList;

    //这是在查询时候 传递 岗位等级
    private transient String[] gwdjList;

    /**
     * 政治面貌
     */

    @ExcelField(value ="政治面貌")
    private String politicalStatus;

    @ExcelField(value ="职位职级")
    private String staffGrade;
    @ExcelField(value ="职位聘任时间")
    private String staffDate;

    @ExcelField(value ="照片id")
    private String pictureId;
    @ExcelField(value ="照片地址")
    private String pictureUrl;
    /**
     * 身份证号
     */

    @ExcelField(value ="身份证号")
    private String idCard;


    @ExcelField(value = "岗位等级")
    private transient String gwdj;

    public String getGwdj() {
        String name = "";
        if (!StrUtil.hasBlank(gwdj)) {
            return gwdj;
        }
        if (npPositionName == null) {
            return "";
        }
        switch (npPositionName) {
            case "教授主任医师":
                name = "正高";
                break;
            case "教授":
                name = "正高";
                break;
            case "主任医师":
                name = "正高";
                break;
            case "研究员":
                name = "正高";
                break;
            case "主任护师":
                name = "正高";
                break;
            case "主任技师":
                name = "正高";
                break;
            case "主任药师":
                name = "正高";
                break;
            case "教授级高级工程师":
                name = "正高";
                break;
            case "编审":
                name = "正高";
                break;
            case "副教授副主任医师":
                name = "副高";
                break;
            case "副教授":
                name = "副高";
                break;
            case "副主任医师":
                name = "副高";
                break;
            case "副研究员":
                name = "副高";
                break;
            case "副主任护师":
                name = "副高";
                break;
            case "副主任技师":
                name = "副高";
                break;
            case "副主任药师":
                name = "副高";
                break;
            case "高级工程师":
                name = "副高";
                break;
            case "副编审":
                name = "副高";
                break;
        }
        List<String> listCj = new ArrayList<String>() {
            {
                this.add("住院医师");
                this.add("药师");

                this.add("护师");

                this.add("技师");

                this.add("图书管理员");

                this.add("会计员");

                this.add("助理编辑");
                this.add("助理工程师");
                this.add("助理馆员");
                this.add("助理会计师");

            }
        };
        if (listCj.contains(npPositionName)) {
            return "初级";
        }

        List<String> listZC = new ArrayList<String>() {
            {
                this.add("主治医师");
                this.add("主管药师");
                this.add("主管护师");
                this.add("主管技师");
                this.add("编辑");
                this.add("工程师");
                this.add("馆员");
                this.add("会计师");
                this.add("经济师");
                this.add("审计师");
                this.add("助理研究员");
            }
        };
        if (listZC.contains(npPositionName)) {
            return "中级";
        }
        if (npPositionName.equals("二级") || npPositionName.equals("三级")) {
            return "二三级";
        }
        return name;
    }

    public String getXl() {
        String name = "";
        if (npPositionName == null) {
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

    private transient int  applyState;

    public static final String ID = "id";

    public static final String USER_ACCOUNT_NAME = "user_account_name";

    public static final String USER_ACCOUNT = "user_account";

    public static final String KS = "ks";

    public static final String XL = "xl";

    public static final String TELEPHONE = "telephone";

    public static final String DEPT_NAME = "dept_name";

    public static final String POSITION_NAME = "position_name";

    public static final String NP_POSITION_NAME = "np_position_name";

    public static final String SEX_NAME = "sex_name";

    public static final String BIRTHDAY = "birthday";

    public static final String SCHOOL_DATE = "school_date";

    public static final String ZYJSGW = "zyjsgw";

    public static final String ZYJSGW_LC = "zyjsgw_lc";

    public static final String XCSZYJZC = "xcszyjzc";

    public static final String APPOINTED_DATE = "appointed_date";

    public static final String PATENT_RANKNUM = "patent_ranknum";

    public static final String IS_AUTHORITY = "is_authority";

    public static final String FILE_ID = "file_id";

    public static final String IS_ZHUANRANG = "is_zhuanrang";

    public static final String FILE_URL = "file_url";

    public static final String PATENT_GOOD = "patent_good";

    public static final String DCA_YEAR = "dca_year";

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

    public static final String APPOINTED_DATE_LC = "appointed_date_lc";

    public static final String FILE_ID_LC = "file_id_lc";

    public static final String FILE_URL_LC = "file_url_lc";

}