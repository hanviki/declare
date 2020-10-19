package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaDDeclaremodules;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 申请项目表 服务类
 * </p>
 *
 * @author viki
 * @since 2020-09-22
 */
public interface IDcaDDeclaremodulesService extends IService<DcaDDeclaremodules> {

        IPage<DcaDDeclaremodules> findDcaDDeclaremoduless(QueryRequest request, DcaDDeclaremodules dcaDDeclaremodules);

        IPage<DcaDDeclaremodules> findDcaDDeclaremodulesList(QueryRequest request, DcaDDeclaremodules dcaDDeclaremodules);

        void createDcaDDeclaremodules(DcaDDeclaremodules dcaDDeclaremodules);

        void updateDcaDDeclaremodules(DcaDDeclaremodules dcaDDeclaremodules);

        void deleteDcaDDeclaremoduless(String[]Ids);
        }
