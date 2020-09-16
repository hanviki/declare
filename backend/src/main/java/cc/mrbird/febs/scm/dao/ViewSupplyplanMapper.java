package cc.mrbird.febs.scm.dao;

import cc.mrbird.febs.scm.entity.ViewSupplyplan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * VIEW Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2019-12-05
 */
public interface ViewSupplyplanMapper extends BaseMapper<ViewSupplyplan> {
        void updateViewSupplyplan(ViewSupplyplan viewSupplyplan);

        @Select("select *  from view_supplyplan where id in (${ids})")
        List<ViewSupplyplan> GetViewSupplyPlanByIds(@Param(value = "ids") String ids);



        IPage<ViewSupplyplan> findVPurcharseorder(Page page, @Param("order") ViewSupplyplan order);

        Long findVPurcharseorder_COUNT(@Param("order") ViewSupplyplan order);
        @Select("select COUNT(1) from scm_b_userandarea where UserID=#{username} and AreaID=#{werk}")
        Long findAreaCount(@Param(value = "username") String username,@Param(value = "werk") String werk);

        Long findVPurcharseorder_noOrder(@Param("order") ViewSupplyplan order);
        List<ViewSupplyplan>  findVPlanByOrderCode(@Param(value = "orderCode") String orderCode);

        List<ViewSupplyplan> findVPurcharseorder(@Param("order") ViewSupplyplan order);

        Long findMatnrValid_Count(@Param("order") ViewSupplyplan order);
        Long findMatnrValid_Count2(@Param("order") ViewSupplyplan order);

        IPage<ViewSupplyplan>  findMatnrValid(Page page, @Param("order") ViewSupplyplan order);


        List<ViewSupplyplan> findVPurcharseorderForPda(@Param("id") String id);
        }
