package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBOtherwork;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 其他工作及成果，拟聘岗位工作思路及预期目标，个人总结 服务类
 * </p>
 *
 * @author viki
 * @since 2020-08-11
 */
public interface IDcaBOtherworkService extends IService<DcaBOtherwork> {

        IPage<DcaBOtherwork> findDcaBOtherworks(QueryRequest request, DcaBOtherwork dcaBOtherwork);

        IPage<DcaBOtherwork> findDcaBOtherworkList(QueryRequest request, DcaBOtherwork dcaBOtherwork);

        void createDcaBOtherwork(DcaBOtherwork dcaBOtherwork);

        void updateDcaBOtherwork(DcaBOtherwork dcaBOtherwork);

        void deleteDcaBOtherworks(String[]Ids);

        void deleteByuseraccount(String userAccount);
        }
