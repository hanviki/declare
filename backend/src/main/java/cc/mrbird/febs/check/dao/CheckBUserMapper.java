package cc.mrbird.febs.check.dao;

import cc.mrbird.febs.check.entity.CheckBUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 待审核用户 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2021-01-22
 */
public interface CheckBUserMapper extends BaseMapper<CheckBUser> {
        void updateCheckBUser(CheckBUser checkBUser);
        IPage<CheckBUser> findCheckBUser(Page page, @Param("checkBUser") CheckBUser checkBUser);
        }
