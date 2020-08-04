package cc.mrbird.febs.scm.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
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
 * VIEW
 * </p>
 *
 * @author viki
 * @since 2020-07-13
 */

@Excel("v_scmbgysfpuser")
@Data
@Accessors(chain = true)
public class VScmbgysfpuser implements Serializable{

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
     * 院区ID
     */
            @ExcelField(value ="院区ID")
    private String werks;

    /**
     * 院区
     */
            @ExcelField(value ="院区")
    private String werkt;

    /**
     * 库房ID
     */
            @ExcelField(value ="库房ID")
    private String lgort;

    private Long userid;

    /**
     * 库房
     */
    @TableField("lgortName")
            @ExcelField(value ="库房")
    private String lgortName;

    private  transient  String keyword_gys;

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

    /**
     * 发票日期
     */
            @ExcelField(value ="发票日期")
    private Date fprq;
    private transient String fprqFrom;
    private transient String fprqTo;



    public static final String ID ="id" ;

    public static final String FP_HM ="fp_hm" ;

    public static final String FP_BM ="fp_bm" ;

    public static final String FP_AMOUNT ="fp_amount" ;

    public static final String AUDITCAUSE ="AuditCause" ;

    public static final String WERKS ="werks" ;

    public static final String WERKT ="werkt" ;

    public static final String LGORT ="lgort" ;

    public static final String LGORTNAME ="lgortName" ;

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

    public static final String FPRQ ="fprq" ;

        }