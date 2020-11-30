package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaBReport;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-11-13
 */
public interface DcaBReportMapper extends BaseMapper<DcaBReport> {
        void updateDcaBReport(DcaBReport dcaBReport);
        IPage<DcaBReport> findDcaBReport(Page page, @Param("dcaBReport") DcaBReport dcaBReport);

        void insertCopy(Map<String,Object> map);
        }
