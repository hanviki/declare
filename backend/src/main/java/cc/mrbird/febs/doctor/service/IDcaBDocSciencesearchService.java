package cc.mrbird.febs.doctor.service;

import cc.mrbird.febs.doctor.entity.DcaBDocSciencesearch;
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
 * @since 2021-01-11
 */
public interface IDcaBDocSciencesearchService extends IService<DcaBDocSciencesearch> {

        IPage<DcaBDocSciencesearch> findDcaBDocSciencesearchs(QueryRequest request, DcaBDocSciencesearch dcaBDocSciencesearch);

        IPage<DcaBDocSciencesearch> findDcaBDocSciencesearchList(QueryRequest request, DcaBDocSciencesearch dcaBDocSciencesearch);

        void createDcaBDocSciencesearch(DcaBDocSciencesearch dcaBDocSciencesearch);

        void updateDcaBDocSciencesearch(DcaBDocSciencesearch dcaBDocSciencesearch);

        void deleteDcaBDocSciencesearchs(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
