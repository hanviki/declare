package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaBGoal;
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
 * @since 2020-09-15
 */
public interface DcaBGoalMapper extends BaseMapper<DcaBGoal> {
        void updateDcaBGoal(DcaBGoal dcaBGoal);
        IPage<DcaBGoal> findDcaBGoal(Page page, @Param("dcaBGoal") DcaBGoal dcaBGoal);

@Update(" update dca_b_goal set IS_DELETEMARK=0 where user_account=#{useraccount}")
        void deleteByAccount(@Param("useraccount") String useraccount);
        }
