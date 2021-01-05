package cc.mrbird.febs.dcacopy.dao;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyUser;
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
 * @since 2020-12-25
 */
public interface DcaBCopyUserMapper extends BaseMapper<DcaBCopyUser> {
        void updateDcaBCopyUser(DcaBCopyUser dcaBCopyUser);
        IPage<DcaBCopyUser> findDcaBCopyUser(Page page, @Param("dcaBCopyUser") DcaBCopyUser dcaBCopyUser);
        }
