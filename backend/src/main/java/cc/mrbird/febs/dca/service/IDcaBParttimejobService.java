package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBParttimejob;
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
 * @since 2020-10-20
 */
public interface IDcaBParttimejobService extends IService<DcaBParttimejob> {

        IPage<DcaBParttimejob> findDcaBParttimejobs(QueryRequest request, DcaBParttimejob dcaBParttimejob);

        IPage<DcaBParttimejob> findDcaBParttimejobList(QueryRequest request, DcaBParttimejob dcaBParttimejob);

        void createDcaBParttimejob(DcaBParttimejob dcaBParttimejob);

        void updateDcaBParttimejob(DcaBParttimejob dcaBParttimejob);

        void deleteDcaBParttimejobs(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
