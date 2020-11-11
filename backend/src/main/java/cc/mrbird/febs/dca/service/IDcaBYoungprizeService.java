package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBYoungprize;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 青年教师教学竞赛获奖 服务类
 * </p>
 *
 * @author viki
 * @since 2020-11-03
 */
public interface IDcaBYoungprizeService extends IService<DcaBYoungprize> {

        IPage<DcaBYoungprize> findDcaBYoungprizes(QueryRequest request, DcaBYoungprize dcaBYoungprize);

        IPage<DcaBYoungprize> findDcaBYoungprizeList(QueryRequest request, DcaBYoungprize dcaBYoungprize);

        void createDcaBYoungprize(DcaBYoungprize dcaBYoungprize);

        void updateDcaBYoungprize(DcaBYoungprize dcaBYoungprize);

        void deleteDcaBYoungprizes(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
