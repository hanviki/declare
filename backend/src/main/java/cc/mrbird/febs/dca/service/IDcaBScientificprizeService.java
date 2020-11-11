package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBScientificprize;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 任现职以来科研获奖情况 服务类
 * </p>
 *
 * @author viki
 * @since 2020-11-06
 */
public interface IDcaBScientificprizeService extends IService<DcaBScientificprize> {

        IPage<DcaBScientificprize> findDcaBScientificprizes(QueryRequest request, DcaBScientificprize dcaBScientificprize);

        IPage<DcaBScientificprize> findDcaBScientificprizeList(QueryRequest request, DcaBScientificprize dcaBScientificprize);

        void createDcaBScientificprize(DcaBScientificprize dcaBScientificprize);

        void updateDcaBScientificprize(DcaBScientificprize dcaBScientificprize);

        void deleteDcaBScientificprizes(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
