package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaBAuditfive;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 近五年总体评价情况 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-10-19
 */
public interface DcaBAuditfiveMapper extends BaseMapper<DcaBAuditfive> {
        void updateDcaBAuditfive(DcaBAuditfive dcaBAuditfive);
        IPage<DcaBAuditfive> findDcaBAuditfive(Page page, @Param("dcaBAuditfive") DcaBAuditfive dcaBAuditfive);

@Update(" update dca_b_auditfive set IS_DELETEMARK=0 where user_account=#{useraccount}")
        void deleteByAccount(@Param("useraccount") String useraccount);
        }
