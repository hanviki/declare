package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaBAuditdynamic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-10-27
 */
public interface DcaBAuditdynamicMapper extends BaseMapper<DcaBAuditdynamic> {
        void updateDcaBAuditdynamic(DcaBAuditdynamic dcaBAuditdynamic);
        IPage<DcaBAuditdynamic> findDcaBAuditdynamic(Page page, @Param("dcaBAuditdynamic") DcaBAuditdynamic dcaBAuditdynamic);

@Update(" update dca_b_auditdynamic set IS_DELETEMARK=0 where user_account=#{useraccount}  and (state=0 or state=2)")
        void deleteByAccount(@Param("useraccount") String useraccount);
@Select(" select IFNULL(max(display_index),0) As maxIndex from  dca_b_auditdynamic  where user_account=#{useraccount} ")
        int getMaxDisplayIndexByuseraccount(@Param("useraccount") String useraccount);
@Select("SELECT\n" +
        "\ta.audit_result,\n" +
        "\tb.field_title auditTitle,\n" +
        "\tb.audit_dept,\n" +
        "\ta.audit_note,\n" +
        "\tb.display_index,\n" +
        "\tb.state,\n" +
        "\ta.audit_titletype\n" +
        "FROM\n" +
        "\tdca_b_auditdynamic a\n" +
        "right JOIN dca_d_auditinfo b ON a.audit_titletype = b.field_name and a.user_account=#{userAccount}\n" +
        "ORDER BY\n" +
        "\tb.display_index asc")
List<DcaBAuditdynamic> getAllByUserAccount(@Param("userAccount") String userAccount);


        }
