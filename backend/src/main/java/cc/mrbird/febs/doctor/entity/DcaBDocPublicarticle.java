package cc.mrbird.febs.doctor.entity;

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
import cc.mrbird.febs.common.converter.*;

/**
 * <p>
 * 
 * </p>
 *
 * @author viki
 * @since 2021-01-13
 */

@Excel("dca_b_doc_publicarticle")
@Data
@Accessors(chain = true)
public class DcaBDocPublicarticle implements Serializable{

private static final long serialVersionUID=1L;

    /**
     * 主键
     */
                            
        @ExcelField(value ="主键")
    private String id;

    /**
     * 附件
     */
        
        @ExcelField(value ="附件")
    private String fileId;

    /**
     * 排序
     */
        
        @ExcelField(value ="排序")
    private Integer displayIndex;

    /**
     * 附件地址
     */
        
        @ExcelField(value ="附件地址")
    private String fileUrl;

    /**
     * 姓名
     */
        
        @ExcelField(value ="姓名")
    private String userAccountName;

    /**
     * 用户帐号
     */
        
        @ExcelField(value ="用户帐号")
    private String userAccount;

    /**
     * 著作类型
     */
        
        @ExcelField(value ="著作类型")
    private String zzlx;

    /**
     * 著作名称
     */
        
        @ExcelField(value ="著作名称")
    private String zzmc;

    /**
     * 著者身份
     */
        
        @ExcelField(value ="著者身份")
    private String zzsf;

    /**
     * 出版时间
     */
        
        @ExcelField(value ="出版时间", writeConverter = DateConverter.class)
    private Date cbDate;
    private transient String cbDateFrom;
    private transient String cbDateTo;

    /**
     * 第几版
     */
        
        @ExcelField(value ="第几版")
    private Integer ranknum;

    /**
     *  第几次印刷
     */
        
        @ExcelField(value =" 第几次印刷")
    private Integer printnum;

    /**
     *  书号（ISNB号）
     */
        
        @ExcelField(value =" 书号（ISNB号）")
    private String bookNo;

    /**
     * 出版社名称
     */
        
        @ExcelField(value ="出版社名称")
    private String cbsmc;

    /**
     * 作为第一编写人编写章节名称
     */
        
        @ExcelField(value ="作为第一编写人编写章节名称")
    private String bxzjmc;

    /**
     * 作为第一编写人编写章节起止页
     */
        
        @ExcelField(value ="作为第一编写人编写章节起止页")
    private String bxwzqzy;

    /**
     * 作为第一编写人编写字数合计
     */
        
        @ExcelField(value ="作为第一编写人编写字数合计")
    private BigDecimal bxwzzshj;

    /**
     * 作为第一编写人编写字数合计（单位：万字）
     */
        
        @ExcelField(value ="作为第一编写人编写字数合计（单位：万字）")
    private BigDecimal cdzs;

    /**
     * 审核状态
     */
        
        @ExcelField(value ="审核状态")
    private Integer auditState;

    /**
     * 状态
     */
        
        @ExcelField(value ="状态")
    private Integer state;

    /**
     * 序号
     */
        
        @ExcelField(value ="序号")
    private Integer auditXuhao;

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
     * 审核人
     */
    @TableField("auditMan")
        
        @ExcelField(value ="审核人")
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



    public static final String ID ="id" ;

    public static final String FILE_ID ="file_id" ;

    public static final String DISPLAY_INDEX ="display_index" ;

    public static final String FILE_URL ="file_url" ;

    public static final String USER_ACCOUNT_NAME ="user_account_name" ;

    public static final String USER_ACCOUNT ="user_account" ;

    public static final String ZZLX ="zzlx" ;

    public static final String ZZMC ="zzmc" ;

    public static final String ZZSF ="zzsf" ;

    public static final String CB_DATE ="cb_date" ;

    public static final String RANKNUM ="ranknum" ;

    public static final String PRINTNUM ="printnum" ;

    public static final String BOOK_NO ="book_no" ;

    public static final String CBSMC ="cbsmc" ;

    public static final String BXZJMC ="bxzjmc" ;

    public static final String BXWZQZY ="bxwzqzy" ;

    public static final String BXWZZSHJ ="bxwzzshj" ;

    public static final String CDZS ="cdzs" ;

    public static final String AUDIT_STATE ="audit_state" ;

    public static final String STATE ="state" ;

    public static final String AUDIT_XUHAO ="audit_xuhao" ;

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