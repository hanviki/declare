package cc.mrbird.febs.dcacopy.dao;

import cc.mrbird.febs.dcacopy.entity.DcaBCopySciencesearch;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 任现职以来承担的科研项目 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
public interface DcaBCopySciencesearchMapper extends BaseMapper<DcaBCopySciencesearch> {
        void updateDcaBCopySciencesearch(DcaBCopySciencesearch dcaBCopySciencesearch);
        IPage<DcaBCopySciencesearch> findDcaBCopySciencesearch(Page page, @Param("dcaBCopySciencesearch") DcaBCopySciencesearch dcaBCopySciencesearch);
        }
