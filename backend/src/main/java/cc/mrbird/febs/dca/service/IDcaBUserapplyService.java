package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBUserapply;
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
 * @since 2020-11-05
 */
public interface IDcaBUserapplyService extends IService<DcaBUserapply> {

        IPage<DcaBUserapply> findDcaBUserapplys(QueryRequest request, DcaBUserapply dcaBUserapply);

        IPage<DcaBUserapply> findDcaBUserapplyList(QueryRequest request, DcaBUserapply dcaBUserapply);

        void createDcaBUserapply(DcaBUserapply dcaBUserapply);

        void updateDcaBUserapply(DcaBUserapply dcaBUserapply);
        void updateDcaBUserapplyState(DcaBUserapply dcaBUserapply);

        void deleteDcaBUserapplys(String[]Ids);

         boolean IsExistApply(DcaBUserapply dcaBUserapply);

         List<String> getApplyAccount(String dcaYear,String type);

    /**
     * 部门审核的结果
     * @param request
     * @param dcaBUserapply
     * @return
     */
    IPage<DcaBUserapply> findDcaBUserapplyAudit(QueryRequest request, DcaBUserapply dcaBUserapply);
        }
