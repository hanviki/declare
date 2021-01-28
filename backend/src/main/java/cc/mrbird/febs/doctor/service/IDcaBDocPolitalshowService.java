package cc.mrbird.febs.doctor.service;

import cc.mrbird.febs.doctor.entity.DcaBDocPolitalshow;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 个人思想政治表现 服务类
 * </p>
 *
 * @author viki
 * @since 2021-01-11
 */
public interface IDcaBDocPolitalshowService extends IService<DcaBDocPolitalshow> {

        IPage<DcaBDocPolitalshow> findDcaBDocPolitalshows(QueryRequest request, DcaBDocPolitalshow dcaBDocPolitalshow);

        IPage<DcaBDocPolitalshow> findDcaBDocPolitalshowList(QueryRequest request, DcaBDocPolitalshow dcaBDocPolitalshow);

        void createDcaBDocPolitalshow(DcaBDocPolitalshow dcaBDocPolitalshow);

        void updateDcaBDocPolitalshow(DcaBDocPolitalshow dcaBDocPolitalshow);

        void deleteDcaBDocPolitalshows(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
