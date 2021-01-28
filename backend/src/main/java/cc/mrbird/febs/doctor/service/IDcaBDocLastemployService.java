package cc.mrbird.febs.doctor.service;

import cc.mrbird.febs.doctor.entity.DcaBDocLastemploy;
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
 * @since 2021-01-11
 */
public interface IDcaBDocLastemployService extends IService<DcaBDocLastemploy> {

        IPage<DcaBDocLastemploy> findDcaBDocLastemploys(QueryRequest request, DcaBDocLastemploy dcaBDocLastemploy);

        IPage<DcaBDocLastemploy> findDcaBDocLastemployList(QueryRequest request, DcaBDocLastemploy dcaBDocLastemploy);

        void createDcaBDocLastemploy(DcaBDocLastemploy dcaBDocLastemploy);

        void updateDcaBDocLastemploy(DcaBDocLastemploy dcaBDocLastemploy);

        void deleteDcaBDocLastemploys(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
