package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaBFivecomment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 近五年总体项目评价 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-08-13
 */
public interface DcaBFivecommentMapper extends BaseMapper<DcaBFivecomment> {
        void updateDcaBFivecomment(DcaBFivecomment dcaBFivecomment);
        IPage<DcaBFivecomment> findDcaBFivecomment(Page page, @Param("dcaBFivecomment") DcaBFivecomment dcaBFivecomment);

@Update(" update dca_b_fivecomment set IS_DELETEMARK=0 where user_account=#{useraccount}")
        void deleteByAccount(@Param("useraccount") String useraccount);
        }
