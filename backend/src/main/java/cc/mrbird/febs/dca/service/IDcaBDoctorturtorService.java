package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBDoctorturtor;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 担任博导硕导 服务类
 * </p>
 *
 * @author viki
 * @since 2020-12-15
 */
public interface IDcaBDoctorturtorService extends IService<DcaBDoctorturtor> {

        IPage<DcaBDoctorturtor> findDcaBDoctorturtors(QueryRequest request, DcaBDoctorturtor dcaBDoctorturtor);

        IPage<DcaBDoctorturtor> findDcaBDoctorturtorList(QueryRequest request, DcaBDoctorturtor dcaBDoctorturtor);

        void createDcaBDoctorturtor(DcaBDoctorturtor dcaBDoctorturtor);

        void updateDcaBDoctorturtor(DcaBDoctorturtor dcaBDoctorturtor);

        void deleteDcaBDoctorturtors(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
