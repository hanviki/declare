package cc.mrbird.febs.dcacopy.dao;

import cc.mrbird.febs.dcacopy.entity.DcaBCopySciencepublish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 任现职以来发表的论文、出版著作 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
public interface DcaBCopySciencepublishMapper extends BaseMapper<DcaBCopySciencepublish> {
        void updateDcaBCopySciencepublish(DcaBCopySciencepublish dcaBCopySciencepublish);
        IPage<DcaBCopySciencepublish> findDcaBCopySciencepublish(Page page, @Param("dcaBCopySciencepublish") DcaBCopySciencepublish dcaBCopySciencepublish);
        }
