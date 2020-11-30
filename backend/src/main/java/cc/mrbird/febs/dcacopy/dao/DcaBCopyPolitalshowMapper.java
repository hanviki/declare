package cc.mrbird.febs.dcacopy.dao;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyPolitalshow;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 个人思想政治表现 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
public interface DcaBCopyPolitalshowMapper extends BaseMapper<DcaBCopyPolitalshow> {
        void updateDcaBCopyPolitalshow(DcaBCopyPolitalshow dcaBCopyPolitalshow);
        IPage<DcaBCopyPolitalshow> findDcaBCopyPolitalshow(Page page, @Param("dcaBCopyPolitalshow") DcaBCopyPolitalshow dcaBCopyPolitalshow);
        }
