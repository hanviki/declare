package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBAssitant;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 支援情况 服务类
 * </p>
 *
 * @author viki
 * @since 2021-03-02
 */
public interface IDcaBAssitantService extends IService<DcaBAssitant> {

        IPage<DcaBAssitant> findDcaBAssitants(QueryRequest request, DcaBAssitant dcaBAssitant);

        IPage<DcaBAssitant> findDcaBAssitantList(QueryRequest request, DcaBAssitant dcaBAssitant);

        void createDcaBAssitant(DcaBAssitant dcaBAssitant);

        void updateDcaBAssitant(DcaBAssitant dcaBAssitant);

        void deleteDcaBAssitants(String[]Ids);

        List<DcaBAssitant> getAll(String userAccount,String dcaYear);
        }
