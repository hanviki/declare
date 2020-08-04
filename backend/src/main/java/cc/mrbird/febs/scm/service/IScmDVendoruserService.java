package cc.mrbird.febs.scm.service;

import cc.mrbird.febs.scm.entity.ScmDVendoruser;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author viki
 * @since 2020-06-03
 */
public interface IScmDVendoruserService extends IService<ScmDVendoruser> {

        IPage<ScmDVendoruser> findScmDVendorusers(QueryRequest request, ScmDVendoruser scmDVendoruser);

        void createScmDVendoruser(ScmDVendoruser scmDVendoruser);

        void updateScmDVendoruser(ScmDVendoruser scmDVendoruser);

        void deleteScmDVendorusers(String[]Ids);

        ScmDVendoruser getUserInfo(String gys);
        }
