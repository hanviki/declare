package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaBUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-10-15
 */
public interface DcaBUserMapper extends BaseMapper<DcaBUser> {
        void updateDcaBUser(DcaBUser dcaBUser);
        IPage<DcaBUser> findDcaBUser(Page page, @Param("dcaBUser") DcaBUser dcaBUser);

@Update(" update dca_b_user set IS_DELETEMARK=0 where user_account=#{useraccount}")
        void deleteByAccount(@Param("useraccount") String useraccount);
        }
