package cc.mrbird.febs.check.dao;

import cc.mrbird.febs.check.entity.CheckDReport;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2021-01-27
 */
public interface CheckDReportMapper extends BaseMapper<CheckDReport> {
        void updateCheckDReport(CheckDReport checkDReport);
        IPage<CheckDReport> findCheckDReport(Page page, @Param("checkDReport") CheckDReport checkDReport);
        }
