package cc.mrbird.febs.check.service;

import cc.mrbird.febs.check.entity.CheckBAuditresult;
import cc.mrbird.febs.check.entity.TotalResultNum;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 指标结果表 服务类
 * </p>
 *
 * @author viki
 * @since 2021-01-22
 */
public interface ICheckBAuditresultService extends IService<CheckBAuditresult> {

        IPage<CheckBAuditresult> findCheckBAuditresults(QueryRequest request, CheckBAuditresult checkBAuditresult);

        IPage<CheckBAuditresult> findCheckBAuditresultList(QueryRequest request, CheckBAuditresult checkBAuditresult);

        void createCheckBAuditresult(CheckBAuditresult checkBAuditresult);

        void updateCheckBAuditresult(CheckBAuditresult checkBAuditresult);

        void deleteCheckBAuditresults(String[]Ids);

        List<CheckBAuditresult> getAll(String userAccount,String dcaYear);
        void deleteBy(List<String> accounts,List<String> dataIndexList,String userName,String dcaYear);
        List<TotalResultNum> findTotalResult(CheckBAuditresult checkBAuditresult);
        List<TotalResultNum> findStrResult(CheckBAuditresult checkBAuditresult);
        }
