package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBTeachtalent;
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
 * @since 2020-09-27
 */
public interface IDcaBTeachtalentService extends IService<DcaBTeachtalent> {

        IPage<DcaBTeachtalent> findDcaBTeachtalents(QueryRequest request, DcaBTeachtalent dcaBTeachtalent);

        IPage<DcaBTeachtalent> findDcaBTeachtalentList(QueryRequest request, DcaBTeachtalent dcaBTeachtalent);

        void createDcaBTeachtalent(DcaBTeachtalent dcaBTeachtalent);

        void updateDcaBTeachtalent(DcaBTeachtalent dcaBTeachtalent);

        void deleteDcaBTeachtalents(String[]Ids);

        void deleteByuseraccount(String userAccount);
        }
