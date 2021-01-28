package cc.mrbird.febs.doctor.service;

import cc.mrbird.febs.doctor.entity.DcaBDocMedicalaccident;
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
 * @since 2021-01-11
 */
public interface IDcaBDocMedicalaccidentService extends IService<DcaBDocMedicalaccident> {

        IPage<DcaBDocMedicalaccident> findDcaBDocMedicalaccidents(QueryRequest request, DcaBDocMedicalaccident dcaBDocMedicalaccident);

        IPage<DcaBDocMedicalaccident> findDcaBDocMedicalaccidentList(QueryRequest request, DcaBDocMedicalaccident dcaBDocMedicalaccident);

        void createDcaBDocMedicalaccident(DcaBDocMedicalaccident dcaBDocMedicalaccident);

        void updateDcaBDocMedicalaccident(DcaBDocMedicalaccident dcaBDocMedicalaccident);

        void deleteDcaBDocMedicalaccidents(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
