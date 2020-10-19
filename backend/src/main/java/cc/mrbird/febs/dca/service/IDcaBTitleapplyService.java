package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBTitleapply;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 职称申请表 服务类
 * </p>
 *
 * @author viki
 * @since 2020-09-22
 */
public interface IDcaBTitleapplyService extends IService<DcaBTitleapply> {

        IPage<DcaBTitleapply> findDcaBTitleapplys(QueryRequest request, DcaBTitleapply dcaBTitleapply);

        IPage<DcaBTitleapply> findDcaBTitleapplyList(QueryRequest request, DcaBTitleapply dcaBTitleapply);

        void createDcaBTitleapply(DcaBTitleapply dcaBTitleapply);

        void updateDcaBTitleapply(DcaBTitleapply dcaBTitleapply);

        void deleteDcaBTitleapplys(String[]Ids);
        }
