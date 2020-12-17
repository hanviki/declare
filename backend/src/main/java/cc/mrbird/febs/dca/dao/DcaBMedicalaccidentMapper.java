package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaBMedicalaccident;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 担任博导硕导 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-12-15
 */
public interface DcaBMedicalaccidentMapper extends BaseMapper<DcaBMedicalaccident> {
        void updateDcaBMedicalaccident(DcaBMedicalaccident dcaBMedicalaccident);
        IPage<DcaBMedicalaccident> findDcaBMedicalaccident(Page page, @Param("dcaBMedicalaccident") DcaBMedicalaccident dcaBMedicalaccident);

@Update(" update dca_b_medicalaccident set IS_DELETEMARK=0 where user_account=#{useraccount}  and (state=0 or state=2)")
        void deleteByAccount(@Param("useraccount") String useraccount);
@Select(" select IFNULL(max(display_index),0) As maxIndex from  dca_b_medicalaccident  where user_account=#{useraccount} ")
        int getMaxDisplayIndexByuseraccount(@Param("useraccount") String useraccount);
        }
