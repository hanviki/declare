package cc.mrbird.febs.dcacopy.service;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyApplyjob;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 拟聘岗位 服务类
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
public interface IDcaBCopyApplyjobService extends IService<DcaBCopyApplyjob> {

        IPage<DcaBCopyApplyjob> findDcaBCopyApplyjobs(QueryRequest request, DcaBCopyApplyjob dcaBCopyApplyjob);

        IPage<DcaBCopyApplyjob> findDcaBCopyApplyjobList(QueryRequest request, DcaBCopyApplyjob dcaBCopyApplyjob);

        void createDcaBCopyApplyjob(DcaBCopyApplyjob dcaBCopyApplyjob);

        void updateDcaBCopyApplyjob(DcaBCopyApplyjob dcaBCopyApplyjob);

        void deleteDcaBCopyApplyjobs(String[]Ids);

        List<DcaBCopyApplyjob> getAll(String userAccount,String dcaYear);
        }
