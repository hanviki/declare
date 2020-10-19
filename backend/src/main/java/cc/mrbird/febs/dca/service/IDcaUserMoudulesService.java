package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaUserMoudules;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 申报模块和用户表关联表 服务类
 * </p>
 *
 * @author viki
 * @since 2020-08-10
 */
public interface IDcaUserMoudulesService extends IService<DcaUserMoudules> {

        IPage<DcaUserMoudules> findDcaUserMouduless(QueryRequest request, DcaUserMoudules dcaUserMoudules);

        IPage<DcaUserMoudules> findDcaUserMoudulesList(QueryRequest request, DcaUserMoudules dcaUserMoudules);

        void createDcaUserMoudules(DcaUserMoudules dcaUserMoudules);

        void updateDcaUserMoudules(DcaUserMoudules dcaUserMoudules);

        void deleteDcaUserMouduless(String[]Ids);

        List<DcaUserMoudules> getMudulesByUserId(Integer userId);
        }
