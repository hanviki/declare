package cc.mrbird.febs.scm.service;

import cc.mrbird.febs.scm.entity.ScmBQuerypriceProduct;
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
public interface IScmBQuerypriceProductService extends IService<ScmBQuerypriceProduct> {

        IPage<ScmBQuerypriceProduct> findScmBQuerypriceProducts(QueryRequest request, ScmBQuerypriceProduct scmBQuerypriceProduct);

        void createScmBQuerypriceProduct(ScmBQuerypriceProduct scmBQuerypriceProduct);

        void updateScmBQuerypriceProduct(ScmBQuerypriceProduct scmBQuerypriceProduct);

        void deleteScmBQuerypriceProducts(String[]Ids);
        }
