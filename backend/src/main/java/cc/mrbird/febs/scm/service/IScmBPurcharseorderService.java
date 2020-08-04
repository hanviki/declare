package cc.mrbird.febs.scm.service;

import cc.mrbird.febs.scm.entity.ScmBPurcharseorder;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * SCM_B_PURCHARSEORDER 服务类
 * </p>
 *
 * @author viki
 * @since 2019-11-21
 */
public interface IScmBPurcharseorderService extends IService<ScmBPurcharseorder> {

        IPage<ScmBPurcharseorder> findScmBPurcharseorders(QueryRequest request, ScmBPurcharseorder scmBPurcharseorder);

        void createScmBPurcharseorder(ScmBPurcharseorder scmBPurcharseorder);

        void updateScmBPurcharseorder(ScmBPurcharseorder scmBPurcharseorder);

        void deleteScmBPurcharseorders(String[]Ids);

        ScmBPurcharseorder getOrderById(String id);

        }
