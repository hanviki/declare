package cc.mrbird.febs.check.dao;

import cc.mrbird.febs.check.entity.CheckBSetting;
import cc.mrbird.febs.check.entity.CheckDTitle;
import cc.mrbird.febs.check.entity.CheckShowTitle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

    @Select("SELECT DISTINCT\n" +
            "CONCAT(audit_titletype, title_id) filed_name,\n" +
            "\tCONCAT(ks, '_', audit_title) filed_title,\n" +
            "\tks,\n" +
            "\taudit_titletype,\n" +
            "  lb,\n" +
            " user_account\n" +
            "FROM\n" +
            "\tcheck_b_setting\n" +
            "UNION all\n" +
            "\tSELECT\n" +
            "\t\tfiled_name,\n" +
            "\t\tfiled_title,\n" +
            "\t\t'' ks,\n" +
            "\t\t filed_name audit_titletype,\n" +
            "    check_person lb,\n" +
            "     '' user_account\n" +
            "\tFROM\n" +
            "\t\tcheck_d_title\n" +
            "\tWHERE\n" +
            "\t\tcheck_person IN ('2', '3')")
    List<CheckShowTitle> findAllTitle();
}
