package cc.mrbird.febs.scm.dao;

import cc.mrbird.febs.scm.entity.ScmBGysfp;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-07-10
 */
public interface ScmBGysfpMapper extends BaseMapper<ScmBGysfp> {
        void updateScmBGysfp(ScmBGysfp scmBGysfp);
        IPage<ScmBGysfp> findScmBGysfp(Page page, @Param("scmBGysfp") ScmBGysfp scmBGysfp);

        @Select("SELECT count(1) FROM `scm_b_gysfp` WHERE fp_hm=#{fphm} and GYSACCOUNT=#{gys}")
        Integer IsExist( @Param("fphm") String fphm,@Param("gys") String gys);

        @Select("SELECT count(1) FROM `scm_b_gysfp` WHERE fp_hm=#{fphm} and GYSACCOUNT=#{gys} and id!=#{id} ")
        Integer IsExistById( @Param("fphm") String fphm,@Param("gys") String gys,@Param("id") String id);

        }
