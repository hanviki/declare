package cc.mrbird.febs.scm.dao;

import cc.mrbird.febs.scm.entity.VQuotedprice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * VIEW Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-01-07
 */
public interface VQuotedpriceMapper extends BaseMapper<VQuotedprice> {
        void updateVQuotedprice(VQuotedprice vQuotedprice);
        }
