package cc.mrbird.febs.dcacopy.service;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyUndergraduate;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 本科教学情况 服务类
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
public interface IDcaBCopyUndergraduateService extends IService<DcaBCopyUndergraduate> {

        IPage<DcaBCopyUndergraduate> findDcaBCopyUndergraduates(QueryRequest request, DcaBCopyUndergraduate dcaBCopyUndergraduate);

        IPage<DcaBCopyUndergraduate> findDcaBCopyUndergraduateList(QueryRequest request, DcaBCopyUndergraduate dcaBCopyUndergraduate);

        void createDcaBCopyUndergraduate(DcaBCopyUndergraduate dcaBCopyUndergraduate);

        void updateDcaBCopyUndergraduate(DcaBCopyUndergraduate dcaBCopyUndergraduate);

        void deleteDcaBCopyUndergraduates(String[]Ids);

        List<DcaBCopyUndergraduate> getAll(String userAccount,String dcaYear);
        }
