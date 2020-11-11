package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaBSchoolprize;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 校教学质量奖、校教学成果奖 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-11-03
 */
public interface DcaBSchoolprizeMapper extends BaseMapper<DcaBSchoolprize> {
        void updateDcaBSchoolprize(DcaBSchoolprize dcaBSchoolprize);
        IPage<DcaBSchoolprize> findDcaBSchoolprize(Page page, @Param("dcaBSchoolprize") DcaBSchoolprize dcaBSchoolprize);

@Update(" update dca_b_schoolprize set IS_DELETEMARK=0 where user_account=#{useraccount}  and (state=0 or state=2)")
        void deleteByAccount(@Param("useraccount") String useraccount);
@Select(" select IFNULL(max(display_index),0) As maxIndex from  dca_b_schoolprize  where user_account=#{useraccount} ")
        int getMaxDisplayIndexByuseraccount(@Param("useraccount") String useraccount);
        }
