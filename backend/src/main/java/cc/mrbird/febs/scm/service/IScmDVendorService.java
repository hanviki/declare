package cc.mrbird.febs.scm.service;

import cc.mrbird.febs.scm.entity.*;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 供应商表 服务类
 * </p>
 *
 * @author viki
 * @since 2019-11-14
 */
public interface IScmDVendorService extends IService<ScmDVendor> {

        IPage<ScmDVendor> findScmDVendors(QueryRequest request, ScmDVendor scmDVendor, String keyword);

        void createScmDVendor(ScmDVendor scmDVendor);

        void updateScmDVendor(ScmDVendor scmDVendor);

        void deleteScmDVendors(String[]Ids);

        void createScmVendor(ScmDVendor scmDVendor, List<ScmDVendorD> scmDVendorDS, ScmDVendoruser enscmDVendoruser);
        void updateScmDVendor(ScmDVendor scmDVendor, List<ScmDVendorD> scmDVendorDS, ScmDVendoruser enscmDVendoruser);

        IPage<VendorRank> findScmDVendorsRank(QueryRequest request, ScmBPurcharseorder order);
        IPage<MaterPercentage> findScmDVendorsMater(QueryRequest request, ScmBPurcharseorder order);
        IPage<TotalStatistic> findVendorM(QueryRequest request, ScmBPurcharseorder order);
        IPage<TotalStatistic> findMaterVendor(QueryRequest request, ScmBPurcharseorder order);
        }
