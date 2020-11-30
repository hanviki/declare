package cc.mrbird.febs.dcacopy.service;

import cc.mrbird.febs.dcacopy.entity.DcaBCopySciencesearch;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 任现职以来承担的科研项目 服务类
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
public interface IDcaBCopySciencesearchService extends IService<DcaBCopySciencesearch> {

        IPage<DcaBCopySciencesearch> findDcaBCopySciencesearchs(QueryRequest request, DcaBCopySciencesearch dcaBCopySciencesearch);

        IPage<DcaBCopySciencesearch> findDcaBCopySciencesearchList(QueryRequest request, DcaBCopySciencesearch dcaBCopySciencesearch);

        void createDcaBCopySciencesearch(DcaBCopySciencesearch dcaBCopySciencesearch);

        void updateDcaBCopySciencesearch(DcaBCopySciencesearch dcaBCopySciencesearch);

        void deleteDcaBCopySciencesearchs(String[]Ids);

        List<DcaBCopySciencesearch> getAll(String userAccount,String dcaYear);
        }
