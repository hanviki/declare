package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBAuditfive;
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
 * @since 2020-12-16
 */
public interface IDcaBAuditfiveService extends IService<DcaBAuditfive> {

        IPage<DcaBAuditfive> findDcaBAuditfives(QueryRequest request, DcaBAuditfive dcaBAuditfive);

        IPage<DcaBAuditfive> findDcaBAuditfiveList(QueryRequest request, DcaBAuditfive dcaBAuditfive);

        void createDcaBAuditfive(DcaBAuditfive dcaBAuditfive);

        void updateDcaBAuditfive(DcaBAuditfive dcaBAuditfive);

        void deleteDcaBAuditfives(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
