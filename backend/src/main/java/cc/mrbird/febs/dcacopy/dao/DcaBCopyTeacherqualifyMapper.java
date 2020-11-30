package cc.mrbird.febs.dcacopy.dao;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyTeacherqualify;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 教师资格证书(教师系列需填写) Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
public interface DcaBCopyTeacherqualifyMapper extends BaseMapper<DcaBCopyTeacherqualify> {
        void updateDcaBCopyTeacherqualify(DcaBCopyTeacherqualify dcaBCopyTeacherqualify);
        IPage<DcaBCopyTeacherqualify> findDcaBCopyTeacherqualify(Page page, @Param("dcaBCopyTeacherqualify") DcaBCopyTeacherqualify dcaBCopyTeacherqualify);
        }
