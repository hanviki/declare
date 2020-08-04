package cc.mrbird.febs.scm.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;


import lombok.Data;
import lombok.experimental.Accessors;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 
 * </p>
 *
 * @author viki
 * @since 2020-06-03
 */

@Excel("scm_b_gysmatersend")
@Data
@Accessors(chain = true)
public class ScmBGysmatersend implements Serializable{

private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableField("ID")
            @ExcelField(value ="主键")
private String id;
    /**
     * 编码
     */
    @TableField("CODE")
            @ExcelField(value ="编码")
private String code;
    /**
     * 姓名
     */
    @TableField("NAME")
            @ExcelField(value ="姓名")
private String name;
    /**
     * 供应商账号
     */
    @TableField("GYSACCOUNT")
            @ExcelField(value ="供应商账号")
private String gysaccount;
    /**
     * 药品ID
     */
    @TableField("MATER_ID")
            @ExcelField(value ="药品ID")
private String materId;
    /**
     * 药品编码
     */
            @ExcelField(value ="药品编码")
private String matnr;
    /**
     * 批次号
     */
    @TableField("CHARGE")
            @ExcelField(value ="批次号")
private String charge;
    /**
     * 配送开始时间
     */
    @TableField("Send_Start_Time")
            @ExcelField(value ="配送开始时间")
private Date sendStartTime;
    /**
     * 配送结束时间
     */
    @TableField("Send_END_Time")
            @ExcelField(value ="配送结束时间")
private Date sendEndTime;

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
     * 附件ID
     */
    @TableField("FILE_ID")
            @ExcelField(value ="附件ID")
private String fileId;
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
    /**
     * 修改时间
     */
    @TableField("MODIFY_TIME")
            @ExcelField(value ="修改时间")
private Date modifyTime;
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
     * 物料类型
     */
    @TableField("MTART")
            @ExcelField(value ="物料类型")
private String mtart;
    /**
     * 单位描述
     */
    @TableField("MSEHT")
            @ExcelField(value ="单位描述")
private String mseht;
    /**
     * 物料描述
     */
    @TableField("TXZ01")
            @ExcelField(value ="物料描述")
private String txz01;
    /**
     * 生产厂家
     */
    @TableField("PRODUCE_AREA")
            @ExcelField(value ="生产厂家")
private String produceArea;
    /**
     * 规格
     */
    @TableField("SPEC")
            @ExcelField(value ="规格")
private String spec;
    /**
     * 拼音码
     */
    @TableField("SPELL_CODE")
            @ExcelField(value ="拼音码")
private String spellCode;
    /**
     * 审核原因
     */
    @TableField("AuditCause")
            @ExcelField(value ="审核原因")
private String AuditCause;

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


    public static final String ID ="ID" ;

    public static final String CODE ="CODE" ;

    public static final String NAME ="NAME" ;

    public static final String GYSACCOUNT ="GYSACCOUNT" ;

    public static final String MATER_ID ="MATER_ID" ;

    public static final String MATNR ="matnr" ;

    public static final String CHARGE ="CHARGE" ;

    public static final String SEND_START_TIME ="Send_Start_Time" ;

    public static final String SEND_END_TIME ="Send_END_Time" ;

    public static final String COMMENTS ="COMMENTS" ;

    public static final String STATE ="STATE" ;

    public static final String FILE_ID ="FILE_ID" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

    public static final String MTART ="MTART" ;

    public static final String MSEHT ="MSEHT" ;

    public static final String TXZ01 ="TXZ01" ;

    public static final String PRODUCE_AREA ="PRODUCE_AREA" ;

    public static final String SPEC ="SPEC" ;

    public static final String SPELL_CODE ="SPELL_CODE" ;

    public static final String AUDITCAUSE ="AuditCause" ;

        }