package cc.mrbird.febs.scm.dao;

import cc.mrbird.febs.scm.entity.VMsgFilevalidinfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * VIEW Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-04-06
 */
public interface VMsgFilevalidinfoMapper extends BaseMapper<VMsgFilevalidinfo> {
        @Select("select *  from v_msg_fileValidInfo")
        List<VMsgFilevalidinfo> GetMsgFileValid();
        }
