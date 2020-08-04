package cc.mrbird.febs.scm.dao;

import cc.mrbird.febs.scm.entity.ScmDMater;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 药品物料库 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2019-11-11
 */
public interface ScmDMaterMapper extends BaseMapper<ScmDMater> {
        void updateScmDMater(ScmDMater scmDMater);
        }
