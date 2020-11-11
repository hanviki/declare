package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaUserAudit;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 用户审核字段表 服务类
 * </p>
 *
 * @author viki
 * @since 2020-10-23
 */
public interface IDcaUserAuditService extends IService<DcaUserAudit> {

        IPage<DcaUserAudit> findDcaUserAudits(QueryRequest request, DcaUserAudit dcaUserAudit);

        IPage<DcaUserAudit> findDcaUserAuditList(QueryRequest request, DcaUserAudit dcaUserAudit);



        void createDcaUserAudit(DcaUserAudit dcaUserAudit);

        void updateDcaUserAudit(DcaUserAudit dcaUserAudit);

        void deleteDcaUserAudits(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
