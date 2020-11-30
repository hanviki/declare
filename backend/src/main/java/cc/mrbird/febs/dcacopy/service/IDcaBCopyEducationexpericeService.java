package cc.mrbird.febs.dcacopy.service;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyEducationexperice;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 学习工作经历 服务类
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
public interface IDcaBCopyEducationexpericeService extends IService<DcaBCopyEducationexperice> {

        IPage<DcaBCopyEducationexperice> findDcaBCopyEducationexperices(QueryRequest request, DcaBCopyEducationexperice dcaBCopyEducationexperice);

        IPage<DcaBCopyEducationexperice> findDcaBCopyEducationexpericeList(QueryRequest request, DcaBCopyEducationexperice dcaBCopyEducationexperice);

        void createDcaBCopyEducationexperice(DcaBCopyEducationexperice dcaBCopyEducationexperice);

        void updateDcaBCopyEducationexperice(DcaBCopyEducationexperice dcaBCopyEducationexperice);

        void deleteDcaBCopyEducationexperices(String[]Ids);

        List<DcaBCopyEducationexperice> getAll(String userAccount,String dcaYear);
        }
