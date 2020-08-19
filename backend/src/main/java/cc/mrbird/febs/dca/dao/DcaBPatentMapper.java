package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaBPatent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 申请专利情况 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-08-11
 */
public interface DcaBPatentMapper extends BaseMapper<DcaBPatent> {
        void updateDcaBPatent(DcaBPatent dcaBPatent);
        IPage<DcaBPatent> findDcaBPatent(Page page, @Param("dcaBPatent") DcaBPatent dcaBPatent);

@Update(" update dca_b_patent set IS_DELETEMARK=0 where user_account=#{useraccount}")
        void deleteByAccount(@Param("useraccount") String useraccount);
        }
