package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaBYearapply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-09-24
 */
public interface DcaBYearapplyMapper extends BaseMapper<DcaBYearapply> {
        void updateDcaBYearapply(DcaBYearapply dcaBYearapply);
        IPage<DcaBYearapply> findDcaBYearapply(Page page, @Param("dcaBYearapply") DcaBYearapply dcaBYearapply);
        }
