package cc.mrbird.febs.dcacopy.service;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyParttimejob;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 社会兼职表 服务类
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
public interface IDcaBCopyParttimejobService extends IService<DcaBCopyParttimejob> {

        IPage<DcaBCopyParttimejob> findDcaBCopyParttimejobs(QueryRequest request, DcaBCopyParttimejob dcaBCopyParttimejob);

        IPage<DcaBCopyParttimejob> findDcaBCopyParttimejobList(QueryRequest request, DcaBCopyParttimejob dcaBCopyParttimejob);

        void createDcaBCopyParttimejob(DcaBCopyParttimejob dcaBCopyParttimejob);

        void updateDcaBCopyParttimejob(DcaBCopyParttimejob dcaBCopyParttimejob);

        void deleteDcaBCopyParttimejobs(String[]Ids);

        List<DcaBCopyParttimejob> getAll(String userAccount,String dcaYear);
        }
