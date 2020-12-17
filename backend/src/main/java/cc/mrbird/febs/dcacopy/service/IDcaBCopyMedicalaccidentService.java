package cc.mrbird.febs.dcacopy.service;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyMedicalaccident;
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
public interface IDcaBCopyMedicalaccidentService extends IService<DcaBCopyMedicalaccident> {

        IPage<DcaBCopyMedicalaccident> findDcaBCopyMedicalaccidents(QueryRequest request, DcaBCopyMedicalaccident dcaBCopyMedicalaccident);

        IPage<DcaBCopyMedicalaccident> findDcaBCopyMedicalaccidentList(QueryRequest request, DcaBCopyMedicalaccident dcaBCopyMedicalaccident);

        void createDcaBCopyMedicalaccident(DcaBCopyMedicalaccident dcaBCopyMedicalaccident);

        void updateDcaBCopyMedicalaccident(DcaBCopyMedicalaccident dcaBCopyMedicalaccident);

        void deleteDcaBCopyMedicalaccidents(String[]Ids);

        List<DcaBCopyMedicalaccident> getAll(String userAccount,String dcaYear);
        }
