package cc.mrbird.febs.dcacopy.service;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyYoungprize;
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
 * @since 2020-11-26
 */
public interface IDcaBCopyYoungprizeService extends IService<DcaBCopyYoungprize> {

        IPage<DcaBCopyYoungprize> findDcaBCopyYoungprizes(QueryRequest request, DcaBCopyYoungprize dcaBCopyYoungprize);

        IPage<DcaBCopyYoungprize> findDcaBCopyYoungprizeList(QueryRequest request, DcaBCopyYoungprize dcaBCopyYoungprize);

        void createDcaBCopyYoungprize(DcaBCopyYoungprize dcaBCopyYoungprize);

        void updateDcaBCopyYoungprize(DcaBCopyYoungprize dcaBCopyYoungprize);

        void deleteDcaBCopyYoungprizes(String[]Ids);

        List<DcaBCopyYoungprize> getAll(String userAccount,String dcaYear);
        }
