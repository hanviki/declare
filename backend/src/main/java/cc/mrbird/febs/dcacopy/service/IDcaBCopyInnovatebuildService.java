package cc.mrbird.febs.dcacopy.service;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyInnovatebuild;
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
 * @since 2020-11-26
 */
public interface IDcaBCopyInnovatebuildService extends IService<DcaBCopyInnovatebuild> {

        IPage<DcaBCopyInnovatebuild> findDcaBCopyInnovatebuilds(QueryRequest request, DcaBCopyInnovatebuild dcaBCopyInnovatebuild);

        IPage<DcaBCopyInnovatebuild> findDcaBCopyInnovatebuildList(QueryRequest request, DcaBCopyInnovatebuild dcaBCopyInnovatebuild);

        void createDcaBCopyInnovatebuild(DcaBCopyInnovatebuild dcaBCopyInnovatebuild);

        void updateDcaBCopyInnovatebuild(DcaBCopyInnovatebuild dcaBCopyInnovatebuild);

        void deleteDcaBCopyInnovatebuilds(String[]Ids);

        List<DcaBCopyInnovatebuild> getAll(String userAccount,String dcaYear);
        }
