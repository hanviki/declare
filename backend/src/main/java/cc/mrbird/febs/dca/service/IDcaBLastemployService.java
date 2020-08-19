package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBLastemploy;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 完成上一聘期 服务类
 * </p>
 *
 * @author viki
 * @since 2020-08-12
 */
public interface IDcaBLastemployService extends IService<DcaBLastemploy> {

        IPage<DcaBLastemploy> findDcaBLastemploys(QueryRequest request, DcaBLastemploy dcaBLastemploy);

        IPage<DcaBLastemploy> findDcaBLastemployList(QueryRequest request, DcaBLastemploy dcaBLastemploy);

        void createDcaBLastemploy(DcaBLastemploy dcaBLastemploy);

        void updateDcaBLastemploy(DcaBLastemploy dcaBLastemploy);

        void deleteDcaBLastemploys(String[]Ids);

        void deleteByuseraccount(String userAccount);
        }
