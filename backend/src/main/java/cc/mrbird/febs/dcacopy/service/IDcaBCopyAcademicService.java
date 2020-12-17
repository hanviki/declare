package cc.mrbird.febs.dcacopy.service;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyAcademic;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 担任辅导员教师班主任情况(教师系列需填写) 服务类
 * </p>
 *
 * @author viki
 * @since 2020-12-15
 */
public interface IDcaBCopyAcademicService extends IService<DcaBCopyAcademic> {

        IPage<DcaBCopyAcademic> findDcaBCopyAcademics(QueryRequest request, DcaBCopyAcademic dcaBCopyAcademic);

        IPage<DcaBCopyAcademic> findDcaBCopyAcademicList(QueryRequest request, DcaBCopyAcademic dcaBCopyAcademic);

        void createDcaBCopyAcademic(DcaBCopyAcademic dcaBCopyAcademic);

        void updateDcaBCopyAcademic(DcaBCopyAcademic dcaBCopyAcademic);

        void deleteDcaBCopyAcademics(String[]Ids);

        List<DcaBCopyAcademic> getAll(String userAccount,String dcaYear);
        }
