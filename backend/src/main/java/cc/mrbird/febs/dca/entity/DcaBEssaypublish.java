package cc.mrbird.febs.dca.entity;

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
 * 论文出版
 * </p>
 *
 * @author viki
 * @since 2020-08-11
 */

@Excel("dca_b_essaypublish")
@Data
@Accessors(chain = true)
public class DcaBEssaypublish implements Serializable{

private static final long serialVersionUID=1L;

    /**
     * 主键
     */
                                @ExcelField(value ="主键")
    private String id;

    /**
     * 人事编号
     */
            @ExcelField(value ="人事编号")
    private String userAccount;

    /**
     * 论著名称
     */
            @ExcelField(value ="论著名称")
    private String essayName;

    /**
     * 期刊名称
     */
            @ExcelField(value ="期刊名称")
    private String eassyJournalname;

    /**
     * 出版社
     */
            @ExcelField(value ="出版社")
    private String eassyPublishname;

    /**
     * 起始页码
     */
            @ExcelField(value ="起始页码")
    private Long eassyStartpage;

    /**
     * 截至页码
     */
            @ExcelField(value ="截至页码")
    private Long eassyEndpage;

    /**
     * 刊号
     */
            @ExcelField(value ="刊号")
    private String eassyJournalcode;

    /**
     * 期刊级别
     */
            @ExcelField(value ="期刊级别")
    private String eassyJournalgrade;

    /**
     * 发表年月
     */
            @ExcelField(value ="发表年月")
    private Date eassyPublishdate;
    private transient String eassyPublishdateFrom;
    private transient String eassyPublishdateTo;

    /**
     * 第几作者
     */
            @ExcelField(value ="第几作者")
    private String eassyRankname;

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



    public static final String ID ="id" ;

    public static final String USER_ACCOUNT ="user_account" ;

    public static final String ESSAY_NAME ="essay_name" ;

    public static final String EASSY_JOURNALNAME ="eassy_journalname" ;

    public static final String EASSY_PUBLISHNAME ="eassy_publishname" ;

    public static final String EASSY_STARTPAGE ="eassy_startpage" ;

    public static final String EASSY_ENDPAGE ="eassy_endpage" ;

    public static final String EASSY_JOURNALCODE ="eassy_journalcode" ;

    public static final String EASSY_JOURNALGRADE ="eassy_journalgrade" ;

    public static final String EASSY_PUBLISHDATE ="eassy_publishdate" ;

    public static final String EASSY_RANKNAME ="eassy_rankname" ;

    public static final String STATE ="state" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

        }