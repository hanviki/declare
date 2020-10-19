package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaDDeclaremodules;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 申请项目表 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-09-22
 */
public interface DcaDDeclaremodulesMapper extends BaseMapper<DcaDDeclaremodules> {
        void updateDcaDDeclaremodules(DcaDDeclaremodules dcaDDeclaremodules);
        IPage<DcaDDeclaremodules> findDcaDDeclaremodules(Page page, @Param("dcaDDeclaremodules") DcaDDeclaremodules dcaDDeclaremodules);
        }
