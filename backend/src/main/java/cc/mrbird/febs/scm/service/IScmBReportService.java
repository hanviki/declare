package cc.mrbird.febs.scm.service;

import cc.mrbird.febs.scm.entity.ScmBReport;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 公告表 服务类
 * </p>
 *
 * @author viki
 * @since 2019-11-21
 */
public interface IScmBReportService extends IService<ScmBReport> {

        IPage<ScmBReport> findScmBReports(QueryRequest request, ScmBReport scmBReport);

        void createScmBReport(ScmBReport scmBReport);

        void updateScmBReport(ScmBReport scmBReport);

        void deleteScmBReports(String[]Ids);
        }
