package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBWorker;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 协和医院合同制职工信息确认表 服务类
 * </p>
 *
 * @author viki
 * @since 2021-05-24
 */
public interface IDcaBWorkerService extends IService<DcaBWorker> {

        IPage<DcaBWorker> findDcaBWorkers(QueryRequest request, DcaBWorker dcaBWorker);

        IPage<DcaBWorker> findDcaBWorkerList(QueryRequest request, DcaBWorker dcaBWorker);

        void createDcaBWorker(DcaBWorker dcaBWorker);

        void updateDcaBWorker(DcaBWorker dcaBWorker);

        void deleteDcaBWorkers(String[]Ids);

        List<DcaBWorker> getAll(String userAccount,String dcaYear);
        }
