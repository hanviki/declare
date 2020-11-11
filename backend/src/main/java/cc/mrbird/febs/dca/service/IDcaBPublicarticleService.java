package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBPublicarticle;
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
 * @since 2020-11-06
 */
public interface IDcaBPublicarticleService extends IService<DcaBPublicarticle> {

        IPage<DcaBPublicarticle> findDcaBPublicarticles(QueryRequest request, DcaBPublicarticle dcaBPublicarticle);

        IPage<DcaBPublicarticle> findDcaBPublicarticleList(QueryRequest request, DcaBPublicarticle dcaBPublicarticle);

        void createDcaBPublicarticle(DcaBPublicarticle dcaBPublicarticle);

        void updateDcaBPublicarticle(DcaBPublicarticle dcaBPublicarticle);

        void deleteDcaBPublicarticles(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
