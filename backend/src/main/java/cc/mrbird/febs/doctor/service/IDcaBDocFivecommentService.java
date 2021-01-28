package cc.mrbird.febs.doctor.service;

import cc.mrbird.febs.doctor.entity.DcaBDocFivecomment;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 近五年总体项目评价 服务类
 * </p>
 *
 * @author viki
 * @since 2021-01-11
 */
public interface IDcaBDocFivecommentService extends IService<DcaBDocFivecomment> {

        IPage<DcaBDocFivecomment> findDcaBDocFivecomments(QueryRequest request, DcaBDocFivecomment dcaBDocFivecomment);

        IPage<DcaBDocFivecomment> findDcaBDocFivecommentList(QueryRequest request, DcaBDocFivecomment dcaBDocFivecomment);

        void createDcaBDocFivecomment(DcaBDocFivecomment dcaBDocFivecomment);

        void updateDcaBDocFivecomment(DcaBDocFivecomment dcaBDocFivecomment);

        void deleteDcaBDocFivecomments(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
