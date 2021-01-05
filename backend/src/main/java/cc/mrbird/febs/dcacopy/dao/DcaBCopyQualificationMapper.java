package cc.mrbird.febs.dcacopy.dao;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyQualification;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 资质情况 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-12-25
 */
public interface DcaBCopyQualificationMapper extends BaseMapper<DcaBCopyQualification> {
        void updateDcaBCopyQualification(DcaBCopyQualification dcaBCopyQualification);
        IPage<DcaBCopyQualification> findDcaBCopyQualification(Page page, @Param("dcaBCopyQualification") DcaBCopyQualification dcaBCopyQualification);
        }
