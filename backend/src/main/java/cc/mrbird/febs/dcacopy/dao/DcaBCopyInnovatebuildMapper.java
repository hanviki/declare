package cc.mrbird.febs.dcacopy.dao;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyInnovatebuild;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 任现职以来承担的本科教学改革及建设项目(教师系列需填写) Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
public interface DcaBCopyInnovatebuildMapper extends BaseMapper<DcaBCopyInnovatebuild> {
        void updateDcaBCopyInnovatebuild(DcaBCopyInnovatebuild dcaBCopyInnovatebuild);
        IPage<DcaBCopyInnovatebuild> findDcaBCopyInnovatebuild(Page page, @Param("dcaBCopyInnovatebuild") DcaBCopyInnovatebuild dcaBCopyInnovatebuild);
        }
