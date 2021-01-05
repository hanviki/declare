package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBQualification;
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
 * @since 2020-12-25
 */
public interface IDcaBQualificationService extends IService<DcaBQualification> {

        IPage<DcaBQualification> findDcaBQualifications(QueryRequest request, DcaBQualification dcaBQualification);

        IPage<DcaBQualification> findDcaBQualificationList(QueryRequest request, DcaBQualification dcaBQualification);

        void createDcaBQualification(DcaBQualification dcaBQualification);

        void updateDcaBQualification(DcaBQualification dcaBQualification);

        void deleteDcaBQualifications(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
