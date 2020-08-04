package cc.mrbird.febs.scm.dao;

import cc.mrbird.febs.scm.entity.ScmBSendinfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 送货单 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2019-11-26
 */
public interface ScmBSendinfoMapper extends BaseMapper<ScmBSendinfo> {
        void updateScmBSendinfo(ScmBSendinfo scmBSendinfo);
        }
