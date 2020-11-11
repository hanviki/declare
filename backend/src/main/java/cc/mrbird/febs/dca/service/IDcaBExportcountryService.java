package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBExportcountry;
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
 * @since 2020-10-22
 */
public interface IDcaBExportcountryService extends IService<DcaBExportcountry> {

        IPage<DcaBExportcountry> findDcaBExportcountrys(QueryRequest request, DcaBExportcountry dcaBExportcountry);

        IPage<DcaBExportcountry> findDcaBExportcountryList(QueryRequest request, DcaBExportcountry dcaBExportcountry);

        void createDcaBExportcountry(DcaBExportcountry dcaBExportcountry);

        void updateDcaBExportcountry(DcaBExportcountry dcaBExportcountry);

        void deleteDcaBExportcountrys(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
