package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBGoal;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author viki
 * @since 2020-09-15
 */
public interface IDcaBGoalService extends IService<DcaBGoal> {

        IPage<DcaBGoal> findDcaBGoals(QueryRequest request, DcaBGoal dcaBGoal);

        IPage<DcaBGoal> findDcaBGoalList(QueryRequest request, DcaBGoal dcaBGoal);

        void createDcaBGoal(DcaBGoal dcaBGoal);

        void updateDcaBGoal(DcaBGoal dcaBGoal);

        void deleteDcaBGoals(String[]Ids);

        void deleteByuseraccount(String userAccount);
        }
