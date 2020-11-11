package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaBInnovatebuild;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 任现职以来承担的本科教学改革及建设项目(教师系列需填写) Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-11-06
 */
public interface DcaBInnovatebuildMapper extends BaseMapper<DcaBInnovatebuild> {
        void updateDcaBInnovatebuild(DcaBInnovatebuild dcaBInnovatebuild);
        IPage<DcaBInnovatebuild> findDcaBInnovatebuild(Page page, @Param("dcaBInnovatebuild") DcaBInnovatebuild dcaBInnovatebuild);

@Update(" update dca_b_innovatebuild set IS_DELETEMARK=0 where user_account=#{useraccount}  and (state=0 or state=2)")
        void deleteByAccount(@Param("useraccount") String useraccount);
@Select(" select IFNULL(max(display_index),0) As maxIndex from  dca_b_innovatebuild  where user_account=#{useraccount} ")
        int getMaxDisplayIndexByuseraccount(@Param("useraccount") String useraccount);
        }
