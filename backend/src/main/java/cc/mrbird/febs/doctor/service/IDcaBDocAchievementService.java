package cc.mrbird.febs.doctor.service;

import cc.mrbird.febs.doctor.entity.DcaBDocAchievement;
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
 * @since 2021-01-11
 */
public interface IDcaBDocAchievementService extends IService<DcaBDocAchievement> {

        IPage<DcaBDocAchievement> findDcaBDocAchievements(QueryRequest request, DcaBDocAchievement dcaBDocAchievement);

        IPage<DcaBDocAchievement> findDcaBDocAchievementList(QueryRequest request, DcaBDocAchievement dcaBDocAchievement);

        void createDcaBDocAchievement(DcaBDocAchievement dcaBDocAchievement);

        void updateDcaBDocAchievement(DcaBDocAchievement dcaBDocAchievement);

        void deleteDcaBDocAchievements(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
