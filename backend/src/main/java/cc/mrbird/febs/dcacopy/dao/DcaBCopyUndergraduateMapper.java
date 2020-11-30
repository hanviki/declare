package cc.mrbird.febs.dcacopy.dao;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyUndergraduate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 本科教学情况 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
public interface DcaBCopyUndergraduateMapper extends BaseMapper<DcaBCopyUndergraduate> {
        void updateDcaBCopyUndergraduate(DcaBCopyUndergraduate dcaBCopyUndergraduate);
        IPage<DcaBCopyUndergraduate> findDcaBCopyUndergraduate(Page page, @Param("dcaBCopyUndergraduate") DcaBCopyUndergraduate dcaBCopyUndergraduate);
        }
