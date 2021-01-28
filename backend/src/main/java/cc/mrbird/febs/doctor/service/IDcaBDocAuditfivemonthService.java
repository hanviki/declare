package cc.mrbird.febs.doctor.service;

import cc.mrbird.febs.doctor.entity.DcaBDocAuditfivemonth;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 月度考核 服务类
 * </p>
 *
 * @author viki
 * @since 2021-01-13
 */
public interface IDcaBDocAuditfivemonthService extends IService<DcaBDocAuditfivemonth> {

        IPage<DcaBDocAuditfivemonth> findDcaBDocAuditfivemonths(QueryRequest request, DcaBDocAuditfivemonth dcaBDocAuditfivemonth);

        IPage<DcaBDocAuditfivemonth> findDcaBDocAuditfivemonthList(QueryRequest request, DcaBDocAuditfivemonth dcaBDocAuditfivemonth);

        void createDcaBDocAuditfivemonth(DcaBDocAuditfivemonth dcaBDocAuditfivemonth);

        void updateDcaBDocAuditfivemonth(DcaBDocAuditfivemonth dcaBDocAuditfivemonth);

        void deleteDcaBDocAuditfivemonths(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
