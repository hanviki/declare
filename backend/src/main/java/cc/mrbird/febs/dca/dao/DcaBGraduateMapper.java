package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaBGraduate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 研究生情况 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-08-11
 */
public interface DcaBGraduateMapper extends BaseMapper<DcaBGraduate> {
        void updateDcaBGraduate(DcaBGraduate dcaBGraduate);
        IPage<DcaBGraduate> findDcaBGraduate(Page page, @Param("dcaBGraduate") DcaBGraduate dcaBGraduate);

@Update(" update dca_b_graduate set IS_DELETEMARK=0 where user_account=#{useraccount}")
        void deleteByAccount(@Param("useraccount") String useraccount);
        }
