package cc.mrbird.febs.dcacopy.service;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyExportcountry;
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
 * @since 2020-11-26
 */
public interface IDcaBCopyExportcountryService extends IService<DcaBCopyExportcountry> {

        IPage<DcaBCopyExportcountry> findDcaBCopyExportcountrys(QueryRequest request, DcaBCopyExportcountry dcaBCopyExportcountry);

        IPage<DcaBCopyExportcountry> findDcaBCopyExportcountryList(QueryRequest request, DcaBCopyExportcountry dcaBCopyExportcountry);

        void createDcaBCopyExportcountry(DcaBCopyExportcountry dcaBCopyExportcountry);

        void updateDcaBCopyExportcountry(DcaBCopyExportcountry dcaBCopyExportcountry);

        void deleteDcaBCopyExportcountrys(String[]Ids);

        List<DcaBCopyExportcountry> getAll(String userAccount,String dcaYear);
        }
