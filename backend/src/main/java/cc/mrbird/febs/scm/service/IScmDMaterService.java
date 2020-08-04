package cc.mrbird.febs.scm.service;

import cc.mrbird.febs.scm.entity.ScmDMater;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 药品物料库 服务类
 * </p>
 *
 * @author viki
 * @since 2019-11-11
 */
public interface IScmDMaterService extends IService<ScmDMater> {

        IPage<ScmDMater> findScmDMaters(QueryRequest request, ScmDMater scmDMater);
        IPage<ScmDMater> findScmDMaters_send(QueryRequest request, ScmDMater scmDMater);


        void createScmDMater(ScmDMater scmDMater);

        void updateScmDMater(ScmDMater scmDMater);

        void deleteScmDMaters(String[]Ids);
        }
