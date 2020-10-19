package cc.mrbird.febs.dca.entity;

import lombok.Data;

import java.util.List;

@Data
public class CustomApplyFirst {
    /**
     * 姓名
     */
    private String name;

    /**
     *  人事编号
     */
    private String rsbh;

    /**
     * 所在院系
     */
    private String szyx;

    /**
     * 现岗位职务
     */
  private  String xgwzw;

    /**
     * 岗位类别
     */
    private  String gwlb;

    /**
     * 教师资格证编号及获得时间
     */
    private String jszgzbhjhdsj;

    /**
     * 担任辅导员教师班主任及考核情况
     */
    private String drfdyjsbzrjkhqk;
    /**
     * 完成上一聘期工作任务情况
     */

    private  String wcsypqgzrwqk;
    /**
     * 拟聘岗位职务
     */
    private  String npgwzw;

    /**
     * 性别
     */
    private String sex;

    /**
     * 出生年月
     */
   private String birthday;

    /**
     * 现专业技术岗位
     */
    private  String xzyjsgw;

    /**
     * 聘任时间
     */
    private String prsj;

    /**
     * 来校工作时间
     */
    private String lxgzsj;

    /**
     * 现从事专业及专长
     */
    private String xcszyjzc;

    /**
     * 社会兼职
     */
    private String shjz;

    /**
     * 何时何地受奖励及处分
     */
    private String hshdshjljcf;

    /**
     * 近5年考核情况
     */
    private String j5nkhqk;

    /**
     * 主要学习及工作经历
     */
    private List<DcaBEducationexperice> dcaBEducationexpericeList;

    /**
     * 个人细想政治及师德师风表现情况
     */
    private String grsxzzjsdsf;

    /**
     * 申报拟聘岗位理由
     */
private  String sbnpgwly;

    /**
     * 其他工作及成果
     */
    private String qtgzjcg;

    /**
     * 拟聘岗位工作思路及预期目标
     */
    private String npgwgzsljyqmb;
    /**
     * 任现职以来完成教学、人才培养情况
     */
private List<DcaBTeachtalent> dcaBTeachtalentList;

/**
 * 任职以来发表的论文、出版著作和教材
 */
private  List<DcaBEssaypublish> dcaBEssaypublishList;

    /**
     * 任现职以来承担的主要科研项目
     */
    private List<DcaBSciencesearch> dcaBSciencesearchList;

    /**
     * 任现职以来科研获奖情况
     */
    private List<DcaBScientificprize> dcaBScientificprizeList;

    /**
     * 任现职以来完成研究生教学、人才培养情况
     */
    private  List<DcaBTalent> dcaBTalentList;

    /**
     * 任现职以来申请专利情况
     */
    private List<DcaBPatent> dcaBPatentList;

    /**
     * 任现职以来独立指导研究生情况
     */
    private  DcaBGraduate dcaBGraduate;

    /**
     * 完成本科教学情况
     */
    private  List<DcaBUndergraduate> dcaBUndergraduateList;

    /**
     * 任现职以来发表的教学论文、出版教材
     */
    private List<DcaBPaperspublish> dcaBPaperspublishList;

    /**
     * 任现职以来发表的科研论文、出版著作等
     */
    private List<DcaBSciencepublish> dcaBSciencepublishList;




    /**
     * 本科教学改革及建设项目
     */
    private  List<DcaBInnovatebuild> dcaBInnovatebuildList;


    /**
     * 个人总结
     */
    private  String grzj;

}
