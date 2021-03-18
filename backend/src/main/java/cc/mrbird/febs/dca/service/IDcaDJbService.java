package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaDJb;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author viki
 * @since 2021-03-09
 */
public interface IDcaDJbService extends IService<DcaDJb> {

        IPage<DcaDJb> findDcaDJbs(QueryRequest request, DcaDJb dcaDJb);

        IPage<DcaDJb> findDcaDJbList(QueryRequest request, DcaDJb dcaDJb);

        void createDcaDJb(DcaDJb dcaDJb);

        void updateDcaDJb(DcaDJb dcaDJb);

        void deleteDcaDJbs(String[]Ids);

        List<DcaDJb> getAll(String userAccount,String dcaYear);
        }
