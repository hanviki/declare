package cc.mrbird.febs.scm.entity;

import java.math.BigDecimal;
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
 * 
 * </p>
 *
 * @author viki
 * @since 2020-07-10
 */

@Excel("scm_b_gysfp")
@Data
@Accessors(chain = true)
public class ScmBGysfp implements Serializable{

private static final long serialVersionUID=1L;

    /**
     * 主键
     */
                                @ExcelField(value ="主键")
    private String id;

    /**
     * 发票号码
     */
            @ExcelField(value ="发票号码")
    private String fpHm;

    /**
     * 发票编码
     */
            @ExcelField(value ="发票编码")
    private String fpBm;

    /**
     * 发票金额
     */
            @ExcelField(value ="发票金额")
    private BigDecimal fpAmount;

    /**
     * 审核原因
     */
    @TableField("AuditCause")
            @ExcelField(value ="审核原因")
    private String AuditCause;

    /**
     * 备注
     */
    @TableField("COMMENTS")
            @ExcelField(value ="备注")
    private String comments;

    /**
     * 状态
     */
    @TableField("STATE")
            @ExcelField(value ="状态")
    private Integer state;

    /**
     * 发票日期
     */
    @TableField("fprq")
    @ExcelField(value = "发票日期")
    private Date fprq;
    /**
     * 供应商发票
     */
    @TableField("mater_ID")
            @ExcelField(value ="供应商发票")
    private String materId;

    /**
     * 药厂发票
     */
    @TableField("FILE_ID")
            @ExcelField(value ="药厂发票")
    private String fileId;

    /**
     * 供应商名称
     */
    @TableField("GysName")
            @ExcelField(value ="供应商名称")
    private String GysName;

    /**
     * 供应商账号
     */
    @TableField("GYSACCOUNT")
            @ExcelField(value ="供应商账号")
    private String gysaccount;

    private  transient  String userid;

    /**
     * 院区
     */
    @TableField("werks")
    @ExcelField(value ="院区ID")
    private String werks;

    /**
     * 库房
     */
    @TableField("lgort")
    @ExcelField(value ="库房ID")
    private String lgort;

    /**
     * 院区
     */
    @TableField("werkt")
    @ExcelField(value ="院区")
    private String werkt;

    /**
     * 库房
     */
    @TableField("lgortName")
    @ExcelField(value ="库房")
    private String lgortName;

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

    private  transient  String keyword_gys;

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

    public static final String FP_HM ="fp_hm" ;

    public static final String FP_BM ="fp_bm" ;

    public static final String FP_AMOUNT ="fp_amount" ;

    public static final String AUDITCAUSE ="AuditCause" ;

    public static final String COMMENTS ="COMMENTS" ;

    public static final String STATE ="STATE" ;

    public static final String MATER_ID ="mater_ID" ;

    public static final String FILE_ID ="FILE_ID" ;

    public static final String GYSNAME ="GysName" ;

    public static final String GYSACCOUNT ="GYSACCOUNT" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

        }