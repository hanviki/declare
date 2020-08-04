package cc.mrbird.febs.scm.dao;

import cc.mrbird.febs.scm.entity.VMsgVendorundo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * VIEW Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-05-12
 */
public interface VMsgVendorundoMapper extends BaseMapper<VMsgVendorundo> {
        void updateVMsgVendorundo(VMsgVendorundo vMsgVendorundo);
        }
