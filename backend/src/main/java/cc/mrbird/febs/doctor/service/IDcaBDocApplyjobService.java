package cc.mrbird.febs.doctor.service;

import cc.mrbird.febs.doctor.entity.DcaBDocApplyjob;
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
 * @since 2021-01-12
 */
public interface IDcaBDocApplyjobService extends IService<DcaBDocApplyjob> {

        IPage<DcaBDocApplyjob> findDcaBDocApplyjobs(QueryRequest request, DcaBDocApplyjob dcaBDocApplyjob);

        IPage<DcaBDocApplyjob> findDcaBDocApplyjobList(QueryRequest request, DcaBDocApplyjob dcaBDocApplyjob);

        void createDcaBDocApplyjob(DcaBDocApplyjob dcaBDocApplyjob);

        void updateDcaBDocApplyjob(DcaBDocApplyjob dcaBDocApplyjob);

        void deleteDcaBDocApplyjobs(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
