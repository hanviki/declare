package cc.mrbird.febs.dcacopy.dao;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyTurtor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 担任辅导员教师班主任情况(教师系列需填写) Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
public interface DcaBCopyTurtorMapper extends BaseMapper<DcaBCopyTurtor> {
        void updateDcaBCopyTurtor(DcaBCopyTurtor dcaBCopyTurtor);
        IPage<DcaBCopyTurtor> findDcaBCopyTurtor(Page page, @Param("dcaBCopyTurtor") DcaBCopyTurtor dcaBCopyTurtor);
        }
