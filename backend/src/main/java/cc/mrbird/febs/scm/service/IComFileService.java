package cc.mrbird.febs.scm.service;

import cc.mrbird.febs.scm.entity.ComFile;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 附件 服务类
 * </p>
 *
 * @author viki
 * @since 2019-11-14
 */
public interface IComFileService extends IService<ComFile> {

        IPage<ComFile> findComFiles(QueryRequest request, ComFile comFile);

        void createComFile(ComFile comFile);

        void updateComFile(ComFile comFile);

        void deleteComFiles(String[]Ids);
        }
