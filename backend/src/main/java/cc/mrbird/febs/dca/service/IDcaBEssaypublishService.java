package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBEssaypublish;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 论文出版 服务类
 * </p>
 *
 * @author viki
 * @since 2020-10-20
 */
public interface IDcaBEssaypublishService extends IService<DcaBEssaypublish> {

        IPage<DcaBEssaypublish> findDcaBEssaypublishs(QueryRequest request, DcaBEssaypublish dcaBEssaypublish);

        IPage<DcaBEssaypublish> findDcaBEssaypublishList(QueryRequest request, DcaBEssaypublish dcaBEssaypublish);

        void createDcaBEssaypublish(DcaBEssaypublish dcaBEssaypublish);

        void updateDcaBEssaypublish(DcaBEssaypublish dcaBEssaypublish);

        void deleteDcaBEssaypublishs(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
