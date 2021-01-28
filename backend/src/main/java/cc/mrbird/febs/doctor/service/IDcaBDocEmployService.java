package cc.mrbird.febs.doctor.service;

import cc.mrbird.febs.doctor.entity.DcaBDocEmploy;
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
 * @since 2021-01-11
 */
public interface IDcaBDocEmployService extends IService<DcaBDocEmploy> {

        IPage<DcaBDocEmploy> findDcaBDocEmploys(QueryRequest request, DcaBDocEmploy dcaBDocEmploy);

        IPage<DcaBDocEmploy> findDcaBDocEmployList(QueryRequest request, DcaBDocEmploy dcaBDocEmploy);

        void createDcaBDocEmploy(DcaBDocEmploy dcaBDocEmploy);

        void updateDcaBDocEmploy(DcaBDocEmploy dcaBDocEmploy);

        void deleteDcaBDocEmploys(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
