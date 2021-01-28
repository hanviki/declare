package cc.mrbird.febs.doctor.service;

import cc.mrbird.febs.doctor.entity.DcaBDocAttachfile;
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
 * @since 2021-01-11
 */
public interface IDcaBDocAttachfileService extends IService<DcaBDocAttachfile> {

        IPage<DcaBDocAttachfile> findDcaBDocAttachfiles(QueryRequest request, DcaBDocAttachfile dcaBDocAttachfile);

        IPage<DcaBDocAttachfile> findDcaBDocAttachfileList(QueryRequest request, DcaBDocAttachfile dcaBDocAttachfile);

        void createDcaBDocAttachfile(DcaBDocAttachfile dcaBDocAttachfile);

        void updateDcaBDocAttachfile(DcaBDocAttachfile dcaBDocAttachfile);

        void deleteDcaBDocAttachfiles(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
