package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaBPersonalsummary;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 个人总结 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-08-12
 */
public interface DcaBPersonalsummaryMapper extends BaseMapper<DcaBPersonalsummary> {
        void updateDcaBPersonalsummary(DcaBPersonalsummary dcaBPersonalsummary);
        IPage<DcaBPersonalsummary> findDcaBPersonalsummary(Page page, @Param("dcaBPersonalsummary") DcaBPersonalsummary dcaBPersonalsummary);

@Update(" update dca_b_personalsummary set IS_DELETEMARK=0 where user_account=#{useraccount}")
        void deleteByAccount(@Param("useraccount") String useraccount);
        }
