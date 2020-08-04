package cc.mrbird.febs.scm.dao;

import cc.mrbird.febs.scm.entity.ScmBGysmatersend;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-06-03
 */
public interface ScmBGysmatersendMapper extends BaseMapper<ScmBGysmatersend> {
        void updateScmBGysmatersend(ScmBGysmatersend scmBGysmatersend);
        }
