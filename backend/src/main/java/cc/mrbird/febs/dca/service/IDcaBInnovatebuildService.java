package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBInnovatebuild;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 任现职以来承担的本科教学改革及建设项目(教师系列需填写) 服务类
 * </p>
 *
 * @author viki
 * @since 2020-11-06
 */
public interface IDcaBInnovatebuildService extends IService<DcaBInnovatebuild> {

        IPage<DcaBInnovatebuild> findDcaBInnovatebuilds(QueryRequest request, DcaBInnovatebuild dcaBInnovatebuild);

        IPage<DcaBInnovatebuild> findDcaBInnovatebuildList(QueryRequest request, DcaBInnovatebuild dcaBInnovatebuild);

        void createDcaBInnovatebuild(DcaBInnovatebuild dcaBInnovatebuild);

        void updateDcaBInnovatebuild(DcaBInnovatebuild dcaBInnovatebuild);

        void deleteDcaBInnovatebuilds(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
