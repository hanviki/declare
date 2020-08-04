package cc.mrbird.febs.scm.dao;

import cc.mrbird.febs.scm.entity.VMsgOrderinfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * VIEW Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-04-09
 */
public interface VMsgOrderinfoMapper extends BaseMapper<VMsgOrderinfo> {
        @Select("select *  from v_msg_orderinfo")
        List<VMsgOrderinfo> GetMsgFileValid();
        }
