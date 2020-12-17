package cc.mrbird.febs.dcacopy.dao;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyAchievement;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 主要医疗业绩 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-12-15
 */
public interface DcaBCopyAchievementMapper extends BaseMapper<DcaBCopyAchievement> {
        void updateDcaBCopyAchievement(DcaBCopyAchievement dcaBCopyAchievement);
        IPage<DcaBCopyAchievement> findDcaBCopyAchievement(Page page, @Param("dcaBCopyAchievement") DcaBCopyAchievement dcaBCopyAchievement);
        }
