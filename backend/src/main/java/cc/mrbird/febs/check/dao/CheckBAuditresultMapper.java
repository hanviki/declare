package cc.mrbird.febs.check.dao;

import cc.mrbird.febs.check.entity.CheckBAuditresult;
import cc.mrbird.febs.check.entity.TotalResultNum;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 指标结果表 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2021-01-22
 */
public interface CheckBAuditresultMapper extends BaseMapper<CheckBAuditresult> {
        void updateCheckBAuditresult(CheckBAuditresult checkBAuditresult);
        IPage<CheckBAuditresult> findCheckBAuditresult(Page page, @Param("checkBAuditresult") CheckBAuditresult checkBAuditresult);

        List<TotalResultNum> findTotalResult(@Param("checkBAuditresult") CheckBAuditresult checkBAuditresult);
        List<TotalResultNum> findStrResult(@Param("checkBAuditresult") CheckBAuditresult checkBAuditresult);
        }
