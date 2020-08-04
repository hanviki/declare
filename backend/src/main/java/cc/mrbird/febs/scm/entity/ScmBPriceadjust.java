package cc.mrbird.febs.scm.entity;

import java.math.BigDecimal;
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
 * @since 2019-12-27
 */

@Excel("scm_b_priceadjust")
@Data
@Accessors(chain = true)
public class ScmBPriceadjust implements Serializable{

private static final long serialVersionUID=1L;

    /**
     * 主键
     */
                                @ExcelField(value ="主键")
private Long id;
        private String code;
        private String name;
    /**
     * 物料ID
     */
            @ExcelField(value ="物料ID")
private String matnrId;
    /**
     * 物料编码
     */
            @ExcelField(value ="物料编码")
private String matnr;
    /**
     * 单位
     */
            @ExcelField(value ="单位")
private String mseht;
    /**
     * 物料名称
     */
            @ExcelField(value ="物料名称")
private String txz01;
    /**
     * 规格
     */
            @ExcelField(value ="规格")
private String spec;
    /**
     * 创建日期
     */
            @ExcelField(value ="创建日期")
private Date createDate;
    /**
     * 旧价格
     */
            @ExcelField(value ="旧价格")
private BigDecimal oldPrice;
    /**
     * 调整价格
     */
            @ExcelField(value ="调整价格")
private BigDecimal adjustPrice;
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
     * 供应商账号
     */
            @ExcelField(value ="供应商账号")
private String gysaccount;
    /**
     * 供应商名称
     */
            @ExcelField(value ="供应商名称")
private String gysname;
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


    public static final String ID ="id" ;

    public static final String CODE ="code" ;

    public static final String NAME ="name" ;

    public static final String MATNR_ID ="matnr_id" ;

    public static final String MATNR ="matnr" ;

    public static final String MSEHT ="mseht" ;

    public static final String TXZ01 ="txz01" ;

    public static final String SPEC ="spec" ;

    public static final String CREATE_DATE ="create_date" ;

    public static final String OLD_PRICE ="old_price" ;

    public static final String ADJUST_PRICE ="adjust_price" ;

    public static final String COMMENTS ="comments" ;

    public static final String STATE ="state" ;

    public static final String GYSACCOUNT ="gysaccount" ;

    public static final String GYSNAME ="gysname" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

        }