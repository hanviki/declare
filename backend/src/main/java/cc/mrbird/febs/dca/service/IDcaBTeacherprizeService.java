package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBTeacherprize;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 任现职以来教学获奖 服务类
 * </p>
 *
 * @author viki
 * @since 2020-11-03
 */
public interface IDcaBTeacherprizeService extends IService<DcaBTeacherprize> {

        IPage<DcaBTeacherprize> findDcaBTeacherprizes(QueryRequest request, DcaBTeacherprize dcaBTeacherprize);

        IPage<DcaBTeacherprize> findDcaBTeacherprizeList(QueryRequest request, DcaBTeacherprize dcaBTeacherprize);

        void createDcaBTeacherprize(DcaBTeacherprize dcaBTeacherprize);

        void updateDcaBTeacherprize(DcaBTeacherprize dcaBTeacherprize);

        void deleteDcaBTeacherprizes(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
