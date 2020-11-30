package cc.mrbird.febs.dcacopy.dao;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyEssaypublish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 论文出版 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
public interface DcaBCopyEssaypublishMapper extends BaseMapper<DcaBCopyEssaypublish> {
        void updateDcaBCopyEssaypublish(DcaBCopyEssaypublish dcaBCopyEssaypublish);
        IPage<DcaBCopyEssaypublish> findDcaBCopyEssaypublish(Page page, @Param("dcaBCopyEssaypublish") DcaBCopyEssaypublish dcaBCopyEssaypublish);
        }
