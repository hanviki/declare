package cc.mrbird.febs.dcacopy.dao;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyTeacherprize;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 任现职以来教学获奖 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
public interface DcaBCopyTeacherprizeMapper extends BaseMapper<DcaBCopyTeacherprize> {
        void updateDcaBCopyTeacherprize(DcaBCopyTeacherprize dcaBCopyTeacherprize);
        IPage<DcaBCopyTeacherprize> findDcaBCopyTeacherprize(Page page, @Param("dcaBCopyTeacherprize") DcaBCopyTeacherprize dcaBCopyTeacherprize);
        }
