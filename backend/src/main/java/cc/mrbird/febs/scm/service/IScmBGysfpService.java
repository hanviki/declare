package cc.mrbird.febs.scm.service;

import cc.mrbird.febs.scm.entity.ScmBGysfp;
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
 * @since 2020-07-10
 */
public interface IScmBGysfpService extends IService<ScmBGysfp> {

        IPage<ScmBGysfp> findScmBGysfps(QueryRequest request, ScmBGysfp scmBGysfp);

        IPage<ScmBGysfp> findScmBGysfpsAudit(QueryRequest request, ScmBGysfp scmBGysfp);

        void createScmBGysfp(ScmBGysfp scmBGysfp);

        void updateScmBGysfp(ScmBGysfp scmBGysfp);

        void deleteScmBGysfps(String[]Ids);

        boolean IsExist(String fphm,String gys,String id);
        }
