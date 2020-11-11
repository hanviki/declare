package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBTurtor;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 担任辅导员 服务类
 * </p>
 *
 * @author viki
 * @since 2020-10-20
 */
public interface IDcaBTurtorService extends IService<DcaBTurtor> {

        IPage<DcaBTurtor> findDcaBTurtors(QueryRequest request, DcaBTurtor dcaBTurtor);

        IPage<DcaBTurtor> findDcaBTurtorList(QueryRequest request, DcaBTurtor dcaBTurtor);

        void createDcaBTurtor(DcaBTurtor dcaBTurtor);

        void updateDcaBTurtor(DcaBTurtor dcaBTurtor);

        void deleteDcaBTurtors(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
