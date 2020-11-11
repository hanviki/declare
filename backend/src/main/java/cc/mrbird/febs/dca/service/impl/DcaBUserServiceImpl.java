package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.dao.DcaBAuditdynamicMapper;
import cc.mrbird.febs.dca.dao.DcaBEducationexpericeMapper;
import cc.mrbird.febs.dca.entity.DcaBAuditdynamic;
import cc.mrbird.febs.dca.entity.DcaBEducationexperice;
import cc.mrbird.febs.dca.entity.DcaBUser;
import cc.mrbird.febs.dca.dao.DcaBUserMapper;
import cc.mrbird.febs.dca.entity.userAuditAccount;
import cc.mrbird.febs.dca.service.IDcaBUserService;
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
    public List<DcaBAuditdynamic> getAllInfo(List<String> listUniqUser ) {
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
        queryWrapper.eq(DcaBAuditdynamic::getIsDeletemark,1);
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
        List<String> listOthes=listUniqUser.stream().filter(p->!listUnique.contains(p)).collect(Collectors.toList());
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
        DoEvery(userAndAccount, "xsddsc", "10011471", "kyc", auditdynamicList, listAll, listKy,listUniqUser);
        // 0-科研处 end

        //医务处
        // 科研处
        List<String> listYWC = new ArrayList<>();
        DoEvery(userAndAccount, "ylpfbfz", "10010800", "ywc", auditdynamicList, listAll, listYWC,listUniqUser);

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
        DoEvery(userAndAccount, "jxpf", "10010937", "jxglbgs", auditdynamicList, listAll, listJxglbgs,listUniqUser);

        // 研究生管理办公室
        List<String> listYjsglbgs = new ArrayList<>();
        listYjsglbgs.addAll(talentUndo);
        listYjsglbgs.addAll(graduateUndo);
        DoEvery(userAndAccount, "sftgsdsf", "10011654", "yjsglbgs", auditdynamicList, listAll, listYjsglbgs,listUniqUser);

        //党办
        List<String> listDb = new ArrayList<>();
        DoEvery(userAndAccount, "ydyf", "10040430", "db", auditdynamicList, listAll, listDb,listUniqUser);

        //党委组织部
        List<String> listDwzzb = new ArrayList<>();
        DoEvery(userAndAccount, "zzsc", "10040495", "dwzzb", auditdynamicList, listAll, listDwzzb,listUniqUser);

        //纪委办公室
        List<String> listJwbgs = new ArrayList<>();
        DoEvery(userAndAccount, "jlsc", "10040493", "jwbgs", auditdynamicList, listAll, listJwbgs,listUniqUser);

        //宣传部
        List<String> listCcb = new ArrayList<>();
        DoEvery(userAndAccount, "yyxtsc", "10011136", "ccb", auditdynamicList, listAll, listCcb,listUniqUser);

        //门诊办公室
        List<String> listMzbgs = new ArrayList<>();
        DoEvery(userAndAccount, "mzylpf", "10010352", "mzbgs", auditdynamicList, listAll, listMzbgs,listUniqUser);

        //护理部
        List<String> listHlb = new ArrayList<>();
        DoEvery(userAndAccount, "ylpfbfz", "10020783", "hlb", auditdynamicList, listAll, listHlb,listUniqUser);

        //行风建设办公室
        List<String> listHfjsbgs = new ArrayList<>();
        DoEvery(userAndAccount, "sshbdts", "10011480", "hfjsbgs", auditdynamicList, listAll, listHfjsbgs,listUniqUser);
        return listAll;
    }

    private void DoEvery(List<userAuditAccount> userAndAccount, String filedtitletype, String username, String title, List<DcaBAuditdynamic> auditdynamicList, List<DcaBAuditdynamic> listAll, List<String> listYWC,List<String> listUniqUser) {

        List<userAuditAccount> userAuditAccountListYwc = userAndAccount.stream().filter(p -> p.getUserName().equals(username)).collect(Collectors.toList());
       List<String> listau= new ArrayList<>();
       List<String> userUn=userAuditAccountListYwc.stream().map(p->p.getUserAccount()).distinct().collect(Collectors.toList());
       if(listYWC.size()>0) {

           for (userAuditAccount item : userAuditAccountListYwc
           ) {
               long c = auditdynamicList.stream().filter(p -> p.getUserAccount().equals(item.getUserAccount()) &&
                       p.getAuditTitletype().equals(filedtitletype)).count();
               long p = 0L;
               if (c > p) {
                   if(!listau.contains(item.getUserAccount())){
                       listau.add(item.getUserAccount());//已完成数据
                       }
               }

           }
           List<String> undou=userUn.stream().filter(p->!(listau.contains(p) ||listYWC.contains(p))).collect(Collectors.toList());
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
           List<String> otherYwNoall=listUniqUser.stream().filter(p->!(listYWC.contains(p)||undou.contains(p))).collect(Collectors.toList());
           for (String item : otherYwNoall
           ) {
               DcaBAuditdynamic dy = new DcaBAuditdynamic();
               dy.setUserAccount(item);
               dy.setAuditTitletype(title);
               dy.setAuditResult("已完成");
               listAll.add(dy);
           }
       }
       else{
           for (userAuditAccount item : userAuditAccountListYwc
           ) {
               long c = auditdynamicList.stream().filter(p -> p.getUserAccount().equals(item.getUserAccount()) &&
                       p.getAuditTitletype().equals(filedtitletype)).count();
               long p = 0L;
               if (c > p) {
                   if(!listau.contains(item.getUserAccount())) {
                       DcaBAuditdynamic dy = new DcaBAuditdynamic();
                       dy.setUserAccount(item.getUserAccount());
                       dy.setAuditTitletype(title);
                       dy.setAuditResult("已完成");
                       listAll.add(dy);
                       listau.add(item.getUserAccount());
                   }
               }
           }
           List<String> otherYwNo=userUn.stream().filter(p->!listau.contains(p)).collect(Collectors.toList());
           for (String item : otherYwNo
           ) {
               DcaBAuditdynamic dy = new DcaBAuditdynamic();
               dy.setUserAccount(item);
               dy.setAuditTitletype(title);
               dy.setAuditResult("未完成");
               listAll.add(dy);
           }

           List<String> otherYwNoall=listUniqUser.stream().filter(p->!userUn.contains(p)).collect(Collectors.toList());
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
}