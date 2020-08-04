package cc.mrbird.febs.scm.service;

import cc.mrbird.febs.scm.entity.VScmBGyspicUser;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * VIEW 服务类
 * </p>
 *
 * @author viki
 * @since 2020-07-13
 */
public interface IVScmBGyspicUserService extends IService<VScmBGyspicUser> {

        IPage<VScmBGyspicUser> findVScmBGyspicUsers(QueryRequest request, VScmBGyspicUser vScmBGyspicUser, String keyword_mater, String keyword_gys,String userid);

        void createVScmBGyspicUser(VScmBGyspicUser vScmBGyspicUser);

        void updateVScmBGyspicUser(VScmBGyspicUser vScmBGyspicUser);

        void deleteVScmBGyspicUsers(String[]Ids);
        }
