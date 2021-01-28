package cc.mrbird.febs.doctor.service;

import cc.mrbird.febs.doctor.entity.DcaBDocTeacherqualify;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 教师资格 服务类
 * </p>
 *
 * @author viki
 * @since 2021-01-11
 */
public interface IDcaBDocTeacherqualifyService extends IService<DcaBDocTeacherqualify> {

        IPage<DcaBDocTeacherqualify> findDcaBDocTeacherqualifys(QueryRequest request, DcaBDocTeacherqualify dcaBDocTeacherqualify);

        IPage<DcaBDocTeacherqualify> findDcaBDocTeacherqualifyList(QueryRequest request, DcaBDocTeacherqualify dcaBDocTeacherqualify);

        void createDcaBDocTeacherqualify(DcaBDocTeacherqualify dcaBDocTeacherqualify);

        void updateDcaBDocTeacherqualify(DcaBDocTeacherqualify dcaBDocTeacherqualify);

        void deleteDcaBDocTeacherqualifys(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
