package cc.mrbird.febs.dcacopy.dao;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyTalent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 任现职以来完成研究生教学人才培养情况 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
public interface DcaBCopyTalentMapper extends BaseMapper<DcaBCopyTalent> {
        void updateDcaBCopyTalent(DcaBCopyTalent dcaBCopyTalent);
        IPage<DcaBCopyTalent> findDcaBCopyTalent(Page page, @Param("dcaBCopyTalent") DcaBCopyTalent dcaBCopyTalent);
        }
