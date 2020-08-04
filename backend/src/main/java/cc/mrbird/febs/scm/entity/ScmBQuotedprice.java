package cc.mrbird.febs.scm.entity;

import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;


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
 * @since 2019-12-27
 */

@Excel("scm_b_quotedprice")
@Data
@Accessors(chain = true)
public class ScmBQuotedprice implements Serializable{

private static final long serialVersionUID=1L;

    /**
     * 主键
     */
                                @ExcelField(value ="主键")
private String id;
    /**
     * 编码
     */
            @ExcelField(value ="编码")
private String code;
    /**
     * 父ID
     */
            @ExcelField(value ="父ID")
private Long baseId;
    /**
     * 厂家
     */
            @ExcelField(value ="厂家")
private String productName;
    /**
     * 价格
     */
            @ExcelField(value ="价格")
private BigDecimal quotedPrice;
    /**
     * 供应数量
     */
            @ExcelField(value ="供应数量")
private BigDecimal supplyAmount;
    /**
     * 备注
     */
            @ExcelField(value ="备注")
private String comments;
    /**
     * 状态
     */
            @ExcelField(value ="状态")
private Integer state;
    /**
     * 排序
     */
            @ExcelField(value ="排序")
private Integer displayIndex;
    /**
     * 供应商名称
     */
            @ExcelField(value ="供应商名称")
private String gysname;
    /**
     * 供应商账号
     */
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

    private  transient List<ScmBQuotedpriceD> hospital;


    public static final String ID ="id" ;

    public static final String CODE ="code" ;

    public static final String BASE_ID ="base_id" ;

    public static final String PRODUCT_NAME ="product_name" ;

    public static final String QUOTED_PRICE ="quoted_price" ;

    public static final String SUPPLY_AMOUNT ="supply_amount" ;

    public static final String COMMENTS ="comments" ;

    public static final String STATE ="state" ;

    public static final String DISPLAY_INDEX ="display_index" ;

    public static final String GYSNAME ="gysname" ;

    public static final String GYSACCOUNT ="gysaccount" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

        }