package cc.mrbird.febs.scm.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;


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
 * @since 2020-05-12
 */

@Excel("v_msg_gysysauditnot")
@Data
@Accessors(chain = true)
public class VMsgGysysauditnot implements Serializable{

private static final long serialVersionUID=1L;

    /**
     * 编码
     */
    @TableField("CODE")
            @ExcelField(value ="编码")
private String code;
    /**
     * 名字
     */
    @TableField("NAME")
            @ExcelField(value ="名字")
private String name;
    /**
     * 微信用户ID
     */
    @TableField("vxCode")
            @ExcelField(value ="微信用户ID")
private String vxCode;
    /**
     * 主键
     */
    @TableField("ID")
            @ExcelField(value ="主键")
private String id;


    public static final String CODE ="CODE" ;

    public static final String NAME ="NAME" ;

    public static final String VXCODE ="vxCode" ;

    public static final String ID ="ID" ;

        }