package cc.mrbird.febs.check.dao;

import cc.mrbird.febs.check.entity.CheckBSetting;
import cc.mrbird.febs.check.entity.CheckDTitle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 指标配置表 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2021-01-22
 */
public interface CheckBSettingMapper extends BaseMapper<CheckBSetting> {
    void updateCheckBSetting(CheckBSetting checkBSetting);

    IPage<CheckBSetting> findCheckBSetting(Page page, @Param("checkBSetting") CheckBSetting checkBSetting);

    List<CheckDTitle> findTitleByUserAccount(@Param("userAccount") String userAccount);
}
