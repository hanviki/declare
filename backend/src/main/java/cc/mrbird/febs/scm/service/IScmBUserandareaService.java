package cc.mrbird.febs.scm.service;

import cc.mrbird.febs.scm.entity.ScmBUserandarea;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 用户表和院区表配置表 服务类
 * </p>
 *
 * @author viki
 * @since 2019-11-13
 */
public interface IScmBUserandareaService extends IService<ScmBUserandarea> {

        IPage<ScmBUserandarea> findScmBUserandareas(QueryRequest request, ScmBUserandarea scmBUserandarea);

        void createScmBUserandarea(ScmBUserandarea scmBUserandarea);

        void updateScmBUserandarea(ScmBUserandarea scmBUserandarea);

        void deleteScmBUserandareas(String[]Ids);
        List<ScmBUserandarea> getAreaByUserId(String userId);
        }
