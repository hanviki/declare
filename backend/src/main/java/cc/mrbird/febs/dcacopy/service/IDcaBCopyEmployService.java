package cc.mrbird.febs.dcacopy.service;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyEmploy;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 任职培养 服务类
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
public interface IDcaBCopyEmployService extends IService<DcaBCopyEmploy> {

        IPage<DcaBCopyEmploy> findDcaBCopyEmploys(QueryRequest request, DcaBCopyEmploy dcaBCopyEmploy);

        IPage<DcaBCopyEmploy> findDcaBCopyEmployList(QueryRequest request, DcaBCopyEmploy dcaBCopyEmploy);

        void createDcaBCopyEmploy(DcaBCopyEmploy dcaBCopyEmploy);

        void updateDcaBCopyEmploy(DcaBCopyEmploy dcaBCopyEmploy);

        void deleteDcaBCopyEmploys(String[]Ids);

        List<DcaBCopyEmploy> getAll(String userAccount,String dcaYear);
        }
