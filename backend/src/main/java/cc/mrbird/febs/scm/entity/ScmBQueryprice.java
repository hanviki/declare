package cc.mrbird.febs.scm.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;


import com.baomidou.mybatisplus.annotation.TableId;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;

/**
 * <p>
 *
 * </p>
 *
 * @author viki
 * @since 2019-12-26
 */

@Excel("scm_b_queryprice")
@Data
@Accessors(chain = true)
public class ScmBQueryprice implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ExcelField(value = "主键")
    private Long id;
    /**
     * 编码
     */
    @ExcelField(value = "编码")
    private String code;
    /**
     * 名字
     */
    @ExcelField(value = "名字")
    private String name;
    /**
     * 物料ID
     */
    @ExcelField(value = "物料ID")
    private String matnrId;
    /**
     * 物料编码
     */
    @ExcelField(value = "物料编码")
    private String matnr;
    /**
     * 物料单位
     */
    @ExcelField(value = "物料单位")
    private String mseht;
    /**
     * 物料名称
     */
    @ExcelField(value = "物料名称")
    private String txz01;
    /**
     * 物料规格
     */
    @ExcelField(value = "物料规格")
    private String spec;
    /**
     * 采购数量
     */
    @ExcelField(value = "采购数量")
    private BigDecimal amount;
    /**
     * 询价日期
     */
    @ExcelField(value = "询价日期")
    private Date queryDate;


    /**
     * 结束日期
     */
    @ExcelField(value = "结束日期")
    private Date endDate;


    /**
     * 询价状态
     */
    @ExcelField(value = "询价状态")
    private Integer queryState;
    /**
     * 询价类型
     */
    @ExcelField(value = "询价类型")
    private Integer queryType;
    /**
     * 部门ID
     */
    @ExcelField(value = "部门ID")
    private Long deptId;
    /**
     * 备注
     */
    @ExcelField(value = "备注")
    private String comments;
    /**
     * 状态
     */
    @ExcelField(value = "状态")
    private Integer state;
    /**
     * 是否删除
     */
    @TableField("IS_DELETEMARK")
    @ExcelField(value = "是否删除")
    private Integer isDeletemark;
    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    @ExcelField(value = "创建时间")
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField("MODIFY_TIME")
    @ExcelField(value = "修改时间")
    private Date modifyTime;
    /**
     * 创建人
     */
    @TableField("CREATE_USER_ID")
    @ExcelField(value = "创建人")
    private Long createUserId;
    /**
     * 修改人
     */
    @TableField("MODIFY_USER_ID")
    @ExcelField(value = "修改人")
    private Long modifyUserId;

    /**
     * 查询询价开始和结束时间使用
     */
    private transient String queryDateFrom;
    private transient String queryDateTo;
    /**
     * 报价状态
     */
    private transient int quoteState;

    /**
     * 查询截止时间使用
     */
    private transient String endDateFrom;
    private transient String endDateTo;

    /**
     * 供应商查询使用
     */
    private  transient  String gysaccount;

    private  transient  int gyscount;

    private transient  int gysdonecount;

    /**
     * 药品编码、名称查询
     */
    private  transient  String keyword;


    public static final String ID = "id";

    public static final String CODE = "code";

    public static final String NAME = "name";

    public static final String MATNR_ID = "matnr_id";

    public static final String MATNR = "matnr";

    public static final String MSEHT = "mseht";

    public static final String TXZ01 = "txz01";

    public static final String SPEC = "spec";

    public static final String AMOUNT = "amount";

    public static final String QUERY_DATE = "query_date";

    public static final String END_DATE = "end_date";

    public static final String QUERY_STATE = "query_state";

    public static final String QUERY_TYPE = "query_type";

    public static final String DEPT_ID = "dept_id";

    public static final String COMMENTS = "comments";

    public static final String STATE = "state";

    public static final String IS_DELETEMARK = "IS_DELETEMARK";

    public static final String CREATE_TIME = "CREATE_TIME";

    public static final String MODIFY_TIME = "MODIFY_TIME";

    public static final String CREATE_USER_ID = "CREATE_USER_ID";

    public static final String MODIFY_USER_ID = "MODIFY_USER_ID";

}