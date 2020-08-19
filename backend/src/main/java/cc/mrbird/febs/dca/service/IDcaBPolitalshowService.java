package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBPolitalshow;
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
 * @since 2020-08-12
 */
public interface IDcaBPolitalshowService extends IService<DcaBPolitalshow> {

        IPage<DcaBPolitalshow> findDcaBPolitalshows(QueryRequest request, DcaBPolitalshow dcaBPolitalshow);

        IPage<DcaBPolitalshow> findDcaBPolitalshowList(QueryRequest request, DcaBPolitalshow dcaBPolitalshow);

        void createDcaBPolitalshow(DcaBPolitalshow dcaBPolitalshow);

        void updateDcaBPolitalshow(DcaBPolitalshow dcaBPolitalshow);

        void deleteDcaBPolitalshows(String[]Ids);

        void deleteByuseraccount(String userAccount);
        }
