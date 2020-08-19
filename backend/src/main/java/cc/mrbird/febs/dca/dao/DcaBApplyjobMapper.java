package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaBApplyjob;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 拟聘岗位 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-08-04
 */
public interface DcaBApplyjobMapper extends BaseMapper<DcaBApplyjob> {
        void updateDcaBApplyjob(DcaBApplyjob dcaBApplyjob);
        IPage<DcaBApplyjob> findDcaBApplyjob(Page page, @Param("dcaBApplyjob") DcaBApplyjob dcaBApplyjob);
        @Update(" update dca_b_applyjob set IS_DELETEMARK=0 where user_account=#{useraccount}")
        void deleteByAccount(@Param("useraccount") String useraccount);
        }
