package cc.mrbird.febs.dcacopy.service;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyPatent;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 任现职以来申请专利情况 服务类
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
public interface IDcaBCopyPatentService extends IService<DcaBCopyPatent> {

        IPage<DcaBCopyPatent> findDcaBCopyPatents(QueryRequest request, DcaBCopyPatent dcaBCopyPatent);

        IPage<DcaBCopyPatent> findDcaBCopyPatentList(QueryRequest request, DcaBCopyPatent dcaBCopyPatent);

        void createDcaBCopyPatent(DcaBCopyPatent dcaBCopyPatent);

        void updateDcaBCopyPatent(DcaBCopyPatent dcaBCopyPatent);

        void deleteDcaBCopyPatents(String[]Ids);

        List<DcaBCopyPatent> getAll(String userAccount,String dcaYear);
        }
