package cc.mrbird.febs.dcacopy.dao;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyPrizeorpunish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 何时受奖励处分 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
public interface DcaBCopyPrizeorpunishMapper extends BaseMapper<DcaBCopyPrizeorpunish> {
        void updateDcaBCopyPrizeorpunish(DcaBCopyPrizeorpunish dcaBCopyPrizeorpunish);
        IPage<DcaBCopyPrizeorpunish> findDcaBCopyPrizeorpunish(Page page, @Param("dcaBCopyPrizeorpunish") DcaBCopyPrizeorpunish dcaBCopyPrizeorpunish);
        }
