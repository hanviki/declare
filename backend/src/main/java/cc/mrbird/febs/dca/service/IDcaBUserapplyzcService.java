package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBUserapplyzc;
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
 * @since 2020-12-22
 */
public interface IDcaBUserapplyzcService extends IService<DcaBUserapplyzc> {

        IPage<DcaBUserapplyzc> findDcaBUserapplyzcs(QueryRequest request, DcaBUserapplyzc dcaBUserapplyzc);

        IPage<DcaBUserapplyzc> findDcaBUserapplyzcList(QueryRequest request, DcaBUserapplyzc dcaBUserapplyzc);

        void createDcaBUserapplyzc(DcaBUserapplyzc dcaBUserapplyzc);

        void updateDcaBUserapplyzc(DcaBUserapplyzc dcaBUserapplyzc);

        void deleteDcaBUserapplyzcs(String[]Ids);

        boolean IsExistApply(DcaBUserapplyzc dcaBUserapply);



        List<DcaBUserapplyzc> getAll(String userAccount,String dcaYear);
        }
