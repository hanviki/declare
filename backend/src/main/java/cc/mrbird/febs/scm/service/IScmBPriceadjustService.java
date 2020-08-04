package cc.mrbird.febs.scm.service;

import cc.mrbird.febs.scm.entity.ScmBPriceadjust;
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
 * @since 2019-12-27
 */
public interface IScmBPriceadjustService extends IService<ScmBPriceadjust> {

        IPage<ScmBPriceadjust> findScmBPriceadjusts(QueryRequest request, ScmBPriceadjust scmBPriceadjust);

        void createScmBPriceadjust(ScmBPriceadjust scmBPriceadjust);

        void updateScmBPriceadjust(ScmBPriceadjust scmBPriceadjust);

        void deleteScmBPriceadjusts(String[]Ids);
        }
