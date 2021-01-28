package cc.mrbird.febs.out.service;

import cc.mrbird.febs.out.entity.OutBInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 接口投票 服务类
 * </p>
 *
 * @author viki
 * @since 2020-12-01
 */
public interface IOutBInfoService extends IService<OutBInfo> {

        IPage<OutBInfo> findOutBInfos(QueryRequest request, OutBInfo outBInfo);

        IPage<OutBInfo> findOutBInfoList(QueryRequest request, OutBInfo outBInfo);

        void createOutBInfo(OutBInfo outBInfo);

        void updateOutBInfo(OutBInfo outBInfo);

        void deleteOutBInfos(String[]Ids);

        List<OutBInfo> getAll(String dcayear,String tpzb,String dyzc,String tpbt);
        }
