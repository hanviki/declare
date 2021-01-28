package cc.mrbird.febs.check.service;

import cc.mrbird.febs.check.entity.CheckDReport;
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
 * @since 2021-01-27
 */
public interface ICheckDReportService extends IService<CheckDReport> {

        IPage<CheckDReport> findCheckDReports(QueryRequest request, CheckDReport checkDReport);

        IPage<CheckDReport> findCheckDReportList(QueryRequest request, CheckDReport checkDReport);

        void createCheckDReport(CheckDReport checkDReport);

        void updateCheckDReport(CheckDReport checkDReport);

        void deleteCheckDReports(String[]Ids);

        List<CheckDReport> getAll(String userAccount,String dcaYear);
        }
