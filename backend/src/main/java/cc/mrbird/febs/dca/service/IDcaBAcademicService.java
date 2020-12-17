package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBAcademic;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 重要岗位任职及学术影响 服务类
 * </p>
 *
 * @author viki
 * @since 2020-12-15
 */
public interface IDcaBAcademicService extends IService<DcaBAcademic> {

        IPage<DcaBAcademic> findDcaBAcademics(QueryRequest request, DcaBAcademic dcaBAcademic);

        IPage<DcaBAcademic> findDcaBAcademicList(QueryRequest request, DcaBAcademic dcaBAcademic);

        void createDcaBAcademic(DcaBAcademic dcaBAcademic);

        void updateDcaBAcademic(DcaBAcademic dcaBAcademic);

        void deleteDcaBAcademics(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
