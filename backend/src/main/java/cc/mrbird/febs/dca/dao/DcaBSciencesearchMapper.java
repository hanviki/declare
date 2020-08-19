package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaBSciencesearch;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 科研项目 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-08-13
 */
public interface DcaBSciencesearchMapper extends BaseMapper<DcaBSciencesearch> {
        void updateDcaBSciencesearch(DcaBSciencesearch dcaBSciencesearch);
        IPage<DcaBSciencesearch> findDcaBSciencesearch(Page page, @Param("dcaBSciencesearch") DcaBSciencesearch dcaBSciencesearch);

@Update(" update dca_b_sciencesearch set IS_DELETEMARK=0 where user_account=#{useraccount}")
        void deleteByAccount(@Param("useraccount") String useraccount);
        }
