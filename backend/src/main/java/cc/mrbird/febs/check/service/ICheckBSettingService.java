package cc.mrbird.febs.check.service;

import cc.mrbird.febs.check.entity.CheckBSetting;
import cc.mrbird.febs.check.entity.CheckDTitle;
import cc.mrbird.febs.check.entity.CheckShowTitle;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 指标配置表 服务类
 * </p>
 *
 * @author viki
 * @since 2021-01-22
 */
public interface ICheckBSettingService extends IService<CheckBSetting> {

        IPage<CheckBSetting> findCheckBSettings(QueryRequest request, CheckBSetting checkBSetting);

        IPage<CheckBSetting> findCheckBSettingList(QueryRequest request, CheckBSetting checkBSetting);

        void createCheckBSetting(CheckBSetting checkBSetting);

        void updateCheckBSetting(CheckBSetting checkBSetting);

        void deleteCheckBSettings(String[]Ids);

        List<CheckBSetting> getAll(String userAccount,String dcaYear);

        List<CheckDTitle> getTitleByUserAccount(String userAccount);

        List<CheckShowTitle> findAllTitle();
        }
