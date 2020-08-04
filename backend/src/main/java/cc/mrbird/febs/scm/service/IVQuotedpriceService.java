package cc.mrbird.febs.scm.service;

import cc.mrbird.febs.scm.entity.VQuotedprice;
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
 * @since 2020-01-07
 */
public interface IVQuotedpriceService extends IService<VQuotedprice> {

        IPage<VQuotedprice> findVQuotedprices(QueryRequest request, VQuotedprice vQuotedprice);

        void createVQuotedprice(VQuotedprice vQuotedprice);

        void updateVQuotedprice(VQuotedprice vQuotedprice);

        void deleteVQuotedprices(String[]Ids);
        }
