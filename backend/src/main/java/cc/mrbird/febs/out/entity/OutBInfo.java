package cc.mrbird.febs.out.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * 接口投票
 * </p>
 *
 * @author viki
 * @since 2020-12-01
 */

@Excel("out_b_info")
@Data
@Accessors(chain = true)
public class OutBInfo implements Serializable{

private static final long serialVersionUID=1L;

                    @TableId(value = "id" , type = IdType.AUTO)
                    private Long id;

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
     * 申报年度
     */
            @ExcelField(value ="申报年度")
    private String dcayear;

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



    public static final String ID ="id" ;

    public static final String TPZB ="tpzb" ;

    public static final String DYZC ="dyzc" ;

    public static final String TPBT ="tpbt" ;

    public static final String XUHAO ="xuhao" ;

    public static final String ZGBM ="zgbm" ;

    public static final String DCAYEAR ="dcayear" ;

    public static final String KS ="ks" ;

    public static final String NAME ="name" ;

    public static final String SBZC ="sbzc" ;

    public static final String ZBBZ ="zbbz" ;

    public static final String TOTALNUM ="totalnum" ;

    public static final String XKZNUM ="xkznum" ;

    public static final String HOSPITALNUM ="hospitalnum" ;

    public static final String SQLB ="sqlb" ;

    public static final String SFZZB ="sfzzb" ;

    public static final String STATE ="state" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

        }