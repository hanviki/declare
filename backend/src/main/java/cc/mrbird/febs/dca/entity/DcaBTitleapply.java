package cc.mrbird.febs.dca.entity;

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
 * 职称申请表
 * </p>
 *
 * @author viki
 * @since 2020-09-22
 */

@Excel("dca_b_titleapply")
@Data
@Accessors(chain = true)
public class DcaBTitleapply implements Serializable{

private static final long serialVersionUID=1L;

    /**
     * 主键
     */
                    @TableId("ID")
                    @ExcelField(value ="主键")
    private String id;

    /**
     * 编码=主键
     */
    @TableField("CODE")
            @ExcelField(value ="编码=主键")
    private String code;

    /**
     * 名字
     */
    @TableField("NAME")
            @ExcelField(value ="名字")
    private String name;

    /**
     * 人事编号
     */
            @ExcelField(value ="人事编号")
    private String userAccount;

    /**
     * 姓名
     */
            @ExcelField(value ="姓名")
    private String userAccountName;

    /**
     * 职称ID
     */
            @ExcelField(value ="职称ID")
    private String titleId;

    /**
     * 申请职称
     */
            @ExcelField(value ="申请职称")
    private String titleName;

    /**
     * 年度
     */
            @ExcelField(value ="年度")
    private String year;

    /**
     * 是否高级
     */
    @TableField("IsGj")
            @ExcelField(value ="是否高级")
    private Boolean IsGj;

    /**
     * 备注
     */
    @TableField("COMMENTS")
            @ExcelField(value ="备注")
    private String comments;

    /**
     * 审核状态
     */
    @TableField("STATE")
            @ExcelField(value ="审核状态")
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

    /**
     * 排序
     */
    @TableField("ORDER_NUM")
            @ExcelField(value ="排序")
    private Double orderNum;



    public static final String ID ="ID" ;

    public static final String CODE ="CODE" ;

    public static final String NAME ="NAME" ;

    public static final String USER_ACCOUNT ="user_account" ;

    public static final String USER_ACCOUNT_NAME ="user_account_name" ;

    public static final String TITLE_ID ="title_id" ;

    public static final String TITLE_NAME ="title_name" ;

    public static final String YEAR ="year" ;

    public static final String ISGJ ="IsGj" ;

    public static final String COMMENTS ="COMMENTS" ;

    public static final String STATE ="STATE" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

    public static final String ORDER_NUM ="ORDER_NUM" ;

        }