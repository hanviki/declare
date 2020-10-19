package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaBAttachfile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 其他附件 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-10-15
 */
public interface DcaBAttachfileMapper extends BaseMapper<DcaBAttachfile> {
        void updateDcaBAttachfile(DcaBAttachfile dcaBAttachfile);
        IPage<DcaBAttachfile> findDcaBAttachfile(Page page, @Param("dcaBAttachfile") DcaBAttachfile dcaBAttachfile);

@Update(" update dca_b_attachfile set IS_DELETEMARK=0 where user_account=#{useraccount}")
        void deleteByAccount(@Param("useraccount") String useraccount);
        }
