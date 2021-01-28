package cc.mrbird.febs.check.service;

import cc.mrbird.febs.check.entity.CheckDTitle;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 指标表 服务类
 * </p>
 *
 * @author viki
 * @since 2021-01-22
 */
public interface ICheckDTitleService extends IService<CheckDTitle> {

        IPage<CheckDTitle> findCheckDTitles(QueryRequest request, CheckDTitle checkDTitle);

        IPage<CheckDTitle> findCheckDTitleList(QueryRequest request, CheckDTitle checkDTitle);

        void createCheckDTitle(CheckDTitle checkDTitle);

        void updateCheckDTitle(CheckDTitle checkDTitle);

        void deleteCheckDTitles(String[]Ids);

        List<CheckDTitle> getAll(String userAccount,String dcaYear);
        }
