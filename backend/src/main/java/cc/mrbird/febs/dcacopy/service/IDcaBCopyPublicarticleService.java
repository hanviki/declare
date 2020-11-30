package cc.mrbird.febs.dcacopy.service;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyPublicarticle;
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
 * @since 2020-11-26
 */
public interface IDcaBCopyPublicarticleService extends IService<DcaBCopyPublicarticle> {

        IPage<DcaBCopyPublicarticle> findDcaBCopyPublicarticles(QueryRequest request, DcaBCopyPublicarticle dcaBCopyPublicarticle);

        IPage<DcaBCopyPublicarticle> findDcaBCopyPublicarticleList(QueryRequest request, DcaBCopyPublicarticle dcaBCopyPublicarticle);

        void createDcaBCopyPublicarticle(DcaBCopyPublicarticle dcaBCopyPublicarticle);

        void updateDcaBCopyPublicarticle(DcaBCopyPublicarticle dcaBCopyPublicarticle);

        void deleteDcaBCopyPublicarticles(String[]Ids);

        List<DcaBCopyPublicarticle> getAll(String userAccount,String dcaYear);
        }
