package cc.mrbird.febs.dcacopy.service;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyDoctorturtor;
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
public interface IDcaBCopyDoctorturtorService extends IService<DcaBCopyDoctorturtor> {

        IPage<DcaBCopyDoctorturtor> findDcaBCopyDoctorturtors(QueryRequest request, DcaBCopyDoctorturtor dcaBCopyDoctorturtor);

        IPage<DcaBCopyDoctorturtor> findDcaBCopyDoctorturtorList(QueryRequest request, DcaBCopyDoctorturtor dcaBCopyDoctorturtor);

        void createDcaBCopyDoctorturtor(DcaBCopyDoctorturtor dcaBCopyDoctorturtor);

        void updateDcaBCopyDoctorturtor(DcaBCopyDoctorturtor dcaBCopyDoctorturtor);

        void deleteDcaBCopyDoctorturtors(String[]Ids);

        List<DcaBCopyDoctorturtor> getAll(String userAccount,String dcaYear);
        }
