package cc.mrbird.febs.scm.service;

import cc.mrbird.febs.scm.entity.ScmBQuotedpriceD;
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
public interface IScmBQuotedpriceDService extends IService<ScmBQuotedpriceD> {

        IPage<ScmBQuotedpriceD> findScmBQuotedpriceDs(QueryRequest request, ScmBQuotedpriceD scmBQuotedpriceD);

        void createScmBQuotedpriceD(ScmBQuotedpriceD scmBQuotedpriceD);

        void updateScmBQuotedpriceD(ScmBQuotedpriceD scmBQuotedpriceD);

        void deleteScmBQuotedpriceDs(String[]Ids);
        }
