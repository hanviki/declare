package cc.mrbird.febs.dcacopy.dao;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyParttimejob;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 社会兼职表 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
public interface DcaBCopyParttimejobMapper extends BaseMapper<DcaBCopyParttimejob> {
        void updateDcaBCopyParttimejob(DcaBCopyParttimejob dcaBCopyParttimejob);
        IPage<DcaBCopyParttimejob> findDcaBCopyParttimejob(Page page, @Param("dcaBCopyParttimejob") DcaBCopyParttimejob dcaBCopyParttimejob);
        }
