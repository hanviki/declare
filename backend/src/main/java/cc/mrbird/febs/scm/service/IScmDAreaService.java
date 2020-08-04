package cc.mrbird.febs.scm.service;

import cc.mrbird.febs.scm.entity.ScmDArea;
import cc.mrbird.febs.system.domain.Dept;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 药房院区表 服务类
 * </p>
 *
 * @author viki
 * @since 2019-11-11
 */
public interface IScmDAreaService extends IService<ScmDArea> {



        Map<String, Object> findScmDAreas(QueryRequest request, ScmDArea scmDArea);

        List<ScmDArea> findScmDAreas(ScmDArea scmDArea, QueryRequest request);

        void createScmDArea(ScmDArea scmDArea);

        void updateScmDArea(ScmDArea scmDArea);

        void deleteScmDAreas(String[]Ids);

        List<ScmDArea> getAreasByUserId(Long id);
        List<ScmDArea> getAreasAll();
        }
