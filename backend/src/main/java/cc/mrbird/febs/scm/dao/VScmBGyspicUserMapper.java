package cc.mrbird.febs.scm.dao;

import cc.mrbird.febs.scm.entity.VScmBGyspicUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * VIEW Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-07-13
 */
public interface VScmBGyspicUserMapper extends BaseMapper<VScmBGyspicUser> {
        void updateVScmBGyspicUser(VScmBGyspicUser vScmBGyspicUser);
        IPage<VScmBGyspicUser> findVScmBGyspicUser(Page page, @Param("vScmBGyspicUser") VScmBGyspicUser vScmBGyspicUser);
        }
