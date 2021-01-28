package cc.mrbird.febs.doctor.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import cc.mrbird.febs.common.converter.DateConverter;
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
 * @since 2021-01-12
 */

@Excel("dca_b_doc_user")
@Data
@Accessors(chain = true)
public class DcaBDocUser implements Serializable{

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
     * 部门描述
     */
            @ExcelField(value ="部门描述")
    private String deptDesc;

    /**
     * 员工工号
     */
            @ExcelField(value ="员工工号")
    private String yggh;

    /**
     * 手机号
     */
            @ExcelField(value ="手机号")
    private String telephone;

    /**
     * 华科人事编号
     */
            @ExcelField(value ="华科人事编号")
    private String hkrsbh;

    /**
     * 招生类型
     */
            @ExcelField(value ="招生类型")
    private String zslx;

    /**
     * 流动站
     */
            @ExcelField(value ="流动站")
    private String ldz;

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
     * 来院工作时间
     */
            @ExcelField(value ="来院工作时间", writeConverter = DateConverter.class)
    private Date schoolDate;
    private transient String schoolDateFrom;
    private transient String schoolDateTo;

    /**
     * 身份
     */
            @ExcelField(value ="身份")
    private String shenfen;

    /**
     * 学历
     */
            @ExcelField(value ="学历")
    private String xueli;

    /**
     * 博士导师
     */
            @ExcelField(value ="博士导师")
    private String boshidaoshi;

    /**
     * 毕业时间
     */
            @ExcelField(value ="毕业时间", writeConverter = DateConverter.class)
    private Date biyeDate;
    private transient String biyeDateFrom;
    private transient String biyeDateTo;

    /**
     * 第几个聘期
     */
            @ExcelField(value ="第几个聘期")
    private Integer pinqiRanknum;

    /**
     * 是否授权
     */
            @ExcelField(value ="是否授权")
    private String isAuthority;

    /**
     * 附件
     */
            @ExcelField(value ="附件")
    private String fileId;

    /**
     * 是否转让
     */
            @ExcelField(value ="是否转让")
    private String isZhuanrang;

    /**
     * 附件地址
     */
            @ExcelField(value ="附件地址")
    private String fileUrl;

    /**
     * 合作导师
     */
            @ExcelField(value ="合作导师")
    private String hezuodaoshi;

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
     * 排序
     */
            @ExcelField(value ="排序")
    private Integer displayIndex;

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

    /**
     * 毕业院校
     */
            @ExcelField(value ="毕业院校")
    private String biyexuexiao;

    /**
     * 专业
     */
            @ExcelField(value ="专业")
    private String zhuanye;

    /**
     * 进站日期
     */
            @ExcelField(value ="进站日期", writeConverter = DateConverter.class)
    private Date inDate;
    private transient String inDateFrom;
    private transient String inDateTo;

    /**
     * 站内工作目标
     */
            @ExcelField(value ="站内工作目标")
    private String goal;

    /**
     * 是否用于本次评审
     */
    @TableField("IsUse")
            @ExcelField(value ="是否用于本次评审")
    private Boolean IsUse;

    /**
     * 延期时间
     */
            @ExcelField(value ="延期时间", writeConverter = DateConverter.class)
    private Date yqDate;
    private transient String yqDateFrom;
    private transient String yqDateTo;

    /**
     * 附件（临床）
     */
            @ExcelField(value ="附件（临床）")
    private String fileIdLc;

    /**
     * 附件地址（临床）
     */
            @ExcelField(value ="附件地址（临床）")
    private String fileUrlLc;

    /**
     * 员工组
     */
            @ExcelField(value ="员工组")
    private String yuangongzu;

    /**
     * 民族
     */
            @ExcelField(value ="民族")
    private String nation;

    /**
     * 现任岗位级别聘任时间
     */
            @ExcelField(value ="现任岗位级别聘任时间", writeConverter = DateConverter.class)
    private Date xrgwjbprsj;
    private transient String xrgwjbprsjFrom;
    private transient String xrgwjbprsjTo;

    /**
     * 籍贯
     */
            @ExcelField(value ="籍贯")
    private String jiguan;

            private String dcaYear;

    /**
     * 政治面貌
     */
            @ExcelField(value ="政治面貌")
    private String politicalStatus;

    /**
     * 国籍
     */
            @ExcelField(value ="国籍")
    private String country;

    /**
     * 身份证号
     */
            @ExcelField(value ="身份证号")
    private String idCard;

    /**
     * 职位时间
     */
            @ExcelField(value ="职位时间", writeConverter = DateConverter.class)
    private Date staffDate;
    private transient String staffDateFrom;
    private transient String staffDateTo;

    /**
     * 照片id
     */
            @ExcelField(value ="照片id")
    private String pictureId;

    /**
     * 照片url
     */
            @ExcelField(value ="照片url")
    private String pictureUrl;

    /**
     * 聘期
     */
            @ExcelField(value ="聘期")
    private String pinqi;



    public static final String ID ="id" ;

    public static final String USER_ACCOUNT_NAME ="user_account_name" ;

    public static final String USER_ACCOUNT ="user_account" ;

    public static final String DEPT_DESC ="dept_desc" ;

    public static final String YGGH ="yggh" ;

    public static final String TELEPHONE ="telephone" ;

    public static final String HKRSBH ="hkrsbh" ;

    public static final String ZSLX ="zslx" ;

    public static final String LDZ ="ldz" ;

    public static final String SEX_NAME ="sex_name" ;

    public static final String BIRTHDAY ="birthday" ;

    public static final String SCHOOL_DATE ="school_date" ;

    public static final String SHENFEN ="shenfen" ;

    public static final String XUELI ="xueli" ;

    public static final String BOSHIDAOSHI ="boshidaoshi" ;

    public static final String BIYE_DATE ="biye_date" ;

    public static final String PINQI_RANKNUM ="pinqi_ranknum" ;

    public static final String IS_AUTHORITY ="is_authority" ;

    public static final String FILE_ID ="file_id" ;

    public static final String IS_ZHUANRANG ="is_zhuanrang" ;

    public static final String FILE_URL ="file_url" ;

    public static final String HEZUODAOSHI ="hezuodaoshi" ;

    public static final String STATE ="state" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String DISPLAY_INDEX ="display_index" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

    public static final String BIYEXUEXIAO ="biyexuexiao" ;

    public static final String ZHUANYE ="zhuanye" ;

    public static final String IN_DATE ="in_date" ;

    public static final String GOAL ="goal" ;

    public static final String ISUSE ="IsUse" ;

    public static final String YQ_DATE ="yq_date" ;

    public static final String FILE_ID_LC ="file_id_lc" ;

    public static final String FILE_URL_LC ="file_url_lc" ;

    public static final String YUANGONGZU ="yuangongzu" ;

    public static final String NATION ="nation" ;

    public static final String XRGWJBPRSJ ="xrgwjbprsj" ;

    public static final String JIGUAN ="jiguan" ;

    public static final String DCA_YEAR ="dca_year" ;

    public static final String POLITICAL_STATUS ="political_status" ;

    public static final String COUNTRY ="country" ;

    public static final String ID_CARD ="id_card" ;

    public static final String STAFF_DATE ="staff_date" ;

    public static final String PICTURE_ID ="picture_id" ;

    public static final String PICTURE_URL ="picture_url" ;

    public static final String PINQI ="pinqi" ;

        }