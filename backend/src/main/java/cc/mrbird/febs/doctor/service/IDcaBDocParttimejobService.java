package cc.mrbird.febs.doctor.service;

import cc.mrbird.febs.doctor.entity.DcaBDocParttimejob;
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
 * @since 2021-01-11
 */
public interface IDcaBDocParttimejobService extends IService<DcaBDocParttimejob> {

        IPage<DcaBDocParttimejob> findDcaBDocParttimejobs(QueryRequest request, DcaBDocParttimejob dcaBDocParttimejob);

        IPage<DcaBDocParttimejob> findDcaBDocParttimejobList(QueryRequest request, DcaBDocParttimejob dcaBDocParttimejob);

        void createDcaBDocParttimejob(DcaBDocParttimejob dcaBDocParttimejob);

        void updateDcaBDocParttimejob(DcaBDocParttimejob dcaBDocParttimejob);

        void deleteDcaBDocParttimejobs(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
