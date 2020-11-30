package cc.mrbird.febs.dcacopy.service;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyPersonalsummary;
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
 * @since 2020-11-26
 */
public interface IDcaBCopyPersonalsummaryService extends IService<DcaBCopyPersonalsummary> {

        IPage<DcaBCopyPersonalsummary> findDcaBCopyPersonalsummarys(QueryRequest request, DcaBCopyPersonalsummary dcaBCopyPersonalsummary);

        IPage<DcaBCopyPersonalsummary> findDcaBCopyPersonalsummaryList(QueryRequest request, DcaBCopyPersonalsummary dcaBCopyPersonalsummary);

        void createDcaBCopyPersonalsummary(DcaBCopyPersonalsummary dcaBCopyPersonalsummary);

        void updateDcaBCopyPersonalsummary(DcaBCopyPersonalsummary dcaBCopyPersonalsummary);

        void deleteDcaBCopyPersonalsummarys(String[]Ids);

        List<DcaBCopyPersonalsummary> getAll(String userAccount,String dcaYear);
        }
