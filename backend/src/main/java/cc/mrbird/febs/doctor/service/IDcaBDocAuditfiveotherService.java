package cc.mrbird.febs.doctor.service;

import cc.mrbird.febs.doctor.entity.DcaBDocAuditfiveother;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 中期考核和出站考核 服务类
 * </p>
 *
 * @author viki
 * @since 2021-01-13
 */
public interface IDcaBDocAuditfiveotherService extends IService<DcaBDocAuditfiveother> {

        IPage<DcaBDocAuditfiveother> findDcaBDocAuditfiveothers(QueryRequest request, DcaBDocAuditfiveother dcaBDocAuditfiveother);

        IPage<DcaBDocAuditfiveother> findDcaBDocAuditfiveotherList(QueryRequest request, DcaBDocAuditfiveother dcaBDocAuditfiveother);

        void createDcaBDocAuditfiveother(DcaBDocAuditfiveother dcaBDocAuditfiveother);

        void updateDcaBDocAuditfiveother(DcaBDocAuditfiveother dcaBDocAuditfiveother);

        void deleteDcaBDocAuditfiveothers(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
