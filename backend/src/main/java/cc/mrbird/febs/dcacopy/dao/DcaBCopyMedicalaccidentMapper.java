package cc.mrbird.febs.dcacopy.dao;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyMedicalaccident;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 担任博导硕导 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-12-15
 */
public interface DcaBCopyMedicalaccidentMapper extends BaseMapper<DcaBCopyMedicalaccident> {
        void updateDcaBCopyMedicalaccident(DcaBCopyMedicalaccident dcaBCopyMedicalaccident);
        IPage<DcaBCopyMedicalaccident> findDcaBCopyMedicalaccident(Page page, @Param("dcaBCopyMedicalaccident") DcaBCopyMedicalaccident dcaBCopyMedicalaccident);
        }
