package cc.mrbird.febs.doctor.service;

import cc.mrbird.febs.doctor.entity.DcaBDocUndergraduate;
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
 * @since 2021-01-11
 */
public interface IDcaBDocUndergraduateService extends IService<DcaBDocUndergraduate> {

        IPage<DcaBDocUndergraduate> findDcaBDocUndergraduates(QueryRequest request, DcaBDocUndergraduate dcaBDocUndergraduate);

        IPage<DcaBDocUndergraduate> findDcaBDocUndergraduateList(QueryRequest request, DcaBDocUndergraduate dcaBDocUndergraduate);

        void createDcaBDocUndergraduate(DcaBDocUndergraduate dcaBDocUndergraduate);

        void updateDcaBDocUndergraduate(DcaBDocUndergraduate dcaBDocUndergraduate);

        void deleteDcaBDocUndergraduates(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
