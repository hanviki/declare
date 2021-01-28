package cc.mrbird.febs.doctor.service;

import cc.mrbird.febs.doctor.entity.DcaBDocSchoolprize;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 校教学质量奖、校教学成果奖 服务类
 * </p>
 *
 * @author viki
 * @since 2021-01-11
 */
public interface IDcaBDocSchoolprizeService extends IService<DcaBDocSchoolprize> {

        IPage<DcaBDocSchoolprize> findDcaBDocSchoolprizes(QueryRequest request, DcaBDocSchoolprize dcaBDocSchoolprize);

        IPage<DcaBDocSchoolprize> findDcaBDocSchoolprizeList(QueryRequest request, DcaBDocSchoolprize dcaBDocSchoolprize);

        void createDcaBDocSchoolprize(DcaBDocSchoolprize dcaBDocSchoolprize);

        void updateDcaBDocSchoolprize(DcaBDocSchoolprize dcaBDocSchoolprize);

        void deleteDcaBDocSchoolprizes(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
