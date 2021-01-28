package cc.mrbird.febs.doctor.service;

import cc.mrbird.febs.doctor.entity.DcaBDocUndergraduateprize;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 任现职以来本科教学工作获奖情况 服务类
 * </p>
 *
 * @author viki
 * @since 2021-01-11
 */
public interface IDcaBDocUndergraduateprizeService extends IService<DcaBDocUndergraduateprize> {

        IPage<DcaBDocUndergraduateprize> findDcaBDocUndergraduateprizes(QueryRequest request, DcaBDocUndergraduateprize dcaBDocUndergraduateprize);

        IPage<DcaBDocUndergraduateprize> findDcaBDocUndergraduateprizeList(QueryRequest request, DcaBDocUndergraduateprize dcaBDocUndergraduateprize);

        void createDcaBDocUndergraduateprize(DcaBDocUndergraduateprize dcaBDocUndergraduateprize);

        void updateDcaBDocUndergraduateprize(DcaBDocUndergraduateprize dcaBDocUndergraduateprize);

        void deleteDcaBDocUndergraduateprizes(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
