package cc.mrbird.febs.doctor.service;

import cc.mrbird.febs.doctor.entity.DcaBDocInnovatebuild;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 改革及建设项目 服务类
 * </p>
 *
 * @author viki
 * @since 2021-01-11
 */
public interface IDcaBDocInnovatebuildService extends IService<DcaBDocInnovatebuild> {

        IPage<DcaBDocInnovatebuild> findDcaBDocInnovatebuilds(QueryRequest request, DcaBDocInnovatebuild dcaBDocInnovatebuild);

        IPage<DcaBDocInnovatebuild> findDcaBDocInnovatebuildList(QueryRequest request, DcaBDocInnovatebuild dcaBDocInnovatebuild);

        void createDcaBDocInnovatebuild(DcaBDocInnovatebuild dcaBDocInnovatebuild);

        void updateDcaBDocInnovatebuild(DcaBDocInnovatebuild dcaBDocInnovatebuild);

        void deleteDcaBDocInnovatebuilds(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
