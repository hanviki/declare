package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBYearapply;
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
 * @since 2020-09-24
 */
public interface IDcaBYearapplyService extends IService<DcaBYearapply> {

        IPage<DcaBYearapply> findDcaBYearapplys(QueryRequest request, DcaBYearapply dcaBYearapply);

        IPage<DcaBYearapply> findDcaBYearapplyList(QueryRequest request, DcaBYearapply dcaBYearapply);

        void createDcaBYearapply(DcaBYearapply dcaBYearapply);

        void updateDcaBYearapply(DcaBYearapply dcaBYearapply);

        void deleteDcaBYearapplys(String[]Ids);
        }
