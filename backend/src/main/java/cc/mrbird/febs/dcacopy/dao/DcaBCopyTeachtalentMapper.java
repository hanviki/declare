package cc.mrbird.febs.dcacopy.dao;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyTeachtalent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 任现职以来完成教学、人才培养情况 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
public interface DcaBCopyTeachtalentMapper extends BaseMapper<DcaBCopyTeachtalent> {
        void updateDcaBCopyTeachtalent(DcaBCopyTeachtalent dcaBCopyTeachtalent);
        IPage<DcaBCopyTeachtalent> findDcaBCopyTeachtalent(Page page, @Param("dcaBCopyTeachtalent") DcaBCopyTeachtalent dcaBCopyTeachtalent);
        }
