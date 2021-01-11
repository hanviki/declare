package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBAuditdynamic;
import cc.mrbird.febs.dca.entity.DcaBReport;
import cc.mrbird.febs.dca.entity.DcaBUser;
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
 * @since 2020-10-15
 */
public interface IDcaBUserService extends IService<DcaBUser> {

        IPage<DcaBUser> findDcaBUsers(QueryRequest request, DcaBUser dcaBUser);
        IPage<DcaBUser> findDcaBUserswithDoctor(QueryRequest request, DcaBUser dcaBUser);

        IPage<DcaBUser> findDcaBUserList(QueryRequest request, DcaBUser dcaBUser);

        void createDcaBUser(DcaBUser dcaBUser);

        void updateDcaBUser(DcaBUser dcaBUser);

        void deleteDcaBUsers(String[]Ids);

        void deleteByuseraccount(String userAccount);

        IPage<DcaBUser> findDcaBUsersAudit(QueryRequest request, DcaBUser dcaBUser, int state);
        IPage<DcaBUser> findDcaBUsersAll(QueryRequest request, DcaBUser dcaBUser);

        IPage<DcaBUser> findDcaBUsersAuditCustom(QueryRequest request, DcaBUser dcaBUser,int state);
        IPage<DcaBUser> findDcaBUsersAuditCustomExport(QueryRequest request, DcaBUser dcaBUser);

        IPage<DcaBUser> findDcaBUsersAuditResult(QueryRequest request, DcaBUser dcaBUser);
        IPage<DcaBReport> findDcaBUsersAuditReport(QueryRequest request, DcaBUser dcaBUser);
        List<DcaBAuditdynamic> getAllInfo(List<String> listUniqUser);

        List<DcaBUser> findPerson(String userAccount);

        List<DcaBReport> getBigTableAllInfo(List<DcaBUser> listUniqUser, List<DcaBReport> reportList);
        }
