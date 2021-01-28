package cc.mrbird.febs.doctor.service;

import cc.mrbird.febs.doctor.entity.DcaBDocPrizeorpunish;
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
 * @since 2021-01-11
 */
public interface IDcaBDocPrizeorpunishService extends IService<DcaBDocPrizeorpunish> {

        IPage<DcaBDocPrizeorpunish> findDcaBDocPrizeorpunishs(QueryRequest request, DcaBDocPrizeorpunish dcaBDocPrizeorpunish);

        IPage<DcaBDocPrizeorpunish> findDcaBDocPrizeorpunishList(QueryRequest request, DcaBDocPrizeorpunish dcaBDocPrizeorpunish);

        void createDcaBDocPrizeorpunish(DcaBDocPrizeorpunish dcaBDocPrizeorpunish);

        void updateDcaBDocPrizeorpunish(DcaBDocPrizeorpunish dcaBDocPrizeorpunish);

        void deleteDcaBDocPrizeorpunishs(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
