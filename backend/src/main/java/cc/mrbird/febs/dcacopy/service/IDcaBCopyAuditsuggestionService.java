package cc.mrbird.febs.dcacopy.service;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyAuditsuggestion;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 审核意见表 服务类
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
public interface IDcaBCopyAuditsuggestionService extends IService<DcaBCopyAuditsuggestion> {

        IPage<DcaBCopyAuditsuggestion> findDcaBCopyAuditsuggestions(QueryRequest request, DcaBCopyAuditsuggestion dcaBCopyAuditsuggestion);

        IPage<DcaBCopyAuditsuggestion> findDcaBCopyAuditsuggestionList(QueryRequest request, DcaBCopyAuditsuggestion dcaBCopyAuditsuggestion);

        void createDcaBCopyAuditsuggestion(DcaBCopyAuditsuggestion dcaBCopyAuditsuggestion);

        void updateDcaBCopyAuditsuggestion(DcaBCopyAuditsuggestion dcaBCopyAuditsuggestion);

        void deleteDcaBCopyAuditsuggestions(String[]Ids);

        List<DcaBCopyAuditsuggestion> getAll(String userAccount,String dcaYear);
        }
