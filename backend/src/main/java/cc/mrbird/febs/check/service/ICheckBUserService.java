package cc.mrbird.febs.check.service;

import cc.mrbird.febs.check.entity.CheckBUser;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 待审核用户 服务类
 * </p>
 *
 * @author viki
 * @since 2021-01-22
 */
public interface ICheckBUserService extends IService<CheckBUser> {

        IPage<CheckBUser> findCheckBUsers(QueryRequest request, CheckBUser checkBUser);

        IPage<CheckBUser> findCheckBUserList(QueryRequest request, CheckBUser checkBUser);

        void createCheckBUser(CheckBUser checkBUser);

        void updateCheckBUser(CheckBUser checkBUser);

        void deleteCheckBUsers(String[]Ids);

        List<CheckBUser> getAll(String userAccount,String dcaYear);

         IPage<CheckBUser> findDcaBUsersAudit(QueryRequest request,String userAccount, CheckBUser dcaBUser, int state);
        IPage<CheckBUser>   findDcaBUsersAuditCustom(QueryRequest request,String userAccount, CheckBUser dcaBUser, int state);
        }
