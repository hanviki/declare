package cc.mrbird.febs.doctor.service;

import cc.mrbird.febs.doctor.entity.DcaBDocScientificprize;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 自任职以来科研获奖情况 服务类
 * </p>
 *
 * @author viki
 * @since 2021-01-11
 */
public interface IDcaBDocScientificprizeService extends IService<DcaBDocScientificprize> {

        IPage<DcaBDocScientificprize> findDcaBDocScientificprizes(QueryRequest request, DcaBDocScientificprize dcaBDocScientificprize);

        IPage<DcaBDocScientificprize> findDcaBDocScientificprizeList(QueryRequest request, DcaBDocScientificprize dcaBDocScientificprize);

        void createDcaBDocScientificprize(DcaBDocScientificprize dcaBDocScientificprize);

        void updateDcaBDocScientificprize(DcaBDocScientificprize dcaBDocScientificprize);

        void deleteDcaBDocScientificprizes(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
