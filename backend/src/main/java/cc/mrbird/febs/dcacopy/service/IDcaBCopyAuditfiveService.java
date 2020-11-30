package cc.mrbird.febs.dcacopy.service;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyAuditfive;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 近五年总体评价情况 服务类
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
public interface IDcaBCopyAuditfiveService extends IService<DcaBCopyAuditfive> {

        IPage<DcaBCopyAuditfive> findDcaBCopyAuditfives(QueryRequest request, DcaBCopyAuditfive dcaBCopyAuditfive);

        IPage<DcaBCopyAuditfive> findDcaBCopyAuditfiveList(QueryRequest request, DcaBCopyAuditfive dcaBCopyAuditfive);

        void createDcaBCopyAuditfive(DcaBCopyAuditfive dcaBCopyAuditfive);

        void updateDcaBCopyAuditfive(DcaBCopyAuditfive dcaBCopyAuditfive);

        void deleteDcaBCopyAuditfives(String[]Ids);

        List<DcaBCopyAuditfive> getAll(String userAccount,String dcaYear);
        }
