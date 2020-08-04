package cc.mrbird.febs.scm.entity;

import java.time.LocalDate;
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

@Excel("v_msg_materinfovalid")
@Data
@Accessors(chain = true)
public class VMsgMaterinfovalid implements Serializable{

private static final long serialVersionUID=1L;

    /**
     * 批号
     */
    @TableField("CHARGE")
            @ExcelField(value ="批号")
private String charge;
    /**
     * 有效期
     */
    @TableField("VFDAT")
            @ExcelField(value ="有效期")
private LocalDate vfdat;
    /**
     * 供应商账号
     */
            @ExcelField(value ="供应商账号")
private String lifnr;
    /**
     * 供应商名称
     */
    @TableField("GysName")
            @ExcelField(value ="供应商名称")
private String GysName;
    /**
     * 物料ID
     */
            @ExcelField(value ="物料ID")
private String matnr;
    /**
     * 物料描述
     */
            @ExcelField(value ="物料描述")
private String txz01;
        private Integer days;


    public static final String CHARGE ="CHARGE" ;

    public static final String VFDAT ="VFDAT" ;

    public static final String LIFNR ="lifnr" ;

    public static final String GYSNAME ="GysName" ;

    public static final String MATNR ="matnr" ;

    public static final String TXZ01 ="txz01" ;

    public static final String DAYS ="days" ;

        }