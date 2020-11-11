package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBPaperspublish;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 教学论文出版教材 服务类
 * </p>
 *
 * @author viki
 * @since 2020-10-20
 */
public interface IDcaBPaperspublishService extends IService<DcaBPaperspublish> {

        IPage<DcaBPaperspublish> findDcaBPaperspublishs(QueryRequest request, DcaBPaperspublish dcaBPaperspublish);

        IPage<DcaBPaperspublish> findDcaBPaperspublishList(QueryRequest request, DcaBPaperspublish dcaBPaperspublish);

        void createDcaBPaperspublish(DcaBPaperspublish dcaBPaperspublish);

        void updateDcaBPaperspublish(DcaBPaperspublish dcaBPaperspublish);

        void deleteDcaBPaperspublishs(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
