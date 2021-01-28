package cc.mrbird.febs.doctor.service;

import cc.mrbird.febs.doctor.entity.DcaBDocOtherwork;
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
 * @since 2021-01-11
 */
public interface IDcaBDocOtherworkService extends IService<DcaBDocOtherwork> {

        IPage<DcaBDocOtherwork> findDcaBDocOtherworks(QueryRequest request, DcaBDocOtherwork dcaBDocOtherwork);

        IPage<DcaBDocOtherwork> findDcaBDocOtherworkList(QueryRequest request, DcaBDocOtherwork dcaBDocOtherwork);

        void createDcaBDocOtherwork(DcaBDocOtherwork dcaBDocOtherwork);

        void updateDcaBDocOtherwork(DcaBDocOtherwork dcaBDocOtherwork);

        void deleteDcaBDocOtherworks(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
