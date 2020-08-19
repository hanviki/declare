package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaBSciencepublish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 科研论文 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-08-11
 */
public interface DcaBSciencepublishMapper extends BaseMapper<DcaBSciencepublish> {
        void updateDcaBSciencepublish(DcaBSciencepublish dcaBSciencepublish);
        IPage<DcaBSciencepublish> findDcaBSciencepublish(Page page, @Param("dcaBSciencepublish") DcaBSciencepublish dcaBSciencepublish);

@Update(" update dca_b_sciencepublish set IS_DELETEMARK=0 where user_account=#{useraccount}")
        void deleteByAccount(@Param("useraccount") String useraccount);
        }
