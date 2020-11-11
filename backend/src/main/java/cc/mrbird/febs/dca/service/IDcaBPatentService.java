package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBPatent;
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
 * @since 2020-10-20
 */
public interface IDcaBPatentService extends IService<DcaBPatent> {

        IPage<DcaBPatent> findDcaBPatents(QueryRequest request, DcaBPatent dcaBPatent);

        IPage<DcaBPatent> findDcaBPatentList(QueryRequest request, DcaBPatent dcaBPatent);

        void createDcaBPatent(DcaBPatent dcaBPatent);

        void updateDcaBPatent(DcaBPatent dcaBPatent);

        void deleteDcaBPatents(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
