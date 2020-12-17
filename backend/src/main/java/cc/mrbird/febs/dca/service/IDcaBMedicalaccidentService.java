package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBMedicalaccident;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 担任博导硕导 服务类
 * </p>
 *
 * @author viki
 * @since 2020-12-15
 */
public interface IDcaBMedicalaccidentService extends IService<DcaBMedicalaccident> {

        IPage<DcaBMedicalaccident> findDcaBMedicalaccidents(QueryRequest request, DcaBMedicalaccident dcaBMedicalaccident);

        IPage<DcaBMedicalaccident> findDcaBMedicalaccidentList(QueryRequest request, DcaBMedicalaccident dcaBMedicalaccident);

        void createDcaBMedicalaccident(DcaBMedicalaccident dcaBMedicalaccident);

        void updateDcaBMedicalaccident(DcaBMedicalaccident dcaBMedicalaccident);

        void deleteDcaBMedicalaccidents(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
