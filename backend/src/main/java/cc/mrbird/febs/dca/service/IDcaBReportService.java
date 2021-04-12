package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBReport;
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
 * @since 2020-11-13
 */
public interface IDcaBReportService extends IService<DcaBReport> {

        IPage<DcaBReport> findDcaBReports(QueryRequest request, DcaBReport dcaBReport);

        IPage<DcaBReport> findDcaBReportList(QueryRequest request, DcaBReport dcaBReport);

        void createDcaBReport(DcaBReport dcaBReport);

        void updateDcaBReport(DcaBReport dcaBReport);

        void updateShuangBaoDcaBReport(DcaBReport dcaBReport);

        void deleteDcaBReports(String[]Ids);

        List<DcaBReport> getReportTest();
        }
