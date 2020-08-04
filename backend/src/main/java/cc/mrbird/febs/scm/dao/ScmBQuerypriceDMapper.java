package cc.mrbird.febs.scm.dao;

import cc.mrbird.febs.scm.entity.ScmBQuerypriceD;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2019-12-26
 */
public interface ScmBQuerypriceDMapper extends BaseMapper<ScmBQuerypriceD> {
        void updateScmBQuerypriceD(ScmBQuerypriceD scmBQuerypriceD);


        @Update("update scm_b_queryprice_d set gysstate=1 where gysaccount=#{gysaccout} and base_id=#{baseId}")
        void updateScmBQuertpriceSate(@Param("gysaccout") String gysaccout,@Param("baseId") Long baseId);
        }
