package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBEducationexperice;
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
 * @since 2020-08-11
 */
public interface IDcaBEducationexpericeService extends IService<DcaBEducationexperice> {

        IPage<DcaBEducationexperice> findDcaBEducationexperices(QueryRequest request, DcaBEducationexperice dcaBEducationexperice);

        IPage<DcaBEducationexperice> findDcaBEducationexpericeList(QueryRequest request, DcaBEducationexperice dcaBEducationexperice);

        void createDcaBEducationexperice(DcaBEducationexperice dcaBEducationexperice);

        void updateDcaBEducationexperice(DcaBEducationexperice dcaBEducationexperice);

        void deleteDcaBEducationexperices(String[]Ids);

        void deleteByuseraccount(String userAccount);
        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
