package cc.mrbird.febs.doctor.service;

import cc.mrbird.febs.doctor.entity.DcaBDocPublicarticle;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author viki
 * @since 2021-01-12
 */
public interface IDcaBDocPublicarticleService extends IService<DcaBDocPublicarticle> {

        IPage<DcaBDocPublicarticle> findDcaBDocPublicarticles(QueryRequest request, DcaBDocPublicarticle dcaBDocPublicarticle);

        IPage<DcaBDocPublicarticle> findDcaBDocPublicarticleList(QueryRequest request, DcaBDocPublicarticle dcaBDocPublicarticle);

        void createDcaBDocPublicarticle(DcaBDocPublicarticle dcaBDocPublicarticle);

        void updateDcaBDocPublicarticle(DcaBDocPublicarticle dcaBDocPublicarticle);

        void deleteDcaBDocPublicarticles(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
