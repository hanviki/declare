package cc.mrbird.febs.dcacopy.service;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyCourseclass;
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
 * @since 2020-11-26
 */
public interface IDcaBCopyCourseclassService extends IService<DcaBCopyCourseclass> {

        IPage<DcaBCopyCourseclass> findDcaBCopyCourseclasss(QueryRequest request, DcaBCopyCourseclass dcaBCopyCourseclass);

        IPage<DcaBCopyCourseclass> findDcaBCopyCourseclassList(QueryRequest request, DcaBCopyCourseclass dcaBCopyCourseclass);

        void createDcaBCopyCourseclass(DcaBCopyCourseclass dcaBCopyCourseclass);

        void updateDcaBCopyCourseclass(DcaBCopyCourseclass dcaBCopyCourseclass);

        void deleteDcaBCopyCourseclasss(String[]Ids);

        List<DcaBCopyCourseclass> getAll(String userAccount,String dcaYear);
        }
