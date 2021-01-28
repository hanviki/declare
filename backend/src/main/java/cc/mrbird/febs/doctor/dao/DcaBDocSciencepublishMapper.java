package cc.mrbird.febs.doctor.dao;

import cc.mrbird.febs.doctor.entity.DcaBDocSciencepublish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 科研论文 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2021-01-11
 */
public interface DcaBDocSciencepublishMapper extends BaseMapper<DcaBDocSciencepublish> {
        void updateDcaBDocSciencepublish(DcaBDocSciencepublish dcaBDocSciencepublish);
        IPage<DcaBDocSciencepublish> findDcaBDocSciencepublish(Page page, @Param("dcaBDocSciencepublish") DcaBDocSciencepublish dcaBDocSciencepublish);

@Update(" update dca_b_doc_sciencepublish set IS_DELETEMARK=0 where user_account=#{useraccount}  and (state=0 or state=2)")
        void deleteByAccount(@Param("useraccount") String useraccount);
@Select(" select IFNULL(max(display_index),0) As maxIndex from  dca_b_doc_sciencepublish  where user_account=#{useraccount} ")
        int getMaxDisplayIndexByuseraccount(@Param("useraccount") String useraccount);
        }
