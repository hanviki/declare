package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBGraduate;
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
 * @since 2020-10-20
 */
public interface IDcaBGraduateService extends IService<DcaBGraduate> {

        IPage<DcaBGraduate> findDcaBGraduates(QueryRequest request, DcaBGraduate dcaBGraduate);

        IPage<DcaBGraduate> findDcaBGraduateList(QueryRequest request, DcaBGraduate dcaBGraduate);

        void createDcaBGraduate(DcaBGraduate dcaBGraduate);

        void updateDcaBGraduate(DcaBGraduate dcaBGraduate);

        void deleteDcaBGraduates(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
