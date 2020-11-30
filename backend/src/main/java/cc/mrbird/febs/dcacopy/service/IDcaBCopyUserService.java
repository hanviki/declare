package cc.mrbird.febs.dcacopy.service;

import cc.mrbird.febs.dca.entity.CustomApplyFirst;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyUser;
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
 * @since 2020-11-26
 */
public interface IDcaBCopyUserService extends IService<DcaBCopyUser> {

        IPage<DcaBCopyUser> findDcaBCopyUsers(QueryRequest request, DcaBCopyUser dcaBCopyUser);

        IPage<DcaBCopyUser> findDcaBCopyUserList(QueryRequest request, DcaBCopyUser dcaBCopyUser);

        void createDcaBCopyUser(DcaBCopyUser dcaBCopyUser);

        void updateDcaBCopyUser(DcaBCopyUser dcaBCopyUser);

        void deleteDcaBCopyUsers(String[]Ids);

        List<DcaBCopyUser> getAll(String userAccount,String dcaYear);
        CustomApplyFirst getPrintPdf(String userAccount, String dcaYear,String zc);
        }
