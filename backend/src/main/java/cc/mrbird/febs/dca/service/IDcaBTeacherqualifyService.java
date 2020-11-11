package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBTeacherqualify;
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
 * @since 2020-10-20
 */
public interface IDcaBTeacherqualifyService extends IService<DcaBTeacherqualify> {

        IPage<DcaBTeacherqualify> findDcaBTeacherqualifys(QueryRequest request, DcaBTeacherqualify dcaBTeacherqualify);

        IPage<DcaBTeacherqualify> findDcaBTeacherqualifyList(QueryRequest request, DcaBTeacherqualify dcaBTeacherqualify);

        void createDcaBTeacherqualify(DcaBTeacherqualify dcaBTeacherqualify);

        void updateDcaBTeacherqualify(DcaBTeacherqualify dcaBTeacherqualify);

        void deleteDcaBTeacherqualifys(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
