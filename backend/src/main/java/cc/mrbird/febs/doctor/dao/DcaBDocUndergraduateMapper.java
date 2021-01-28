package cc.mrbird.febs.doctor.dao;

import cc.mrbird.febs.doctor.entity.DcaBDocUndergraduate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 本科教学情况 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2021-01-11
 */
public interface DcaBDocUndergraduateMapper extends BaseMapper<DcaBDocUndergraduate> {
        void updateDcaBDocUndergraduate(DcaBDocUndergraduate dcaBDocUndergraduate);
        IPage<DcaBDocUndergraduate> findDcaBDocUndergraduate(Page page, @Param("dcaBDocUndergraduate") DcaBDocUndergraduate dcaBDocUndergraduate);

@Update(" update dca_b_doc_undergraduate set IS_DELETEMARK=0 where user_account=#{useraccount}  and (state=0 or state=2)")
        void deleteByAccount(@Param("useraccount") String useraccount);
@Select(" select IFNULL(max(display_index),0) As maxIndex from  dca_b_doc_undergraduate  where user_account=#{useraccount} ")
        int getMaxDisplayIndexByuseraccount(@Param("useraccount") String useraccount);
        }
