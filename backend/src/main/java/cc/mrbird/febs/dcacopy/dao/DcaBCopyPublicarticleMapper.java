package cc.mrbird.febs.dcacopy.dao;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyPublicarticle;
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
 * @since 2020-11-26
 */
public interface DcaBCopyPublicarticleMapper extends BaseMapper<DcaBCopyPublicarticle> {
        void updateDcaBCopyPublicarticle(DcaBCopyPublicarticle dcaBCopyPublicarticle);
        IPage<DcaBCopyPublicarticle> findDcaBCopyPublicarticle(Page page, @Param("dcaBCopyPublicarticle") DcaBCopyPublicarticle dcaBCopyPublicarticle);
        }
