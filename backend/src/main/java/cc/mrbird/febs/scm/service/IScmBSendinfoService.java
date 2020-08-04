package cc.mrbird.febs.scm.service;

import cc.mrbird.febs.scm.entity.ScmBSendinfo;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 送货单 服务类
 * </p>
 *
 * @author viki
 * @since 2019-11-26
 */
public interface IScmBSendinfoService extends IService<ScmBSendinfo> {

        IPage<ScmBSendinfo> findScmBSendinfos(QueryRequest request, ScmBSendinfo scmBSendinfo);

        void createScmBSendinfo(ScmBSendinfo scmBSendinfo);

        void updateScmBSendinfo(ScmBSendinfo scmBSendinfo);

        void deleteScmBSendinfos(String[]Ids);
        }
