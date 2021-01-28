package cc.mrbird.febs.doctor.service;

import cc.mrbird.febs.doctor.entity.DcaBDocPaperspublish;
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
 * @since 2021-01-11
 */
public interface IDcaBDocPaperspublishService extends IService<DcaBDocPaperspublish> {

        IPage<DcaBDocPaperspublish> findDcaBDocPaperspublishs(QueryRequest request, DcaBDocPaperspublish dcaBDocPaperspublish);

        IPage<DcaBDocPaperspublish> findDcaBDocPaperspublishList(QueryRequest request, DcaBDocPaperspublish dcaBDocPaperspublish);

        void createDcaBDocPaperspublish(DcaBDocPaperspublish dcaBDocPaperspublish);

        void updateDcaBDocPaperspublish(DcaBDocPaperspublish dcaBDocPaperspublish);

        void deleteDcaBDocPaperspublishs(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
