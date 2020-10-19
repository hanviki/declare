package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaBAuditsuggestion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 审核意见表 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-09-22
 */
public interface DcaBAuditsuggestionMapper extends BaseMapper<DcaBAuditsuggestion> {
        void updateDcaBAuditsuggestion(DcaBAuditsuggestion dcaBAuditsuggestion);
        IPage<DcaBAuditsuggestion> findDcaBAuditsuggestion(Page page, @Param("dcaBAuditsuggestion") DcaBAuditsuggestion dcaBAuditsuggestion);
        }
