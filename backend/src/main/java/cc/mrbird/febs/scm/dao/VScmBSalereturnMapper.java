package cc.mrbird.febs.scm.dao;

import cc.mrbird.febs.scm.entity.VScmBSalereturn;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * VIEW Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2019-12-09
 */
public interface VScmBSalereturnMapper extends BaseMapper<VScmBSalereturn> {
        void updateVScmBSalereturn(VScmBSalereturn vScmBSalereturn);
        }
