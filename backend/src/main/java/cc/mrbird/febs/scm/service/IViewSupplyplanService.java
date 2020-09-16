package cc.mrbird.febs.scm.service;

import cc.mrbird.febs.scm.entity.ViewSupplyplan;
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
 * @since 2019-12-05
 */
public interface IViewSupplyplanService extends IService<ViewSupplyplan> {

        IPage<ViewSupplyplan> findViewSupplyplans(QueryRequest request, ViewSupplyplan viewSupplyplan);

        /**
         * 获取送货清单 的供应计划
         * @param request
         * @param viewSupplyplan
         * @return
         */
        IPage<ViewSupplyplan> findViewSupplyplans2(QueryRequest request, ViewSupplyplan viewSupplyplan);

        IPage<ViewSupplyplan> findDoneViewSupplyplans(QueryRequest request, ViewSupplyplan viewSupplyplan, String statusType);

        void createViewSupplyplan(ViewSupplyplan viewSupplyplan);

        void updateViewSupplyplan(ViewSupplyplan viewSupplyplan);

        void deleteViewSupplyplans(String[]Ids);

        Long findAreaCount(String username,String werks);

        List<ViewSupplyplan> getViewSupplyPlanByIds(String ids);

        List<ViewSupplyplan> getViewSupplyPlanByOrderId(String sendOrderId);

        List<ViewSupplyplan> findVPlanByOrderCode(String orderCode);

        IPage<ViewSupplyplan> findVPurcharseorder (QueryRequest request, ViewSupplyplan viewSupplyplan);

        List<ViewSupplyplan> findPurcharseSendOrder(ViewSupplyplan viewSupplyplan);

        IPage<ViewSupplyplan> findMatnrValid (QueryRequest request, ViewSupplyplan viewSupplyplan);

        List<ViewSupplyplan> getViewSupplyPlanByPdaId(String id);
        }
