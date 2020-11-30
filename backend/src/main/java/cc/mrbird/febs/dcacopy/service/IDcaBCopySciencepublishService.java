package cc.mrbird.febs.dcacopy.service;

import cc.mrbird.febs.dcacopy.entity.DcaBCopySciencepublish;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 任现职以来发表的论文、出版著作 服务类
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
public interface IDcaBCopySciencepublishService extends IService<DcaBCopySciencepublish> {

        IPage<DcaBCopySciencepublish> findDcaBCopySciencepublishs(QueryRequest request, DcaBCopySciencepublish dcaBCopySciencepublish);

        IPage<DcaBCopySciencepublish> findDcaBCopySciencepublishList(QueryRequest request, DcaBCopySciencepublish dcaBCopySciencepublish);

        void createDcaBCopySciencepublish(DcaBCopySciencepublish dcaBCopySciencepublish);

        void updateDcaBCopySciencepublish(DcaBCopySciencepublish dcaBCopySciencepublish);

        void deleteDcaBCopySciencepublishs(String[]Ids);

        List<DcaBCopySciencepublish> getAll(String userAccount,String dcaYear);
        }
