package cc.mrbird.febs.dca.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * 
 * </p>
 *
 * @author viki
 * @since 2020-10-23
 */

@Excel("dca_d_auditinfo")
@Data
@Accessors(chain = true)
public class DcaDAuditinfo implements Serializable{

private static final long serialVersionUID=1L;

                    @TableId(value = "id" , type = IdType.AUTO)
                    private Integer id;

    /**
     * 字段名称
     */
            @ExcelField(value ="字段名称")
    private String fieldName;

    /**
     * 字段显示
     */
            @ExcelField(value ="字段显示")
    private String fieldTitle;

    /**
     * 内容显示形式
     */
            @ExcelField(value ="内容显示形式")
    private Integer showType;

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

    /**
     * 父ID
     */
            @ExcelField(value ="父ID")
    private Integer parentId;



    public static final String ID ="id" ;

    public static final String FIELD_NAME ="field_name" ;

    public static final String FIELD_TITLE ="field_title" ;

    public static final String SHOW_TYPE ="show_type" ;

    public static final String STATE ="state" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

    public static final String PARENT_ID ="parent_id" ;

        }