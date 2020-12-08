package cc.mrbird.febs.out.dao;

import cc.mrbird.febs.out.entity.OutBInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 接口投票 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-12-01
 */
public interface OutBInfoMapper extends BaseMapper<OutBInfo> {
        void updateOutBInfo(OutBInfo outBInfo);
        IPage<OutBInfo> findOutBInfo(Page page, @Param("outBInfo") OutBInfo outBInfo);
        }
