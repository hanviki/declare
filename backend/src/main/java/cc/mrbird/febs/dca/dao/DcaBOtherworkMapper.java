package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaBOtherwork;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 其他工作及成果，拟聘岗位工作思路及预期目标，个人总结 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-08-11
 */
public interface DcaBOtherworkMapper extends BaseMapper<DcaBOtherwork> {
        void updateDcaBOtherwork(DcaBOtherwork dcaBOtherwork);
        IPage<DcaBOtherwork> findDcaBOtherwork(Page page, @Param("dcaBOtherwork") DcaBOtherwork dcaBOtherwork);

@Update(" update dca_b_otherwork set IS_DELETEMARK=0 where user_account=#{useraccount}")
        void deleteByAccount(@Param("useraccount") String useraccount);
        }
