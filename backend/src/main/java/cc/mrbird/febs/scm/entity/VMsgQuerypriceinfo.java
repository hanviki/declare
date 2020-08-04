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

@Excel("v_msg_querypriceinfo")
@Data
@Accessors(chain = true)
public class VMsgQuerypriceinfo implements Serializable{

private static final long serialVersionUID=1L;

    /**
     * 物料编码
     */
            @ExcelField(value ="物料编码")
private String matnr;
    /**
     * 物料名称
     */
            @ExcelField(value ="物料名称")
private String txz01;
    /**
     * 供应商账号
     */
            @ExcelField(value ="供应商账号")
private String gysaccount;
    /**
     * 供应商名称
     */
            @ExcelField(value ="供应商名称")
private String gysname;
    /**
     * 微信用户ID
     */
    @TableField("vxCode")
            @ExcelField(value ="微信用户ID")
private String vxCode;


    public static final String MATNR ="matnr" ;

    public static final String TXZ01 ="txz01" ;

    public static final String GYSACCOUNT ="gysaccount" ;

    public static final String GYSNAME ="gysname" ;

    public static final String VXCODE ="vxCode" ;

        }