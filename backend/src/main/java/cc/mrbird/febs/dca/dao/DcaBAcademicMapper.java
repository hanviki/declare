package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaBAcademic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 重要岗位任职及学术影响 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-12-15
 */
public interface DcaBAcademicMapper extends BaseMapper<DcaBAcademic> {
        void updateDcaBAcademic(DcaBAcademic dcaBAcademic);
        IPage<DcaBAcademic> findDcaBAcademic(Page page, @Param("dcaBAcademic") DcaBAcademic dcaBAcademic);

@Update(" update dca_b_academic set IS_DELETEMARK=0 where user_account=#{useraccount}  and (state=0 or state=2)")
        void deleteByAccount(@Param("useraccount") String useraccount);
@Select(" select IFNULL(max(display_index),0) As maxIndex from  dca_b_academic  where user_account=#{useraccount} ")
        int getMaxDisplayIndexByuseraccount(@Param("useraccount") String useraccount);
        }
