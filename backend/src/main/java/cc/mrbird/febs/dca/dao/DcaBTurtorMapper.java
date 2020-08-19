package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaBTurtor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 担任辅导员 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-08-11
 */
public interface DcaBTurtorMapper extends BaseMapper<DcaBTurtor> {
        void updateDcaBTurtor(DcaBTurtor dcaBTurtor);
        IPage<DcaBTurtor> findDcaBTurtor(Page page, @Param("dcaBTurtor") DcaBTurtor dcaBTurtor);

@Update(" update dca_b_turtor set IS_DELETEMARK=0 where user_account=#{useraccount}")
        void deleteByAccount(@Param("useraccount") String useraccount);
        }
