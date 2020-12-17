package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaBAuditfive;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 近五年总体评价情况 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-12-16
 */
public interface DcaBAuditfiveMapper extends BaseMapper<DcaBAuditfive> {
        void updateDcaBAuditfive(DcaBAuditfive dcaBAuditfive);
        IPage<DcaBAuditfive> findDcaBAuditfive(Page page, @Param("dcaBAuditfive") DcaBAuditfive dcaBAuditfive);

@Update(" update dca_b_auditfive set IS_DELETEMARK=0 where user_account=#{useraccount}  and (state=0 or state=2)")
        void deleteByAccount(@Param("useraccount") String useraccount);
@Select(" select IFNULL(max(display_index),0) As maxIndex from  dca_b_auditfive  where user_account=#{useraccount} ")
        int getMaxDisplayIndexByuseraccount(@Param("useraccount") String useraccount);
        }
