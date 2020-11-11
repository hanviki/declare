package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaBScientificprize;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 任现职以来科研获奖情况 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-11-06
 */
public interface DcaBScientificprizeMapper extends BaseMapper<DcaBScientificprize> {
        void updateDcaBScientificprize(DcaBScientificprize dcaBScientificprize);
        IPage<DcaBScientificprize> findDcaBScientificprize(Page page, @Param("dcaBScientificprize") DcaBScientificprize dcaBScientificprize);

@Update(" update dca_b_scientificprize set IS_DELETEMARK=0 where user_account=#{useraccount}  and (state=0 or state=2)")
        void deleteByAccount(@Param("useraccount") String useraccount);
@Select(" select IFNULL(max(display_index),0) As maxIndex from  dca_b_scientificprize  where user_account=#{useraccount} ")
        int getMaxDisplayIndexByuseraccount(@Param("useraccount") String useraccount);
        }
