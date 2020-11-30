package cc.mrbird.febs.dcacopy.dao;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyEmploy;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 任职培养 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
public interface DcaBCopyEmployMapper extends BaseMapper<DcaBCopyEmploy> {
        void updateDcaBCopyEmploy(DcaBCopyEmploy dcaBCopyEmploy);
        IPage<DcaBCopyEmploy> findDcaBCopyEmploy(Page page, @Param("dcaBCopyEmploy") DcaBCopyEmploy dcaBCopyEmploy);
        }
