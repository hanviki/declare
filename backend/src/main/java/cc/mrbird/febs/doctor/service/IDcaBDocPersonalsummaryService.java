package cc.mrbird.febs.doctor.service;

import cc.mrbird.febs.doctor.entity.DcaBDocPersonalsummary;
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
 * @since 2021-01-11
 */
public interface IDcaBDocPersonalsummaryService extends IService<DcaBDocPersonalsummary> {

        IPage<DcaBDocPersonalsummary> findDcaBDocPersonalsummarys(QueryRequest request, DcaBDocPersonalsummary dcaBDocPersonalsummary);

        IPage<DcaBDocPersonalsummary> findDcaBDocPersonalsummaryList(QueryRequest request, DcaBDocPersonalsummary dcaBDocPersonalsummary);

        void createDcaBDocPersonalsummary(DcaBDocPersonalsummary dcaBDocPersonalsummary);

        void updateDcaBDocPersonalsummary(DcaBDocPersonalsummary dcaBDocPersonalsummary);

        void deleteDcaBDocPersonalsummarys(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
