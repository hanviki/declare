package cc.mrbird.febs.scm.service;

import cc.mrbird.febs.scm.entity.ScmDVendorD;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 供应商对应的资质文件表 服务类
 * </p>
 *
 * @author viki
 * @since 2019-11-14
 */
public interface IScmDVendorDService extends IService<ScmDVendorD> {

        IPage<ScmDVendorD> findScmDVendorDs(QueryRequest request, ScmDVendorD scmDVendorD);

        void createScmDVendorD(ScmDVendorD scmDVendorD);

        void updateScmDVendorD(ScmDVendorD scmDVendorD);

        void deleteScmDVendorDs(String[]Ids);
        List<ScmDVendorD> findScmDVendorDByBaseId(String base_id);
        }
