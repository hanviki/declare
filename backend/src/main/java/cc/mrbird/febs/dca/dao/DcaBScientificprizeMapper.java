package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaBScientificprize;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 自任职以来科研获奖情况 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-08-11
 */
public interface DcaBScientificprizeMapper extends BaseMapper<DcaBScientificprize> {
        void updateDcaBScientificprize(DcaBScientificprize dcaBScientificprize);
        IPage<DcaBScientificprize> findDcaBScientificprize(Page page, @Param("dcaBScientificprize") DcaBScientificprize dcaBScientificprize);

@Update(" update dca_b_scientificprize set IS_DELETEMARK=0 where user_account=#{useraccount}")
        void deleteByAccount(@Param("useraccount") String useraccount);
        }
