package cc.mrbird.febs.dcacopy.service;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyScientificprize;
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
 * @since 2020-11-26
 */
public interface IDcaBCopyScientificprizeService extends IService<DcaBCopyScientificprize> {

        IPage<DcaBCopyScientificprize> findDcaBCopyScientificprizes(QueryRequest request, DcaBCopyScientificprize dcaBCopyScientificprize);

        IPage<DcaBCopyScientificprize> findDcaBCopyScientificprizeList(QueryRequest request, DcaBCopyScientificprize dcaBCopyScientificprize);

        void createDcaBCopyScientificprize(DcaBCopyScientificprize dcaBCopyScientificprize);

        void updateDcaBCopyScientificprize(DcaBCopyScientificprize dcaBCopyScientificprize);

        void deleteDcaBCopyScientificprizes(String[]Ids);

        List<DcaBCopyScientificprize> getAll(String userAccount,String dcaYear);
        }
