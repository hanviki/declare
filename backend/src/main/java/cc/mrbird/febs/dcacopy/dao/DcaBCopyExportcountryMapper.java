package cc.mrbird.febs.dcacopy.dao;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyExportcountry;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 出国情况 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
public interface DcaBCopyExportcountryMapper extends BaseMapper<DcaBCopyExportcountry> {
        void updateDcaBCopyExportcountry(DcaBCopyExportcountry dcaBCopyExportcountry);
        IPage<DcaBCopyExportcountry> findDcaBCopyExportcountry(Page page, @Param("dcaBCopyExportcountry") DcaBCopyExportcountry dcaBCopyExportcountry);
        }
