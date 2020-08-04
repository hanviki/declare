package cc.mrbird.febs.scm.dao;

import cc.mrbird.febs.scm.entity.ScmDArea;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 药房院区表 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2019-11-11
 */
public interface ScmDAreaMapper extends BaseMapper<ScmDArea> {
        void updateScmDArea(ScmDArea scmDArea);

        @Select("SELECT *  from scm_d_area where ID in (SELECT AreaID from scm_b_userandarea where UserID=${userid})")
        List<ScmDArea>  GetAreaByUserId(@Param(value="userid") Long userid);
        @Select("SELECT *  from scm_d_area")
        List<ScmDArea>  GetAreaAll();
        }
