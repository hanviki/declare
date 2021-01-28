package cc.mrbird.febs.doctor.service;

import cc.mrbird.febs.doctor.entity.DcaBDocSciencepublish;
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
 * @since 2021-01-11
 */
public interface IDcaBDocSciencepublishService extends IService<DcaBDocSciencepublish> {

        IPage<DcaBDocSciencepublish> findDcaBDocSciencepublishs(QueryRequest request, DcaBDocSciencepublish dcaBDocSciencepublish);

        IPage<DcaBDocSciencepublish> findDcaBDocSciencepublishList(QueryRequest request, DcaBDocSciencepublish dcaBDocSciencepublish);

        void createDcaBDocSciencepublish(DcaBDocSciencepublish dcaBDocSciencepublish);

        void updateDcaBDocSciencepublish(DcaBDocSciencepublish dcaBDocSciencepublish);

        void deleteDcaBDocSciencepublishs(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
