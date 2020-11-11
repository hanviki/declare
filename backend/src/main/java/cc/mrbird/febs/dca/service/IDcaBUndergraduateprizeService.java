package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBUndergraduateprize;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 任现职以来本科教学工作获奖情况 服务类
 * </p>
 *
 * @author viki
 * @since 2020-10-20
 */
public interface IDcaBUndergraduateprizeService extends IService<DcaBUndergraduateprize> {

        IPage<DcaBUndergraduateprize> findDcaBUndergraduateprizes(QueryRequest request, DcaBUndergraduateprize dcaBUndergraduateprize);

        IPage<DcaBUndergraduateprize> findDcaBUndergraduateprizeList(QueryRequest request, DcaBUndergraduateprize dcaBUndergraduateprize);

        void createDcaBUndergraduateprize(DcaBUndergraduateprize dcaBUndergraduateprize);

        void updateDcaBUndergraduateprize(DcaBUndergraduateprize dcaBUndergraduateprize);

        void deleteDcaBUndergraduateprizes(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
