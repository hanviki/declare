package cc.mrbird.febs.doctor.service;

import cc.mrbird.febs.doctor.entity.DcaBDocEssaypublish;
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
 * @since 2021-01-11
 */
public interface IDcaBDocEssaypublishService extends IService<DcaBDocEssaypublish> {

        IPage<DcaBDocEssaypublish> findDcaBDocEssaypublishs(QueryRequest request, DcaBDocEssaypublish dcaBDocEssaypublish);

        IPage<DcaBDocEssaypublish> findDcaBDocEssaypublishList(QueryRequest request, DcaBDocEssaypublish dcaBDocEssaypublish);

        void createDcaBDocEssaypublish(DcaBDocEssaypublish dcaBDocEssaypublish);

        void updateDcaBDocEssaypublish(DcaBDocEssaypublish dcaBDocEssaypublish);

        void deleteDcaBDocEssaypublishs(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
