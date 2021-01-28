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
 * 
 * </p>
 *
 * @author viki
 * @since 2021-01-27
 */

@Excel("check_d_report")
@Data
@Accessors(chain = true)
public class CheckDReport implements Serializable{

private static final long serialVersionUID=1L;

                    @TableId(value = "id" , type = IdType.AUTO)
                
    private Long id;

    /**
     * 考核年度
     */
        
        @ExcelField(value ="考核年度")
    private String dcaYear;

    /**
     * 序号
     */
        
        @ExcelField(value ="序号")
    private Integer code;

    /**
     * 类别
     */
        
        @ExcelField(value ="类别")
    private String lb;

    /**
     * 考核项目及满分
     */
        
        @ExcelField(value ="考核项目及满分")
    private String moudleName;

    /**
     * 打分科室
     */
        
        @ExcelField(value ="打分科室")
    private String ks;

    /**
     * 总分
     */
        
        @ExcelField(value ="总分")
    private Double totalNum;

    /**
     * 考核指标
     */
        
        @ExcelField(value ="考核指标")
    private String titles;

    /**
     * 打分处室负责人签字
     */
        
        @ExcelField(value ="打分处室负责人签字")
    private String ksLeader;

    /**
     * 备注
     */
        
        @ExcelField(value ="备注")
    private String note;

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

    /**
     * 指标分值
     */
    private transient String titleResult;

    public static final String ID ="id" ;

    public static final String DCA_YEAR ="dca_year" ;

    public static final String CODE ="code" ;

    public static final String LB ="lb" ;

    public static final String MOUDLE_NAME ="moudle_name" ;

    public static final String KS ="ks" ;

    public static final String TOTAL_NUM ="total_num" ;

    public static final String TITLES ="titles" ;

    public static final String KS_LEADER ="ks_leader" ;

    public static final String NOTE ="note" ;

    public static final String STATE ="state" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

        }