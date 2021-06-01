package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaBWorker;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 协和医院合同制职工信息确认表 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2021-05-24
 */
public interface DcaBWorkerMapper extends BaseMapper<DcaBWorker> {
        void updateDcaBWorker(DcaBWorker dcaBWorker);
        IPage<DcaBWorker> findDcaBWorker(Page page, @Param("dcaBWorker") DcaBWorker dcaBWorker);
        }
