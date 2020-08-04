package cc.mrbird.febs.scm.dao;

import cc.mrbird.febs.scm.entity.ScmBUserandarea;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 用户表和院区表配置表 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2019-11-13
 */
public interface ScmBUserandareaMapper extends BaseMapper<ScmBUserandarea> {
        void updateScmBUserandarea(ScmBUserandarea scmBUserandarea);
        }
