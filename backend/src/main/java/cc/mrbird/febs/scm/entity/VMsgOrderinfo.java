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
 * @since 2020-04-09
 */

@Excel("v_msg_orderinfo")
@Data
@Accessors(chain = true)
public class VMsgOrderinfo implements Serializable{

private static final long serialVersionUID=1L;

    @TableField("numCount")
        private Long numCount;
    /**
     * 院区名称
     */
            @ExcelField(value ="院区名称")
private String werkst;
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


    public static final String NUMCOUNT ="numCount" ;

    public static final String WERKST ="werkst" ;

    public static final String NAME ="NAME" ;

    public static final String CODE ="CODE" ;

    public static final String VXCODE ="vxcode" ;

        }