package cc.mrbird.febs.doctor.service;

import cc.mrbird.febs.doctor.entity.DcaBDocQualification;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 资质情况 服务类
 * </p>
 *
 * @author viki
 * @since 2021-01-12
 */
public interface IDcaBDocQualificationService extends IService<DcaBDocQualification> {

        IPage<DcaBDocQualification> findDcaBDocQualifications(QueryRequest request, DcaBDocQualification dcaBDocQualification);

        IPage<DcaBDocQualification> findDcaBDocQualificationList(QueryRequest request, DcaBDocQualification dcaBDocQualification);

        void createDcaBDocQualification(DcaBDocQualification dcaBDocQualification);

        void updateDcaBDocQualification(DcaBDocQualification dcaBDocQualification);

        void deleteDcaBDocQualifications(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
