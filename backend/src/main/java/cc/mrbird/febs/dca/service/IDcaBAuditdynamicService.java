package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.CustomDynamic;
import cc.mrbird.febs.dca.entity.DcaBAuditdynamic;
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
 * @since 2020-10-27
 */
public interface IDcaBAuditdynamicService extends IService<DcaBAuditdynamic> {

        IPage<DcaBAuditdynamic> findDcaBAuditdynamics(QueryRequest request, DcaBAuditdynamic dcaBAuditdynamic);

        IPage<DcaBAuditdynamic> findDcaBAuditdynamicList(QueryRequest request, DcaBAuditdynamic dcaBAuditdynamic);

        void createDcaBAuditdynamic(DcaBAuditdynamic dcaBAuditdynamic);

        void updateDcaBAuditdynamic(DcaBAuditdynamic dcaBAuditdynamic);

        void deleteDcaBAuditdynamics(String[]Ids);

        void deleteByuseraccount(String userAccount);

        void deleteBy(List<String> accounts,List<String> dataIndexList);

        int getMaxDisplayIndexByuseraccount(String userAccount);

       List<DcaBAuditdynamic> findAllAuditdynamics(String userAccount);


    List<DcaBCopyAuditdynamic> findDcaBCopyAuditdynamicList(DcaBCopyAuditdynamic dcaBAuditdynamic);
        }
