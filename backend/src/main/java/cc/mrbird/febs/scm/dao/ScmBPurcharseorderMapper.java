package cc.mrbird.febs.scm.dao;

import cc.mrbird.febs.scm.entity.ScmBPurcharseorder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * SCM_B_PURCHARSEORDER Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2019-11-21
 */
public interface ScmBPurcharseorderMapper extends BaseMapper<ScmBPurcharseorder> {
        void updateScmBPurcharseorder(ScmBPurcharseorder scmBPurcharseorder);
        IPage<ScmBPurcharseorder> findPurcharseorder(Page page, @Param("order") ScmBPurcharseorder order);

       List<ScmBPurcharseorder> getAllByIds(@Param("ids") List<String> ids);

       ScmBPurcharseorder findPurcharseorderById(@Param("id") String id);
        }
