package cc.mrbird.febs.dca.entity;

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
 * 
 * </p>
 *
 * @author viki
 * @since 2021-03-18
 */

@Excel("dca_b_report")
@Data
@Accessors(chain = true)
public class DcaBReport implements Serializable{

private static final long serialVersionUID=1L;

    /**
     * 主键
     */
                            
        @ExcelField(value ="主键")
    private String id;

    /**
     * 起止年度
     */
        
        @ExcelField(value ="起止年度")
    private String year;

    /**
     * 排序
     */
        
        @ExcelField(value ="排序")
    private Integer displayIndex;

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
     * 确认顺序号
     */
        
        @ExcelField(value ="确认顺序号")
    private Integer confirmIndex;

    /**
     * 档案袋顺序号
     */
        
        @ExcelField(value ="档案袋顺序号")
    private Integer danganIndex;

    /**
     * 报名顺序号
     */
        
        @ExcelField(value ="报名顺序号")
    private Integer baomingIndex;

    /**
     * 系列
     */
        
        @ExcelField(value ="系列")
    private String xl;

    /**
     * 评审分组
     */
        
        @ExcelField(value ="评审分组")
    private String pingshenfenzu;

    /**
     * 双报标志
     */
        
        @ExcelField(value ="双报标志")
    private String ifshuangbao;

    /**
     * 申报等级
     */
        
        @ExcelField(value ="申报等级")
    private String gwdj;

    /**
     * 科室
     */
        
        @ExcelField(value ="科室")
    private String ks;

    /**
     * 科室分类
     */
        
        @ExcelField(value ="科室分类")
    private String kslb;

    /**
     * 出生年月
     */
        
        @ExcelField(value ="出生年月")
    private String birthdaystr;

    /**
     * 年龄
至20201031
     */
        
        @ExcelField(value ="年龄至20201031")
    private String age;

    /**
     * 性别
     */
        
        @ExcelField(value ="性别")
    private String sexName;

    /**
     * 学历(位)
     */
        
        @ExcelField(value ="学历(位)")
    private String edu;

    /**
     * 毕业时间
     */
        
        @ExcelField(value ="毕业时间")
    private String eduDate;

    /**
     * 现职务名称
     */
        
        @ExcelField(value ="现职务名称")
    private String positionName;

    /**
     * 聘任时间
     */
        
        @ExcelField(value ="聘任时间")
    private String zygwDate;

    /**
     * 申报职称
     */
        
        @ExcelField(value ="申报职称")
    private String npPositionName;

    /**
     * 来院时间
     */
        
        @ExcelField(value ="来院时间")
    private String schoolDate;

    /**
     * 是否起带头或骨干作用
     */
        
        @ExcelField(value ="是否起带头或骨干作用")
    private String ifdaitou;

    /**
     * 医疗评分
     */
        
        @ExcelField(value ="医疗评分")
    private String ylpfbfz;

    /**
     * 教学评分
     */
        
        @ExcelField(value ="教学评分")
    private String jxpf;

    /**
     * 教学科研项目或获奖情况是否符合
     */
        
        @ExcelField(value ="教学科研项目或获奖情况是否符合")
    private String iffuhekeyan;

    /**
     * 第一作者论文情况是否符合
     */
        
        @ExcelField(value ="第一作者论文情况是否符合")
    private String iffuhediyi;

    /**
     * 是否符合必备条件
     */
        
        @ExcelField(value ="是否符合必备条件")
    private String iffuhebibei;

    /**
     * 国家、省部级科研奖名称
     */
        
        @ExcelField(value ="国家、省部级科研奖名称")
    private String sciName;

    /**
     * 国家、省部级科研奖等级
     */
        
        @ExcelField(value ="国家、省部级科研奖等级")
    private String sciDengji;

    /**
     * 国家、省部级科研奖排名
     */
        
        @ExcelField(value ="国家、省部级科研奖排名")
    private String sciRanknum;

    /**
     * 教学名称
     */
        
        @ExcelField(value ="教学名称")
    private String teachName;

    /**
     * 教学等级
     */
        
        @ExcelField(value ="教学等级")
    private String teachDengji;

    /**
     * 教学排名
     */
        
        @ExcelField(value ="教学排名")
    private String teachRanknum;

    /**
     * 发明专利项数
     */
        
        @ExcelField(value ="发明专利项数")
    private String patentNum;

    /**
     * 实施转让费
     */
        
        @ExcelField(value ="实施转让费")
    private String patentFund;

    /**
     * A 类
     */
        
        @ExcelField(value ="A 类")
    private String publishA;

    /**
     * B 类
     */
        
        @ExcelField(value ="B 类")
    private String publishB;

    /**
     * C 类
     */
        
        @ExcelField(value ="C 类")
    private String publishC;

    /**
     * D 类
     */
        
        @ExcelField(value ="D 类")
    private String publishD;

    /**
     * E 类
     */
        
        @ExcelField(value ="E 类")
    private String publishE;

    /**
     * F 类
     */
        
        @ExcelField(value ="F 类")
    private String publishF;

    /**
     * D类以上
     */
        
        @ExcelField(value ="D类以上")
    private String publishDup;

    /**
     * E类以上
     */
        
        @ExcelField(value ="E类以上")
    private String publishEup;

    /**
     * F类以上
     */
        
        @ExcelField(value ="F类以上")
    private String publishFup;

    /**
     * 出版书类别
     */
        
        @ExcelField(value ="出版书类别")
    private String publicarticle1;

    /**
     * 承担字数(万)
     */
        
        @ExcelField(value ="承担字数(万)")
    private String publicarticle2;

    /**
     * 教学质量奖与成果奖名称
     */
        
        @ExcelField(value ="教学质量奖与成果奖名称")
    private String schoolprizeName;

    /**
     * 等级
     */
        
        @ExcelField(value ="等级")
    private String schoolprizeDengji;

    /**
     * 排名
     */
        
        @ExcelField(value ="排名")
    private String schoolprizeRanknum;

    /**
     * 时间
     */
        
        @ExcelField(value ="时间")
    private String schoolprizeDate;

    /**
     * 精品课程名称
     */
        
        @ExcelField(value ="精品课程名称")
    private String courseName;

    /**
     * 排名
     */
        
        @ExcelField(value ="排名")
    private String courseRanknum;

    /**
     * 时间
     */
        
        @ExcelField(value ="时间")
    private String courseDate;

    /**
     * 教学竞赛获奖奖项级别
     */
        
        @ExcelField(value ="教学竞赛获奖奖项级别")
    private String youngName;

    /**
     * 等级
     */
        
        @ExcelField(value ="等级")
    private String youngDengji;

    /**
     * 排名
     */
        
        @ExcelField(value ="排名")
    private String youngRanknum;

    /**
     * 时间
     */
        
        @ExcelField(value ="时间")
    private String youngDate;

    /**
     * 科研项目教改项目类别
     */
        
        @ExcelField(value ="科研项目教改项目类别")
    private String sciDjlb;

    /**
     * 经费
     */
        
        @ExcelField(value ="经费")
    private String sciDjfund;

    /**
     * 排名
     */
        
        @ExcelField(value ="排名")
    private String sciDjranknum;

    /**
     * 实到校单项科研经费类别
     */
        
        @ExcelField(value ="实到校单项科研经费类别")
    private String sciJflb;

    /**
     * 实到校单项科研经费类别
     */
        
        @ExcelField(value ="实到校单项科研经费类别")
    private String sciJffund;

    /**
     * 排名
     */
        
        @ExcelField(value ="排名")
    private String sciJfranknum;

    /**
     * 医疗评分等级
     */
        
        @ExcelField(value ="医疗评分等级")
    private String ylpfdj;

    /**
     * 教学评分等级
     */
        
        @ExcelField(value ="教学评分等级")
    private String jxpfdj;

    /**
     * 评分合计
     */
        
        @ExcelField(value ="评分合计")
    private String pfHeji;

    /**
     * 是否担任一年辅导员或班主任
     */
        
        @ExcelField(value ="是否担任一年辅导员或班主任")
    private String tutor;

    /**
     * 申报类型
     */
        
        @ExcelField(value ="申报类型")
    private String sblx;

    /**
     * 达到选择条件一之第几条
     */
        
        @ExcelField(value ="达到选择条件一之第几条")
    private String choosepos;

    /**
     * 材料审核结果
     */
        
        @ExcelField(value ="材料审核结果")
    private String clshjg;

    /**
     * 拟退原因
     */
        
        @ExcelField(value ="拟退原因")
    private String ntyy;

    /**
     * 科室排名
     */
        
        @ExcelField(value ="科室排名")
    private String ksrank;

    /**
     * 教师资格证
     */
        
        @ExcelField(value ="教师资格证")
    private String teacherQualify;

    /**
     * 内聘情况
     */
        
        @ExcelField(value ="内聘情况")
    private String npqk;

    /**
     * 出国情况
     */
        
        @ExcelField(value ="出国情况")
    private String borad;

    /**
     * 备注
     */
        
        @ExcelField(value ="备注")
    private String note;

    /**
     * 援助情况
     */
        
        @ExcelField(value ="援助情况")
    private String help;

    /**
     * 联系方式
     */
        
        @ExcelField(value ="联系方式")
    private String telephone;

    /**
     * 等级
     */
        
        @ExcelField(value ="等级")
    private String courseDengji;

    /**
     * 状态
     */
        
        @ExcelField(value ="状态")
    private Integer state;

    /**
     * 员工组
     */
        
        @ExcelField(value ="员工组")
    private String yuangongzu;

    /**
     * 中专毕业时间
     */
        
        @ExcelField(value ="中专毕业时间")
    private String zzbysj;

    /**
     * 大专毕业时间
     */
        
        @ExcelField(value ="大专毕业时间")
    private String dzbysj;

    /**
     * 本科毕业时间
     */
        
        @ExcelField(value ="本科毕业时间")
    private String bkbysj;

    /**
     * 硕士毕业时间
     */
        
        @ExcelField(value ="硕士毕业时间")
    private String ssbysj;

    /**
     * 博士毕业时间
     */
        
        @ExcelField(value ="博士毕业时间")
    private String bsbysj;

    /**
     * 岗前培训情况
     */
        
        @ExcelField(value ="岗前培训情况")
    private String gqpxqk;

    /**
     * 规范化医师培训情况
     */
        
        @ExcelField(value ="规范化医师培训情况")
    private String gfhyspxqk;

    /**
     * 中级水平能力测试情况
     */
        
        @ExcelField(value ="中级水平能力测试情况")
    private String zjspnlceqk;

    /**
     * 申报类型
     */
        
        @ExcelField(value ="申报类型")
    private String sbleix;

    /**
     * 入职前最高学历
     */
        
        @ExcelField(value ="入职前最高学历")
    private String rzqedu;

    /**
     * 法定资质
     */
        
        @ExcelField(value ="法定资质")
    private String fdzz;

    /**
     * 专业技术职务资格名称
     */
        
        @ExcelField(value ="专业技术职务资格名称")
    private String zyjszwzg;

    /**
     * 专业技术职务资格时间
     */
        
        @ExcelField(value ="专业技术职务资格时间")
    private String zyjszwzgsj;

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

    /**
     * 审核结果
     */
    @TableField("auditMan")
        
        @ExcelField(value ="审核结果")
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
        
        @ExcelField(value ="审核时间", writeConverter = DateConverter.class)
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
     *  是否 个人大表数据查询
     */
    private transient String isSingel;

    private transient List<DcaBAuditdynamic> dcaBAuditdynamicList;

    private transient int  applyState;

    public static final String ID ="id" ;

    public static final String YEAR ="year" ;

    public static final String DISPLAY_INDEX ="display_index" ;

    public static final String USER_ACCOUNT_NAME ="user_account_name" ;

    public static final String USER_ACCOUNT ="user_account" ;

    public static final String CONFIRM_INDEX ="confirm_index" ;

    public static final String DANGAN_INDEX ="dangan_index" ;

    public static final String BAOMING_INDEX ="baoming_index" ;

    public static final String XL ="xl" ;

    public static final String PINGSHENFENZU ="pingshenfenzu" ;

    public static final String IFSHUANGBAO ="ifshuangbao" ;

    public static final String GWDJ ="gwdj" ;

    public static final String KS ="ks" ;

    public static final String KSLB ="kslb" ;

    public static final String BIRTHDAYSTR ="birthdaystr" ;

    public static final String AGE ="age" ;

    public static final String SEX_NAME ="sex_name" ;

    public static final String EDU ="edu" ;

    public static final String EDU_DATE ="edu_date" ;

    public static final String POSITION_NAME ="position_name" ;

    public static final String ZYGW_DATE ="zygw_date" ;

    public static final String NP_POSITION_NAME ="np_position_name" ;

    public static final String SCHOOL_DATE ="school_date" ;

    public static final String IFDAITOU ="ifdaitou" ;

    public static final String YLPFBFZ ="ylpfbfz" ;

    public static final String JXPF ="jxpf" ;

    public static final String IFFUHEKEYAN ="iffuhekeyan" ;

    public static final String IFFUHEDIYI ="iffuhediyi" ;

    public static final String IFFUHEBIBEI ="iffuhebibei" ;

    public static final String SCI_NAME ="sci_name" ;

    public static final String SCI_DENGJI ="sci_dengji" ;

    public static final String SCI_RANKNUM ="sci_ranknum" ;

    public static final String TEACH_NAME ="teach_name" ;

    public static final String TEACH_DENGJI ="teach_dengji" ;

    public static final String TEACH_RANKNUM ="teach_ranknum" ;

    public static final String PATENT_NUM ="patent_num" ;

    public static final String PATENT_FUND ="patent_fund" ;

    public static final String PUBLISH_A ="publish_a" ;

    public static final String PUBLISH_B ="publish_b" ;

    public static final String PUBLISH_C ="publish_c" ;

    public static final String PUBLISH_D ="publish_d" ;

    public static final String PUBLISH_E ="publish_e" ;

    public static final String PUBLISH_F ="publish_f" ;

    public static final String PUBLISH_DUP ="publish_dup" ;

    public static final String PUBLISH_EUP ="publish_eup" ;

    public static final String PUBLISH_FUP ="publish_fup" ;

    public static final String PUBLICARTICLE1 ="publicarticle1" ;

    public static final String PUBLICARTICLE2 ="publicarticle2" ;

    public static final String SCHOOLPRIZE_NAME ="schoolprize_name" ;

    public static final String SCHOOLPRIZE_DENGJI ="schoolprize_dengji" ;

    public static final String SCHOOLPRIZE_RANKNUM ="schoolprize_ranknum" ;

    public static final String SCHOOLPRIZE_DATE ="schoolprize_date" ;

    public static final String COURSE_NAME ="course_name" ;

    public static final String COURSE_RANKNUM ="course_ranknum" ;

    public static final String COURSE_DATE ="course_date" ;

    public static final String YOUNG_NAME ="young_name" ;

    public static final String YOUNG_DENGJI ="young_dengji" ;

    public static final String YOUNG_RANKNUM ="young_ranknum" ;

    public static final String YOUNG_DATE ="young_date" ;

    public static final String SCI_DJLB ="sci_djlb" ;

    public static final String SCI_DJFUND ="sci_djfund" ;

    public static final String SCI_DJRANKNUM ="sci_djranknum" ;

    public static final String SCI_JFLB ="sci_jflb" ;

    public static final String SCI_JFFUND ="sci_jffund" ;

    public static final String SCI_JFRANKNUM ="sci_jfranknum" ;

    public static final String YLPFDJ ="ylpfdj" ;

    public static final String JXPFDJ ="jxpfdj" ;

    public static final String PF_HEJI ="pf_heji" ;

    public static final String TUTOR ="tutor" ;

    public static final String SBLX ="sblx" ;

    public static final String CHOOSEPOS ="choosepos" ;

    public static final String CLSHJG ="clshjg" ;

    public static final String NTYY ="ntyy" ;

    public static final String KSRANK ="ksrank" ;

    public static final String TEACHER_QUALIFY ="teacher_qualify" ;

    public static final String NPQK ="npqk" ;

    public static final String BORAD ="borad" ;

    public static final String NOTE ="note" ;

    public static final String HELP ="help" ;

    public static final String TELEPHONE ="telephone" ;

    public static final String COURSE_DENGJI ="course_dengji" ;

    public static final String STATE ="state" ;

    public static final String YUANGONGZU ="yuangongzu" ;

    public static final String ZZBYSJ ="zzbysj" ;

    public static final String DZBYSJ ="dzbysj" ;

    public static final String BKBYSJ ="bkbysj" ;

    public static final String SSBYSJ ="ssbysj" ;

    public static final String BSBYSJ ="bsbysj" ;

    public static final String GQPXQK ="gqpxqk" ;

    public static final String GFHYSPXQK ="gfhyspxqk" ;

    public static final String ZJSPNLCEQK ="zjspnlceqk" ;

    public static final String SBLEIX ="sbleix" ;

    public static final String RZQEDU ="rzqedu" ;

    public static final String FDZZ ="fdzz" ;

    public static final String ZYJSZWZG ="zyjszwzg" ;

    public static final String ZYJSZWZGSJ ="zyjszwzgsj" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

    public static final String AUDITMAN ="auditMan" ;

    public static final String AUDITMANNAME ="auditManName" ;

    public static final String AUDITDATE ="auditDate" ;

    public static final String AUDITSUGGESTION ="auditSuggestion" ;

    public static final String ISUSE ="IsUse" ;

        }