package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaBTeacherqualify;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 教师资格 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-08-11
 */
public interface DcaBTeacherqualifyMapper extends BaseMapper<DcaBTeacherqualify> {
        void updateDcaBTeacherqualify(DcaBTeacherqualify dcaBTeacherqualify);
        IPage<DcaBTeacherqualify> findDcaBTeacherqualify(Page page, @Param("dcaBTeacherqualify") DcaBTeacherqualify dcaBTeacherqualify);

@Update(" update dca_b_teacherqualify set IS_DELETEMARK=0 where user_account=#{useraccount}")
        void deleteByAccount(@Param("useraccount") String useraccount);
        }
