package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaBTitleapply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 职称申请表 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-09-22
 */
public interface DcaBTitleapplyMapper extends BaseMapper<DcaBTitleapply> {
        void updateDcaBTitleapply(DcaBTitleapply dcaBTitleapply);
        IPage<DcaBTitleapply> findDcaBTitleapply(Page page, @Param("dcaBTitleapply") DcaBTitleapply dcaBTitleapply);
        }
