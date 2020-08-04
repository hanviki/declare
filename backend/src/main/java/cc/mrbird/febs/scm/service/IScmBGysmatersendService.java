package cc.mrbird.febs.scm.service;

import cc.mrbird.febs.scm.entity.ScmBGysmatersend;
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
 * @since 2020-06-03
 */
public interface IScmBGysmatersendService extends IService<ScmBGysmatersend> {

        IPage<ScmBGysmatersend> findScmBGysmatersends(QueryRequest request, ScmBGysmatersend scmBGysmatersend,String key_matnr,String key_gys);

        void createScmBGysmatersend(ScmBGysmatersend scmBGysmatersend);

        void updateScmBGysmatersend(ScmBGysmatersend scmBGysmatersend);

        void deleteScmBGysmatersends(String[]Ids);
        }
