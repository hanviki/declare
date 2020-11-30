package cc.mrbird.febs.dcacopy.dao;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyGoal;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
public interface DcaBCopyGoalMapper extends BaseMapper<DcaBCopyGoal> {
        void updateDcaBCopyGoal(DcaBCopyGoal dcaBCopyGoal);
        IPage<DcaBCopyGoal> findDcaBCopyGoal(Page page, @Param("dcaBCopyGoal") DcaBCopyGoal dcaBCopyGoal);
        }
