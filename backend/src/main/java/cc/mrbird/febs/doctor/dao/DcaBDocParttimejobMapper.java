package cc.mrbird.febs.doctor.dao;

import cc.mrbird.febs.doctor.entity.DcaBDocParttimejob;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 社会兼职表 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2021-01-11
 */
public interface DcaBDocParttimejobMapper extends BaseMapper<DcaBDocParttimejob> {
        void updateDcaBDocParttimejob(DcaBDocParttimejob dcaBDocParttimejob);
        IPage<DcaBDocParttimejob> findDcaBDocParttimejob(Page page, @Param("dcaBDocParttimejob") DcaBDocParttimejob dcaBDocParttimejob);

@Update(" update dca_b_doc_parttimejob set IS_DELETEMARK=0 where user_account=#{useraccount}  and (state=0 or state=2)")
        void deleteByAccount(@Param("useraccount") String useraccount);
@Select(" select IFNULL(max(display_index),0) As maxIndex from  dca_b_doc_parttimejob  where user_account=#{useraccount} ")
        int getMaxDisplayIndexByuseraccount(@Param("useraccount") String useraccount);
        }
