package cc.mrbird.febs.scm.service;

import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.scm.entity.ScmBGysMaterPic;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 资质文件记录表 服务类
 * </p>
 *
 * @author viki
 * @since 2019-11-21
 */
public interface IScmBGysMaterPicService extends IService<ScmBGysMaterPic> {

        IPage<ScmBGysMaterPic> findScmBGysMaterPics(QueryRequest request, ScmBGysMaterPic scmBGysMaterPic, String keyword_mater, String keyword_gys);

        IPage<ScmBGysMaterPic> findScmBGysMaterPicsAudit(QueryRequest request, ScmBGysMaterPic scmBGysMaterPic, String keyword_mater, String keyword_gys, String userid );

        void createScmBGysMaterPic(ScmBGysMaterPic scmBGysMaterPic) throws FebsException ;

        void updateScmBGysMaterPic(ScmBGysMaterPic scmBGysMaterPic) throws FebsException;

        void auditScmBGysMaterPic(ScmBGysMaterPic scmBGysMaterPic);

        void deleteScmBGysMaterPics(String[]Ids,int state) throws FebsException;

        List<String> findChargeByBaseId(String base_Id,String account);

        /*
        是否可以删除验收资质
         */
        Boolean IsDelete(String id);
        }
