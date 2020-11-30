package cc.mrbird.febs.dcacopy.dao;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyPaperspublish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 教学论文出版教材 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
public interface DcaBCopyPaperspublishMapper extends BaseMapper<DcaBCopyPaperspublish> {
        void updateDcaBCopyPaperspublish(DcaBCopyPaperspublish dcaBCopyPaperspublish);
        IPage<DcaBCopyPaperspublish> findDcaBCopyPaperspublish(Page page, @Param("dcaBCopyPaperspublish") DcaBCopyPaperspublish dcaBCopyPaperspublish);
        }
