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

@Excel("v_msg_gysysaudit")
@Data
@Accessors(chain = true)
public class VMsgGysysaudit implements Serializable{

private static final long serialVersionUID=1L;

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


    public static final String VXCODE ="vxCode" ;

    public static final String ID ="ID" ;

        }