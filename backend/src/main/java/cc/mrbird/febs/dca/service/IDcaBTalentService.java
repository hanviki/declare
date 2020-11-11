package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBTalent;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 任现职以来完成研究生教学人才培养情况 服务类
 * </p>
 *
 * @author viki
 * @since 2020-10-20
 */
public interface IDcaBTalentService extends IService<DcaBTalent> {

        IPage<DcaBTalent> findDcaBTalents(QueryRequest request, DcaBTalent dcaBTalent);

        IPage<DcaBTalent> findDcaBTalentList(QueryRequest request, DcaBTalent dcaBTalent);

        void createDcaBTalent(DcaBTalent dcaBTalent);

        void updateDcaBTalent(DcaBTalent dcaBTalent);

        void deleteDcaBTalents(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
