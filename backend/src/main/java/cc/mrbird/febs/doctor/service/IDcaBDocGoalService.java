package cc.mrbird.febs.doctor.service;

import cc.mrbird.febs.doctor.entity.DcaBDocGoal;
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
 * @since 2021-01-11
 */
public interface IDcaBDocGoalService extends IService<DcaBDocGoal> {

        IPage<DcaBDocGoal> findDcaBDocGoals(QueryRequest request, DcaBDocGoal dcaBDocGoal);

        IPage<DcaBDocGoal> findDcaBDocGoalList(QueryRequest request, DcaBDocGoal dcaBDocGoal);

        void createDcaBDocGoal(DcaBDocGoal dcaBDocGoal);

        void updateDcaBDocGoal(DcaBDocGoal dcaBDocGoal);

        void deleteDcaBDocGoals(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
