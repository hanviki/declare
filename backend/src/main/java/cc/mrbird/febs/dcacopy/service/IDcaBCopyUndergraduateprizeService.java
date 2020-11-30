package cc.mrbird.febs.dcacopy.service;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyUndergraduateprize;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 任现职以来本科教学工作获奖情况(教师系列需填写) 服务类
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
public interface IDcaBCopyUndergraduateprizeService extends IService<DcaBCopyUndergraduateprize> {

        IPage<DcaBCopyUndergraduateprize> findDcaBCopyUndergraduateprizes(QueryRequest request, DcaBCopyUndergraduateprize dcaBCopyUndergraduateprize);

        IPage<DcaBCopyUndergraduateprize> findDcaBCopyUndergraduateprizeList(QueryRequest request, DcaBCopyUndergraduateprize dcaBCopyUndergraduateprize);

        void createDcaBCopyUndergraduateprize(DcaBCopyUndergraduateprize dcaBCopyUndergraduateprize);

        void updateDcaBCopyUndergraduateprize(DcaBCopyUndergraduateprize dcaBCopyUndergraduateprize);

        void deleteDcaBCopyUndergraduateprizes(String[]Ids);

        List<DcaBCopyUndergraduateprize> getAll(String userAccount,String dcaYear);
        }
