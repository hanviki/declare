package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaBUserapplyzc;
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
 * @since 2020-12-24
 */
public interface DcaBUserapplyzcMapper extends BaseMapper<DcaBUserapplyzc> {
        void updateDcaBUserapplyzc(DcaBUserapplyzc dcaBUserapplyzc);
        IPage<DcaBUserapplyzc> findDcaBUserapplyzc(Page page, @Param("dcaBUserapplyzc") DcaBUserapplyzc dcaBUserapplyzc);
        }
