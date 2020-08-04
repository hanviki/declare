package cc.mrbird.febs.scm.service;

import cc.mrbird.febs.scm.entity.VMsgPlanundo;
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
 * @since 2020-06-16
 */
public interface IVMsgPlanundoService extends IService<VMsgPlanundo> {

        List<VMsgPlanundo> GetMsgFileValid();
        }
