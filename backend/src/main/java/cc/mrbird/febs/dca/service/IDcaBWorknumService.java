package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBWorknum;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author viki
 * @since 2020-10-20
 */
public interface IDcaBWorknumService extends IService<DcaBWorknum> {

        IPage<DcaBWorknum> findDcaBWorknums(QueryRequest request, DcaBWorknum dcaBWorknum);

        IPage<DcaBWorknum> findDcaBWorknumList(QueryRequest request, DcaBWorknum dcaBWorknum);

        void createDcaBWorknum(DcaBWorknum dcaBWorknum);

        void updateDcaBWorknum(DcaBWorknum dcaBWorknum);

        void deleteDcaBWorknums(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
