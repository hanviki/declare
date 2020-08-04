package cc.mrbird.febs.scm.dao;

import cc.mrbird.febs.scm.entity.VMsgQuerypriceinfo;
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
public interface VMsgQuerypriceinfoMapper extends BaseMapper<VMsgQuerypriceinfo> {
        @Select("select *  from v_msg_queryPriceInfo")
        List<VMsgQuerypriceinfo> GetMsgFileValid();
        }
