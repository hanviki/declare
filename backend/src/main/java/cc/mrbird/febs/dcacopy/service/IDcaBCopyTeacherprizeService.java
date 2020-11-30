package cc.mrbird.febs.dcacopy.service;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyTeacherprize;
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
 * @since 2020-11-26
 */
public interface IDcaBCopyTeacherprizeService extends IService<DcaBCopyTeacherprize> {

        IPage<DcaBCopyTeacherprize> findDcaBCopyTeacherprizes(QueryRequest request, DcaBCopyTeacherprize dcaBCopyTeacherprize);

        IPage<DcaBCopyTeacherprize> findDcaBCopyTeacherprizeList(QueryRequest request, DcaBCopyTeacherprize dcaBCopyTeacherprize);

        void createDcaBCopyTeacherprize(DcaBCopyTeacherprize dcaBCopyTeacherprize);

        void updateDcaBCopyTeacherprize(DcaBCopyTeacherprize dcaBCopyTeacherprize);

        void deleteDcaBCopyTeacherprizes(String[]Ids);

        List<DcaBCopyTeacherprize> getAll(String userAccount,String dcaYear);
        }
