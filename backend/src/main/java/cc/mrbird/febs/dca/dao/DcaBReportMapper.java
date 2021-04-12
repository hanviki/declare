package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaBReport;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
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

        void mutiUpdate(List<String> list);

        @Select("Select *  from dca_b_report_test")
        List<DcaBReport> getReportTest();

        @Update("update dca_b_report set ifshuangbao='否' where user_account=#{userAccount} and year =#{year}")
        void UpdateShuangBao(@Param("userAccount") String userAccount, @Param("year") String year);
        }
