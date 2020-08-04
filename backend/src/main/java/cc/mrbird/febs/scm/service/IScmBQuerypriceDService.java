package cc.mrbird.febs.scm.service;

import cc.mrbird.febs.scm.entity.ScmBQuerypriceD;
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
 * @since 2019-12-26
 */
public interface IScmBQuerypriceDService extends IService<ScmBQuerypriceD> {

        IPage<ScmBQuerypriceD> findScmBQuerypriceDs(QueryRequest request, ScmBQuerypriceD scmBQuerypriceD);

        void createScmBQuerypriceD(ScmBQuerypriceD scmBQuerypriceD);

        void updateScmBQuerypriceD(ScmBQuerypriceD scmBQuerypriceD);

        void deleteScmBQuerypriceDs(String[]Ids);

        void updateScmBQuotedpriceDState(String gysaccount,Long baseid);
        }
