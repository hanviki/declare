package cc.mrbird.febs.dcacopy.dao;

import cc.mrbird.febs.dcacopy.entity.DcaBCopySchoolprize;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 校教学质量奖、校教学成果奖 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
public interface DcaBCopySchoolprizeMapper extends BaseMapper<DcaBCopySchoolprize> {
        void updateDcaBCopySchoolprize(DcaBCopySchoolprize dcaBCopySchoolprize);
        IPage<DcaBCopySchoolprize> findDcaBCopySchoolprize(Page page, @Param("dcaBCopySchoolprize") DcaBCopySchoolprize dcaBCopySchoolprize);
        }
