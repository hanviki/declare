package cc.mrbird.febs.scm.dao;

import cc.mrbird.febs.scm.entity.ScmBSupplyplan;
import cc.mrbird.febs.scm.entity.StatisticMenge;
import cc.mrbird.febs.scm.entity.ViewSupplyplan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 供应计划 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2019-11-21
 */
public interface ScmBSupplyplanMapper extends BaseMapper<ScmBSupplyplan> {
        void updateScmBSupplyplan(ScmBSupplyplan scmBSupplyplan);

        Long IsOutMenge(@Param(value = "plan") ScmBSupplyplan scmBSupplyplan);

        int IsExistFphm(@Param(value="id") String id,@Param(value="fphm") String fphm,@Param(value="gys") String gys);

        @Update("update Scm_B_Supplyplan set doneMenge=ifnull(doneMenge,0) + ${doneMenge} where id=${id} and status=0")
        int UpdateDoneMenge(@Param(value="id") String id, @Param(value="doneMenge")String doneMenge);

        @Update("update Scm_B_Supplyplan set doneMenge=0 where id=${id} and status=0")
        int UpdateCancelDoneMenge(@Param(value="id") String id);

        void doneSupplyPlan(@Param("ids") List<Long> ids);

        void cancelSupplyPlan(@Param("ids") List<Long> ids);

        @Update("update scm_b_gysfp set werks=#{plan.werks} ,werkt=#{plan.werkst} ,lgort=#{plan.lgort},lgortName=#{plan.lgortName} where fp_hm= #{plan.fphm} and GYSACCOUNT = #{plan.gysaccount} and state=0;" +
                "UPDATE scm_b_gys_mater_pic set werks=#{plan.werks} ,werkt=#{plan.werkst} ,lgort=#{plan.lgort},lgortName=#{plan.lgortName} WHERE CHARGE =#{plan.charge} and GYSACCOUNT =#{plan.gysaccount} and matnr=#{plan.matnr} and state=0;")
        void UpdateWerkAndLgort(@Param("plan") ViewSupplyplan plan);


        List<ScmBSupplyplan> getAllPlansByIds(@Param(value = "ids") List<String> ids);

        int flagRecordByIds(@Param(value = "ids") List<String> ids);

        @Select("select COUNT(1) from Scm_B_Supplyplan where id in (${ids}) and Is_Deletemark=1 and LENGTH(SEND_ORDER_CODE)>0")
        Long hasSendOrder(@Param(value="ids") String ids);//是否有送货清单

        @Select("select COUNT(1) from Scm_B_Supplyplan where id in (${ids}) and Is_Deletemark=1 and donemenge>0")
        Long hasPreDone(@Param(value="ids") String ids);//是否预收

        @Select("SELECT sum(G_MENGE) gMenge,sum(DoneMenge) doneMenge FROM scm_b_supplyplan WHERE scm_b_supplyplan.BASE_ID = #{baseId}  AND scm_b_supplyplan.IS_DELETEMARK = 1")
        StatisticMenge getSupplanMengeByBaseID(@Param(value="baseId") String baseId);
        }
