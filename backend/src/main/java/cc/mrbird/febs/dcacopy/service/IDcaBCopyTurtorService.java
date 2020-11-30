package cc.mrbird.febs.dcacopy.service;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyTurtor;
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
 * @since 2020-11-26
 */
public interface IDcaBCopyTurtorService extends IService<DcaBCopyTurtor> {

        IPage<DcaBCopyTurtor> findDcaBCopyTurtors(QueryRequest request, DcaBCopyTurtor dcaBCopyTurtor);

        IPage<DcaBCopyTurtor> findDcaBCopyTurtorList(QueryRequest request, DcaBCopyTurtor dcaBCopyTurtor);

        void createDcaBCopyTurtor(DcaBCopyTurtor dcaBCopyTurtor);

        void updateDcaBCopyTurtor(DcaBCopyTurtor dcaBCopyTurtor);

        void deleteDcaBCopyTurtors(String[]Ids);

        List<DcaBCopyTurtor> getAll(String userAccount,String dcaYear);
        }
