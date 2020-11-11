package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaDXl;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 系列名称 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-10-29
 */
public interface DcaDXlMapper extends BaseMapper<DcaDXl> {
        void updateDcaDXl(DcaDXl dcaDXl);
        IPage<DcaDXl> findDcaDXl(Page page, @Param("dcaDXl") DcaDXl dcaDXl);

@Update(" update dca_d_xl set IS_DELETEMARK=0 where user_account=#{useraccount}  and (state=0 or state=2)")
        void deleteByAccount(@Param("useraccount") String useraccount);
@Select(" select IFNULL(max(display_index),0) As maxIndex from  dca_d_xl  where user_account=#{useraccount} ")
        int getMaxDisplayIndexByuseraccount(@Param("useraccount") String useraccount);
        }
