package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaDXl;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 系列名称 服务类
 * </p>
 *
 * @author viki
 * @since 2020-10-29
 */
public interface IDcaDXlService extends IService<DcaDXl> {

        IPage<DcaDXl> findDcaDXls(QueryRequest request, DcaDXl dcaDXl);

        IPage<DcaDXl> findDcaDXlList(QueryRequest request, DcaDXl dcaDXl);

        void createDcaDXl(DcaDXl dcaDXl);

        void updateDcaDXl(DcaDXl dcaDXl);

        void deleteDcaDXls(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);

        /**
         * 模块树
         * @return
         */
        Map<String, Object> findDepts();

        /**
         * 根据用户ID获取模块
         * @param userId
         * @return
         */
        Map<String, Object> findDeptsByUserId(Long userId);
        }
