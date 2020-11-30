package cc.mrbird.febs.dcacopy.dao;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyYoungprize;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 青年教师教学竞赛获奖 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
public interface DcaBCopyYoungprizeMapper extends BaseMapper<DcaBCopyYoungprize> {
        void updateDcaBCopyYoungprize(DcaBCopyYoungprize dcaBCopyYoungprize);
        IPage<DcaBCopyYoungprize> findDcaBCopyYoungprize(Page page, @Param("dcaBCopyYoungprize") DcaBCopyYoungprize dcaBCopyYoungprize);
        }
