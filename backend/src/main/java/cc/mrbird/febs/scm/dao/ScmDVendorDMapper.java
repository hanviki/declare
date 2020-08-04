package cc.mrbird.febs.scm.dao;

import cc.mrbird.febs.scm.entity.ScmDVendorD;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 供应商对应的资质文件表 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2019-11-14
 */
public interface ScmDVendorDMapper extends BaseMapper<ScmDVendorD> {
        void updateScmDVendorD(ScmDVendorD scmDVendorD);
        }
