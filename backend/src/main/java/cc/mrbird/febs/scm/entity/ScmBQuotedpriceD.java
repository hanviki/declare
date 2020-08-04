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

@Excel("scm_b_quotedprice_d")
@Data
@Accessors(chain = true)
public class ScmBQuotedpriceD implements Serializable{

private static final long serialVersionUID=1L;

    /**
     * 主键
     */
                                @ExcelField(value ="主键")
private String id;
    /**
     * 父ID
     */
            @ExcelField(value ="父ID")
private String baseId;
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
private Integer displayIndex;
    /**
     * 文件ID
     */
            @ExcelField(value ="文件ID")
private String comFileId;
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

    private transient  String clientName;

    private  transient  String serverName;

    public static final String ID ="id" ;

    public static final String BASE_ID ="base_id" ;

    public static final String HOSPITAL_NAME ="hospital_name" ;

    public static final String PRICE ="price" ;

    public static final String COMMENTS_D ="comments_d" ;

    public static final String DISPLAY_INDEX ="display_index" ;

    public static final String COM_FILE_ID ="com_file_id" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

        }