package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaDJb;
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
 * @since 2021-03-09
 */
public interface DcaDJbMapper extends BaseMapper<DcaDJb> {
        void updateDcaDJb(DcaDJb dcaDJb);
        IPage<DcaDJb> findDcaDJb(Page page, @Param("dcaDJb") DcaDJb dcaDJb);
        }
