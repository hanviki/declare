package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaBPaperspublish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 教学论文出版教材 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-08-11
 */
public interface DcaBPaperspublishMapper extends BaseMapper<DcaBPaperspublish> {
        void updateDcaBPaperspublish(DcaBPaperspublish dcaBPaperspublish);
        IPage<DcaBPaperspublish> findDcaBPaperspublish(Page page, @Param("dcaBPaperspublish") DcaBPaperspublish dcaBPaperspublish);

@Update(" update dca_b_paperspublish set IS_DELETEMARK=0 where user_account=#{useraccount}")
        void deleteByAccount(@Param("useraccount") String useraccount);
        }
