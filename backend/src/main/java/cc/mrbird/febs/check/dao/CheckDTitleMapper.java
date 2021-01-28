package cc.mrbird.febs.check.dao;

import cc.mrbird.febs.check.entity.CheckDTitle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 指标表 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2021-01-22
 */
public interface CheckDTitleMapper extends BaseMapper<CheckDTitle> {
        void updateCheckDTitle(CheckDTitle checkDTitle);
        IPage<CheckDTitle> findCheckDTitle(Page page, @Param("checkDTitle") CheckDTitle checkDTitle);
        }
