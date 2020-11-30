package cc.mrbird.febs.dcacopy.dao;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyUndergraduateprize;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 任现职以来本科教学工作获奖情况(教师系列需填写) Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
public interface DcaBCopyUndergraduateprizeMapper extends BaseMapper<DcaBCopyUndergraduateprize> {
        void updateDcaBCopyUndergraduateprize(DcaBCopyUndergraduateprize dcaBCopyUndergraduateprize);
        IPage<DcaBCopyUndergraduateprize> findDcaBCopyUndergraduateprize(Page page, @Param("dcaBCopyUndergraduateprize") DcaBCopyUndergraduateprize dcaBCopyUndergraduateprize);
        }
