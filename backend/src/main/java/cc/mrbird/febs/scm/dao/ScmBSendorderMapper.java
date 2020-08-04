package cc.mrbird.febs.scm.dao;

import cc.mrbird.febs.scm.entity.ScmBSendorder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.hibernate.validator.constraints.EAN;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 药品的送货清单 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2019-11-26
 */
public interface ScmBSendorderMapper extends BaseMapper<ScmBSendorder> {
        void updateScmBSendorder(ScmBSendorder scmBSendorder);

        @Update("UPDATE scm_b_sendorder set IS_DELETEMARK=0 where ID=#{lid};UPDATE scm_b_supplyplan SET SEND_ORDER_CODE = '',FPHM = '' WHERE SEND_ORDER_CODE=#{id} and bsart_d='1' and status=0 ")
        void updateDeleteOrder(@Param("id") String id,@Param("lid") Long lid);

        void updateSupplyPlan(@Param("ids") List<Long> ids,@Param("id") String id,@Param("fphm") String fphm,@Param("fprq") String fprq);

        void updateSupplyPlan2(@Param("ids") List<Long> ids,@Param("id") String id);
        IPage<ScmBSendorder> findSendInfos(Page page, @Param("order") ScmBSendorder order);
        IPage<ScmBSendorder> findSendInfos2(Page page, @Param("order") ScmBSendorder order);
        @Update("UPDATE scm_b_supplyplan SET SEND_ORDER_CODE = '',FPHM = '' WHERE SEND_ORDER_CODE=#{ids}) and bsart_d='1' and status=0 ")
        int removeMaterOrderCode(@Param("ids") String ids);

        /*
        已经收获都不可以更改
         */
        @Update("UPDATE scm_b_supplyplan SET SEND_ORDER_CODE = '' WHERE SEND_ORDER_CODE =#{ids} and bsart_d='0' and status=0 ")
        int removeOrderCode(@Param("ids") String ids);

        @Select("Select id from scm_b_supplyplan where SEND_ORDER_CODE=#{sendCode}")
        List<Long> findPlanIds(@Param("sendCode") String sendCode);

        @Update("update scm_b_sendorder set fpjr=( SELECT SUM(FPJR) FROM scm_b_supplyplan WHERE SEND_ORDER_CODE = #{id}) where id= ${id}")
         void UpdateSendOrderFpjr(@Param("id") String id );


        }
