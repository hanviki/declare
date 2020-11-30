package cc.mrbird.febs.dcacopy.dao;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyAttachfile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 其他附件材料 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
public interface DcaBCopyAttachfileMapper extends BaseMapper<DcaBCopyAttachfile> {
        void updateDcaBCopyAttachfile(DcaBCopyAttachfile dcaBCopyAttachfile);
        IPage<DcaBCopyAttachfile> findDcaBCopyAttachfile(Page page, @Param("dcaBCopyAttachfile") DcaBCopyAttachfile dcaBCopyAttachfile);
        }
