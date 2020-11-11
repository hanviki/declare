package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBEmploy;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 任职培养 服务类
 * </p>
 *
 * @author viki
 * @since 2020-10-20
 */
public interface IDcaBEmployService extends IService<DcaBEmploy> {

        IPage<DcaBEmploy> findDcaBEmploys(QueryRequest request, DcaBEmploy dcaBEmploy);

        IPage<DcaBEmploy> findDcaBEmployList(QueryRequest request, DcaBEmploy dcaBEmploy);

        void createDcaBEmploy(DcaBEmploy dcaBEmploy);

        void updateDcaBEmploy(DcaBEmploy dcaBEmploy);

        void deleteDcaBEmploys(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
