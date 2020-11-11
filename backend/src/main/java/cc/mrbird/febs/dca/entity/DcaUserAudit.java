package cc.mrbird.febs.dca.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;


import lombok.Data;
import lombok.experimental.Accessors;
import java.util.Date;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;

/**
 * <p>
 * 用户审核字段表
 * </p>
 *
 * @author viki
 * @since 2020-10-23
 */

@Excel("dca_user_audit")
@Data
@Accessors(chain = true)
public class DcaUserAudit implements Serializable{

private static final long serialVersionUID=1L;

                    @TableId(value = "id" , type = IdType.AUTO)
                    private Integer id;

    /**
     * 用户ID
     */
    @TableField("userId")
            @ExcelField(value ="用户ID")
    private Long userId;

    /**
     * 申报模块ID
     */
            @ExcelField(value ="申报模块ID")
    private Integer auditId;



    public static final String ID ="id" ;

    public static final String USERID ="userId" ;

    public static final String AUDIT_ID ="audit_id" ;

        }