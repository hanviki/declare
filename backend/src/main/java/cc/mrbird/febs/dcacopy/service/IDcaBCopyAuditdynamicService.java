package cc.mrbird.febs.dcacopy.service;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyAuditdynamic;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author viki
 * @since 2020-11-23
 */
public interface IDcaBCopyAuditdynamicService extends IService<DcaBCopyAuditdynamic> {

        IPage<DcaBCopyAuditdynamic> findDcaBCopyAuditdynamics(QueryRequest request, DcaBCopyAuditdynamic dcaBCopyAuditdynamic);

        IPage<DcaBCopyAuditdynamic> findDcaBCopyAuditdynamicList(QueryRequest request, DcaBCopyAuditdynamic dcaBCopyAuditdynamic);

        void createDcaBCopyAuditdynamic(DcaBCopyAuditdynamic dcaBCopyAuditdynamic);

        void updateDcaBCopyAuditdynamic(DcaBCopyAuditdynamic dcaBCopyAuditdynamic);

        void deleteDcaBCopyAuditdynamics(String[]Ids);

        List<DcaBCopyAuditdynamic> GetAllInfo(String userAccount,String dcaYear);
        String GetZtkhqk(String userAccount,String dcaYear);
        }
