package cc.mrbird.febs.scm.entity;

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
 * @since 2020-04-06
 */

@Excel("v_msg_filevalidinfo")
@Data
@Accessors(chain = true)
public class VMsgFilevalidinfo implements Serializable{

private static final long serialVersionUID=1L;

    /**
     * 有效期
     */
    @TableField("VALID_DATE")
            @ExcelField(value ="有效期")
private Date validDate;
    /**
     * 资质名称
     */
    @TableField("FILENAME")
            @ExcelField(value ="资质名称")
private String filename;
    /**
     * 名字
     */
    @TableField("NAME")
            @ExcelField(value ="名字")
private String name;
    /**
     * 编码
     */
    @TableField("CODE")
            @ExcelField(value ="编码")
private String code;
    /**
     * 微信用户ID
     */
            @ExcelField(value ="微信用户ID")
private String vxcode;
    /**
     * 过期天数
     */
    private  long days ;


    public static final String VALID_DATE ="VALID_DATE" ;

    public static final String FILENAME ="FILENAME" ;

    public static final String NAME ="NAME" ;

    public static final String CODE ="CODE" ;

    public static final String VXCODE ="vxcode" ;

        }