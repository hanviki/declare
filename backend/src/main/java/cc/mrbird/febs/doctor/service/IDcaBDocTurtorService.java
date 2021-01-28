package cc.mrbird.febs.doctor.service;

import cc.mrbird.febs.doctor.entity.DcaBDocTurtor;
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
 * @since 2021-01-11
 */
public interface IDcaBDocTurtorService extends IService<DcaBDocTurtor> {

        IPage<DcaBDocTurtor> findDcaBDocTurtors(QueryRequest request, DcaBDocTurtor dcaBDocTurtor);

        IPage<DcaBDocTurtor> findDcaBDocTurtorList(QueryRequest request, DcaBDocTurtor dcaBDocTurtor);

        void createDcaBDocTurtor(DcaBDocTurtor dcaBDocTurtor);

        void updateDcaBDocTurtor(DcaBDocTurtor dcaBDocTurtor);

        void deleteDcaBDocTurtors(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
