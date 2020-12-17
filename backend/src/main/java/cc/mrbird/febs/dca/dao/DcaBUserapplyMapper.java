package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaBUserapply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-11-05
 */
public interface DcaBUserapplyMapper extends BaseMapper<DcaBUserapply> {
        void updateDcaBUserapply(DcaBUserapply dcaBUserapply);
        IPage<DcaBUserapply> findDcaBUserapply(Page page, @Param("dcaBUserapply") DcaBUserapply dcaBUserapply);
        @Select("SELECT DISTINCT\n" +
                "\tuser_account \n" +
                "FROM\n" +
                "\tdca_b_userapply \n" +
                "WHERE\n" +
                "\tIF(#{dcaYear} = '', '1=1', dca_year = #{dcaYear})  \n" +
                "\t AND state=1 \n" +
                "\tAND gwdj IN (\n" +
                "\t'正高',\n" +
                "\t'副高' \n" +
                "\t)")
        List<String> GetGj(String dcaYear);
        @Select("SELECT DISTINCT\n" +
                "\tuser_account \n" +
                "FROM\n" +
                "\tdca_b_userapply \n" +
                "WHERE\n" +
                "\tIF(#{dcaYear} = '', '1=1', dca_year = #{dcaYear})  \n" +
                "\t AND state=1 \n" +
                "\tAND gwdj IN (\n" +
                "\t'中级',\n" +
                "\t'初级' \n" +
                "\t)")
        List<String> GetZj(String dcaYear);
        @Select("SELECT DISTINCT\n" +
                "\tuser_account \n" +
                "FROM\n" +
                "\tdca_b_userapply \n" +
                "WHERE\n" +
                "\tIF(#{dcaYear} = '', '1=1', dca_year = #{dcaYear})  \n" +
                "\t AND state=1 \n" +
                "\tAND gwdj IN (\n" +
                "\t'二三级' \n" +
                "\t)")
        List<String> GetDj(String dcaYear);
        }
