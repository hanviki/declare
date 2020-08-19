package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaBParttimejob;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 社会兼职表 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-08-04
 */
public interface DcaBParttimejobMapper extends BaseMapper<DcaBParttimejob> {
        void updateDcaBParttimejob(DcaBParttimejob dcaBParttimejob);
        IPage<DcaBParttimejob> findDcaBParttimejob(Page page, @Param("dcaBParttimejob") DcaBParttimejob dcaBParttimejob);

        @Update(" update dca_b_parttimejob set IS_DELETEMARK=0 where user_account=#{useraccount}")
        void deleteByAccount(@Param("useraccount") String useraccount);
        }
