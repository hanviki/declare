package cc.mrbird.febs.doctor.service;

import cc.mrbird.febs.doctor.entity.DcaBDocPatent;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 申请专利情况 服务类
 * </p>
 *
 * @author viki
 * @since 2021-01-11
 */
public interface IDcaBDocPatentService extends IService<DcaBDocPatent> {

        IPage<DcaBDocPatent> findDcaBDocPatents(QueryRequest request, DcaBDocPatent dcaBDocPatent);

        IPage<DcaBDocPatent> findDcaBDocPatentList(QueryRequest request, DcaBDocPatent dcaBDocPatent);

        void createDcaBDocPatent(DcaBDocPatent dcaBDocPatent);

        void updateDcaBDocPatent(DcaBDocPatent dcaBDocPatent);

        void deleteDcaBDocPatents(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
