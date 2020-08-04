package cc.mrbird.febs.scm.entity;

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

@Excel("scm_b_queryprice_d")
@Data
@Accessors(chain = true)
public class ScmBQuerypriceD implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelField(value = "主键")
    private String id;
    /**
     * 父ID
     */
    @ExcelField(value = "父ID")
    private Long baseId;
    /**
     * 供应商账号
     */
    @ExcelField(value = "供应商账号")
    private String gysaccount;
    /**
     * 供应商名称
     */
    @ExcelField(value = "供应商名称")
    private String gysname;
    /**
     * 供应商拼音码
     */
    @ExcelField(value = "供应商拼音码")
    private String gyspym;
    /**
     * 供应商标签
     */
    @ExcelField(value = "供应商标签")
    private String gyslabel;
    /**
     * 备注
     */
    @ExcelField(value = "备注")
    private String commentsD;
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
     * 报价状态
     */
    private int gysstate;


    public static final String ID = "id";

    public static final String BASE_ID = "base_id";

    public static final String GYSACCOUNT = "gysaccount";

    public static final String GYSNAME = "gysname";

    public static final String GYSPYM = "gyspym";

    public static final String GYSLABEL = "gyslabel";

    public static final String COMMENTS_D = "comments_d";

    public static final String IS_DELETEMARK = "IS_DELETEMARK";

    public static final String CREATE_TIME = "CREATE_TIME";

    public static final String MODIFY_TIME = "MODIFY_TIME";

    public static final String CREATE_USER_ID = "CREATE_USER_ID";

    public static final String MODIFY_USER_ID = "MODIFY_USER_ID";

}