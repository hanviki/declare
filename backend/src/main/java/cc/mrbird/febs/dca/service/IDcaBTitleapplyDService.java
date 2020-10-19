package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBTitleapplyD;
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
 * @since 2020-09-22
 */
public interface IDcaBTitleapplyDService extends IService<DcaBTitleapplyD> {

        IPage<DcaBTitleapplyD> findDcaBTitleapplyDs(QueryRequest request, DcaBTitleapplyD dcaBTitleapplyD);

        IPage<DcaBTitleapplyD> findDcaBTitleapplyDList(QueryRequest request, DcaBTitleapplyD dcaBTitleapplyD);

        void createDcaBTitleapplyD(DcaBTitleapplyD dcaBTitleapplyD);

        void updateDcaBTitleapplyD(DcaBTitleapplyD dcaBTitleapplyD);

        void deleteDcaBTitleapplyDs(String[]Ids);
        }
