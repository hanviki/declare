package cc.mrbird.febs.dcacopy.dao;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyEducationexperice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 学习工作经历 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
public interface DcaBCopyEducationexpericeMapper extends BaseMapper<DcaBCopyEducationexperice> {
        void updateDcaBCopyEducationexperice(DcaBCopyEducationexperice dcaBCopyEducationexperice);
        IPage<DcaBCopyEducationexperice> findDcaBCopyEducationexperice(Page page, @Param("dcaBCopyEducationexperice") DcaBCopyEducationexperice dcaBCopyEducationexperice);
        }
