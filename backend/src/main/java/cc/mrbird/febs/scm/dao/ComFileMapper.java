package cc.mrbird.febs.scm.dao;

import cc.mrbird.febs.scm.entity.ComFile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 附件 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2019-11-14
 */
public interface ComFileMapper extends BaseMapper<ComFile> {
        void updateComFile(ComFile comFile);
        }
