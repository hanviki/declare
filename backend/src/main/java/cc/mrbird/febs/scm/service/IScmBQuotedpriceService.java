package cc.mrbird.febs.scm.service;

import cc.mrbird.febs.scm.entity.ScmBQuotedprice;
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
public interface IScmBQuotedpriceService extends IService<ScmBQuotedprice> {

        IPage<ScmBQuotedprice> findScmBQuotedprices(QueryRequest request, ScmBQuotedprice scmBQuotedprice);

        void createScmBQuotedprice(ScmBQuotedprice scmBQuotedprice);

        void updateScmBQuotedprice(ScmBQuotedprice scmBQuotedprice);

        void deleteScmBQuotedprices(String[]Ids);
        void deleteScmBQuotedprices(String Id);
        }
