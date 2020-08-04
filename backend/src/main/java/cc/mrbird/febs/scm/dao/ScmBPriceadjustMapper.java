package cc.mrbird.febs.scm.dao;

import cc.mrbird.febs.scm.entity.ScmBPriceadjust;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2019-12-27
 */
public interface ScmBPriceadjustMapper extends BaseMapper<ScmBPriceadjust> {
        void updateScmBPriceadjust(ScmBPriceadjust scmBPriceadjust);
        }
