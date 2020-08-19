package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBFivecomment;
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
 * @since 2020-08-13
 */
public interface IDcaBFivecommentService extends IService<DcaBFivecomment> {

        IPage<DcaBFivecomment> findDcaBFivecomments(QueryRequest request, DcaBFivecomment dcaBFivecomment);

        IPage<DcaBFivecomment> findDcaBFivecommentList(QueryRequest request, DcaBFivecomment dcaBFivecomment);

        void createDcaBFivecomment(DcaBFivecomment dcaBFivecomment);

        void updateDcaBFivecomment(DcaBFivecomment dcaBFivecomment);

        void deleteDcaBFivecomments(String[]Ids);

        void deleteByuseraccount(String userAccount);
        }
