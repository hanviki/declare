package cc.mrbird.febs.scm.dao;

import cc.mrbird.febs.scm.entity.*;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 * 供应商表 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2019-11-14
 */
public interface ScmDVendorMapper extends BaseMapper<ScmDVendor> {
    void updateScmDVendor(ScmDVendor scmDVendor);


    @Select("select name,code from scm_d_vendor where IS_DELETEMARK=1 AND LENGTH(code)>0")
    List<GysEntity> getGysNameAndCode();

    List<VendorRank> getRank(@Param("order") ScmBPurcharseorder order);

    IPage<MaterPercentage> getMatnrPercentage(Page page, @Param("order") ScmBPurcharseorder order);

    IPage<TotalStatistic> getGysAmount(Page page, @Param("order") ScmBPurcharseorder order);
    IPage<TotalStatistic> getMatnrAmount(Page page, @Param("order") ScmBPurcharseorder order);

    /*
       在用户审核后，清除供应商表更改消息
        */
    @Update("UPDATE scm_d_vendor  set note='' WHERE code=#{id}")
    void removeNoteVendor(@Param("id") String id);

    /*
   在用户审核后，清除供应商附件表更改消息
    */
    @Update("UPDATE scm_d_vendor_d set noted='' where BASE_ID in (SELECT id  from scm_d_vendor where code=#{id})")
    void removeNoteVendord(@Param("id") String id);

    /*
   在用户审核后，清除供应商业务员表更改消息
    */
    @Update("UPDATE scm_d_vendoruser set noteu='' where BASE_ID in (SELECT id  from scm_d_vendor where code=#{id})")
    void removeNoteVendoruser(@Param("id") String id);

}
