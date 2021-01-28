package cc.mrbird.febs.doctor.service;

import cc.mrbird.febs.doctor.entity.DcaBDocExportcountry;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 出国情况 服务类
 * </p>
 *
 * @author viki
 * @since 2021-01-11
 */
public interface IDcaBDocExportcountryService extends IService<DcaBDocExportcountry> {

        IPage<DcaBDocExportcountry> findDcaBDocExportcountrys(QueryRequest request, DcaBDocExportcountry dcaBDocExportcountry);

        IPage<DcaBDocExportcountry> findDcaBDocExportcountryList(QueryRequest request, DcaBDocExportcountry dcaBDocExportcountry);

        void createDcaBDocExportcountry(DcaBDocExportcountry dcaBDocExportcountry);

        void updateDcaBDocExportcountry(DcaBDocExportcountry dcaBDocExportcountry);

        void deleteDcaBDocExportcountrys(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
