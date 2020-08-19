package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaBInnovatebuild;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 改革及建设项目 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-08-13
 */
public interface DcaBInnovatebuildMapper extends BaseMapper<DcaBInnovatebuild> {
        void updateDcaBInnovatebuild(DcaBInnovatebuild dcaBInnovatebuild);
        IPage<DcaBInnovatebuild> findDcaBInnovatebuild(Page page, @Param("dcaBInnovatebuild") DcaBInnovatebuild dcaBInnovatebuild);

@Update(" update dca_b_innovatebuild set IS_DELETEMARK=0 where user_account=#{useraccount}")
        void deleteByAccount(@Param("useraccount") String useraccount);
        }
