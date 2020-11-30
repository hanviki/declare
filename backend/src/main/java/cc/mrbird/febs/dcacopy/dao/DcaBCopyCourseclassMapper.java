package cc.mrbird.febs.dcacopy.dao;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyCourseclass;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 精品课程 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
public interface DcaBCopyCourseclassMapper extends BaseMapper<DcaBCopyCourseclass> {
        void updateDcaBCopyCourseclass(DcaBCopyCourseclass dcaBCopyCourseclass);
        IPage<DcaBCopyCourseclass> findDcaBCopyCourseclass(Page page, @Param("dcaBCopyCourseclass") DcaBCopyCourseclass dcaBCopyCourseclass);
        }
