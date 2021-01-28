package cc.mrbird.febs.doctor.service;

import cc.mrbird.febs.doctor.entity.DcaBDocEducationexperice;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 学习工作经历 服务类
 * </p>
 *
 * @author viki
 * @since 2021-01-11
 */
public interface IDcaBDocEducationexpericeService extends IService<DcaBDocEducationexperice> {

        IPage<DcaBDocEducationexperice> findDcaBDocEducationexperices(QueryRequest request, DcaBDocEducationexperice dcaBDocEducationexperice);

        IPage<DcaBDocEducationexperice> findDcaBDocEducationexpericeList(QueryRequest request, DcaBDocEducationexperice dcaBDocEducationexperice);

        void createDcaBDocEducationexperice(DcaBDocEducationexperice dcaBDocEducationexperice);

        void updateDcaBDocEducationexperice(DcaBDocEducationexperice dcaBDocEducationexperice);

        void deleteDcaBDocEducationexperices(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
