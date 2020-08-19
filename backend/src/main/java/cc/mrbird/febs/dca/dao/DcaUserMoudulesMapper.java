package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaUserMoudules;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 申报模块和用户表关联表 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-08-10
 */
public interface DcaUserMoudulesMapper extends BaseMapper<DcaUserMoudules> {
        void updateDcaUserMoudules(DcaUserMoudules dcaUserMoudules);
        IPage<DcaUserMoudules> findDcaUserMoudules(Page page, @Param("dcaUserMoudules") DcaUserMoudules dcaUserMoudules);
        }
