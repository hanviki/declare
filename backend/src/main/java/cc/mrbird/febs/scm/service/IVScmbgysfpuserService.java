package cc.mrbird.febs.scm.service;

import cc.mrbird.febs.scm.entity.VScmbgysfpuser;
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
public interface IVScmbgysfpuserService extends IService<VScmbgysfpuser> {

        IPage<VScmbgysfpuser> findVScmbgysfpusers(QueryRequest request, VScmbgysfpuser vScmbgysfpuser,String userid);

        void createVScmbgysfpuser(VScmbgysfpuser vScmbgysfpuser);

        void updateVScmbgysfpuser(VScmbgysfpuser vScmbgysfpuser);

        void deleteVScmbgysfpusers(String[]Ids);
        }
