package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaBTeachtalent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 任现职以来完成教学、人才培养情况 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-09-27
 */
public interface DcaBTeachtalentMapper extends BaseMapper<DcaBTeachtalent> {
        void updateDcaBTeachtalent(DcaBTeachtalent dcaBTeachtalent);
        IPage<DcaBTeachtalent> findDcaBTeachtalent(Page page, @Param("dcaBTeachtalent") DcaBTeachtalent dcaBTeachtalent);

@Update(" update dca_b_teachtalent set IS_DELETEMARK=0 where user_account=#{useraccount}")
        void deleteByAccount(@Param("useraccount") String useraccount);
        }
