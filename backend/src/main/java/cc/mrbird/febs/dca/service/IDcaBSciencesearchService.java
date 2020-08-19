package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBSciencesearch;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 科研项目 服务类
 * </p>
 *
 * @author viki
 * @since 2020-08-13
 */
public interface IDcaBSciencesearchService extends IService<DcaBSciencesearch> {

        IPage<DcaBSciencesearch> findDcaBSciencesearchs(QueryRequest request, DcaBSciencesearch dcaBSciencesearch);

        IPage<DcaBSciencesearch> findDcaBSciencesearchList(QueryRequest request, DcaBSciencesearch dcaBSciencesearch);

        void createDcaBSciencesearch(DcaBSciencesearch dcaBSciencesearch);

        void updateDcaBSciencesearch(DcaBSciencesearch dcaBSciencesearch);

        void deleteDcaBSciencesearchs(String[]Ids);

        void deleteByuseraccount(String userAccount);
        }
