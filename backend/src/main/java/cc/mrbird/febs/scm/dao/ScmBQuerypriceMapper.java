package cc.mrbird.febs.scm.dao;

import cc.mrbird.febs.scm.entity.ScmBQueryprice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Delete;
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
public interface ScmBQuerypriceMapper extends BaseMapper<ScmBQueryprice> {
        void updateScmBQueryprice(ScmBQueryprice scmBQueryprice);

        @Delete("Delete from Scm_b_queryprice_d where base_id=${baseId}")
        void deleteScmBQuerypriceByBaseId(@Param("baseId") Long baseId);

        /**
         * 删除数据
         * @param ids 1,2,3 这样
         */
        @Update("update Scm_b_queryprice set IS_DELETEMARK=0 where id in (${ids}) and query_state=0 ")
        void updateByIds(String ids);

        @Update("update Scm_b_queryprice set query_state=2 where id in (${ids}) and query_state=1 ")
        void stopByIds(String ids);

        @Update("update Scm_b_queryprice set query_state=1 where id in (${ids}) and query_state=2 ")
        void cancleByIds(String ids);

        IPage<ScmBQueryprice> getQueryPriceByGys(Page page, @Param("queryPrice") ScmBQueryprice queryPrice);
        IPage<ScmBQueryprice> getQueryPrice(Page page, @Param("queryPrice") ScmBQueryprice queryPrice);
        }
