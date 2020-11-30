package cc.mrbird.febs.dcacopy.dao;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyAuditdynamic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-11-23
 */
public interface DcaBCopyAuditdynamicMapper extends BaseMapper<DcaBCopyAuditdynamic> {
        void updateDcaBCopyAuditdynamic(DcaBCopyAuditdynamic dcaBCopyAuditdynamic);
        IPage<DcaBCopyAuditdynamic> findDcaBCopyAuditdynamic(Page page, @Param("dcaBCopyAuditdynamic") DcaBCopyAuditdynamic dcaBCopyAuditdynamic);

        @Select("SELECT\n" +
                "\ta.id,\n" +
                "\ta.audit_result,\n" +
                "\tb.field_title auditTitle,\n" +
                "\tb.audit_dept,\n" +
                "\ta.audit_note,\n" +
                "\tb.display_index,\n" +
                "\tb.state,\n" +
                "\ta.audit_titletype \n" +
                "FROM\n" +
                "\tdca_b_copy_auditdynamic a\n" +
                "\tRIGHT JOIN dca_d_auditinfo b ON a.audit_titletype = b.field_name \n" +
                "and \n" +
                "\ta.dca_year = #{dcaYear} and a.user_account = #{userAccount}\n" +
                "\t\n" +
                "ORDER BY\n" +
                "\tb.display_index ASC")
        List<DcaBCopyAuditdynamic> getAllByUserAccount(@Param("userAccount") String userAccount,@Param("dcaYear") String dcaYear);

        @Select("SELECT\n" +
                "\taudit_result\n" +
                "FROM\n" +
                "\tdca_b_copy_auditdynamic\n" +
                "WHERE\n" +
                "\tuser_account =  #{userAccount}\n" +
                "AND audit_titletype = 'j5njxgz'\n" +
                "AND dca_year = #{dcaYear}")
   String  getZtkhqk(@Param("userAccount") String userAccount,@Param("dcaYear") String dcaYear);
        }
