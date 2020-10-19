package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaBWorknum;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-10-16
 */
public interface DcaBWorknumMapper extends BaseMapper<DcaBWorknum> {
        void updateDcaBWorknum(DcaBWorknum dcaBWorknum);
        IPage<DcaBWorknum> findDcaBWorknum(Page page, @Param("dcaBWorknum") DcaBWorknum dcaBWorknum);

@Update(" update dca_b_worknum set IS_DELETEMARK=0 where user_account=#{useraccount}")
        void deleteByAccount(@Param("useraccount") String useraccount);
        }
