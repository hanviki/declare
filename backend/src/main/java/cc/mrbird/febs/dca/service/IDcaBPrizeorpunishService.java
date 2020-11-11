package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBPrizeorpunish;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 何时受奖励处分 服务类
 * </p>
 *
 * @author viki
 * @since 2020-10-20
 */
public interface IDcaBPrizeorpunishService extends IService<DcaBPrizeorpunish> {

        IPage<DcaBPrizeorpunish> findDcaBPrizeorpunishs(QueryRequest request, DcaBPrizeorpunish dcaBPrizeorpunish);

        IPage<DcaBPrizeorpunish> findDcaBPrizeorpunishList(QueryRequest request, DcaBPrizeorpunish dcaBPrizeorpunish);

        void createDcaBPrizeorpunish(DcaBPrizeorpunish dcaBPrizeorpunish);

        void updateDcaBPrizeorpunish(DcaBPrizeorpunish dcaBPrizeorpunish);

        void deleteDcaBPrizeorpunishs(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
