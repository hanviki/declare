package cc.mrbird.febs.dcacopy.service;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyLastemploy;
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
 * @since 2020-11-26
 */
public interface IDcaBCopyLastemployService extends IService<DcaBCopyLastemploy> {

        IPage<DcaBCopyLastemploy> findDcaBCopyLastemploys(QueryRequest request, DcaBCopyLastemploy dcaBCopyLastemploy);

        IPage<DcaBCopyLastemploy> findDcaBCopyLastemployList(QueryRequest request, DcaBCopyLastemploy dcaBCopyLastemploy);

        void createDcaBCopyLastemploy(DcaBCopyLastemploy dcaBCopyLastemploy);

        void updateDcaBCopyLastemploy(DcaBCopyLastemploy dcaBCopyLastemploy);

        void deleteDcaBCopyLastemploys(String[]Ids);

        List<DcaBCopyLastemploy> getAll(String userAccount,String dcaYear);
        }
