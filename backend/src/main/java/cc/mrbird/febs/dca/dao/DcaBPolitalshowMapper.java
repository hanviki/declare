package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaBPolitalshow;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 个人思想政治表现 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-08-12
 */
public interface DcaBPolitalshowMapper extends BaseMapper<DcaBPolitalshow> {
        void updateDcaBPolitalshow(DcaBPolitalshow dcaBPolitalshow);
        IPage<DcaBPolitalshow> findDcaBPolitalshow(Page page, @Param("dcaBPolitalshow") DcaBPolitalshow dcaBPolitalshow);

@Update(" update dca_b_politalshow set IS_DELETEMARK=0 where user_account=#{useraccount}")
        void deleteByAccount(@Param("useraccount") String useraccount);
        }
