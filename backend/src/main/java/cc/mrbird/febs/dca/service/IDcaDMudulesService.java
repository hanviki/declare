package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaDMudules;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 模块表名称 服务类
 * </p>
 *
 * @author viki
 * @since 2020-08-10
 */
public interface IDcaDMudulesService extends IService<DcaDMudules> {

        IPage<DcaDMudules> findDcaDMuduless(QueryRequest request, DcaDMudules dcaDMudules);

        IPage<DcaDMudules> findDcaDMudulesList(QueryRequest request, DcaDMudules dcaDMudules);

        void createDcaDMudules(DcaDMudules dcaDMudules);

        void updateDcaDMudules(DcaDMudules dcaDMudules);

        void deleteDcaDMuduless(String[]Ids);

        /**
         * 模块树
         * @return
         */
        Map<String, Object> findDepts();
        }
