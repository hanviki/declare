package cc.mrbird.febs.scm.dao;

import cc.mrbird.febs.scm.entity.ScmBQuotedprice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2019-12-27
 */
public interface ScmBQuotedpriceMapper extends BaseMapper<ScmBQuotedprice> {
        void updateScmBQuotedprice(ScmBQuotedprice scmBQuotedprice);

        @Update("update scm_b_quotedprice set IS_DELETEMARK=0 where base_id=#{baseid} and state=0")
        void deleteScmBQuotedprice(@Param("baseid") String baseid);
        }
