package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.dao.DcaBAuditdynamicMapper;
import cc.mrbird.febs.dca.dao.DcaBEducationexpericeMapper;
import cc.mrbird.febs.dca.entity.*;
import cc.mrbird.febs.dca.dao.DcaBUserMapper;
import cc.mrbird.febs.dca.service.IDcaBReportService;
import cc.mrbird.febs.dca.service.IDcaBUserService;
import cn.hutool.Hutool;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.time.LocalDate;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-10-15
 */
@Slf4j
@Service("IDcaBUserService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBUserServiceImpl extends ServiceImpl<DcaBUserMapper, DcaBUser> implements IDcaBUserService {

    @Autowired
    private DcaBEducationexpericeMapper dcaBEducationexpericeMapper;
    @Autowired
    private DcaBAuditdynamicMapper dcaBAuditdynamicMapper;
    @Autowired
    private IDcaBReportService iDcaBReportService;

    @Override
    public IPage<DcaBUser> findDcaBUserswithDoctor(QueryRequest request, DcaBUser dcaBUser) {
        try {
            LambdaQueryWrapper<DcaBUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DcaBUser::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(dcaBUser.getUserAccount())) {
                queryWrapper.like(DcaBUser::getUserAccount, dcaBUser.getUserAccount());
            }
            if (dcaBUser.getState() != null) {
                queryWrapper.eq(DcaBUser::getState, dcaBUser.getState());
            }
            if (StringUtils.isNotBlank(dcaBUser.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBUser.getCreateTimeTo())) {
                queryWrapper
                        .ge(DcaBUser::getCreateTime, dcaBUser.getCreateTimeFrom())
                        .le(DcaBUser::getCreateTime, dcaBUser.getCreateTimeTo());
            }
            if (StringUtils.isNotBlank(dcaBUser.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaBUser.getModifyTimeTo())) {
                queryWrapper
                        .ge(DcaBUser::getModifyTime, dcaBUser.getModifyTimeFrom())
                        .le(DcaBUser::getModifyTime, dcaBUser.getModifyTimeTo());
            }

            Page<DcaBUser> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            IPage<DcaBUser> listResult = this.page(page, queryWrapper);
            for (DcaBUser user : listResult.getRecords()
            ) {
                LambdaQueryWrapper<DcaBEducationexperice> queryWrapper2 = new LambdaQueryWrapper<>();
                queryWrapper2.like(DcaBEducationexperice::getExpPosition, "博士");
                queryWrapper2.eq(DcaBEducationexperice::getUserAccount, user.getUserAccount());
                // queryWrapper2.eq(DcaBEducationexperice::getState, 3);
                queryWrapper2.notLike(DcaBEducationexperice::getExpPosition, "博士后");
                List<DcaBEducationexperice> dcaBEducationexpericeList = this.dcaBEducationexpericeMapper.selectList(queryWrapper2);
                if (dcaBEducationexpericeList.size() > 0) {
                    if (dcaBEducationexpericeList.get(0).getExpEndTime() != null) {
                        user.setDoctorDesc(new SimpleDateFormat("yyyy-MM-dd").format(dcaBEducationexpericeList.get(0).getExpEndTime()));
                    }
                }
            }
            return listResult;
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<DcaBUser> findDcaBUsers(QueryRequest request, DcaBUser dcaBUser) {
        try {
            LambdaQueryWrapper<DcaBUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DcaBUser::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(dcaBUser.getUserAccount())) {
                queryWrapper.like(DcaBUser::getUserAccount, dcaBUser.getUserAccount());
            }
            if (dcaBUser.getState() != null) {
                queryWrapper.eq(DcaBUser::getState, dcaBUser.getState());
            }
            if (StringUtils.isNotBlank(dcaBUser.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBUser.getCreateTimeTo())) {
                queryWrapper
                        .ge(DcaBUser::getCreateTime, dcaBUser.getCreateTimeFrom())
                        .le(DcaBUser::getCreateTime, dcaBUser.getCreateTimeTo());
            }
            if (StringUtils.isNotBlank(dcaBUser.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaBUser.getModifyTimeTo())) {
                queryWrapper
                        .ge(DcaBUser::getModifyTime, dcaBUser.getModifyTimeFrom())
                        .le(DcaBUser::getModifyTime, dcaBUser.getModifyTimeTo());
            }

            Page<DcaBUser> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<DcaBUser> findDcaBUserList(QueryRequest request, DcaBUser dcaBUser) {
        try {
            Page<DcaBUser> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findDcaBUser(page, dcaBUser);
        } catch (Exception e) {
            log.error("获取失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createDcaBUser(DcaBUser dcaBUser) {
        dcaBUser.setId(UUID.randomUUID().toString());
        dcaBUser.setCreateTime(new Date());
        dcaBUser.setIsDeletemark(1);
        this.save(dcaBUser);

    }

    @Override
    @Transactional
    public void updateDcaBUser(DcaBUser dcaBUser) {
        dcaBUser.setModifyTime(new Date());
        this.baseMapper.updateDcaBUser(dcaBUser);
    }

    @Override
    @Transactional
    public void deleteDcaBUsers(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }

    @Override
    @Transactional
    public void deleteByuseraccount(String userAccount) {
        this.baseMapper.deleteByAccount(userAccount);
    }

    @Override
    @Transactional
    public IPage<DcaBUser> findDcaBUsersAll(QueryRequest request, DcaBUser dcaBUser) {
        try {
            LambdaQueryWrapper<DcaBUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DcaBUser::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(dcaBUser.getUserAccount())) {
                queryWrapper.and(wrap -> wrap.eq(DcaBUser::getUserAccount, dcaBUser.getUserAccount()).or()
                        .like(DcaBUser::getUserAccountName, dcaBUser.getUserAccount()));

            }
            if (StringUtils.isNotBlank(dcaBUser.getNpPositionName())) {
                queryWrapper.like(DcaBUser::getNpPositionName, dcaBUser.getNpPositionName());
            }
            if (StringUtils.isNotBlank(dcaBUser.getDcaYear())) {
                queryWrapper.eq(DcaBUser::getDcaYear, dcaBUser.getDcaYear());
            }

            queryWrapper.apply(" LENGTH(dca_b_user.np_position_name)>0 ");

            Page<DcaBUser> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            IPage<DcaBUser> listResult = this.page(page, queryWrapper);


            return listResult;
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public IPage<DcaBUser> findDcaBUsersAudit(QueryRequest request, DcaBUser dcaBUser, int state) {
        try {
            LambdaQueryWrapper<DcaBUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DcaBUser::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(dcaBUser.getUserAccount())) {
                queryWrapper.and(wrap -> wrap.eq(DcaBUser::getUserAccount, dcaBUser.getUserAccount()).or()
                        .like(DcaBUser::getUserAccountName, dcaBUser.getUserAccount()));

            }
            if (state == 3) {
                queryWrapper.apply("dca_b_user.user_account  in (select  user_account from  dca_b_auditdynamic inner JOIN dca_d_auditinfo on dca_b_auditdynamic.audit_titletype=dca_d_auditinfo.field_name\n" +
                        "inner join dca_user_audit on dca_d_auditinfo.id=dca_user_audit.audit_id\n" +
                        "where dca_user_audit.userId =" + dcaBUser.getCreateUserId() + ") and LENGTH(dca_b_user.np_position_name)>0 and \n" +
                        "dca_b_user.np_position_name in (SELECT dca_d_xl.mudule_name  from dca_d_xl inner join dca_user_xl on dca_user_xl.xl_id=dca_d_xl.id where dca_user_xl.user_id=" + dcaBUser.getCreateUserId() + ")");
            }
            if (state == 1) {
                queryWrapper.apply("dca_b_user.user_account  not in (select  user_account from  dca_b_auditdynamic inner JOIN dca_d_auditinfo on dca_b_auditdynamic.audit_titletype=dca_d_auditinfo.field_name\n" +
                        "inner join dca_user_audit on dca_d_auditinfo.id=dca_user_audit.audit_id\n" +
                        "where dca_user_audit.userId =" + dcaBUser.getCreateUserId() + ") and LENGTH(dca_b_user.np_position_name)>0 and \n" +
                        "dca_b_user.np_position_name in (SELECT dca_d_xl.mudule_name  from dca_d_xl inner join dca_user_xl on dca_user_xl.xl_id=dca_d_xl.id where dca_user_xl.user_id=" + dcaBUser.getCreateUserId() + ")");
            }
            Page<DcaBUser> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            IPage<DcaBUser> listResult = this.page(page, queryWrapper);

            if (state == 3) {
                if (listResult.getRecords().size() > 0) {
                    List<String> listDynamic = listResult.getRecords().stream().map(p -> p.getUserAccount()).collect(Collectors.toList());
                    LambdaQueryWrapper<DcaBAuditdynamic> queryWrapperDynamic = new LambdaQueryWrapper<>();
                    if (listDynamic.size() > 0) {
                        queryWrapperDynamic.in(DcaBAuditdynamic::getUserAccount, listDynamic);
                        List<DcaBAuditdynamic> auditdynamicList = this.dcaBAuditdynamicMapper.selectList(queryWrapperDynamic);
                        for (DcaBUser user : listResult.getRecords()
                        ) {
                            List<DcaBAuditdynamic> listDy = auditdynamicList.stream().filter(p -> p.getUserAccount().equals(user.getUserAccount())).collect(Collectors.toList());
                            user.setDcaBAuditdynamicList(listDy);
                        }
                    }
                }
            }
            return listResult;
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public IPage<DcaBUser> findDcaBUsersAuditCustom(QueryRequest request, DcaBUser dcaBUser) {
        try {
            LambdaQueryWrapper<DcaBUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DcaBUser::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(dcaBUser.getUserAccount())) {
                queryWrapper.and(wrap -> wrap.eq(DcaBUser::getUserAccount, dcaBUser.getUserAccount()).or()
                        .like(DcaBUser::getUserAccountName, dcaBUser.getUserAccount()));

            }

            queryWrapper.apply(" LENGTH(dca_b_user.np_position_name)>0 and \n" +
                    "dca_b_user.np_position_name in (SELECT dca_d_xl.mudule_name  from dca_d_xl inner join dca_user_xl on dca_user_xl.xl_id=dca_d_xl.id where dca_user_xl.user_id=" + dcaBUser.getCreateUserId() + ")");


            Page<DcaBUser> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            IPage<DcaBUser> listResult = this.page(page, queryWrapper);


            return listResult;
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public IPage<DcaBUser> findDcaBUsersAuditCustomExport(QueryRequest request, DcaBUser dcaBUser) {
        try {
            LambdaQueryWrapper<DcaBUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DcaBUser::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(dcaBUser.getUserAccount())) {
                queryWrapper.and(wrap -> wrap.eq(DcaBUser::getUserAccount, dcaBUser.getUserAccount()).or()
                        .like(DcaBUser::getUserAccountName, dcaBUser.getUserAccount()));

            }

            queryWrapper.apply(" LENGTH(dca_b_user.np_position_name)>0");


            Page<DcaBUser> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            IPage<DcaBUser> listResult = this.page(page, queryWrapper);


            return listResult;
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public IPage<DcaBUser> findDcaBUsersAuditResult(QueryRequest request, DcaBUser dcaBUser) {
        try {
            LambdaQueryWrapper<DcaBUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DcaBUser::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(dcaBUser.getUserAccount())) {
                queryWrapper.and(wrap -> wrap.eq(DcaBUser::getUserAccount, dcaBUser.getUserAccount()).or()
                        .like(DcaBUser::getUserAccountName, dcaBUser.getUserAccount()));

            }
            queryWrapper.apply(" LENGTH(dca_b_user.np_position_name)>0 ");
            Page<DcaBUser> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            IPage<DcaBUser> listResult = this.page(page, queryWrapper);


            if (listResult.getRecords().size() > 0) {
                List<String> listDynamic = listResult.getRecords().stream().map(p -> p.getUserAccount()).collect(Collectors.toList());
                if (listDynamic.size() > 0) {
                    List<DcaBAuditdynamic> auditdynamicList = getAllInfo(listDynamic);
                    if (auditdynamicList.size() > 0) {
                        for (DcaBUser user : listResult.getRecords()
                        ) {
                            List<DcaBAuditdynamic> listDy = auditdynamicList.stream().filter(p -> p.getUserAccount().equals(user.getUserAccount())).collect(Collectors.toList());
                            user.setDcaBAuditdynamicList(listDy);
                        }
                    }
                }
            }

            return listResult;
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }


    @Override
    @Transactional
    public List<DcaBAuditdynamic> getAllInfo(List<String> listUniqUser) {
        List<String> parttimrjob = this.baseMapper.getParttimeUndo();
        List<String> prizeorpunishUndo = this.baseMapper.getPrizeorpunishUndo();
        List<String> educationexpericeUndo = this.baseMapper.getEducationexpericeUndo();
        List<String> sciencepublishUndo = this.baseMapper.getSciencepublishUndo();
        List<String> sciencesearchUndo = this.baseMapper.getSciencesearchUndo();
        List<String> patentUndo = this.baseMapper.getPatentUndo();
        List<String> teacherqualifyUndo = this.baseMapper.getDca_b_teacherqualifyUndo();

        List<String> attachfileUndo = this.baseMapper.getDca_b_attachfileUndo();
        List<String> exportcountryUndo = this.baseMapper.getDca_b_exportcountryUndo();
        List<String> publicarticleUndo = this.baseMapper.getDca_b_publicarticleUndo();


        List<String> scientificprizeUndo = this.baseMapper.getDca_b_scientificprizeUndo();

        List<String> employUndo = this.baseMapper.getDca_b_employUndo();
        List<String> turtorUndo = this.baseMapper.getDca_b_turtorUndo();
        List<String> undergraduateUndo = this.baseMapper.getDca_b_undergraduateUndo();
        List<String> innovatebuildUndo = this.baseMapper.getDca_b_innovatebuildUndo();
        List<String> undergraduateprizeUndo = this.baseMapper.getDca_b_undergraduateprizeUndo();
        List<String> teacherprizeUndo = this.baseMapper.getDca_b_teacherprizeUndo();
        List<String> schoolprizeUndo = this.baseMapper.getDca_b_schoolprizeUndo();
        List<String> courseclassUndo = this.baseMapper.getDca_b_courseclassUndo();
        List<String> youngprizeUndo = this.baseMapper.getDca_b_youngprizeUndo();

        List<String> talentUndo = this.baseMapper.getDca_b_talentUndo();
        List<String> graduateUndo = this.baseMapper.getDca_b_graduateUndo();

        //党员审核数据
        LambdaQueryWrapper<DcaBAuditdynamic> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBAuditdynamic::getIsDeletemark, 1);
        List<DcaBAuditdynamic> auditdynamicList = this.dcaBAuditdynamicMapper.selectList(queryWrapper);

        List<userAuditAccount> userAndAccount = this.baseMapper.getUserAndAccount();//userAuditAccount
        List<DcaBAuditdynamic> listAll = new ArrayList<>();
        List<String> allAccount = new ArrayList<>();
        allAccount.addAll(parttimrjob);
        allAccount.addAll(prizeorpunishUndo);
        allAccount.addAll(educationexpericeUndo);
        allAccount.addAll(sciencepublishUndo);
        allAccount.addAll(sciencesearchUndo);
        allAccount.addAll(patentUndo);
        allAccount.addAll(teacherqualifyUndo);
        allAccount.addAll(attachfileUndo);
        allAccount.addAll(exportcountryUndo);
        allAccount.addAll(publicarticleUndo);
        allAccount.addAll(scientificprizeUndo);
        //人事处
        List<String> listUnique = allAccount.stream().distinct().collect(Collectors.toList());
        for (String item : listUnique
        ) {
            DcaBAuditdynamic dy = new DcaBAuditdynamic();
            dy.setUserAccount(item);
            dy.setAuditTitletype("rsc");
            dy.setAuditResult("未完成");
            listAll.add(dy);
        }
        List<String> listOthes = listUniqUser.stream().filter(p -> !listUnique.contains(p)).collect(Collectors.toList());
        for (String item : listOthes
        ) {
            DcaBAuditdynamic dy = new DcaBAuditdynamic();
            dy.setUserAccount(item);
            dy.setAuditTitletype("rsc");
            dy.setAuditResult("已完成");
            listAll.add(dy);
        }
        // 科研处
        List<String> listKy = new ArrayList<>();
        listKy.addAll(sciencesearchUndo);
        listKy.addAll(scientificprizeUndo);
        DoEvery(userAndAccount, "xsddsc", "10011471", "kyc", auditdynamicList, listAll, listKy, listUniqUser);
        // 0-科研处 end

        //医务处
        // 科研处
        List<String> listYWC = new ArrayList<>();
        DoEvery(userAndAccount, "ylpfbfz", "10010800", "ywc", auditdynamicList, listAll, listYWC, listUniqUser);

        //教学管理办公室
        List<String> listJxglbgs = new ArrayList<>();
        listJxglbgs.addAll(employUndo);
        listJxglbgs.addAll(turtorUndo);
        listJxglbgs.addAll(undergraduateUndo);
        listJxglbgs.addAll(innovatebuildUndo);
        listJxglbgs.addAll(undergraduateprizeUndo);
        listJxglbgs.addAll(teacherprizeUndo);
        listJxglbgs.addAll(schoolprizeUndo);
        listJxglbgs.addAll(courseclassUndo);
        listJxglbgs.addAll(youngprizeUndo);
        DoEvery(userAndAccount, "jxpf", "10010937", "jxglbgs", auditdynamicList, listAll, listJxglbgs, listUniqUser);

        // 研究生管理办公室
        List<String> listYjsglbgs = new ArrayList<>();
        listYjsglbgs.addAll(talentUndo);
        listYjsglbgs.addAll(graduateUndo);
        DoEvery(userAndAccount, "sftgsdsf", "10011654", "yjsglbgs", auditdynamicList, listAll, listYjsglbgs, listUniqUser);

        //党办
        List<String> listDb = new ArrayList<>();
        DoEvery(userAndAccount, "ydyf", "10040430", "db", auditdynamicList, listAll, listDb, listUniqUser);

        //党委组织部
        List<String> listDwzzb = new ArrayList<>();
        DoEvery(userAndAccount, "zzsc", "10040495", "dwzzb", auditdynamicList, listAll, listDwzzb, listUniqUser);

        //纪委办公室
        List<String> listJwbgs = new ArrayList<>();
        DoEvery(userAndAccount, "jlsc", "10040493", "jwbgs", auditdynamicList, listAll, listJwbgs, listUniqUser);

        //宣传部
        List<String> listCcb = new ArrayList<>();
        DoEvery(userAndAccount, "yyxtsc", "10011136", "ccb", auditdynamicList, listAll, listCcb, listUniqUser);

        //门诊办公室
        List<String> listMzbgs = new ArrayList<>();
        DoEvery(userAndAccount, "mzylpf", "10010352", "mzbgs", auditdynamicList, listAll, listMzbgs, listUniqUser);

        //护理部
        List<String> listHlb = new ArrayList<>();
        DoEvery(userAndAccount, "ylpfbfz", "10020783", "hlb", auditdynamicList, listAll, listHlb, listUniqUser);

        //行风建设办公室
        List<String> listHfjsbgs = new ArrayList<>();
        DoEvery(userAndAccount, "sshbdts", "10011480", "hfjsbgs", auditdynamicList, listAll, listHfjsbgs, listUniqUser);
        return listAll;
    }

    private void DoEvery(List<userAuditAccount> userAndAccount, String filedtitletype, String username, String title, List<DcaBAuditdynamic> auditdynamicList, List<DcaBAuditdynamic> listAll, List<String> listYWC, List<String> listUniqUser) {

        List<userAuditAccount> userAuditAccountListYwc = userAndAccount.stream().filter(p -> p.getUserName().equals(username)).collect(Collectors.toList());
        List<String> listau = new ArrayList<>();
        List<String> userUn = userAuditAccountListYwc.stream().map(p -> p.getUserAccount()).distinct().collect(Collectors.toList());
        if (listYWC.size() > 0) {

            for (userAuditAccount item : userAuditAccountListYwc
            ) {
                long c = auditdynamicList.stream().filter(p -> p.getUserAccount().equals(item.getUserAccount()) &&
                        p.getAuditTitletype().equals(filedtitletype)).count();
                long p = 0L;
                if (c > p) {
                    if (!listau.contains(item.getUserAccount())) {
                        listau.add(item.getUserAccount());//已完成数据
                    }
                }

            }
            List<String> undou = userUn.stream().filter(p -> !(listau.contains(p) || listYWC.contains(p))).collect(Collectors.toList());
            for (String item : undou
            ) {
                DcaBAuditdynamic dy = new DcaBAuditdynamic();
                dy.setUserAccount(item);
                dy.setAuditTitletype(title);
                dy.setAuditResult("未完成");
                listAll.add(dy);
            }
            for (String item : listYWC
            ) {
                DcaBAuditdynamic dy = new DcaBAuditdynamic();
                dy.setUserAccount(item);
                dy.setAuditTitletype(title);
                dy.setAuditResult("未完成");
                listAll.add(dy);
            }
            List<String> otherYwNoall = listUniqUser.stream().filter(p -> !(listYWC.contains(p) || undou.contains(p))).collect(Collectors.toList());
            for (String item : otherYwNoall
            ) {
                DcaBAuditdynamic dy = new DcaBAuditdynamic();
                dy.setUserAccount(item);
                dy.setAuditTitletype(title);
                dy.setAuditResult("已完成");
                listAll.add(dy);
            }
        } else {
            for (userAuditAccount item : userAuditAccountListYwc
            ) {
                long c = auditdynamicList.stream().filter(p -> p.getUserAccount().equals(item.getUserAccount()) &&
                        p.getAuditTitletype().equals(filedtitletype)).count();
                long p = 0L;
                if (c > p) {
                    if (!listau.contains(item.getUserAccount())) {
                        DcaBAuditdynamic dy = new DcaBAuditdynamic();
                        dy.setUserAccount(item.getUserAccount());
                        dy.setAuditTitletype(title);
                        dy.setAuditResult("已完成");
                        listAll.add(dy);
                        listau.add(item.getUserAccount());
                    }
                }
            }
            List<String> otherYwNo = userUn.stream().filter(p -> !listau.contains(p)).collect(Collectors.toList());
            for (String item : otherYwNo
            ) {
                DcaBAuditdynamic dy = new DcaBAuditdynamic();
                dy.setUserAccount(item);
                dy.setAuditTitletype(title);
                dy.setAuditResult("未完成");
                listAll.add(dy);
            }

            List<String> otherYwNoall = listUniqUser.stream().filter(p -> !userUn.contains(p)).collect(Collectors.toList());
            for (String item : otherYwNoall
            ) {
                DcaBAuditdynamic dy = new DcaBAuditdynamic();
                dy.setUserAccount(item);
                dy.setAuditTitletype(title);
                dy.setAuditResult("不审核");
                listAll.add(dy);
            }
        }


    }


    /**
     * 教授
     *
     * @param professorList
     * @param titleA
     * @param auditTitleType
     * @param userAccount
     * @param auditdynamicList
     */
    private void SetABCDEF_Jxzcsl(List<DcaBSciencepublish> professorList, String titleA, String auditTitleType, String userAccount, List<DcaBAuditdynamic> auditdynamicList) {
        double nameF_d = professorList.stream().filter(p -> p.getAuditQkjb().equals(titleA))
                .mapToDouble(p -> Convert.toDouble(p.getJxzcsl() == null ? 0 : p.getJxzcsl(), 0D)).sum();
        String nameF = String.format("%.2f", nameF_d);
        if (nameF.contains(".99")) {
            nameF_d = NumberUtil.add(nameF_d, 0.01D);
        }
        InsertDynamic(auditdynamicList, userAccount, String.format("%.2f", nameF_d), auditTitleType);
    }

    /**
     * 临床
     *
     * @param professorList
     * @param titleA
     * @param auditTitleType
     * @param userAccount
     * @param auditdynamicList
     */
    private void SetABCDEF_Lczcsl(List<DcaBSciencepublish> professorList, String titleA, String auditTitleType, String userAccount, List<DcaBAuditdynamic> auditdynamicList) {
        double nameF_d = professorList.stream().filter(p -> p.getAuditQkjb().equals(titleA))
                .mapToDouble(p -> Convert.toDouble(p.getLczcsl() == null ? 0 : p.getLczcsl(), 0D)).sum();
        String nameF = String.format("%.2f", nameF_d);

        if (nameF.contains(".99")) {
            nameF_d = NumberUtil.add(nameF_d, 0.01D);
        }
        InsertDynamic(auditdynamicList, userAccount, String.format("%.2f", nameF_d), auditTitleType);
    }

    private void InsertDynamic(List<DcaBAuditdynamic> auditdynamicList, String userAccount, String auditResult, String auditTitleType) {
        DcaBAuditdynamic NameC = new DcaBAuditdynamic();
        NameC.setUserAccount(userAccount);
        NameC.setAuditResult(auditResult);
        NameC.setAuditTitletype(auditTitleType);
        auditdynamicList.add(NameC);
    }

    private void SetUserForReport(DcaBUser user, DcaBReport dcaBReport) {
        dcaBReport.setAge(user.getAge());
        dcaBReport.setBirthdaystr(user.getBirthdaystr());
        dcaBReport.setUserAccount(user.getUserAccount());
        dcaBReport.setUserAccountName(user.getUserAccountName());
        dcaBReport.setKs(user.getKs());
        dcaBReport.setYear(user.getDcaYear());
        dcaBReport.setGwdj(user.getGwdj());
        dcaBReport.setSexName(user.getSexName());
        dcaBReport.setXl(user.getXl());
        dcaBReport.setPositionName(user.getPositionName());
        if (user.getSchoolDate() != null) {
            dcaBReport.setSchoolDate(DateUtil.format(user.getSchoolDate(), "yyyy-MM-dd"));
        }
        dcaBReport.setNpqk(user.getAuditManName()); //内聘情况
        dcaBReport.setZygwDate(user.getZygwDate());
        dcaBReport.setTelephone(user.getTelephone());
        dcaBReport.setBaomingIndex(user.getPatentRanknum());//报名序号
    }

    private String GetNullStr(String value) {
        if (value == null) {
            return "";
        }
        return value;
    }

    private String GetNullStrNumber(String value) {
        if (value == null) {
            return "0";
        }
        if (value.trim().equals("")) return "0";
        if (!isNumeric(value)) return "0";
        return value;
    }

    //方法一：用JAVA自带的函数
    private boolean isNumeric(String str2) {
        String str = str2.replace(".", "");
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    @Transactional
    public IPage<DcaBReport> findDcaBUsersAuditReport(QueryRequest request, DcaBUser dcaBUser) {
        try {

            //region 获取已经确认的用户帐号
            List<DcaBReport> reportList = this.baseMapper.getAllReportWithUser();

            Page<DcaBUser> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//
            IPage<DcaBUser> listResult = this.baseMapper.getAllShowUserInfo(page, dcaBUser);
            List<DcaBReport> dcaBReportList = new ArrayList<>();

            if (listResult.getRecords().size() > 0) {
                dcaBReportList = getBigTableAllInfo(listResult.getRecords(), reportList);
            }
            IPage<DcaBReport> listreport = new Page<>();
            listreport.setRecords(dcaBReportList);
            listreport.setTotal(listResult.getTotal());
            listreport.setSize(dcaBReportList.size());
            listreport.setPages(listResult.getPages());
            listreport.setCurrent(listResult.getCurrent());
            return listreport;
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public List<DcaBReport> getBigTableAllInfo(List<DcaBUser> listUser, List<DcaBReport> saveUser) {
        List<DcaBSciencesearch> listScienceSearch = this.baseMapper.getScientSearchAudit();
        List<DcaBTeacherprize> teacherprizeList = this.baseMapper.getTeachPrize();
        List<DcaBSciencepublish> sciencepublishList = this.baseMapper.getSciPublish();
        List<DcaBPublicarticle> publicarticleList = this.baseMapper.getPublicArticle();
        List<DcaBSchoolprize> schoolprizeList = this.baseMapper.getSchoolPrize();
        List<DcaBCourseclass> courseclassList = this.baseMapper.getCoursecalss();
        List<DcaBYoungprize> youngprizeList = this.baseMapper.getYoungprize();
        List<DcaBScientificprize> listSciencePrizeSearch = this.baseMapper.getScientPrize();
        List<String> tutorList = this.baseMapper.getTutor();
        List<String> teacherQualify = this.baseMapper.getTeacherQualify();
        List<String> dynamicIsOk = this.baseMapper.getDynamicIsOk();

        List<DcaBExportcountry> borad = this.baseMapper.getExportCountry();
        List<DcaBPatent> patentList = this.baseMapper.getPatentInfo();


        List<DcaBAuditdynamic> educationAuditList = this.baseMapper.getExpericeStudy();
        List<DcaBAuditdynamic> educationAuditBjList = this.baseMapper.getExpericeBysj();

        LambdaQueryWrapper<DcaBAuditdynamic> ql = new LambdaQueryWrapper<>();
        ql.eq(DcaBAuditdynamic::getIsDeletemark, 1);
        //动态评分表
        List<DcaBAuditdynamic> auditdynamicAuditList = this.dcaBAuditdynamicMapper.selectList(ql);

        /**
         * 医疗评分ylpfbfz   医疗评分等级 ylpfdj
         */
        List<DcaBAuditdynamic> listYwcYlpf = auditdynamicAuditList.stream().filter(p -> p.getAuditTitletype().equals("ylpfbfz")).collect(Collectors.toList());
        List<DcaBAuditdynamic> listYwcYlpfdj = auditdynamicAuditList.stream().filter(p -> p.getAuditTitletype().equals("ylpfdj")).collect(Collectors.toList());

        /**
         *   教学评分  jxpf  教学评分等级  jxpfdj
         */
        List<DcaBAuditdynamic> listjxpf = auditdynamicAuditList.stream().filter(p -> p.getAuditTitletype().equals("jxpf")).collect(Collectors.toList());
        List<DcaBAuditdynamic> listjxpfdj = auditdynamicAuditList.stream().filter(p -> p.getAuditTitletype().equals("jxpfdj")).collect(Collectors.toList());

//        List<DcaBAuditdynamic> auditdynamicList = new ArrayList<>();
//        CollUtil.addAll(auditdynamicList, listYwcYlpf);
//        CollUtil.addAll(auditdynamicList, listYwcYlpfdj);
//        CollUtil.addAll(auditdynamicList, listjxpf);
//        CollUtil.addAll(auditdynamicList, listjxpfdj);

        /**
         * 已经保存的用户数据
         */

        List<DcaBReport> allData = new ArrayList<>();

        for (DcaBUser user : listUser
        ) {
            boolean isShuangbiao = user.getState() != null && user.getState().equals(8);
            String shuangbao = isShuangbiao ? "是" : "否";

            List<DcaBAuditdynamic> auditdynamicList = new ArrayList<>();
            List<DcaBAuditdynamic> auditdynamicList_jiaoshou = new ArrayList<>();
            List<DcaBAuditdynamic> auditdynamicList_zhurenyishi = new ArrayList<>();
            String userAccount = user.getUserAccount();

            DcaBReport dcaBReport = new DcaBReport();
            DcaBReport dcaBReport2 = new DcaBReport();//双报
            boolean isAddJs = true;
            boolean isAddLc = true;


            List<DcaBReport> dcaBReportList = saveUser.stream().filter(p -> p.getUserAccount().equals(userAccount) && p.getNpPositionName().equals(user.getNpPositionName())).collect(Collectors.toList());
            if (dcaBReportList.size() > 0) {
                dcaBReport2 = dcaBReportList.get(0);
                if (dcaBReportList.get(0).getState() > 0) {
                    continue;
                }
            } else {
                dcaBReport2.setId(user.getUserAccount());
            }
            dcaBReport2.setNpPositionName(user.getNpPositionName());
            dcaBReport2.setIfshuangbao(shuangbao);
            SetUserForReport(user, dcaBReport2);


            /**
             * 学习经历
             */

            Optional<DcaBAuditdynamic> edulist = educationAuditList.stream()
                    .filter(p -> p.getUserAccount().equals(userAccount)).findFirst();
            InsertDynamic(auditdynamicList, userAccount, String.valueOf(!edulist.isPresent() ? "" : GetNullStr(edulist.get().getAuditResult())), "edu");
            Optional<DcaBAuditdynamic> eduBjlist = educationAuditBjList.stream()
                    .filter(p -> p.getUserAccount().equals(userAccount)).findFirst();
            InsertDynamic(auditdynamicList, userAccount, String.valueOf(!eduBjlist.isPresent() ? "" : GetNullStr(eduBjlist.get().getAuditResult())), "eduDate");


            /**
             * 医疗评分+教学评分
             */
            Optional<DcaBAuditdynamic> ywzylpf2 = listYwcYlpf.stream()
                    .filter(p -> p.getUserAccount().equals(userAccount)).findFirst();
            InsertDynamic(auditdynamicList, userAccount, String.valueOf(!ywzylpf2.isPresent() ? "" : GetNullStr(ywzylpf2.get().getAuditResult())), "ylpfbfz");

            Optional<DcaBAuditdynamic> ylpfdj222 = listYwcYlpfdj.stream()
                    .filter(p -> p.getUserAccount().equals(userAccount)).findFirst();
            InsertDynamic(auditdynamicList, userAccount, String.valueOf(!ylpfdj222.isPresent() ? "" : GetNullStr(ylpfdj222.get().getAuditResult())), "ylpfdj");

            Optional<DcaBAuditdynamic> jxpf222 = listjxpf.stream()
                    .filter(p -> p.getUserAccount().equals(userAccount)).findFirst();
            InsertDynamic(auditdynamicList, userAccount, String.valueOf(!jxpf222.isPresent() ? "" : GetNullStr(jxpf222.get().getAuditResult())), "jxpf");

            Optional<DcaBAuditdynamic> jxpf444 = listjxpfdj.stream()
                    .filter(p -> p.getUserAccount().equals(userAccount)).findFirst();
            InsertDynamic(auditdynamicList, userAccount, String.valueOf(!jxpf444.isPresent() ? "" : GetNullStr(jxpf444.get().getAuditResult())), "jxpfdj");

            double ylpfvalue = 0D;
            if (ywzylpf2.isPresent()) {
                if (ywzylpf2.get() != null) {
                    String yv = GetNullStrNumber(ywzylpf2.get().getAuditResult());
                    ylpfvalue = Double.parseDouble(yv.trim());
                    log.info("yw:" + ylpfvalue);
                }
            }
            // ylpfvalue = Convert.toDouble(!ywzylpf2.isPresent() ? "0" :GetNullStrNumber(ywzylpf2.get().getAuditResult()));

            // double jxpfvalue = Convert.toDouble(!jxpf222.isPresent() ? "0" : GetNullStrNumber(jxpf222.get().getAuditResult()));

            double jxpfvalue = 0D;
            if (jxpf222.isPresent()) {
                if (jxpf222.get() != null) {
                    String yv2 = GetNullStrNumber(jxpf222.get().getAuditResult());
                    jxpfvalue = Double.parseDouble(yv2.trim());
                    log.info("jxpfvalue:" + jxpfvalue);
                }
            }
            double countValue = NumberUtil.add(ylpfvalue, jxpfvalue);
            log.info("countValue:" + countValue);
            /**
             * 医疗评分+教学评分 合计   pf_heji
             */
            InsertDynamic(auditdynamicList, userAccount, String.valueOf(countValue), "pfHeji");

            //region 国家、省部级科研奖
            /**
             * 国家、省部级科研奖
             */

            List<DcaBScientificprize> sciencePrizeUserList = listSciencePrizeSearch.stream()
                    .filter(p -> p.getUserAccount().equals(userAccount)).collect(Collectors.toList());
            String name = sciencePrizeUserList.stream().map(p -> p.getAuditName() == null ? "" : p.getAuditName()).collect(Collectors.joining("#", "", ""));
            String denngji = sciencePrizeUserList.stream().map(p -> p.getAuditGrade() == null ? "" : p.getAuditGrade()).collect(Collectors.joining("#", "", ""));
            String ranknum = sciencePrizeUserList.stream().map(p -> p.getAuditRank() == null ? "" : String.valueOf(p.getAuditRank())).collect(Collectors.joining("#", "", ""));
            InsertDynamic(auditdynamicList, userAccount, name, "sciName");
            InsertDynamic(auditdynamicList, userAccount, denngji, "sciDengji");
            InsertDynamic(auditdynamicList, userAccount, ranknum, "sciRanknum");
            //endregion
            //region 国家、省部级教学获奖
            /**
             * 国家、省部级教学获奖
             */
            List<DcaBTeacherprize> teachprizeUserList = teacherprizeList.stream()
                    .filter(p -> p.getUserAccount().equals(userAccount)).collect(Collectors.toList());
            String p_name = teachprizeUserList.stream().map(p -> p.getPrizeName() == null ? "" : p.getPrizeName()).collect(Collectors.joining("#", "", ""));
            String p_denngji = teachprizeUserList.stream().map(p -> p.getPrizeGrade() == null ? "" : p.getPrizeGrade()).collect(Collectors.joining("#", "", ""));
            String p_ranknum = teachprizeUserList.stream().map(p -> p.getRanknum() == null ? "" : String.valueOf(p.getRanknum())).collect(Collectors.joining("#", "", ""));
            InsertDynamic(auditdynamicList, userAccount, p_name, "teachName");
            InsertDynamic(auditdynamicList, userAccount, p_denngji, "teachDengji");
            InsertDynamic(auditdynamicList, userAccount, p_ranknum, "teachRanknum");
//endregion

            //region 第一作者或通迅作者论文情况
            /**
             * 第一作者或通迅作者论文情况
             */
            List<DcaBSciencepublish> sciencepublishUserList = sciencepublishList.stream()
                    .filter(p -> p.getUserAccount().equals(userAccount)).collect(Collectors.toList());

            List<DcaBSciencepublish> professorList = sciencepublishUserList.stream().filter(p -> p.getIsJxzcsb() != null && p.getIsJxzcsb().equals("是")).collect(Collectors.toList());


            SetABCDEF_Jxzcsl(professorList, "A", "publishA", userAccount, auditdynamicList_jiaoshou);
            SetABCDEF_Jxzcsl(professorList, "B", "publishB", userAccount, auditdynamicList_jiaoshou);
            SetABCDEF_Jxzcsl(professorList, "C", "publishC", userAccount, auditdynamicList_jiaoshou);
            SetABCDEF_Jxzcsl(professorList, "D", "publishD", userAccount, auditdynamicList_jiaoshou);
            SetABCDEF_Jxzcsl(professorList, "E", "publishE", userAccount, auditdynamicList_jiaoshou);
            SetABCDEF_Jxzcsl(professorList, "F", "publishF", userAccount, auditdynamicList_jiaoshou);

            double auditD = auditdynamicList_jiaoshou.stream().filter(p -> "publishApublishBpublishCpublishD".contains(p.getAuditTitletype())).mapToDouble(p -> Convert.toDouble(p.getAuditResult() == null ? 0 : p.getAuditResult(), 0D)).sum();

            String auditDStr = String.format("%.2f", auditD);
            if (auditDStr.contains(".99")) {
                auditD = NumberUtil.add(auditD, 0.01D);
            }
            InsertDynamic(auditdynamicList_jiaoshou, userAccount, String.format("%.2f", auditD), "publishDup");

            double auditE = auditdynamicList_jiaoshou.stream().filter(p -> "publishApublishBpublishCpublishDpublishE".contains(p.getAuditTitletype())).mapToDouble(p -> Convert.toDouble(p.getAuditResult() == null ? 0 : p.getAuditResult(), 0D)).sum();

            String auditEStr = String.format("%.2f", auditE);
            if (auditEStr.contains(".99")) {
                auditE = NumberUtil.add(auditE, 0.01D);
            }
            InsertDynamic(auditdynamicList_jiaoshou, userAccount, String.format("%.2f", auditE), "publishEup");
            double auditF = auditdynamicList_jiaoshou.stream().filter(p -> "publishApublishBpublishCpublishDpublishEpublishF".contains(p.getAuditTitletype())).mapToDouble(p -> Convert.toDouble(p.getAuditResult() == null ? 0 : p.getAuditResult(), 0D)).sum();
            String auditFStr = String.format("%.2f", auditF);
            if (auditFStr.contains(".99")) {
                auditF = NumberUtil.add(auditF, 0.01D);
            }
            InsertDynamic(auditdynamicList_jiaoshou, userAccount, String.format("%.2f", auditF), "publishFup");


            List<DcaBSciencepublish> professorList2 = sciencepublishUserList.stream().filter(p -> p.getIsLczcsb() != null && p.getIsLczcsb().equals("是")).collect(Collectors.toList());
            SetABCDEF_Lczcsl(professorList2, "A", "publishA", userAccount, auditdynamicList_zhurenyishi);
            SetABCDEF_Lczcsl(professorList2, "B", "publishB", userAccount, auditdynamicList_zhurenyishi);
            SetABCDEF_Lczcsl(professorList2, "C", "publishC", userAccount, auditdynamicList_zhurenyishi);
            SetABCDEF_Lczcsl(professorList2, "D", "publishD", userAccount, auditdynamicList_zhurenyishi);
            SetABCDEF_Lczcsl(professorList2, "E", "publishE", userAccount, auditdynamicList_zhurenyishi);
            SetABCDEF_Lczcsl(professorList2, "F", "publishF", userAccount, auditdynamicList_zhurenyishi);

            double auditD2 = auditdynamicList_zhurenyishi.stream().filter(p -> "publishApublishBpublishCpublishD".contains(p.getAuditTitletype())).mapToDouble(p -> Convert.toDouble(p.getAuditResult() == null ? 0 : p.getAuditResult(), 0D)).sum();
            String auditD2Str = String.format("%.2f", auditD2);
            if (auditD2Str.contains(".99")) {
                auditD2 = NumberUtil.add(auditD2, 0.01D);
            }
            InsertDynamic(auditdynamicList_zhurenyishi, userAccount, String.format("%.2f", auditD2), "publishDup");
            double auditE2 = auditdynamicList_zhurenyishi.stream().filter(p -> "publishApublishBpublishCpublishDpublishE".contains(p.getAuditTitletype())).mapToDouble(p -> Convert.toDouble(p.getAuditResult() == null ? 0 : p.getAuditResult(), 0D)).sum();
            String auditE2Str = String.format("%.2f", auditE2);
            if (auditE2Str.contains(".99")) {
                auditE2 = NumberUtil.add(auditE2, 0.01D);
            }
            InsertDynamic(auditdynamicList_zhurenyishi, userAccount, String.format("%.2f", auditE2), "publishEup");
            double auditF2 = auditdynamicList_zhurenyishi.stream().filter(p -> "publishApublishBpublishCpublishDpublishEpublishF".contains(p.getAuditTitletype())).mapToDouble(p -> Convert.toDouble(p.getAuditResult() == null ? 0 : p.getAuditResult(), 0D)).sum();
            String auditF2Str = String.format("%.2f", auditF2);
            if (auditF2Str.contains(".99")) {
                auditF2 = NumberUtil.add(auditF2, 0.01D);
            }
            InsertDynamic(auditdynamicList_zhurenyishi, userAccount, String.format("%.2f", auditF2), "publishFup");
            //endregion


            //region 专利
            List<DcaBPatent> patentUserList = patentList.stream()
                    .filter(p -> p.getUserAccount().equals(userAccount)).collect(Collectors.toList());
            if (patentUserList.size() > 0) {
                List<String> patentStrList = new ArrayList<>();
                for (int index = 1; index <= patentUserList.size(); index++) {
                    patentStrList.add("专利" + String.valueOf(index));
                }
                String patent_name = patentStrList.stream().collect(Collectors.joining("#", "", ""));
                InsertDynamic(auditdynamicList, userAccount, patent_name, "patentNum");
                String patentFund = patentUserList.stream().map(p -> p.getPatentGood()==null?"":p.getPatentGood()).collect(Collectors.joining("#", "", ""));
                InsertDynamic(auditdynamicList, userAccount, patentFund, "patentFund");
            } else {
                InsertDynamic(auditdynamicList, userAccount, "", "patentNum");
                InsertDynamic(auditdynamicList, userAccount, "", "patentFund");
            }
            //enndregion


            //region 出版书类别   承担字数(万)
            List<DcaBPublicarticle> publicUserList = publicarticleList.stream()
                    .filter(p -> p.getUserAccount().equals(userAccount)).collect(Collectors.toList());
            long pL = publicUserList.stream().filter(p -> p.getCdzs().compareTo(new BigDecimal(0)) == 1).count();
            double pC = publicUserList.stream().mapToDouble(p -> Convert.toDouble(p.getCdzs() == null ? 0 : p.getCdzs())).sum();
            if (NumberUtil.compare(pL, 0) == 1) {
                InsertDynamic(auditdynamicList, userAccount, String.valueOf(pL) + "著", "publicarticle1");
            }
            InsertDynamic(auditdynamicList, userAccount, String.format("%.2f", pC), "publicarticle2");
            //endregion

            //region 教学质量奖与成果奖
            List<DcaBSchoolprize> schoolprizeUserList = schoolprizeList.stream()
                    .filter(p -> p.getUserAccount().equals(userAccount)).collect(Collectors.toList());
            String sp_name = schoolprizeUserList.stream().map(p -> GetNullStr(p.getPrizeName())).collect(Collectors.joining("#", "", ""));
            String sp_denngji = schoolprizeUserList.stream().map(p -> GetNullStr(p.getPrizeGrade())).collect(Collectors.joining("#", "", ""));
            String sp_date = schoolprizeUserList.stream().map(p -> GetNullStr(DateUtil.formatDate(p.getPrizeDate()))).collect(Collectors.joining("#", "", ""));
            String sp_ranknum = schoolprizeUserList.stream().map(p -> p.getRanknum() == null ? "" : String.valueOf(p.getRanknum())).collect(Collectors.joining("#", "", ""));
            InsertDynamic(auditdynamicList, userAccount, sp_name, "schoolprizeName");
            InsertDynamic(auditdynamicList, userAccount, sp_denngji, "schoolprizeDengji");
            InsertDynamic(auditdynamicList, userAccount, sp_date, "schoolprizeDate");
            InsertDynamic(auditdynamicList, userAccount, sp_ranknum, "schoolprizeRanknum");
            //endregion

            //region 精品课程
            List<DcaBCourseclass> courseUserList = courseclassList.stream()
                    .filter(p -> p.getUserAccount().equals(userAccount)).collect(Collectors.toList());
            String cc_name = courseUserList.stream().map(p ->GetNullStr(p.getCourse())).collect(Collectors.joining("#", "", ""));
            String cc_denngji = courseUserList.stream().map(p -> GetNullStr(p.getGrade())).collect(Collectors.joining("#", "", ""));
            String cc_date = courseUserList.stream().map(p -> GetNullStr(DateUtil.formatDate(p.getCoruseDate()))).collect(Collectors.joining("#", "", ""));
            String cc_ranknum = courseUserList.stream().map(p -> p.getRanknum() == null ? "" : String.valueOf(p.getRanknum())).collect(Collectors.joining("#", "", ""));
            InsertDynamic(auditdynamicList, userAccount, cc_name, "courseName");
            InsertDynamic(auditdynamicList, userAccount, cc_denngji, "courseDengji");
            InsertDynamic(auditdynamicList, userAccount, cc_date, "courseDate");
            InsertDynamic(auditdynamicList, userAccount, cc_ranknum, "courseRanknum");
            //endregion

            //region 教学竞赛获奖
            List<DcaBYoungprize> youngprizeUserList = youngprizeList.stream()
                    .filter(p -> p.getUserAccount().equals(userAccount)).collect(Collectors.toList());
            String yp_name = youngprizeUserList.stream().map(p ->GetNullStr(p.getPrizeJb())).collect(Collectors.joining("#", "", ""));
            String yp_denngji = youngprizeUserList.stream().map(p -> GetNullStr(p.getPrizeGrade())).collect(Collectors.joining("#", "", ""));
            String yp_date = youngprizeUserList.stream().map(p -> GetNullStr(DateUtil.formatDate(p.getPrizeDate()))).collect(Collectors.joining("#", "", ""));
            String yp_ranknum = youngprizeUserList.stream().map(p -> p.getRanknum() == null ? "" : String.valueOf(p.getRanknum())).collect(Collectors.joining("#", "", ""));
            InsertDynamic(auditdynamicList, userAccount, yp_name, "youngName");
            InsertDynamic(auditdynamicList, userAccount, yp_denngji, "youngDengji");
            InsertDynamic(auditdynamicList, userAccount, yp_date, "youngDate");
            InsertDynamic(auditdynamicList, userAccount, yp_ranknum, "youngRanknum");
            //endregion

            //region 科研项目教改项目
            List<DcaBSciencesearch> sciencesearchUserList = listScienceSearch.stream()
                    .filter(p -> p.getUserAccount().equals(userAccount)).collect(Collectors.toList());
            String ssu_lb = sciencesearchUserList.stream().filter(p -> p.getAuditTypetp() != null && p.getAuditTypetp().equals("按等级")).map(p -> p.getAuditLb() == null ? "" : String.valueOf(p.getAuditLb())).collect(Collectors.joining("#", "", ""));
            String ssu_fund = sciencesearchUserList.stream().filter(p -> p.getAuditTypetp() != null && p.getAuditTypetp().equals("按等级")).map(p -> p.getAuditFund() == null ? "" : String.valueOf(p.getAuditFund())).collect(Collectors.joining("#", "", ""));
            String ssu_ranknum = sciencesearchUserList.stream().filter(p -> p.getAuditTypetp() != null && p.getAuditTypetp().equals("按等级")).map(p -> p.getAuditRank() == null ? "" : String.valueOf(p.getAuditRank())).collect(Collectors.joining("#", "", ""));
            InsertDynamic(auditdynamicList, userAccount, ssu_lb, "sciDjlb");
            InsertDynamic(auditdynamicList, userAccount, ssu_fund, "sciDjfund");
            InsertDynamic(auditdynamicList, userAccount, ssu_ranknum, "sciDjranknum");

            String ssu_lb2 = sciencesearchUserList.stream().filter(p -> p.getAuditTypetp() != null && p.getAuditTypetp().equals("按经费")).map(p -> p.getAuditLb() == null ? "" : String.valueOf(p.getAuditLb())).collect(Collectors.joining("#", "", ""));
            String ssu_fund2 = sciencesearchUserList.stream().filter(p -> p.getAuditTypetp() != null && p.getAuditTypetp().equals("按经费")).map(p -> p.getAuditFund() == null ? "" : String.valueOf(p.getAuditFund())).collect(Collectors.joining("#", "", ""));
            String ssu_ranknum2 = sciencesearchUserList.stream().filter(p -> p.getAuditTypetp() != null && p.getAuditTypetp().equals("按经费")).map(p -> p.getAuditRank() == null ? "" : String.valueOf(p.getAuditRank())).collect(Collectors.joining("#", "", ""));
            InsertDynamic(auditdynamicList, userAccount, ssu_lb2, "sciJflb");
            InsertDynamic(auditdynamicList, userAccount, ssu_fund2, "sciJffund");
            InsertDynamic(auditdynamicList, userAccount, ssu_ranknum2, "sciJfranknum");
            //endregion


            //region 是否担任一年辅导员或班主任
            InsertDynamic(auditdynamicList, userAccount, tutorList.contains(userAccount) ? "是" : "否", "tutor");
            //endregion

            //region 教师资格证
            InsertDynamic(auditdynamicList, userAccount, teacherQualify.contains(userAccount) ? "是" : "否", "teacherQualify");
            //endregion

            //region 部门审核结果
            InsertDynamic(auditdynamicList, userAccount, dynamicIsOk.contains(userAccount) ? "异常" : "正常", "auditMan");

            //region 出国情况
            List<DcaBExportcountry> boradUserList = borad.stream()
                    .filter(p -> p.getUserAccount().equals(userAccount)).collect(Collectors.toList());

            String gj = boradUserList.stream().map(p -> p.getLxgj()).collect(Collectors.joining("#", "", ""));
            InsertDynamic(auditdynamicList, userAccount, gj, "borad");
            //endregion


            List<DcaBAuditdynamic> auditdynamicList2 = new ArrayList<>();
            auditdynamicList2.addAll(auditdynamicList);
            if (user.getNpPositionName().equals("教授") || user.getNpPositionName().equals("副教授")) {
                auditdynamicList2.addAll(auditdynamicList_jiaoshou);
            } else {
                auditdynamicList2.addAll(auditdynamicList_zhurenyishi);
            }
            dcaBReport2.setDcaBAuditdynamicList(auditdynamicList2);
            allData.add(dcaBReport2);


        }

        return allData;
    }

}