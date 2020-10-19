package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaBTitleapplyD;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-09-22
 */
public interface DcaBTitleapplyDMapper extends BaseMapper<DcaBTitleapplyD> {
        void updateDcaBTitleapplyD(DcaBTitleapplyD dcaBTitleapplyD);
        IPage<DcaBTitleapplyD> findDcaBTitleapplyD(Page page, @Param("dcaBTitleapplyD") DcaBTitleapplyD dcaBTitleapplyD);
        }
