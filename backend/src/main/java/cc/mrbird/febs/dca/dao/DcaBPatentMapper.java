package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaBPatent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 申请专利情况 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-10-20
 */
public interface DcaBPatentMapper extends BaseMapper<DcaBPatent> {
        void updateDcaBPatent(DcaBPatent dcaBPatent);
        IPage<DcaBPatent> findDcaBPatent(Page page, @Param("dcaBPatent") DcaBPatent dcaBPatent);

@Update(" update dca_b_patent set IS_DELETEMARK=0 where user_account=#{useraccount}  and (state=0 or state=2)")
        void deleteByAccount(@Param("useraccount") String useraccount);
@Select(" select IFNULL(max(display_index),0) As maxIndex from  dca_b_patent  where user_account=#{useraccount} ")
        int getMaxDisplayIndexByuseraccount(@Param("useraccount") String useraccount);
        }
