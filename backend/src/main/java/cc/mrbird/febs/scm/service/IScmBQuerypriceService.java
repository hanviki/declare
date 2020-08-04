package cc.mrbird.febs.scm.service;

import cc.mrbird.febs.scm.entity.ScmBQueryprice;
import cc.mrbird.febs.scm.entity.ScmBQuerypriceD;
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
 * @since 2019-12-26
 */
public interface IScmBQuerypriceService extends IService<ScmBQueryprice> {

        IPage<ScmBQueryprice> findScmBQueryprices(QueryRequest request, ScmBQueryprice scmBQueryprice);

        IPage<ScmBQueryprice> getQueryPriceByGys(QueryRequest request, ScmBQueryprice scmBQueryprice);

        void createScmBQueryprice(ScmBQueryprice scmBQueryprice);

        void updateScmBQueryprice(ScmBQueryprice scmBQueryprice);

        void deleteScmBQueryprices(String[]Ids);

        /**
         * 删除询价  设置为is_deletemark=0
         * @param ids
         */
        void deleteScmBQueryprices(String ids);

        /**
         * 更改状态
         * @param ids
         * @param type stop 结束询价  cancle 取消结束
         */
        void updateQueryState(String ids,String type);

        /**
         * 新增询价信息
         * @param maters  询价主表
         * @param gys     询价子表
         * @param userName
         */
        void createScmBQuerypriceNew(List<ScmBQueryprice> maters, List<ScmBQuerypriceD> gys, Long userid,Long deptId,int state);
        void updateScmBQuerypriceNew(Long baseId, List<ScmBQuerypriceD> gys);
}
