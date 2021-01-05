package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaBWorknum;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 近三年业务工作量 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-12-28
 */
public interface DcaBWorknumMapper extends BaseMapper<DcaBWorknum> {
        void updateDcaBWorknum(DcaBWorknum dcaBWorknum);
        IPage<DcaBWorknum> findDcaBWorknum(Page page, @Param("dcaBWorknum") DcaBWorknum dcaBWorknum);

@Update(" update dca_b_worknum set IS_DELETEMARK=0 where user_account=#{useraccount}  and (state=0 or state=2)")
        void deleteByAccount(@Param("useraccount") String useraccount);
@Select(" select IFNULL(max(display_index),0) As maxIndex from  dca_b_worknum  where user_account=#{useraccount} ")
        int getMaxDisplayIndexByuseraccount(@Param("useraccount") String useraccount);
        }
