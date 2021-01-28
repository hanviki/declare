package cc.mrbird.febs.check.entity;

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
import cc.mrbird.febs.common.converter.*;

/**
 * <p>
 * 指标表
 * </p>
 *
 * @author viki
 * @since 2021-01-25
 */

@Excel("check_d_title")
@Data
@Accessors(chain = true)
public class CheckDTitle implements Serializable{

private static final long serialVersionUID=1L;

                    @TableId(value = "id" , type = IdType.AUTO)
                
    private Integer id;

    /**
     * 指标ID
     */
        
        @ExcelField(value ="指标ID")
    private String filedName;

    /**
     * 指标名称
     */
        
        @ExcelField(value ="指标名称")
    private String filedTitle;

    /**
     * 编码
     */
        
        @ExcelField(value ="编码")
    private String code;

    /**
     * 类别显示编码
     */
        
        @ExcelField(value ="类别显示编码")
    private String showCode;

    /**
     * 显示类型1数字2文本
     */
        
        @ExcelField(value ="显示类型1数字2文本")
    private Integer showType;

    /**
     * 值范围
     */
        
        @ExcelField(value ="值范围")
    private Integer range;

    /**
     * 正负
     */
        
        @ExcelField(value ="正负")
    private Boolean isOria;

    /**
     * 1是账号2科主任3主管领导
     */
        
        @ExcelField(value ="1是账号2科主任3主管领导")
    private Integer checkPerson;

    /**
     * 父ID
     */
        
        @ExcelField(value ="父ID")
    private Integer parentId;

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

    private transient String lb;
    private transient String ks;
    private transient String username2;
    private transient String username3;

    public static final String ID ="id" ;

    public static final String FILED_NAME ="filed_name" ;

    public static final String FILED_TITLE ="filed_title" ;

    public static final String CODE ="code" ;

    public static final String SHOW_CODE ="show_code" ;

    public static final String SHOW_TYPE ="show_type" ;

    public static final String RANGE ="range" ;

    public static final String IS_ORIA ="is_oria" ;

    public static final String CHECK_PERSON ="check_person" ;

    public static final String PARENT_ID ="parent_id" ;

    public static final String STATE ="state" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

        }