package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaDAuditinfo;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author viki
 * @since 2020-10-23
 */
public interface IDcaDAuditinfoService extends IService<DcaDAuditinfo> {

        IPage<DcaDAuditinfo> findDcaDAuditinfos(QueryRequest request, DcaDAuditinfo dcaDAuditinfo);

        IPage<DcaDAuditinfo> findDcaDAuditinfoList(QueryRequest request, DcaDAuditinfo dcaDAuditinfo);

        void createDcaDAuditinfo(DcaDAuditinfo dcaDAuditinfo);

        void updateDcaDAuditinfo(DcaDAuditinfo dcaDAuditinfo);

        void deleteDcaDAuditinfos(String[]Ids);

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
