package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaBEmploy;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 任职培养 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-08-11
 */
public interface DcaBEmployMapper extends BaseMapper<DcaBEmploy> {
        void updateDcaBEmploy(DcaBEmploy dcaBEmploy);
        IPage<DcaBEmploy> findDcaBEmploy(Page page, @Param("dcaBEmploy") DcaBEmploy dcaBEmploy);

@Update(" update dca_b_employ set IS_DELETEMARK=0 where user_account=#{useraccount}")
        void deleteByAccount(@Param("useraccount") String useraccount);
        }
