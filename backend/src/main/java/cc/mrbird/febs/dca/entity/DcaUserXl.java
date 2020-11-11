package cc.mrbird.febs.dca.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;


import lombok.Data;
import lombok.experimental.Accessors;
import java.util.Date;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;

/**
 * <p>
 * 
 * </p>
 *
 * @author viki
 * @since 2020-10-29
 */

@Excel("dca_user_xl")
@Data
@Accessors(chain = true)
public class DcaUserXl implements Serializable{

private static final long serialVersionUID=1L;

                    @TableId(value = "id" , type = IdType.AUTO)
                    private Integer id;

    /**
     * 系列id
     */
            @ExcelField(value ="系列id")
    private Long xlId;

    /**
     * 用户id
     */
            @ExcelField(value ="用户id")
    private Long userId;



    public static final String ID ="id" ;

    public static final String XL_ID ="xl_id" ;

    public static final String USER_ID ="user_id" ;

        }