package cc.mrbird.febs.scm.dao;

import cc.mrbird.febs.scm.entity.ScmBReport;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 公告表 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2019-11-21
 */
public interface ScmBReportMapper extends BaseMapper<ScmBReport> {
        void updateScmBReport(ScmBReport scmBReport);
        }
