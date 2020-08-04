package cc.mrbird.febs.scm.dao;

import cc.mrbird.febs.scm.entity.ComFile;
import cc.mrbird.febs.scm.entity.ScmDVendoruser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-06-03
 */
public interface ScmDVendoruserMapper extends BaseMapper<ScmDVendoruser> {
        void updateScmDVendoruser(ScmDVendoruser scmDVendoruser);

        @Select("select * from scm_d_vendoruser where BASE_ID in (SELECT ID from scm_d_vendor where code=#{gys})")
        ScmDVendoruser getVendorusers (@Param("gys") String gys);

        }
