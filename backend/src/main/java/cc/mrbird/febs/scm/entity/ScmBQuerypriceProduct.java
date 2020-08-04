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
 * @since 2019-12-27
 */

@Excel("scm_b_queryprice_product")
@Data
@Accessors(chain = true)
public class ScmBQuerypriceProduct implements Serializable{

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
private Long baseId;
    /**
     * 厂家
     */
            @ExcelField(value ="厂家")
private String productName;
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

    public static final String BASE_ID ="base_id" ;

    public static final String PRODUCT_NAME ="product_name" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

        }