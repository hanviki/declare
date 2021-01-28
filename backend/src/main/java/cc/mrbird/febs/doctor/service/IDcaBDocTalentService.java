package cc.mrbird.febs.doctor.service;

import cc.mrbird.febs.doctor.entity.DcaBDocTalent;
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
 * @since 2021-01-11
 */
public interface IDcaBDocTalentService extends IService<DcaBDocTalent> {

        IPage<DcaBDocTalent> findDcaBDocTalents(QueryRequest request, DcaBDocTalent dcaBDocTalent);

        IPage<DcaBDocTalent> findDcaBDocTalentList(QueryRequest request, DcaBDocTalent dcaBDocTalent);

        void createDcaBDocTalent(DcaBDocTalent dcaBDocTalent);

        void updateDcaBDocTalent(DcaBDocTalent dcaBDocTalent);

        void deleteDcaBDocTalents(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
