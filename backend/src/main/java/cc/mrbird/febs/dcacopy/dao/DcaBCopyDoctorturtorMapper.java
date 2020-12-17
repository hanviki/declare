package cc.mrbird.febs.dcacopy.dao;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyDoctorturtor;
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
public interface DcaBCopyDoctorturtorMapper extends BaseMapper<DcaBCopyDoctorturtor> {
        void updateDcaBCopyDoctorturtor(DcaBCopyDoctorturtor dcaBCopyDoctorturtor);
        IPage<DcaBCopyDoctorturtor> findDcaBCopyDoctorturtor(Page page, @Param("dcaBCopyDoctorturtor") DcaBCopyDoctorturtor dcaBCopyDoctorturtor);
        }
