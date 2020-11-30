package cc.mrbird.febs.dcacopy.dao;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyGraduate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 任现职以来独立指导研究生情况(教师系列需填写) Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
public interface DcaBCopyGraduateMapper extends BaseMapper<DcaBCopyGraduate> {
        void updateDcaBCopyGraduate(DcaBCopyGraduate dcaBCopyGraduate);
        IPage<DcaBCopyGraduate> findDcaBCopyGraduate(Page page, @Param("dcaBCopyGraduate") DcaBCopyGraduate dcaBCopyGraduate);
        }
