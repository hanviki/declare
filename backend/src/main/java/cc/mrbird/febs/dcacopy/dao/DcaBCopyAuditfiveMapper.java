package cc.mrbird.febs.dcacopy.dao;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyAuditfive;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 近五年总体评价情况 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
public interface DcaBCopyAuditfiveMapper extends BaseMapper<DcaBCopyAuditfive> {
        void updateDcaBCopyAuditfive(DcaBCopyAuditfive dcaBCopyAuditfive);
        IPage<DcaBCopyAuditfive> findDcaBCopyAuditfive(Page page, @Param("dcaBCopyAuditfive") DcaBCopyAuditfive dcaBCopyAuditfive);
        }
