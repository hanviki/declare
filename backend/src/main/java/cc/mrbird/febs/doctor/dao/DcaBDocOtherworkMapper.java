package cc.mrbird.febs.doctor.dao;

import cc.mrbird.febs.doctor.entity.DcaBDocOtherwork;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 其他工作及成果，拟聘岗位工作思路及预期目标，个人总结 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2021-01-11
 */
public interface DcaBDocOtherworkMapper extends BaseMapper<DcaBDocOtherwork> {
        void updateDcaBDocOtherwork(DcaBDocOtherwork dcaBDocOtherwork);
        IPage<DcaBDocOtherwork> findDcaBDocOtherwork(Page page, @Param("dcaBDocOtherwork") DcaBDocOtherwork dcaBDocOtherwork);

@Update(" update dca_b_doc_otherwork set IS_DELETEMARK=0 where user_account=#{useraccount}  and (state=0 or state=2)")
        void deleteByAccount(@Param("useraccount") String useraccount);
@Select(" select IFNULL(max(display_index),0) As maxIndex from  dca_b_doc_otherwork  where user_account=#{useraccount} ")
        int getMaxDisplayIndexByuseraccount(@Param("useraccount") String useraccount);
        }
