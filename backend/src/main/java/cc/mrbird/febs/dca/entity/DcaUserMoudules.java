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
 * 申报模块和用户表关联表
 * </p>
 *
 * @author viki
 * @since 2020-08-10
 */

@Excel("dca_user_moudules")
@Data
@Accessors(chain = true)
public class DcaUserMoudules implements Serializable{

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
    private Integer muduleId;



    public static final String ID ="id" ;

    public static final String USERID ="userId" ;

    public static final String MUDULE_ID ="mudule_id" ;

        }