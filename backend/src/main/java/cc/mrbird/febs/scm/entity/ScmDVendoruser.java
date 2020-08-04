package cc.mrbird.febs.scm.entity;

import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;


import lombok.Data;
import lombok.experimental.Accessors;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 
 * </p>
 *
 * @author viki
 * @since 2020-06-03
 */

@Excel("scm_d_vendoruser")
@Data
@Accessors(chain = true)
public class ScmDVendoruser implements Serializable{

private static final long serialVersionUID=1L;

    /**
     * 主键
     */
                    @TableId("ID")
                    @ExcelField(value ="主键")
private String id;
    /**
     * 供应商主表ID
     */
    @TableField("BASE_ID")
            @ExcelField(value ="供应商主表ID")
private String baseId;
    /**
     * 业务员姓名
     */
    @TableField("NAME")
            @ExcelField(value ="业务员姓名")
private String name;
    /**
     * 身份证号
     */
    @TableField("IDCARD")
            @ExcelField(value ="身份证号")
private String idcard;
    /**
     * 身份证照片反面
     */
    @TableField("IDCARD_BACK")
            @ExcelField(value ="身份证照片反面")
private String idcardBack;
    /**
     * 身份证照片正面
     */
    @TableField("IDCARD_FRONT")
            @ExcelField(value ="身份证照片正面")
private String idcardFront;

    /**
     * beizhu
     */
    @TableField("NOTEU")
    @ExcelField(value ="备注")
    private String noteu;
    /**
     * 联系电话
     */
    @TableField("TELPHONE")
            @ExcelField(value ="联系电话")
private String telphone;
    /**
     * 免冠照片
     */
    @TableField("Head_IMAGE")
            @ExcelField(value ="免冠照片")
private String headImage;
    /**
     * 委托书
     */
    @TableField("Agent_Image")
            @ExcelField(value ="委托书")
private String agentImage;
    /**
     * 有效期开始时间
     */
    @TableField("VALID_DATESTART")
            @ExcelField(value ="有效期开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
private Date validDatestart;
    /**
     * 有效期
     */
    @TableField("VALID_DATE")
            @ExcelField(value ="有效期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
private Date validDate;
    /**
     * 是否有效
     */
    @TableField("IS_VALID")
            @ExcelField(value ="是否有效")
private Boolean isValid;
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

    public String getNoteu(){
        return noteu;
    }

    public void setNoteu(String noteu) {
        this.noteu = noteu;
    }
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


    public static final String ID ="ID" ;

    public static final String BASE_ID ="BASE_ID" ;

    public static final String NAME ="NAME" ;

    public static final String IDCARD ="IDCARD" ;

    public static final String IDCARD_BACK ="IDCARD_BACK" ;

    public static final String IDCARD_FRONT ="IDCARD_FRONT" ;

    public static final String TELPHONE ="TELPHONE" ;

    public static final String HEAD_IMAGE ="Head_IMAGE" ;

    public static final String AGENT_IMAGE ="Agent_Image" ;

    public static final String VALID_DATESTART ="VALID_DATESTART" ;

    public static final String VALID_DATE ="VALID_DATE" ;

    public static final String IS_VALID ="IS_VALID" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

        }