package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBCourseclass;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 精品课程 服务类
 * </p>
 *
 * @author viki
 * @since 2020-11-03
 */
public interface IDcaBCourseclassService extends IService<DcaBCourseclass> {

        IPage<DcaBCourseclass> findDcaBCourseclasss(QueryRequest request, DcaBCourseclass dcaBCourseclass);

        IPage<DcaBCourseclass> findDcaBCourseclassList(QueryRequest request, DcaBCourseclass dcaBCourseclass);

        void createDcaBCourseclass(DcaBCourseclass dcaBCourseclass);

        void updateDcaBCourseclass(DcaBCourseclass dcaBCourseclass);

        void deleteDcaBCourseclasss(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
