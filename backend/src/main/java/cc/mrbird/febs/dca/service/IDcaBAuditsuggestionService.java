package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBAuditsuggestion;
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
 * @since 2020-09-22
 */
public interface IDcaBAuditsuggestionService extends IService<DcaBAuditsuggestion> {

        IPage<DcaBAuditsuggestion> findDcaBAuditsuggestions(QueryRequest request, DcaBAuditsuggestion dcaBAuditsuggestion);

        IPage<DcaBAuditsuggestion> findDcaBAuditsuggestionList(QueryRequest request, DcaBAuditsuggestion dcaBAuditsuggestion);

        void createDcaBAuditsuggestion(DcaBAuditsuggestion dcaBAuditsuggestion);

        void updateDcaBAuditsuggestion(DcaBAuditsuggestion dcaBAuditsuggestion);

        void deleteDcaBAuditsuggestions(String[]Ids);
        }
