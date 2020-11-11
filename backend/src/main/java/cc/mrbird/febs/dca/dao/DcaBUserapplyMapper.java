package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaBUserapply;
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
 * @since 2020-11-05
 */
public interface DcaBUserapplyMapper extends BaseMapper<DcaBUserapply> {
        void updateDcaBUserapply(DcaBUserapply dcaBUserapply);
        IPage<DcaBUserapply> findDcaBUserapply(Page page, @Param("dcaBUserapply") DcaBUserapply dcaBUserapply);
        }
