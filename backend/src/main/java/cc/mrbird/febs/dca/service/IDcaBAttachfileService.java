package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBAttachfile;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 其他附件 服务类
 * </p>
 *
 * @author viki
 * @since 2020-10-20
 */
public interface IDcaBAttachfileService extends IService<DcaBAttachfile> {

        IPage<DcaBAttachfile> findDcaBAttachfiles(QueryRequest request, DcaBAttachfile dcaBAttachfile);

        IPage<DcaBAttachfile> findDcaBAttachfileList(QueryRequest request, DcaBAttachfile dcaBAttachfile);

        void createDcaBAttachfile(DcaBAttachfile dcaBAttachfile);

        void updateDcaBAttachfile(DcaBAttachfile dcaBAttachfile);

        void deleteDcaBAttachfiles(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
