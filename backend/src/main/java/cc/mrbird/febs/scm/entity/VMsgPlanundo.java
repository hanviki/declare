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
 * @since 2020-06-16
 */

@Excel("v_msg_planundo")
@Data
@Accessors(chain = true)
public class VMsgPlanundo implements Serializable{

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
private Long id;
    /**
     * 供应商账号
     */
    @TableField("GYSACCOUNT")
            @ExcelField(value ="供应商账号")
private String gysaccount;
        private String txz01;
    /**
     * 物料ID
     */
            @ExcelField(value ="物料ID")
private String matnr;


    public static final String VXCODE ="vxCode" ;

    public static final String ID ="ID" ;

    public static final String GYSACCOUNT ="GYSACCOUNT" ;

    public static final String TXZ01 ="txz01" ;

    public static final String MATNR ="matnr" ;

        }