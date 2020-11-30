package cc.mrbird.febs.dcacopy.service;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyPolitalshow;
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
 * @since 2020-11-26
 */
public interface IDcaBCopyPolitalshowService extends IService<DcaBCopyPolitalshow> {

        IPage<DcaBCopyPolitalshow> findDcaBCopyPolitalshows(QueryRequest request, DcaBCopyPolitalshow dcaBCopyPolitalshow);

        IPage<DcaBCopyPolitalshow> findDcaBCopyPolitalshowList(QueryRequest request, DcaBCopyPolitalshow dcaBCopyPolitalshow);

        void createDcaBCopyPolitalshow(DcaBCopyPolitalshow dcaBCopyPolitalshow);

        void updateDcaBCopyPolitalshow(DcaBCopyPolitalshow dcaBCopyPolitalshow);

        void deleteDcaBCopyPolitalshows(String[]Ids);

        List<DcaBCopyPolitalshow> getAll(String userAccount,String dcaYear);
        }
