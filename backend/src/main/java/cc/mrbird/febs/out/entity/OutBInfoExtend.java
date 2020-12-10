package cc.mrbird.febs.out.entity;

import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

@Data
@Excel("投票")
public class OutBInfoExtend {
    /**
     * 申报年度
     */
    @ExcelField(value ="申报年度")
    private String dcayear;
    /**
     * 投票组别
     */
    @ExcelField(value ="投票组别")
    private String tpzb;

    /**
     * 对应职称
     */
    @ExcelField(value ="对应职称")
    private String dyzc;

    /**
     * 投票标题
     */
    @ExcelField(value ="投票标题")
    private String tpbt;

    /**
     * 序号
     */
    @ExcelField(value ="序号")
    private String xuhao;

    /**
     * 职工编码
     */
    @ExcelField(value ="职工编码")
    private String zgbm;



    /**
     * 科室
     */
    @ExcelField(value ="科室")
    private String ks;

    /**
     * 姓名
     */
    @ExcelField(value ="姓名")
    private String name;

    /**
     * 申报职称
     */
    @ExcelField(value ="申报职称")
    private String sbzc;

    /**
     * 指标备注
     */
    @ExcelField(value ="指标备注")
    private String zbbz;

    /**
     * 总人数
     */
    @ExcelField(value ="总人数")
    private Integer totalnum;

    /**
     * 学科组选出人数
     */
    @ExcelField(value ="学科组选出人数")
    private Integer xkznum;

    /**
     * 医院评审委员会选出人数
     */
    @ExcelField(value ="医院评审委员会选出人数")
    private Integer hospitalnum;

    /**
     * 申请类别
     */
    @ExcelField(value ="申请类别")
    private String sqlb;

    /**
     * 是否占指标
     */
    @ExcelField(value ="是否占指标")
    private String sfzzb;

}
