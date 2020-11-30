package cc.mrbird.febs.dcacopy.dao;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyFivecomment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 近五年总体项目评价 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
public interface DcaBCopyFivecommentMapper extends BaseMapper<DcaBCopyFivecomment> {
        void updateDcaBCopyFivecomment(DcaBCopyFivecomment dcaBCopyFivecomment);
        IPage<DcaBCopyFivecomment> findDcaBCopyFivecomment(Page page, @Param("dcaBCopyFivecomment") DcaBCopyFivecomment dcaBCopyFivecomment);
        }
