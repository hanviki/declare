package cc.mrbird.febs.doctor.service;

import cc.mrbird.febs.doctor.entity.DcaBDocGraduate;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 研究生情况 服务类
 * </p>
 *
 * @author viki
 * @since 2021-01-11
 */
public interface IDcaBDocGraduateService extends IService<DcaBDocGraduate> {

        IPage<DcaBDocGraduate> findDcaBDocGraduates(QueryRequest request, DcaBDocGraduate dcaBDocGraduate);

        IPage<DcaBDocGraduate> findDcaBDocGraduateList(QueryRequest request, DcaBDocGraduate dcaBDocGraduate);

        void createDcaBDocGraduate(DcaBDocGraduate dcaBDocGraduate);

        void updateDcaBDocGraduate(DcaBDocGraduate dcaBDocGraduate);

        void deleteDcaBDocGraduates(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
