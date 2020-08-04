package cc.mrbird.febs.scm.service;

import cc.mrbird.febs.scm.entity.VScmBSalereturn;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * VIEW 服务类
 * </p>
 *
 * @author viki
 * @since 2019-12-09
 */
public interface IVScmBSalereturnService extends IService<VScmBSalereturn> {

        IPage<VScmBSalereturn> findVScmBSalereturns(QueryRequest request, VScmBSalereturn vScmBSalereturn);

        void createVScmBSalereturn(VScmBSalereturn vScmBSalereturn);

        void updateVScmBSalereturn(VScmBSalereturn vScmBSalereturn);

        void deleteVScmBSalereturns(String[]Ids);
        }
