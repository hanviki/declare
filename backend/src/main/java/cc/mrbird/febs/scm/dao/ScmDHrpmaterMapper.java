package cc.mrbird.febs.scm.dao;

import cc.mrbird.febs.scm.entity.ScmDHrpmater;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * SAP的药品字典库，这是关联供应商的 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2019-11-26
 */
public interface ScmDHrpmaterMapper extends BaseMapper<ScmDHrpmater> {
        void updateScmDHrpmater(ScmDHrpmater scmDHrpmater);
        }
