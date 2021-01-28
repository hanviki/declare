package cc.mrbird.febs.doctor.service;

import cc.mrbird.febs.doctor.entity.DcaBDocAcademic;
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
 * @since 2021-01-11
 */
public interface IDcaBDocAcademicService extends IService<DcaBDocAcademic> {

        IPage<DcaBDocAcademic> findDcaBDocAcademics(QueryRequest request, DcaBDocAcademic dcaBDocAcademic);

        IPage<DcaBDocAcademic> findDcaBDocAcademicList(QueryRequest request, DcaBDocAcademic dcaBDocAcademic);

        void createDcaBDocAcademic(DcaBDocAcademic dcaBDocAcademic);

        void updateDcaBDocAcademic(DcaBDocAcademic dcaBDocAcademic);

        void deleteDcaBDocAcademics(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
