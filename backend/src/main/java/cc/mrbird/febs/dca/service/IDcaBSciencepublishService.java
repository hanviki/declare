package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBSciencepublish;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 科研论文 服务类
 * </p>
 *
 * @author viki
 * @since 2020-08-11
 */
public interface IDcaBSciencepublishService extends IService<DcaBSciencepublish> {

        IPage<DcaBSciencepublish> findDcaBSciencepublishs(QueryRequest request, DcaBSciencepublish dcaBSciencepublish);

        IPage<DcaBSciencepublish> findDcaBSciencepublishList(QueryRequest request, DcaBSciencepublish dcaBSciencepublish);

        void createDcaBSciencepublish(DcaBSciencepublish dcaBSciencepublish);

        void updateDcaBSciencepublish(DcaBSciencepublish dcaBSciencepublish);

        void deleteDcaBSciencepublishs(String[]Ids);

        void deleteByuseraccount(String userAccount);
        }
