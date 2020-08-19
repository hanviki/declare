package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBApplyjob;
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
 * @since 2020-08-04
 */
public interface IDcaBApplyjobService extends IService<DcaBApplyjob> {

        IPage<DcaBApplyjob> findDcaBApplyjobs(QueryRequest request, DcaBApplyjob dcaBApplyjob);

        IPage<DcaBApplyjob> findDcaBApplyjobList(QueryRequest request, DcaBApplyjob dcaBApplyjob);

        void createDcaBApplyjob(DcaBApplyjob dcaBApplyjob);

        void updateDcaBApplyjob(DcaBApplyjob dcaBApplyjob);

        void deleteDcaBApplyjobs(String[]Ids);

        void deleteByuseraccount(String userAccount);
        }
