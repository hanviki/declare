package cc.mrbird.febs.dcacopy.service;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyTalent;
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
 * @since 2020-11-26
 */
public interface IDcaBCopyTalentService extends IService<DcaBCopyTalent> {

        IPage<DcaBCopyTalent> findDcaBCopyTalents(QueryRequest request, DcaBCopyTalent dcaBCopyTalent);

        IPage<DcaBCopyTalent> findDcaBCopyTalentList(QueryRequest request, DcaBCopyTalent dcaBCopyTalent);

        void createDcaBCopyTalent(DcaBCopyTalent dcaBCopyTalent);

        void updateDcaBCopyTalent(DcaBCopyTalent dcaBCopyTalent);

        void deleteDcaBCopyTalents(String[]Ids);

        List<DcaBCopyTalent> getAll(String userAccount,String dcaYear);
        }
