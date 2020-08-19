package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBPersonalsummary;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 个人总结 服务类
 * </p>
 *
 * @author viki
 * @since 2020-08-12
 */
public interface IDcaBPersonalsummaryService extends IService<DcaBPersonalsummary> {

        IPage<DcaBPersonalsummary> findDcaBPersonalsummarys(QueryRequest request, DcaBPersonalsummary dcaBPersonalsummary);

        IPage<DcaBPersonalsummary> findDcaBPersonalsummaryList(QueryRequest request, DcaBPersonalsummary dcaBPersonalsummary);

        void createDcaBPersonalsummary(DcaBPersonalsummary dcaBPersonalsummary);

        void updateDcaBPersonalsummary(DcaBPersonalsummary dcaBPersonalsummary);

        void deleteDcaBPersonalsummarys(String[]Ids);

        void deleteByuseraccount(String userAccount);
        }
