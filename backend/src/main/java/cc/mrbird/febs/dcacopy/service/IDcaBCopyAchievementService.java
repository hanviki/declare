package cc.mrbird.febs.dcacopy.service;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyAchievement;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 主要医疗业绩 服务类
 * </p>
 *
 * @author viki
 * @since 2020-12-15
 */
public interface IDcaBCopyAchievementService extends IService<DcaBCopyAchievement> {

        IPage<DcaBCopyAchievement> findDcaBCopyAchievements(QueryRequest request, DcaBCopyAchievement dcaBCopyAchievement);

        IPage<DcaBCopyAchievement> findDcaBCopyAchievementList(QueryRequest request, DcaBCopyAchievement dcaBCopyAchievement);

        void createDcaBCopyAchievement(DcaBCopyAchievement dcaBCopyAchievement);

        void updateDcaBCopyAchievement(DcaBCopyAchievement dcaBCopyAchievement);

        void deleteDcaBCopyAchievements(String[]Ids);

        List<DcaBCopyAchievement> getAll(String userAccount,String dcaYear);
        }
