package cc.mrbird.febs.dcacopy.service;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyGoal;
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
 * @since 2020-11-26
 */
public interface IDcaBCopyGoalService extends IService<DcaBCopyGoal> {

        IPage<DcaBCopyGoal> findDcaBCopyGoals(QueryRequest request, DcaBCopyGoal dcaBCopyGoal);

        IPage<DcaBCopyGoal> findDcaBCopyGoalList(QueryRequest request, DcaBCopyGoal dcaBCopyGoal);

        void createDcaBCopyGoal(DcaBCopyGoal dcaBCopyGoal);

        void updateDcaBCopyGoal(DcaBCopyGoal dcaBCopyGoal);

        void deleteDcaBCopyGoals(String[]Ids);

        List<DcaBCopyGoal> getAll(String userAccount,String dcaYear);
        }
