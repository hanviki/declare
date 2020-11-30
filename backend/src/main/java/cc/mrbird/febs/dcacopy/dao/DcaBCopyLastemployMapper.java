package cc.mrbird.febs.dcacopy.dao;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyLastemploy;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 完成上一聘期 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
public interface DcaBCopyLastemployMapper extends BaseMapper<DcaBCopyLastemploy> {
        void updateDcaBCopyLastemploy(DcaBCopyLastemploy dcaBCopyLastemploy);
        IPage<DcaBCopyLastemploy> findDcaBCopyLastemploy(Page page, @Param("dcaBCopyLastemploy") DcaBCopyLastemploy dcaBCopyLastemploy);
        }
