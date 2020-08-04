package cc.mrbird.febs.scm.dao;

import cc.mrbird.febs.scm.entity.VMsgGysysaudit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * VIEW Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-05-12
 */
public interface VMsgGysysauditMapper extends BaseMapper<VMsgGysysaudit> {
        @Select("select *  from v_msg_gysysaudit")
        List<VMsgGysysaudit> GetMsg();
        }
