package cc.mrbird.febs.scm.dao;

import cc.mrbird.febs.scm.entity.ScmDSenddepart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 物资送货部门 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2019-11-26
 */
public interface ScmDSenddepartMapper extends BaseMapper<ScmDSenddepart> {
        void updateScmDSenddepart(ScmDSenddepart scmDSenddepart);
        }
