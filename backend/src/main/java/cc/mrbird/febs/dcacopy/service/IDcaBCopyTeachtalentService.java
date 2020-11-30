package cc.mrbird.febs.dcacopy.service;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyTeachtalent;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 任现职以来完成教学、人才培养情况 服务类
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
public interface IDcaBCopyTeachtalentService extends IService<DcaBCopyTeachtalent> {

        IPage<DcaBCopyTeachtalent> findDcaBCopyTeachtalents(QueryRequest request, DcaBCopyTeachtalent dcaBCopyTeachtalent);

        IPage<DcaBCopyTeachtalent> findDcaBCopyTeachtalentList(QueryRequest request, DcaBCopyTeachtalent dcaBCopyTeachtalent);

        void createDcaBCopyTeachtalent(DcaBCopyTeachtalent dcaBCopyTeachtalent);

        void updateDcaBCopyTeachtalent(DcaBCopyTeachtalent dcaBCopyTeachtalent);

        void deleteDcaBCopyTeachtalents(String[]Ids);

        List<DcaBCopyTeachtalent> getAll(String userAccount,String dcaYear);
        }
