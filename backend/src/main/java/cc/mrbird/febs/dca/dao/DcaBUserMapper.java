package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaBUser;
import cc.mrbird.febs.dca.entity.userAuditAccount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-10-27
 */
public interface DcaBUserMapper extends BaseMapper<DcaBUser> {
    void updateDcaBUser(DcaBUser dcaBUser);

    IPage<DcaBUser> findDcaBUser(Page page, @Param("dcaBUser") DcaBUser dcaBUser);

    @Update(" update dca_b_user set IS_DELETEMARK=0 where user_account=#{useraccount}  and (state=0 or state=2)")
    void deleteByAccount(@Param("useraccount") String useraccount);

    @Select(" select IFNULL(max(display_index),0) As maxIndex from  dca_b_user  where user_account=#{useraccount} ")
    int getMaxDisplayIndexByuseraccount(@Param("useraccount") String useraccount);

    @Select("SELECT  a.user_account from dca_b_user a\n" +
            "LEFT JOIN  dca_b_parttimejob  b\n" +
            "on a.user_account=b.user_account \n" +
            "where b.IS_DELETEMARK=1 and b.state=1 and LENGTH(a.np_position_name)>0")
    List<String> getParttimeUndo();

    @Select("SELECT  a.user_account from dca_b_user a\n" +
            "INNER JOIN  dca_b_prizeorpunish  b\n" +
            "on a.user_account=b.user_account \n" +
            "where b.IS_DELETEMARK=1 and b.state=1 and LENGTH(a.np_position_name)>0")
    List<String> getPrizeorpunishUndo();

    @Select("SELECT  a.user_account from dca_b_user a\n" +
            "INNER JOIN  dca_b_educationexperice  b\n" +
            "on a.user_account=b.user_account \n" +
            "where b.IS_DELETEMARK=1 and b.state=1 and LENGTH(a.np_position_name)>0")
    List<String> getEducationexpericeUndo();

    @Select("SELECT  a.user_account from dca_b_user a\n" +
            "INNER JOIN  dca_b_sciencepublish  b\n" +
            "on a.user_account=b.user_account \n" +
            "where b.IS_DELETEMARK=1 and b.state=1 and LENGTH(a.np_position_name)>0")
    List<String> getSciencepublishUndo();


    @Select("SELECT  a.user_account from dca_b_user a\n" +
            "INNER JOIN  dca_b_sciencesearch  b\n" +
            "on a.user_account=b.user_account \n" +
            "where b.IS_DELETEMARK=1 and b.state=1 and LENGTH(a.np_position_name)>0")
    List<String> getSciencesearchUndo();

    @Select("SELECT  a.user_account from dca_b_user a\n" +
            "INNER JOIN  dca_b_patent  b\n" +
            "on a.user_account=b.user_account \n" +
            "where b.IS_DELETEMARK=1 and b.state=1 and LENGTH(a.np_position_name)>0")
    List<String> getPatentUndo();

    @Select("SELECT  a.user_account from dca_b_user a\n" +
            "INNER JOIN  dca_b_teacherqualify  b\n" +
            "on a.user_account=b.user_account \n" +
            "where b.IS_DELETEMARK=1 and b.state=1 and LENGTH(a.np_position_name)>0")
    List<String> getDca_b_teacherqualifyUndo();

    @Select("SELECT  a.user_account from dca_b_user a\n" +
            "INNER JOIN  dca_b_attachfile  b\n" +
            "on a.user_account=b.user_account \n" +
            "where b.IS_DELETEMARK=1 and b.state=1 and LENGTH(a.np_position_name)>0")
    List<String> getDca_b_attachfileUndo();

    @Select("SELECT  a.user_account from dca_b_user a\n" +
            "INNER JOIN  dca_b_exportcountry  b\n" +
            "on a.user_account=b.user_account \n" +
            "where b.IS_DELETEMARK=1 and b.state=1 and LENGTH(a.np_position_name)>0")
    List<String> getDca_b_exportcountryUndo();

    @Select("SELECT  a.user_account from dca_b_user a\n" +
            "INNER JOIN  dca_b_publicarticle  b\n" +
            "on a.user_account=b.user_account \n" +
            "where b.IS_DELETEMARK=1 and b.state=1 and LENGTH(a.np_position_name)>0")
    List<String> getDca_b_publicarticleUndo();

    /**
     * 科研获奖
     * @return
     */
    @Select("SELECT  a.user_account from dca_b_user a\n" +
            "INNER JOIN  dca_b_scientificprize  b\n" +
            "on a.user_account=b.user_account \n" +
            "where b.IS_DELETEMARK=1 and b.state=1 and LENGTH(a.np_position_name)>0")
    List<String> getDca_b_scientificprizeUndo();

    /**
     * 担任辅导员教师班主任及考核情况
     * @return
     */
    @Select("SELECT  a.user_account from dca_b_user a\n" +
            "INNER JOIN  dca_b_turtor  b\n" +
            "on a.user_account=b.user_account \n" +
            "where b.IS_DELETEMARK=1 and b.state=1 and LENGTH(a.np_position_name)>0")
    List<String> getDca_b_turtorUndo();

    /**
     * 任现职以来完成教学、人才培养情况
     * @return
     */
    @Select("SELECT  a.user_account from dca_b_user a\n" +
            "INNER JOIN  dca_b_employ  b\n" +
            "on a.user_account=b.user_account \n" +
            "where b.IS_DELETEMARK=1 and b.state=1 and LENGTH(a.np_position_name)>0")
    List<String> getDca_b_employUndo();

    /**
     * 近五年本科教学情况
     * @return
     */
    @Select("SELECT  a.user_account from dca_b_user a\n" +
            "INNER JOIN  dca_b_undergraduate  b\n" +
            "on a.user_account=b.user_account \n" +
            "where b.IS_DELETEMARK=1 and b.state=1 and LENGTH(a.np_position_name)>0")
    List<String> getDca_b_undergraduateUndo();

    /**
     * 任现职以来承担的本科教学改革及建设项目
     * @return
     */
    @Select("SELECT  a.user_account from dca_b_user a\n" +
            "INNER JOIN  dca_b_innovatebuild  b\n" +
            "on a.user_account=b.user_account \n" +
            "where b.IS_DELETEMARK=1 and b.state=1 and LENGTH(a.np_position_name)>0")
    List<String> getDca_b_innovatebuildUndo();

    /**
     * 任现职以来本科教学工作获奖情况
     * @return
     */
    @Select("SELECT  a.user_account from dca_b_user a\n" +
            "INNER JOIN  dca_b_undergraduateprize  b\n" +
            "on a.user_account=b.user_account \n" +
            "where b.IS_DELETEMARK=1 and b.state=1 and LENGTH(a.np_position_name)>0")
    List<String> getDca_b_undergraduateprizeUndo();

    /**
     * 省部级及以上教学获奖
     * @return
     */
    @Select("SELECT  a.user_account from dca_b_user a\n" +
            "INNER JOIN  dca_b_teacherprize  b\n" +
            "on a.user_account=b.user_account \n" +
            "where b.IS_DELETEMARK=1 and b.state=1 and LENGTH(a.np_position_name)>0")
    List<String> getDca_b_teacherprizeUndo();


    /**
     * 校教学质量奖、校教学成果奖
     * @return
     */
    @Select("SELECT  a.user_account from dca_b_user a\n" +
            "INNER JOIN  dca_b_schoolprize  b\n" +
            "on a.user_account=b.user_account \n" +
            "where b.IS_DELETEMARK=1 and b.state=1 and LENGTH(a.np_position_name)>0")
    List<String> getDca_b_schoolprizeUndo();

    /**
     * 精品课程情况
     * @return
     */
    @Select("SELECT  a.user_account from dca_b_user a\n" +
            "INNER JOIN  dca_b_courseclass  b\n" +
            "on a.user_account=b.user_account \n" +
            "where b.IS_DELETEMARK=1 and b.state=1 and LENGTH(a.np_position_name)>0")
    List<String> getDca_b_courseclassUndo();

    /**
     * 教师教学竞赛获奖
     * @return
     */
    @Select("SELECT  a.user_account from dca_b_user a\n" +
            "INNER JOIN  dca_b_youngprize  b\n" +
            "on a.user_account=b.user_account \n" +
            "where b.IS_DELETEMARK=1 and b.state=1 and LENGTH(a.np_position_name)>0")
    List<String> getDca_b_youngprizeUndo();

    /**
     * 任现职以来完成研究生教学人才培养情况
     * @return
     */
    @Select("SELECT  a.user_account from dca_b_user a\n" +
            "INNER JOIN  dca_b_talent  b\n" +
            "on a.user_account=b.user_account \n" +
            "where b.IS_DELETEMARK=1 and b.state=1 and LENGTH(a.np_position_name)>0")
    List<String> getDca_b_talentUndo();

    /**
     * 任现职以来独立指导研究生情况
     * @return
     */
    @Select("SELECT  a.user_account from dca_b_user a\n" +
            "INNER JOIN  dca_b_graduate  b\n" +
            "on a.user_account=b.user_account \n" +
            "where b.IS_DELETEMARK=1 and b.state=1 and LENGTH(a.np_position_name)>0")
    List<String> getDca_b_graduateUndo();

    /**
     *  d党部审核人员
     * @return
     */
    @Select("select c.USERNAME,d.user_account from \n" +
            "dca_d_xl a\n" +
            "INNER JOIN dca_user_xl b\n" +
            "on a.id=b.xl_id\n" +
            "INNER JOIN t_user c on b.user_id=c.USER_ID \n" +
            "INNER JOIN dca_b_user d on a.mudule_name=d.np_position_name\n" +
            "where LENGTH(d.np_position_name)>0")
    List<userAuditAccount> getUserAndAccount();
}
