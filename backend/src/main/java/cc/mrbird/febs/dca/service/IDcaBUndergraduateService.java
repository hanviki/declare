package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBUndergraduate;
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
 * @since 2020-10-20
 */
public interface IDcaBUndergraduateService extends IService<DcaBUndergraduate> {

        IPage<DcaBUndergraduate> findDcaBUndergraduates(QueryRequest request, DcaBUndergraduate dcaBUndergraduate);

        IPage<DcaBUndergraduate> findDcaBUndergraduateList(QueryRequest request, DcaBUndergraduate dcaBUndergraduate);

        void createDcaBUndergraduate(DcaBUndergraduate dcaBUndergraduate);

        void updateDcaBUndergraduate(DcaBUndergraduate dcaBUndergraduate);

        void deleteDcaBUndergraduates(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
