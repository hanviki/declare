package cc.mrbird.febs.dcacopy.service;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyTeacherqualify;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 教师资格证书(教师系列需填写) 服务类
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
public interface IDcaBCopyTeacherqualifyService extends IService<DcaBCopyTeacherqualify> {

        IPage<DcaBCopyTeacherqualify> findDcaBCopyTeacherqualifys(QueryRequest request, DcaBCopyTeacherqualify dcaBCopyTeacherqualify);

        IPage<DcaBCopyTeacherqualify> findDcaBCopyTeacherqualifyList(QueryRequest request, DcaBCopyTeacherqualify dcaBCopyTeacherqualify);

        void createDcaBCopyTeacherqualify(DcaBCopyTeacherqualify dcaBCopyTeacherqualify);

        void updateDcaBCopyTeacherqualify(DcaBCopyTeacherqualify dcaBCopyTeacherqualify);

        void deleteDcaBCopyTeacherqualifys(String[]Ids);

        List<DcaBCopyTeacherqualify> getAll(String userAccount,String dcaYear);
        }
