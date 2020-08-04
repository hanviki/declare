package cc.mrbird.febs.scm.service;

import cc.mrbird.febs.scm.entity.ScmBSalereturn;
import cc.mrbird.febs.scm.entity.VScmBSalereturn;
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
 * @since 2019-12-09
 */
public interface IScmBSalereturnService extends IService<ScmBSalereturn> {

        IPage<VScmBSalereturn> findScmBSalereturns(QueryRequest request, VScmBSalereturn scmBSalereturn);

        void createScmBSalereturn(ScmBSalereturn scmBSalereturn);

        void updateScmBSalereturn(ScmBSalereturn scmBSalereturn);

        void deleteScmBSalereturns(String[]Ids);
        }
