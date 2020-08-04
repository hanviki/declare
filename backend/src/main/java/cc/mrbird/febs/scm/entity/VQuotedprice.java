package cc.mrbird.febs.scm.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;


import lombok.Data;
import lombok.experimental.Accessors;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;

/**
 * <p>
 * VIEW
 * </p>
 *
 * @author viki
 * @since 2020-01-07
 */

@Excel("v_quotedprice")
@Data
@Accessors(chain = true)
public class VQuotedprice implements Serializable{

private static final long serialVersionUID=1L;

    /**
     * 物料名称
     */
            @ExcelField(value ="物料名称")
private String txz01;
    /**
     * 物料编码
     */
            @ExcelField(value ="物料编码")
private String matnr;
    /**
     * 物料单位
     */
            @ExcelField(value ="物料单位")
private String mseht;
    /**
     * 物料规格
     */
            @ExcelField(value ="物料规格")
private String spec;
    /**
     * 询价状态
     */
            @ExcelField(value ="询价状态")
private Integer queryState;
    /**
     * 询价日期
     */
            @ExcelField(value ="询价日期")
private Date queryDate;
    /**
     * 结束日期
     */
            @ExcelField(value ="结束日期")
private Date endDate;
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
     * 父ID
     */
            @ExcelField(value ="父ID")
private String baseIdD;
    /**
     * 医院
     */
            @ExcelField(value ="医院")
private String hospitalName;
    /**
     * 价格
     */
            @ExcelField(value ="价格")
private BigDecimal price;
    /**
     * 备注
     */
            @ExcelField(value ="备注")
private String commentsD;
    /**
     * 排序
     */
            @ExcelField(value ="排序")
private Integer displayIndexD;
    /**
     * 文件ID
     */
            @ExcelField(value ="文件ID")
private String comFileId;
    /**
     * 文件名称
     */
    @TableField("CLIENT_NAME")
            @ExcelField(value ="文件名称")
private String clientName;
    /**
     * 服务器地址
     */
    @TableField("SERVER_NAME")
            @ExcelField(value ="服务器地址")
private String serverName;
    /**
     * 供应商账号
     */
            @ExcelField(value ="供应商账号")
private String gysaccount;
    /**
     * 父ID
     */
            @ExcelField(value ="父ID")
private Long baseId;
    /**
     * 供应商名称
     */
            @ExcelField(value ="供应商名称")
private String gysname;

            private transient String keyword;


    public static final String TXZ01 ="txz01" ;

    public static final String MATNR ="matnr" ;

    public static final String MSEHT ="mseht" ;

    public static final String SPEC ="spec" ;

    public static final String QUERY_STATE ="query_state" ;

    public static final String QUERY_DATE ="query_date" ;

    public static final String END_DATE ="end_date" ;

    public static final String ID ="id" ;

    public static final String CODE ="code" ;

    public static final String PRODUCT_NAME ="product_name" ;

    public static final String QUOTED_PRICE ="quoted_price" ;

    public static final String SUPPLY_AMOUNT ="supply_amount" ;

    public static final String COMMENTS ="comments" ;

    public static final String STATE ="state" ;

    public static final String DISPLAY_INDEX ="display_index" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

    public static final String BASE_ID_D ="base_id_d" ;

    public static final String HOSPITAL_NAME ="hospital_name" ;

    public static final String PRICE ="price" ;

    public static final String COMMENTS_D ="comments_d" ;

    public static final String DISPLAY_INDEX_D ="display_index_d" ;

    public static final String COM_FILE_ID ="com_file_id" ;

    public static final String CLIENT_NAME ="CLIENT_NAME" ;

    public static final String SERVER_NAME ="SERVER_NAME" ;

    public static final String GYSACCOUNT ="gysaccount" ;

    public static final String BASE_ID ="base_id" ;

    public static final String GYSNAME ="gysname" ;

        }