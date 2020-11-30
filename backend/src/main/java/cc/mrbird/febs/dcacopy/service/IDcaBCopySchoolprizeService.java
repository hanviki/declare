package cc.mrbird.febs.dcacopy.service;

import cc.mrbird.febs.dcacopy.entity.DcaBCopySchoolprize;
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
 * @since 2020-11-26
 */
public interface IDcaBCopySchoolprizeService extends IService<DcaBCopySchoolprize> {

        IPage<DcaBCopySchoolprize> findDcaBCopySchoolprizes(QueryRequest request, DcaBCopySchoolprize dcaBCopySchoolprize);

        IPage<DcaBCopySchoolprize> findDcaBCopySchoolprizeList(QueryRequest request, DcaBCopySchoolprize dcaBCopySchoolprize);

        void createDcaBCopySchoolprize(DcaBCopySchoolprize dcaBCopySchoolprize);

        void updateDcaBCopySchoolprize(DcaBCopySchoolprize dcaBCopySchoolprize);

        void deleteDcaBCopySchoolprizes(String[]Ids);

        List<DcaBCopySchoolprize> getAll(String userAccount,String dcaYear);
        }
