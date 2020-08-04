package cc.mrbird.febs.scm.dao;

import cc.mrbird.febs.scm.entity.VMsgPlanundo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * VIEW Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-06-16
 */
public interface VMsgPlanundoMapper extends BaseMapper<VMsgPlanundo> {
        @Select("select *  from v_msg_planundo")
        List<VMsgPlanundo> GetMsgFileValid();
        }
