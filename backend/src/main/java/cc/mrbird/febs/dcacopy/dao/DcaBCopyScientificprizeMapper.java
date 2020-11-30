package cc.mrbird.febs.dcacopy.dao;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyScientificprize;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 任现职以来科研获奖情况 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
public interface DcaBCopyScientificprizeMapper extends BaseMapper<DcaBCopyScientificprize> {
        void updateDcaBCopyScientificprize(DcaBCopyScientificprize dcaBCopyScientificprize);
        IPage<DcaBCopyScientificprize> findDcaBCopyScientificprize(Page page, @Param("dcaBCopyScientificprize") DcaBCopyScientificprize dcaBCopyScientificprize);
        }
