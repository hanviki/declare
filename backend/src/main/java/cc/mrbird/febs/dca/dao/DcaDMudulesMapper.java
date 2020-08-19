package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaDMudules;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 模块表名称 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-08-10
 */
public interface DcaDMudulesMapper extends BaseMapper<DcaDMudules> {
        void updateDcaDMudules(DcaDMudules dcaDMudules);
        IPage<DcaDMudules> findDcaDMudules(Page page, @Param("dcaDMudules") DcaDMudules dcaDMudules);

        @Select("select *  from dca_d_mudules where id in (SELECT mudule_id from dca_user_moudules where userid=#{userid})")
        List<DcaDMudules> findMoudesByUserId(@Param("userid") String userid);
        }
