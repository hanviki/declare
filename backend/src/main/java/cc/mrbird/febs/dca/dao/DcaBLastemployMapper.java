package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaBLastemploy;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 完成上一聘期 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-08-12
 */
public interface DcaBLastemployMapper extends BaseMapper<DcaBLastemploy> {
        void updateDcaBLastemploy(DcaBLastemploy dcaBLastemploy);
        IPage<DcaBLastemploy> findDcaBLastemploy(Page page, @Param("dcaBLastemploy") DcaBLastemploy dcaBLastemploy);

@Update(" update dca_b_lastemploy set IS_DELETEMARK=0 where user_account=#{useraccount}")
        void deleteByAccount(@Param("useraccount") String useraccount);
        }
