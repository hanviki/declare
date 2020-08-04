package cc.mrbird.febs.scm.service;

import cc.mrbird.febs.scm.entity.ScmBSendorder;
import cc.mrbird.febs.scm.entity.ViewSupplyplan;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 药品的送货清单 服务类
 * </p>
 *
 * @author viki
 * @since 2019-11-26
 */
public interface IScmBSendorderService extends IService<ScmBSendorder> {

        IPage<ScmBSendorder> findScmBSendorders(QueryRequest request, ScmBSendorder scmBSendorder);

        IPage<ScmBSendorder> findScmBSendorders_phone(QueryRequest request, ScmBSendorder scmBSendorder);

        void createScmBSendorder(ScmBSendorder scmBSendorder);

        void updateScmBSendorder(ScmBSendorder scmBSendorder);

        void deleteScmBSendorders(String[]Ids);

        List<Long> findPlanIds(String sendCode);

        void updateFpjr(String id);

        IPage<ViewSupplyplan>  findPhoneSendorders(QueryRequest request, ViewSupplyplan viewSupplyplan);
        }
