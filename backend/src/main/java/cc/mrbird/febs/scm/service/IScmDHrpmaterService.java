package cc.mrbird.febs.scm.service;

import cc.mrbird.febs.scm.entity.ScmDHrpmater;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * SAP的药品字典库，这是关联供应商的 服务类
 * </p>
 *
 * @author viki
 * @since 2019-11-26
 */
public interface IScmDHrpmaterService extends IService<ScmDHrpmater> {

        IPage<ScmDHrpmater> findScmDHrpmaters(QueryRequest request, ScmDHrpmater scmDHrpmater);

        void createScmDHrpmater(ScmDHrpmater scmDHrpmater);

        void updateScmDHrpmater(ScmDHrpmater scmDHrpmater);

        void deleteScmDHrpmaters(String[]Ids);
        }
