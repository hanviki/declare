package cc.mrbird.febs.dcacopy.service;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyQualification;
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
public interface IDcaBCopyQualificationService extends IService<DcaBCopyQualification> {

        IPage<DcaBCopyQualification> findDcaBCopyQualifications(QueryRequest request, DcaBCopyQualification dcaBCopyQualification);

        IPage<DcaBCopyQualification> findDcaBCopyQualificationList(QueryRequest request, DcaBCopyQualification dcaBCopyQualification);

        void createDcaBCopyQualification(DcaBCopyQualification dcaBCopyQualification);

        void updateDcaBCopyQualification(DcaBCopyQualification dcaBCopyQualification);

        void deleteDcaBCopyQualifications(String[]Ids);

        List<DcaBCopyQualification> getAll(String userAccount,String dcaYear);
        }
