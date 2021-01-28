package cc.mrbird.febs.doctor.service;

import cc.mrbird.febs.doctor.entity.DcaBDocUser;
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
 * @since 2021-01-12
 */
public interface IDcaBDocUserService extends IService<DcaBDocUser> {

        IPage<DcaBDocUser> findDcaBDocUsers(QueryRequest request, DcaBDocUser dcaBDocUser);

        IPage<DcaBDocUser> findDcaBDocUserList(QueryRequest request, DcaBDocUser dcaBDocUser);

        void createDcaBDocUser(DcaBDocUser dcaBDocUser);

        void updateDcaBDocUser(DcaBDocUser dcaBDocUser);

        void deleteDcaBDocUsers(String[]Ids);

        List<DcaBDocUser> findPerson(String userAccount);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
