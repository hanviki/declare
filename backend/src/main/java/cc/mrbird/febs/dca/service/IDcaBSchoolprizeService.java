package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBSchoolprize;
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
 * @since 2020-11-03
 */
public interface IDcaBSchoolprizeService extends IService<DcaBSchoolprize> {

        IPage<DcaBSchoolprize> findDcaBSchoolprizes(QueryRequest request, DcaBSchoolprize dcaBSchoolprize);

        IPage<DcaBSchoolprize> findDcaBSchoolprizeList(QueryRequest request, DcaBSchoolprize dcaBSchoolprize);

        void createDcaBSchoolprize(DcaBSchoolprize dcaBSchoolprize);

        void updateDcaBSchoolprize(DcaBSchoolprize dcaBSchoolprize);

        void deleteDcaBSchoolprizes(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
