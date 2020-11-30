package cc.mrbird.febs.dcacopy.service;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyPrizeorpunish;
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
 * @since 2020-11-26
 */
public interface IDcaBCopyPrizeorpunishService extends IService<DcaBCopyPrizeorpunish> {

        IPage<DcaBCopyPrizeorpunish> findDcaBCopyPrizeorpunishs(QueryRequest request, DcaBCopyPrizeorpunish dcaBCopyPrizeorpunish);

        IPage<DcaBCopyPrizeorpunish> findDcaBCopyPrizeorpunishList(QueryRequest request, DcaBCopyPrizeorpunish dcaBCopyPrizeorpunish);

        void createDcaBCopyPrizeorpunish(DcaBCopyPrizeorpunish dcaBCopyPrizeorpunish);

        void updateDcaBCopyPrizeorpunish(DcaBCopyPrizeorpunish dcaBCopyPrizeorpunish);

        void deleteDcaBCopyPrizeorpunishs(String[]Ids);

        List<DcaBCopyPrizeorpunish> getAll(String userAccount,String dcaYear);
        }
