package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaBQualification;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 资质情况 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-12-25
 */
public interface DcaBQualificationMapper extends BaseMapper<DcaBQualification> {
        void updateDcaBQualification(DcaBQualification dcaBQualification);
        IPage<DcaBQualification> findDcaBQualification(Page page, @Param("dcaBQualification") DcaBQualification dcaBQualification);

@Update(" update dca_b_qualification set IS_DELETEMARK=0 where user_account=#{useraccount}  and (state=0 or state=2)")
        void deleteByAccount(@Param("useraccount") String useraccount);
@Select(" select IFNULL(max(display_index),0) As maxIndex from  dca_b_qualification  where user_account=#{useraccount} ")
        int getMaxDisplayIndexByuseraccount(@Param("useraccount") String useraccount);
        }
