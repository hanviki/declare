package cc.mrbird.febs.dcacopy.service;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyFivecomment;
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
 * @since 2020-11-26
 */
public interface IDcaBCopyFivecommentService extends IService<DcaBCopyFivecomment> {

        IPage<DcaBCopyFivecomment> findDcaBCopyFivecomments(QueryRequest request, DcaBCopyFivecomment dcaBCopyFivecomment);

        IPage<DcaBCopyFivecomment> findDcaBCopyFivecommentList(QueryRequest request, DcaBCopyFivecomment dcaBCopyFivecomment);

        void createDcaBCopyFivecomment(DcaBCopyFivecomment dcaBCopyFivecomment);

        void updateDcaBCopyFivecomment(DcaBCopyFivecomment dcaBCopyFivecomment);

        void deleteDcaBCopyFivecomments(String[]Ids);

        List<DcaBCopyFivecomment> getAll(String userAccount,String dcaYear);
        }
