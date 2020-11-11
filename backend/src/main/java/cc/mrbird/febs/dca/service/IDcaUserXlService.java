package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaUserMoudules;
import cc.mrbird.febs.dca.entity.DcaUserXl;
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
 * @since 2020-10-29
 */
public interface IDcaUserXlService extends IService<DcaUserXl> {

        IPage<DcaUserXl> findDcaUserXls(QueryRequest request, DcaUserXl dcaUserXl);

        IPage<DcaUserXl> findDcaUserXlList(QueryRequest request, DcaUserXl dcaUserXl);

        void createDcaUserXl(DcaUserXl dcaUserXl);

        void updateDcaUserXl(DcaUserXl dcaUserXl);

        void deleteDcaUserXls(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);

        List<DcaUserXl> getMudulesByUserId(Integer userId);
        }
