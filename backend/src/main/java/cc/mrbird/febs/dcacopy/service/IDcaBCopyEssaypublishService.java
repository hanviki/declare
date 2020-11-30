package cc.mrbird.febs.dcacopy.service;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyEssaypublish;
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
 * @since 2020-11-26
 */
public interface IDcaBCopyEssaypublishService extends IService<DcaBCopyEssaypublish> {

        IPage<DcaBCopyEssaypublish> findDcaBCopyEssaypublishs(QueryRequest request, DcaBCopyEssaypublish dcaBCopyEssaypublish);

        IPage<DcaBCopyEssaypublish> findDcaBCopyEssaypublishList(QueryRequest request, DcaBCopyEssaypublish dcaBCopyEssaypublish);

        void createDcaBCopyEssaypublish(DcaBCopyEssaypublish dcaBCopyEssaypublish);

        void updateDcaBCopyEssaypublish(DcaBCopyEssaypublish dcaBCopyEssaypublish);

        void deleteDcaBCopyEssaypublishs(String[]Ids);

        List<DcaBCopyEssaypublish> getAll(String userAccount,String dcaYear);
        }
