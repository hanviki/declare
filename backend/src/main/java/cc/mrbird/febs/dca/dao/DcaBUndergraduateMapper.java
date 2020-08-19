package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaBUndergraduate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 本科教学情况 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-08-11
 */
public interface DcaBUndergraduateMapper extends BaseMapper<DcaBUndergraduate> {
        void updateDcaBUndergraduate(DcaBUndergraduate dcaBUndergraduate);
        IPage<DcaBUndergraduate> findDcaBUndergraduate(Page page, @Param("dcaBUndergraduate") DcaBUndergraduate dcaBUndergraduate);

@Update(" update dca_b_undergraduate set IS_DELETEMARK=0 where user_account=#{useraccount}")
        void deleteByAccount(@Param("useraccount") String useraccount);
        }
