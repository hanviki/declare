package cc.mrbird.febs.dcacopy.service;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyGraduate;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 任现职以来独立指导研究生情况(教师系列需填写) 服务类
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
public interface IDcaBCopyGraduateService extends IService<DcaBCopyGraduate> {

        IPage<DcaBCopyGraduate> findDcaBCopyGraduates(QueryRequest request, DcaBCopyGraduate dcaBCopyGraduate);

        IPage<DcaBCopyGraduate> findDcaBCopyGraduateList(QueryRequest request, DcaBCopyGraduate dcaBCopyGraduate);

        void createDcaBCopyGraduate(DcaBCopyGraduate dcaBCopyGraduate);

        void updateDcaBCopyGraduate(DcaBCopyGraduate dcaBCopyGraduate);

        void deleteDcaBCopyGraduates(String[]Ids);

        List<DcaBCopyGraduate> getAll(String userAccount,String dcaYear);
        }
