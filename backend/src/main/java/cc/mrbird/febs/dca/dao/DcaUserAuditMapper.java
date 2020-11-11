package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaUserAudit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 用户审核字段表 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-10-23
 */
public interface DcaUserAuditMapper extends BaseMapper<DcaUserAudit> {
        void updateDcaUserAudit(DcaUserAudit dcaUserAudit);
        IPage<DcaUserAudit> findDcaUserAudit(Page page, @Param("dcaUserAudit") DcaUserAudit dcaUserAudit);

@Update(" update dca_user_audit set IS_DELETEMARK=0 where user_account=#{useraccount}  and (state=0 or state=2)")
        void deleteByAccount(@Param("useraccount") String useraccount);
@Select(" select IFNULL(max(display_index),0) As maxIndex from  dca_user_audit  where user_account=#{useraccount} ")
        int getMaxDisplayIndexByuseraccount(@Param("useraccount") String useraccount);
        }
