package cc.mrbird.febs.dcacopy.service;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyPaperspublish;
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
 * @since 2020-11-26
 */
public interface IDcaBCopyPaperspublishService extends IService<DcaBCopyPaperspublish> {

        IPage<DcaBCopyPaperspublish> findDcaBCopyPaperspublishs(QueryRequest request, DcaBCopyPaperspublish dcaBCopyPaperspublish);

        IPage<DcaBCopyPaperspublish> findDcaBCopyPaperspublishList(QueryRequest request, DcaBCopyPaperspublish dcaBCopyPaperspublish);

        void createDcaBCopyPaperspublish(DcaBCopyPaperspublish dcaBCopyPaperspublish);

        void updateDcaBCopyPaperspublish(DcaBCopyPaperspublish dcaBCopyPaperspublish);

        void deleteDcaBCopyPaperspublishs(String[]Ids);

        List<DcaBCopyPaperspublish> getAll(String userAccount,String dcaYear);
        }
