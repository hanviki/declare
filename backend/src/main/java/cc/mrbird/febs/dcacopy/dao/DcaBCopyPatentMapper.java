package cc.mrbird.febs.dcacopy.dao;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyPatent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 任现职以来申请专利情况 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
public interface DcaBCopyPatentMapper extends BaseMapper<DcaBCopyPatent> {
        void updateDcaBCopyPatent(DcaBCopyPatent dcaBCopyPatent);
        IPage<DcaBCopyPatent> findDcaBCopyPatent(Page page, @Param("dcaBCopyPatent") DcaBCopyPatent dcaBCopyPatent);
        }
