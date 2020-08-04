package cc.mrbird.febs.scm.dao;

import cc.mrbird.febs.scm.entity.VMsgGysysauditnot;
import cc.mrbird.febs.scm.entity.VMsgVendoraudit;
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
public interface VMsgVendorauditMapper extends BaseMapper<VMsgVendoraudit> {
        @Select("select *  from v_msg_vendoraudit")
        List<VMsgVendoraudit> GetMsg();
        }
