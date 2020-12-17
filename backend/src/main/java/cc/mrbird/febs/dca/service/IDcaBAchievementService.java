package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBAchievement;
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
public interface IDcaBAchievementService extends IService<DcaBAchievement> {

        IPage<DcaBAchievement> findDcaBAchievements(QueryRequest request, DcaBAchievement dcaBAchievement);

        IPage<DcaBAchievement> findDcaBAchievementList(QueryRequest request, DcaBAchievement dcaBAchievement);

        void createDcaBAchievement(DcaBAchievement dcaBAchievement);

        void updateDcaBAchievement(DcaBAchievement dcaBAchievement);

        void deleteDcaBAchievements(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
