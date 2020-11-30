package cc.mrbird.febs.dcacopy.service;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyOtherwork;
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
 * @since 2020-11-26
 */
public interface IDcaBCopyOtherworkService extends IService<DcaBCopyOtherwork> {

        IPage<DcaBCopyOtherwork> findDcaBCopyOtherworks(QueryRequest request, DcaBCopyOtherwork dcaBCopyOtherwork);

        IPage<DcaBCopyOtherwork> findDcaBCopyOtherworkList(QueryRequest request, DcaBCopyOtherwork dcaBCopyOtherwork);

        void createDcaBCopyOtherwork(DcaBCopyOtherwork dcaBCopyOtherwork);

        void updateDcaBCopyOtherwork(DcaBCopyOtherwork dcaBCopyOtherwork);

        void deleteDcaBCopyOtherworks(String[]Ids);

        List<DcaBCopyOtherwork> getAll(String userAccount,String dcaYear);
        }
