package cc.mrbird.febs.scm.service;

import cc.mrbird.febs.scm.entity.ScmDSenddepart;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 物资送货部门 服务类
 * </p>
 *
 * @author viki
 * @since 2019-11-26
 */
public interface IScmDSenddepartService extends IService<ScmDSenddepart> {

        IPage<ScmDSenddepart> findScmDSenddeparts(QueryRequest request, ScmDSenddepart scmDSenddepart);

        void createScmDSenddepart(ScmDSenddepart scmDSenddepart);

        void updateScmDSenddepart(ScmDSenddepart scmDSenddepart);

        void deleteScmDSenddeparts(String[]Ids);
        }
