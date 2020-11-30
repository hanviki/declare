package cc.mrbird.febs.dcacopy.service;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyAttachfile;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 其他附件材料 服务类
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
public interface IDcaBCopyAttachfileService extends IService<DcaBCopyAttachfile> {

        IPage<DcaBCopyAttachfile> findDcaBCopyAttachfiles(QueryRequest request, DcaBCopyAttachfile dcaBCopyAttachfile);

        IPage<DcaBCopyAttachfile> findDcaBCopyAttachfileList(QueryRequest request, DcaBCopyAttachfile dcaBCopyAttachfile);

        void createDcaBCopyAttachfile(DcaBCopyAttachfile dcaBCopyAttachfile);

        void updateDcaBCopyAttachfile(DcaBCopyAttachfile dcaBCopyAttachfile);

        void deleteDcaBCopyAttachfiles(String[]Ids);

        List<DcaBCopyAttachfile> getAll(String userAccount,String dcaYear);
        }
