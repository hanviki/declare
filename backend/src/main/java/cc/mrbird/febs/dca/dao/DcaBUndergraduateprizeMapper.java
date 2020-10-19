package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaBUndergraduateprize;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 任现职以来本科教学工作获奖情况 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-10-15
 */
public interface DcaBUndergraduateprizeMapper extends BaseMapper<DcaBUndergraduateprize> {
        void updateDcaBUndergraduateprize(DcaBUndergraduateprize dcaBUndergraduateprize);
        IPage<DcaBUndergraduateprize> findDcaBUndergraduateprize(Page page, @Param("dcaBUndergraduateprize") DcaBUndergraduateprize dcaBUndergraduateprize);

@Update(" update dca_b_undergraduateprize set IS_DELETEMARK=0 where user_account=#{useraccount}")
        void deleteByAccount(@Param("useraccount") String useraccount);
        }
