package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.dao.DcaBAuditdynamicMapper;
import cc.mrbird.febs.dca.dao.DcaBEducationexpericeMapper;
import cc.mrbird.febs.dca.dao.DcaBWorknumMapper;
import cc.mrbird.febs.dca.entity.*;
import cc.mrbird.febs.dca.dao.DcaBUserMapper;
import cc.mrbird.febs.dca.service.IDcaBReportService;
import cc.mrbird.febs.dca.service.IDcaBUserService;
import cc.mrbird.febs.dca.service.IDcaBUserapplyService;
import cc.mrbird.febs.dcacopy.dao.DcaBCopyAuditdynamicMapper;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyAuditdynamic;
import cn.hutool.Hutool;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
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
import java.util.concurrent.CountDownLatch;
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
    private DcaBCopyAuditdynamicMapper dcaBCopyAuditdynamicMapper;
    @Autowired
    private IDcaBReportService iDcaBReportService;
    @Autowired
    IDcaBUserapplyService iDcaBUserapplyService;
    @Autowired
    private DcaBWorknumMapper dcaBWorknumMapper;

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

                queryWrapper2.and(wrap -> wrap.like(DcaBEducationexperice::getExpPosition, "博士").or().like(DcaBEducationexperice::getExpPosition, "硕博"));

                queryWrapper2.eq(DcaBEducationexperice::getUserAccount, user.getUserAccount());
                queryWrapper2.eq(DcaBEducationexperice::getIsDeletemark, 1);
                // queryWrapper2.eq(DcaBEducationexperice::getState, 3);
                queryWrapper2.notLike(DcaBEducationexperice::getExpPosition, "博士后");
                List<DcaBEducationexperice> dcaBEducationexpericeList = this.dcaBEducationexpericeMapper.selectList(queryWrapper2);
                if (dcaBEducationexpericeList.size() > 0) {
                    List<DcaBEducationexperice> dcaBoshiList = dcaBEducationexpericeList.stream().filter(p -> p.getExpPosition().equals("博士")).collect(Collectors.toList());
                    if (dcaBoshiList.size() > 0) {
                        if (dcaBoshiList.get(0).getExpEndTime() != null) {
                            user.setDoctorDesc(new SimpleDateFormat("yyyyMM").format(dcaBoshiList.get(0).getExpEndTime()));
                        }
                    } else {
                        List<DcaBEducationexperice> dcaShuoBoList = dcaBEducationexpericeList.stream().filter(p -> p.getExpPosition().contains("硕博")).collect(Collectors.toList());
                        if (dcaShuoBoList.size() > 0) {
                            if (dcaShuoBoList.get(0).getExpEndTime() != null) {
                                user.setDoctorDesc(new SimpleDateFormat("yyyyMM").format(dcaShuoBoList.get(0).getExpEndTime()));
                            }
                        } else {
                            if (dcaBEducationexpericeList.get(0).getExpEndTime() != null) {
                                user.setDoctorDesc(new SimpleDateFormat("yyyyMM").format(dcaBEducationexpericeList.get(0).getExpEndTime()));
                            }
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
    public IPage<DcaBUser> findDcaBUsers(QueryRequest request, DcaBUser dcaBUser) {
        try {
            LambdaQueryWrapper<DcaBUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DcaBUser::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(dcaBUser.getUserAccount())) {
                queryWrapper.and(wrap -> wrap.eq(DcaBUser::getUserAccount, dcaBUser.getUserAccount()).or()
                        .like(DcaBUser::getUserAccountName, dcaBUser.getUserAccount()));

            }
            if (StringUtils.isNotBlank(dcaBUser.getAuditManName())) {// 年度 和高级、中级、初级
                List<String> userAccountsList = this.iDcaBUserapplyService.getApplyAccount(dcaBUser.getAuditMan(), dcaBUser.getAuditManName());
                if (userAccountsList.size() == 0) {
                    userAccountsList.add("qiuc09");
                }
                queryWrapper.in(DcaBUser::getUserAccount, userAccountsList);
            }
            if (dcaBUser.getState() != null) {
                queryWrapper.eq(DcaBUser::getState, dcaBUser.getState());
            }
            queryWrapper.apply(" LENGTH(dca_b_user.dca_year)>0 ");


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
            if (StringUtils.isNotBlank(dcaBUser.getDoctorDesc())) {
                String[] listKs = dcaBUser.getDoctorDesc().split(StringPool.COMMA);
                queryWrapper.in(DcaBUser::getKs, listKs);
            }
            if (StringUtils.isNotBlank(dcaBUser.getDcaYear())) {
                queryWrapper.eq(DcaBUser::getDcaYear, dcaBUser.getDcaYear());
            }
            if (StringUtils.isNotBlank(dcaBUser.getKs())) {
                queryWrapper.apply("dca_b_user.user_account  in (select  user_account from dca_b_userapply where state=1 and  LOCATE(gwdj, '" + dcaBUser.getKs() + "')>0)");
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
            } else {//往年的数据 从之前的获取
                if (listResult.getRecords().size() > 0) {
                    List<String> listDynamic = listResult.getRecords().stream().map(p -> p.getUserAccount()).collect(Collectors.toList());
                    LambdaQueryWrapper<DcaBWorknum> queryWrapperDynamic = new LambdaQueryWrapper<>();

                    List<String> yearList = new ArrayList<>();
                    if (listDynamic.size() > 0) {
                        queryWrapperDynamic.in(DcaBWorknum::getUserAccount, listDynamic);
                        queryWrapperDynamic.eq(DcaBWorknum::getIsDeletemark, 1);
                        List<DcaBWorknum> worknumList = this.dcaBWorknumMapper.selectList(queryWrapperDynamic);
                        //  log.info("list:"+String.valueOf(worknumList.size()));
                        for (DcaBUser user : listResult.getRecords()
                        ) {
                            int year_qu = Integer.parseInt(user.getDcaYear().trim()) - 2;
                            int year_qn = Integer.parseInt(user.getDcaYear().trim()) - 3;
                            int year = Integer.parseInt(user.getDcaYear().trim()) - 1;
                            //  log.info("year_qu:"+String.valueOf(year_qu));
                            // log.info("year_qn:"+String.valueOf(year_qn));
                            List<DcaBWorknum> listDy_qu = worknumList.stream().filter(p -> p.getUserAccount().equals(user.getUserAccount()) && p.getYear().equals(year_qu)).collect(Collectors.toList());
                            List<DcaBWorknum> listDy_qn = worknumList.stream().filter(p -> p.getUserAccount().equals(user.getUserAccount()) && p.getYear().equals(year_qn)).collect(Collectors.toList());
                            List<DcaBWorknum> listDy_now = worknumList.stream().filter(p -> p.getUserAccount().equals(user.getUserAccount()) && p.getYear().equals(year)).collect(Collectors.toList());
                            // user.setDcaBAuditdynamicList(listDy);
                            //  log.info("listDy_now:"+String.valueOf(listDy_now.size()));
                            List<DcaBAuditdynamic> lidy = new ArrayList<>();
                            for (DcaBWorknum cy : listDy_qu
                            ) {
                                ConvertAuditResult(cy, "2020", lidy);
                            }
                            for (DcaBWorknum cy : listDy_qn
                            ) {
                                ConvertAuditResult(cy, "2019", lidy);
                            }
                            for (DcaBWorknum cy : listDy_now
                            ) {
                                ConvertAuditResult(cy, "2021", lidy);
                            }
                            user.setDcaBAuditdynamicList(lidy);
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

    private void ConvertAuditResult(DcaBWorknum dcaBWorknum, String type, List<DcaBAuditdynamic> lidy) {

        if (type.equals("2019")) {
            DcaBAuditdynamic auditdynamic = new DcaBAuditdynamic();
            auditdynamic.setAuditTitletype("ddqnmzgzl");
            auditdynamic.setAuditResult(String.valueOf(dcaBWorknum.getMzbrl()));
            lidy.add(auditdynamic);
            DcaBAuditdynamic auditdynamic2 = new DcaBAuditdynamic();
            auditdynamic2.setAuditTitletype("ddqnglzybrl");
            auditdynamic2.setAuditResult(String.valueOf(dcaBWorknum.getGlzybrl()));
            lidy.add(auditdynamic2);
            DcaBAuditdynamic auditdynamic3 = new DcaBAuditdynamic();
            auditdynamic3.setAuditTitletype("ddqsybrl");
            auditdynamic3.setAuditResult(String.valueOf(dcaBWorknum.getSsbrl()));
            lidy.add(auditdynamic3);

            DcaBAuditdynamic auditdynamic41 = new DcaBAuditdynamic();
            auditdynamic41.setAuditTitletype("dqnssbrl1");
            auditdynamic41.setAuditResult(String.valueOf(dcaBWorknum.getSsbrl1()));
            lidy.add(auditdynamic41);

            DcaBAuditdynamic auditdynamic42 = new DcaBAuditdynamic();
            auditdynamic42.setAuditTitletype("dqnssbrl2");
            auditdynamic42.setAuditResult(String.valueOf(dcaBWorknum.getSsbrl2()));
            lidy.add(auditdynamic42);

            DcaBAuditdynamic auditdynamic43 = new DcaBAuditdynamic();
            auditdynamic43.setAuditTitletype("dqnssbrl3");
            auditdynamic43.setAuditResult(String.valueOf(dcaBWorknum.getSsbrl3()));
            lidy.add(auditdynamic43);

            DcaBAuditdynamic auditdynamic44 = new DcaBAuditdynamic();
            auditdynamic44.setAuditTitletype("dqnssbrl4");
            auditdynamic44.setAuditResult(String.valueOf(dcaBWorknum.getSsbrl4()));
            lidy.add(auditdynamic44);
        }
        if (type.equals("2020")) {
            DcaBAuditdynamic auditdynamic = new DcaBAuditdynamic();
            auditdynamic.setAuditTitletype("dqnmzgzl");
            auditdynamic.setAuditResult(String.valueOf(dcaBWorknum.getMzbrl()));
            lidy.add(auditdynamic);
            DcaBAuditdynamic auditdynamic2 = new DcaBAuditdynamic();
            auditdynamic2.setAuditTitletype("dqnglzybrl");
            auditdynamic2.setAuditResult(String.valueOf(dcaBWorknum.getGlzybrl()));
            lidy.add(auditdynamic2);
            DcaBAuditdynamic auditdynamic3 = new DcaBAuditdynamic();
            auditdynamic3.setAuditTitletype("dqnsybrl");
            auditdynamic3.setAuditResult(String.valueOf(dcaBWorknum.getSsbrl()));
            lidy.add(auditdynamic3);

            DcaBAuditdynamic auditdynamic41 = new DcaBAuditdynamic();
            auditdynamic41.setAuditTitletype("qnbrlss1");
            auditdynamic41.setAuditResult(String.valueOf(dcaBWorknum.getSsbrl1()));
            lidy.add(auditdynamic41);

            DcaBAuditdynamic auditdynamic42 = new DcaBAuditdynamic();
            auditdynamic42.setAuditTitletype("qnbrlss2");
            auditdynamic42.setAuditResult(String.valueOf(dcaBWorknum.getSsbrl2()));
            lidy.add(auditdynamic42);

            DcaBAuditdynamic auditdynamic43 = new DcaBAuditdynamic();
            auditdynamic43.setAuditTitletype("qnbrlss3");
            auditdynamic43.setAuditResult(String.valueOf(dcaBWorknum.getSsbrl3()));
            lidy.add(auditdynamic43);

            DcaBAuditdynamic auditdynamic44 = new DcaBAuditdynamic();
            auditdynamic44.setAuditTitletype("qnbrlss4");
            auditdynamic44.setAuditResult(String.valueOf(dcaBWorknum.getSsbrl4()));
            lidy.add(auditdynamic44);
        }
        if (type.equals("2021")) {
            DcaBAuditdynamic auditdynamic = new DcaBAuditdynamic();
            auditdynamic.setAuditTitletype("qnmzgzl");
            auditdynamic.setAuditResult(String.valueOf(dcaBWorknum.getMzbrl()));
            lidy.add(auditdynamic);
            DcaBAuditdynamic auditdynamic2 = new DcaBAuditdynamic();
            auditdynamic2.setAuditTitletype("qnglzybrl");
            auditdynamic2.setAuditResult(String.valueOf(dcaBWorknum.getGlzybrl()));
            lidy.add(auditdynamic2);
            DcaBAuditdynamic auditdynamic3 = new DcaBAuditdynamic();
            auditdynamic3.setAuditTitletype("qnsybrl");
            auditdynamic3.setAuditResult(String.valueOf(dcaBWorknum.getSsbrl()));
            lidy.add(auditdynamic3);

            DcaBAuditdynamic auditdynamic41 = new DcaBAuditdynamic();
            auditdynamic41.setAuditTitletype("qnbrssl1");
            auditdynamic41.setAuditResult(String.valueOf(dcaBWorknum.getSsbrl1()));
            lidy.add(auditdynamic41);

            DcaBAuditdynamic auditdynamic42 = new DcaBAuditdynamic();
            auditdynamic42.setAuditTitletype("qnbrssl2");
            auditdynamic42.setAuditResult(String.valueOf(dcaBWorknum.getSsbrl2()));
            lidy.add(auditdynamic42);

            DcaBAuditdynamic auditdynamic43 = new DcaBAuditdynamic();
            auditdynamic43.setAuditTitletype("qnbrssl3");
            auditdynamic43.setAuditResult(String.valueOf(dcaBWorknum.getSsbrl3()));
            lidy.add(auditdynamic43);

            DcaBAuditdynamic auditdynamic44 = new DcaBAuditdynamic();
            auditdynamic44.setAuditTitletype("qnbrssl4");
            auditdynamic44.setAuditResult(String.valueOf(dcaBWorknum.getSsbrl4()));
            lidy.add(auditdynamic44);
        }
    }

    private String ConvertAuditResult(String titleType) {
        if (titleType.equals("dqnmzgzl"))//前年门诊工作量
        {
            return "ddqnmzgzl";//大前年门诊工作量
        }
        if (titleType.equals("qnmzgzl")) //去年门诊工作量
        {
            return "dqnmzgzl";//前年门诊工作量
        }
        if (titleType.equals("dqnglzybrl"))//前年出院病人量
        {
            return "ddqnglzybrl";//大前年出院病人量
        }
        if (titleType.equals("qnglzybrl"))//去年出院病人量
        {
            return "dqnglzybrl";//前年出院病人量
        }
        if (titleType.equals("dqnsybrl"))//前年手术病人量(总）
        {
            return "ddqsybrl";//大前年手术病人量(总）
        }
        if (titleType.equals("qnsybrl"))//去年手术病人量(总）
        {
            return "dqnsybrl";//前年手术病人量(总）
        }
        if (titleType.equals("qnbrlss1"))//前年手术病人量（1）
        {
            return "dqnssbrl1";//大前年手术病人量（1）
        }
        if (titleType.equals("qnbrlss2")) {
            return "dqnssbrl2";
        }
        if (titleType.equals("qnbrlss3")) {
            return "dqnssbrl3";
        }
        if (titleType.equals("qnbrlss4")) {
            return "dqnssbrl4";
        }
        if (titleType.equals("qnbrssl1"))//去年手术病人量（1）
        {
            return "qnbrlss1";//前年手术病人量（1）
        }
        if (titleType.equals("qnbrssl2")) {
            return "qnbrlss2";
        }
        if (titleType.equals("qnbrssl3")) {
            return "qnbrlss3";
        }
        if (titleType.equals("qnbrssl4")) {
            return "qnbrlss4";
        }


        return "";
    }


    @Override
    @Transactional
    public IPage<DcaBUser> findDcaBUsersAuditCustom(QueryRequest request, DcaBUser dcaBUser, int state) {
        try {
            LambdaQueryWrapper<DcaBUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DcaBUser::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(dcaBUser.getUserAccount())) {
                queryWrapper.and(wrap -> wrap.eq(DcaBUser::getUserAccount, dcaBUser.getUserAccount()).or()
                        .like(DcaBUser::getUserAccountName, dcaBUser.getUserAccount()));

            }
            if (StringUtils.isNotBlank(dcaBUser.getDcaYear())) {
                queryWrapper.eq(DcaBUser::getDcaYear, dcaBUser.getDcaYear());
            }
            if (StringUtils.isNotBlank(dcaBUser.getDoctorDesc())) {
                String[] listKs = dcaBUser.getDoctorDesc().split(StringPool.COMMA);
                queryWrapper.in(DcaBUser::getKs, listKs);
            }
            if (StringUtils.isNotBlank(dcaBUser.getKs())) {
                queryWrapper.apply("dca_b_user.user_account  in (select  user_account from dca_b_userapply where state=1 and  LOCATE(gwdj, '" + dcaBUser.getKs() + "')>0)");
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


    private void GetUserAccount_Thread(List<String> userAccounts, int index, CountDownLatch countDownLatch, List<String> listUniqUser) {
        switch (index) {
            case 1:
                userAccounts = this.baseMapper.getParttimeUndo();
                break;
            case 2:
                userAccounts = this.baseMapper.getPrizeorpunishUndo();
                break;
            case 3:
                userAccounts = this.baseMapper.getEducationexpericeUndo();
                break;
            case 4:
                userAccounts = this.baseMapper.getSciencepublishUndo();
                break;
            case 5:
                userAccounts = this.baseMapper.getSciencesearchUndo();
                break;
            case 6:
                userAccounts = this.baseMapper.getPatentUndo();
                break;
            case 7:
                userAccounts = this.baseMapper.getDca_b_teacherqualifyUndo();
                break;
            case 8:
                userAccounts = this.baseMapper.getDca_b_attachfileUndo();
                break;
            case 9:
                userAccounts = this.baseMapper.getDca_b_exportcountryUndo();
                break;
            case 10:
                userAccounts = this.baseMapper.getDca_b_publicarticleUndo();
                break;
            case 11:
                userAccounts = this.baseMapper.getDca_b_scientificprizeUndo();
                break;
            case 12:
                userAccounts = this.baseMapper.getDca_b_employUndo();
                break;
            case 13:
                userAccounts = this.baseMapper.getDca_b_turtorUndo();
                break;
            case 14:
                userAccounts = this.baseMapper.getDca_b_undergraduateUndo();
                break;
            case 15:
                userAccounts = this.baseMapper.getDca_b_innovatebuildUndo();
                break;
            case 16:
                userAccounts = this.baseMapper.getDca_b_undergraduateprizeUndo();
                break;
            case 17:
                userAccounts = this.baseMapper.getDca_b_teacherprizeUndo();
                break;
            case 18:
                userAccounts = this.baseMapper.getDca_b_schoolprizeUndo();
                break;
            case 19:
                userAccounts = this.baseMapper.getDca_b_courseclassUndo();
                break;
            case 20:
                userAccounts = this.baseMapper.getDca_b_youngprizeUndo();
                break;
            case 21:
                userAccounts = this.baseMapper.getDca_b_talentUndo();
                break;
            case 22:
                userAccounts = this.baseMapper.getDca_b_graduateUndo();
                break;
        }
        userAccounts = userAccounts.stream().filter(p -> listUniqUser.contains(p)).collect(Collectors.toList());
        countDownLatch.countDown();
    }

    @Override
    @Transactional
    public List<DcaBAuditdynamic> getAllInfo(List<String> listUniqUser) {


        //region 旧方法
        /** List<String> parttimrjob = this.baseMapper.getParttimeUndo();
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
         List<String> graduateUndo = this.baseMapper.getDca_b_graduateUndo();*/
        //endregion
        CountDownLatch countDownLatch_1 = new CountDownLatch(22);
        List<String> parttimrjob = new ArrayList<>();
        new Thread() {
            public void run() {
                GetUserAccount_Thread(parttimrjob, 1, countDownLatch_1, listUniqUser);
                Thread.currentThread().interrupt();
            }
        }.start();
        List<String> prizeorpunishUndo = new ArrayList<>();
        new Thread() {
            public void run() {
                GetUserAccount_Thread(prizeorpunishUndo, 2, countDownLatch_1, listUniqUser);
                Thread.currentThread().interrupt();
            }
        }.start();
        List<String> educationexpericeUndo = new ArrayList<>();
        new Thread() {
            public void run() {
                GetUserAccount_Thread(educationexpericeUndo, 3, countDownLatch_1, listUniqUser);
                Thread.currentThread().interrupt();
            }
        }.start();
        List<String> sciencepublishUndo = new ArrayList<>();
        new Thread() {
            public void run() {
                GetUserAccount_Thread(sciencepublishUndo, 4, countDownLatch_1, listUniqUser);
                Thread.currentThread().interrupt();
            }
        }.start();
        List<String> sciencesearchUndo = new ArrayList<>();
        new Thread() {
            public void run() {
                GetUserAccount_Thread(sciencesearchUndo, 5, countDownLatch_1, listUniqUser);
                Thread.currentThread().interrupt();
            }
        }.start();
        List<String> patentUndo = new ArrayList<>();
        new Thread() {
            public void run() {
                GetUserAccount_Thread(patentUndo, 6, countDownLatch_1, listUniqUser);
                Thread.currentThread().interrupt();
            }
        }.start();
        List<String> teacherqualifyUndo = new ArrayList<>();
        new Thread() {
            public void run() {
                GetUserAccount_Thread(teacherqualifyUndo, 7, countDownLatch_1, listUniqUser);
                Thread.currentThread().interrupt();
            }
        }.start();
        List<String> attachfileUndo = new ArrayList<>();
        new Thread() {
            public void run() {
                GetUserAccount_Thread(attachfileUndo, 8, countDownLatch_1, listUniqUser);
                Thread.currentThread().interrupt();
            }
        }.start();
        List<String> exportcountryUndo = new ArrayList<>();
        new Thread() {
            public void run() {
                GetUserAccount_Thread(exportcountryUndo, 9, countDownLatch_1, listUniqUser);
                Thread.currentThread().interrupt();
            }
        }.start();
        List<String> publicarticleUndo = new ArrayList<>();
        new Thread() {
            public void run() {
                GetUserAccount_Thread(publicarticleUndo, 10, countDownLatch_1, listUniqUser);
                Thread.currentThread().interrupt();
            }
        }.start();
        List<String> scientificprizeUndo = new ArrayList<>();
        new Thread() {
            public void run() {
                GetUserAccount_Thread(scientificprizeUndo, 11, countDownLatch_1, listUniqUser);
                Thread.currentThread().interrupt();
            }
        }.start();
        List<String> employUndo = new ArrayList<>();
        new Thread() {
            public void run() {
                GetUserAccount_Thread(employUndo, 12, countDownLatch_1, listUniqUser);
                Thread.currentThread().interrupt();
            }
        }.start();
        List<String> turtorUndo = new ArrayList<>();
        new Thread() {
            public void run() {
                GetUserAccount_Thread(turtorUndo, 13, countDownLatch_1, listUniqUser);
                Thread.currentThread().interrupt();
            }
        }.start();
        List<String> undergraduateUndo = new ArrayList<>();
        new Thread() {
            public void run() {
                GetUserAccount_Thread(undergraduateUndo, 14, countDownLatch_1, listUniqUser);
                Thread.currentThread().interrupt();
            }
        }.start();
        List<String> innovatebuildUndo = new ArrayList<>();
        new Thread() {
            public void run() {
                GetUserAccount_Thread(innovatebuildUndo, 15, countDownLatch_1, listUniqUser);
                Thread.currentThread().interrupt();
            }
        }.start();
        List<String> undergraduateprizeUndo = new ArrayList<>();
        new Thread() {
            public void run() {
                GetUserAccount_Thread(undergraduateprizeUndo, 16, countDownLatch_1, listUniqUser);
                Thread.currentThread().interrupt();
            }
        }.start();
        List<String> teacherprizeUndo = new ArrayList<>();
        new Thread() {
            public void run() {
                GetUserAccount_Thread(teacherprizeUndo, 17, countDownLatch_1, listUniqUser);
                Thread.currentThread().interrupt();
            }
        }.start();
        List<String> schoolprizeUndo = new ArrayList<>();
        new Thread() {
            public void run() {
                GetUserAccount_Thread(teacherprizeUndo, 18, countDownLatch_1, listUniqUser);
                Thread.currentThread().interrupt();
            }
        }.start();
        List<String> courseclassUndo = new ArrayList<>();
        new Thread() {
            public void run() {
                GetUserAccount_Thread(courseclassUndo, 19, countDownLatch_1, listUniqUser);
                Thread.currentThread().interrupt();
            }
        }.start();
        List<String> youngprizeUndo = new ArrayList<>();
        new Thread() {
            public void run() {
                GetUserAccount_Thread(youngprizeUndo, 20, countDownLatch_1, listUniqUser);
                Thread.currentThread().interrupt();
            }
        }.start();
        List<String> talentUndo = new ArrayList<>();
        new Thread() {
            public void run() {
                GetUserAccount_Thread(talentUndo, 21, countDownLatch_1, listUniqUser);
                Thread.currentThread().interrupt();
            }
        }.start();
        List<String> graduateUndo = new ArrayList<>();
        new Thread() {
            public void run() {
                GetUserAccount_Thread(graduateUndo, 22, countDownLatch_1, listUniqUser);
                Thread.currentThread().interrupt();
            }
        }.start();
        try {
            countDownLatch_1.await();
        } catch (Exception ex) {

        }
        log.info("数据获取部分完成");
        //党员审核数据
        String[] listtype = new String[]{"xsddsc", "ylpfbfz", "jxpf", "sftgsdsf", "ydyf", "zzsc", "jlsc", "yyxtsc", "mzylpf", "sshbdts"};
        LambdaQueryWrapper<DcaBAuditdynamic> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBAuditdynamic::getIsDeletemark, 1);
        queryWrapper.in(DcaBAuditdynamic::getUserAccount, listUniqUser);
        queryWrapper.in(DcaBAuditdynamic::getAuditTitletype, listtype);
        List<DcaBAuditdynamic> auditdynamicList = this.dcaBAuditdynamicMapper.selectList(queryWrapper);
        // List<DcaBAuditdynamic> auditdynamicList=auditdynamicList2.stream().filter(p->listUniqUser.contains(p.getUserAccount())).collect(Collectors.toList());
        List<userAuditAccount> userAndAccount = this.baseMapper.getUserAndAccount();//userAuditAccount
        List<DcaBAuditdynamic> listAll = Collections.synchronizedList(new ArrayList<>());
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

        CountDownLatch countDownLatch = ThreadUtil.newCountDownLatch(11);


        // 科研处
        List<String> listKy = new ArrayList<>();
        listKy.addAll(sciencesearchUndo);
        listKy.addAll(scientificprizeUndo);

//        new Thread() {
//            public void run() {
//                DoEvery(userAndAccount, "xsddsc", "10011471", "kyc", auditdynamicList, listAll, listKy, listUniqUser, countDownLatch);
//
//                countDownLatch.countDown();
//                Thread.currentThread().interrupt();
//            }
//        }.start();
        ThreadUtil.execute(() -> DoEvery(userAndAccount, "xsddsc", "10011292", "kyc", auditdynamicList, listAll, listKy, listUniqUser, countDownLatch));
        // DoEvery(userAndAccount, "xsddsc", "10011471", "kyc", auditdynamicList, listAll, listKy, listUniqUser);
        // 0-科研处 end

        //医务处
        // 科研处
        List<String> listYWC = new ArrayList<>();
//        new Thread() {
//            public void run() {
//                DoEvery(userAndAccount, "ylpfbfz", "10010800", "ywc", auditdynamicList, listAll, listYWC, listUniqUser, countDownLatch);
//
//                countDownLatch.countDown();
//                Thread.currentThread().interrupt();
//            }
//        }.start();
        ThreadUtil.execute(() -> DoEvery(userAndAccount, "ylpfbfz", "10010800", "ywc", auditdynamicList, listAll, listYWC, listUniqUser, countDownLatch));
        // DoEvery(userAndAccount, "ylpfbfz", "10010800", "ywc", auditdynamicList, listAll, listYWC, listUniqUser);

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
//        new Thread() {
//            public void run() {
//                DoEvery(userAndAccount, "jxpf", "10010937", "jxglbgs", auditdynamicList, listAll, listJxglbgs, listUniqUser, countDownLatch);
//                countDownLatch.countDown();
//                Thread.currentThread().interrupt();
//            }
//        }.start();
        ThreadUtil.execute(() -> DoEvery(userAndAccount, "jxpf", "10010937", "jxglbgs", auditdynamicList, listAll, listJxglbgs, listUniqUser, countDownLatch));
        // DoEvery(userAndAccount, "jxpf", "10010937", "jxglbgs", auditdynamicList, listAll, listJxglbgs, listUniqUser);

        // 研究生管理办公室
        List<String> listYjsglbgs = new ArrayList<>();
        listYjsglbgs.addAll(talentUndo);
        listYjsglbgs.addAll(graduateUndo);
//        new Thread() {
//            public void run() {
//                DoEvery(userAndAccount, "sftgsdsf", "10011654", "yjsglbgs", auditdynamicList, listAll, listYjsglbgs, listUniqUser, countDownLatch);
//                countDownLatch.countDown();
//                Thread.currentThread().interrupt();
//            }
//        }.start();
        ThreadUtil.execute(() -> DoEvery(userAndAccount, "sftgsdsf", "10011654", "yjsglbgs", auditdynamicList, listAll, listYjsglbgs, listUniqUser, countDownLatch));
        // DoEvery(userAndAccount, "sftgsdsf", "10011654", "yjsglbgs", auditdynamicList, listAll, listYjsglbgs, listUniqUser);

        //党办
        List<String> listDb = new ArrayList<>();
//        new Thread() {
//            public void run() {
//                DoEvery(userAndAccount, "ydyf", "10040430", "db", auditdynamicList, listAll, listDb, listUniqUser, countDownLatch);
//                countDownLatch.countDown();
//                Thread.currentThread().interrupt();
//            }
//        }.start();
        ThreadUtil.execute(() -> DoEvery(userAndAccount, "ydyf", "10040430", "db", auditdynamicList, listAll, listDb, listUniqUser, countDownLatch));
        // DoEvery(userAndAccount, "ydyf", "10040430", "db", auditdynamicList, listAll, listDb, listUniqUser);

        //党委组织部
        List<String> listDwzzb = new ArrayList<>();
//        new Thread() {
//            public void run() {
//                DoEvery(userAndAccount, "zzsc", "10040495", "dwzzb", auditdynamicList, listAll, listDwzzb, listUniqUser, countDownLatch);
//                countDownLatch.countDown();
//                Thread.currentThread().interrupt();
//            }
//        }.start();
        ThreadUtil.execute(() -> DoEvery(userAndAccount, "zzsc", "10040495", "dwzzb", auditdynamicList, listAll, listDwzzb, listUniqUser, countDownLatch));
        //DoEvery(userAndAccount, "zzsc", "10040495", "dwzzb", auditdynamicList, listAll, listDwzzb, listUniqUser);

        //纪委办公室
        List<String> listJwbgs = new ArrayList<>();
//        new Thread() {
//            public void run() {
//                DoEvery(userAndAccount, "jlsc", "10040493", "jwbgs", auditdynamicList, listAll, listJwbgs, listUniqUser, countDownLatch);
//                countDownLatch.countDown();
//                Thread.currentThread().interrupt();
//            }
//        }.start();
        ThreadUtil.execute(() -> DoEvery(userAndAccount, "jlsc", "10040493", "jwbgs", auditdynamicList, listAll, listJwbgs, listUniqUser, countDownLatch));
        // DoEvery(userAndAccount, "jlsc", "10040493", "jwbgs", auditdynamicList, listAll, listJwbgs, listUniqUser);

        //宣传部
        List<String> listCcb = new ArrayList<>();
//        new Thread() {
//            public void run() {
//                DoEvery(userAndAccount, "yyxtsc", "10011136", "ccb", auditdynamicList, listAll, listCcb, listUniqUser, countDownLatch);
//                countDownLatch.countDown();
//                Thread.currentThread().interrupt();
//            }
//        }.start();
        ThreadUtil.execute(() -> DoEvery(userAndAccount, "yyxtsc", "10011136", "ccb", auditdynamicList, listAll, listCcb, listUniqUser, countDownLatch));
        // DoEvery(userAndAccount, "yyxtsc", "10011136", "ccb", auditdynamicList, listAll, listCcb, listUniqUser);

        //门诊办公室
        List<String> listMzbgs = new ArrayList<>();
//        new Thread() {
//            public void run() {
//                DoEvery(userAndAccount, "mzylpf", "10010352", "mzbgs", auditdynamicList, listAll, listMzbgs, listUniqUser, countDownLatch);
//                countDownLatch.countDown();
//                Thread.currentThread().interrupt();
//            }
//        }.start();
        ThreadUtil.execute(() -> DoEvery(userAndAccount, "mzylpf", "10010352", "mzbgs", auditdynamicList, listAll, listMzbgs, listUniqUser, countDownLatch));
        // DoEvery(userAndAccount, "mzylpf", "10010352", "mzbgs", auditdynamicList, listAll, listMzbgs, listUniqUser);

        //护理部
        List<String> listHlb = new ArrayList<>();
//        new Thread() {
//            public void run() {
//                DoEvery(userAndAccount, "ylpfbfz", "10020783", "hlb", auditdynamicList, listAll, listHlb, listUniqUser, countDownLatch);
//                countDownLatch.countDown();
//                Thread.currentThread().interrupt();
//            }
//        }.start();
        ThreadUtil.execute(() -> DoEvery(userAndAccount, "ylpfbfz", "10020783", "hlb", auditdynamicList, listAll, listHlb, listUniqUser, countDownLatch));
        // DoEvery(userAndAccount, "ylpfbfz", "10020783", "hlb", auditdynamicList, listAll, listHlb, listUniqUser);

        //行风建设办公室
        List<String> listHfjsbgs = new ArrayList<>();
//        new Thread() {
//            public void run() {
//                DoEvery(userAndAccount, "sshbdts", "10011480", "hfjsbgs", auditdynamicList, listAll, listHfjsbgs, listUniqUser, countDownLatch);
//                countDownLatch.countDown();
//                Thread.currentThread().interrupt();
//            }
//        }.start();

        ThreadUtil.execute(() -> DoEvery(userAndAccount, "sshbdts", "10011480", "hfjsbgs", auditdynamicList, listAll, listHfjsbgs, listUniqUser, countDownLatch));
        // DoEvery(userAndAccount, "sshbdts", "10011480", "hfjsbgs", auditdynamicList, listAll, listHfjsbgs, listUniqUser);

        //region 线程处理这部分
        // ThreadUtil.newCountDownLatch(9);
        //ThreadUtil.execAsync(() -> saveStockInout(gmsOrder.getSubOrderId(), "5"));
        //endregion
        try {
            countDownLatch.await();
        } catch (Exception ex) {
            log.info(ex.getMessage());
        }
        log.info(String.valueOf(listAll.size()));
        return listAll;
    }

    private void DoEvery(List<userAuditAccount> userAndAccount, String filedtitletype, String username, String title, List<DcaBAuditdynamic> auditdynamicList, List<DcaBAuditdynamic> listAll, List<String> listYWC, List<String> listUniqUser, CountDownLatch countDownLatch) {


        List<String> userUn = userAndAccount.stream().filter(p -> p.getUserName().equals(username)).map(p -> p.getUserAccount()).collect(Collectors.toList());
        List<String> listau = new ArrayList<>();
      //  List<String> userUn = userAuditAccountListYwc.stream().map(p -> p.getUserAccount()).distinct().collect(Collectors.toList());
        if (listYWC.size() > 0) {//需要计算类型

//                for (userAuditAccount item : userAuditAccountListYwc
//                ) {
//                    if (!listau.contains(item.getUserAccount())) {
//                        List<DcaBAuditdynamic> ugList = auditdynamicList.stream().filter(p -> p.getUserAccount().equals(item.getUserAccount()) &&
//                                p.getAuditTitletype().equals(filedtitletype)).collect(Collectors.toList());
//                        if (ugList.size() > 0) {
//                            listau.add(item.getUserAccount());//已完成数据
//                        }
//                    }
//
//                }
            List<DcaBAuditdynamic> listDca = auditdynamicList.stream().filter(p -> userUn.contains(p.getUserAccount()) &&
                    p.getAuditTitletype().equals(filedtitletype)).collect(Collectors.toList());
            listDca.stream().forEach(item -> {
                if (!listau.contains(item.getUserAccount())) {
                    listau.add(item.getUserAccount());
                }
                    }
            );
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
            List<DcaBAuditdynamic> listDca = auditdynamicList.stream().filter(p -> userUn.contains(p.getUserAccount()) &&
                    p.getAuditTitletype().equals(filedtitletype)).collect(Collectors.toList());
            listDca.stream().forEach(item->{
                if (!listau.contains(item.getUserAccount())) {
                    DcaBAuditdynamic dy = new DcaBAuditdynamic();
                    dy.setUserAccount(item.getUserAccount());
                    dy.setAuditTitletype(title);
                    dy.setAuditResult("已完成");
                    listAll.add(dy);
                    listau.add(item.getUserAccount());
                }
            });
//            for (userAuditAccount item : userAuditAccountListYwc
//            ) {
//                List<DcaBAuditdynamic> ugList = auditdynamicList.stream().filter(p -> p.getUserAccount().equals(item.getUserAccount()) &&
//                        p.getAuditTitletype().equals(filedtitletype)).collect(Collectors.toList());
//                if (ugList.size() > 0) {
//                    if (!listau.contains(item.getUserAccount())) {
//                        DcaBAuditdynamic dy = new DcaBAuditdynamic();
//                        dy.setUserAccount(item.getUserAccount());
//                        dy.setAuditTitletype(title);
//                        dy.setAuditResult("已完成");
//                        listAll.add(dy);
//                        listau.add(item.getUserAccount());
//                    }
//                }
//            }
            List<String> otherYwNo = userUn.stream().filter(p -> !listau.contains(p)).collect(Collectors.toList());
            for (String item : otherYwNo
            ) {
                DcaBAuditdynamic dy = new DcaBAuditdynamic();
                dy.setUserAccount(item);
                dy.setAuditTitletype(title);
                dy.setAuditResult("未完成");
                listAll.add(dy);
            }

            List<String> otherYwNoall = listUniqUser.stream().filter(p -> !userUn.contains(p) && !listau.contains(p)).collect(Collectors.toList());
            for (String item : otherYwNoall
            ) {
                DcaBAuditdynamic dy = new DcaBAuditdynamic();
                dy.setUserAccount(item);
                dy.setAuditTitletype(title);
                dy.setAuditResult("不审核");
                listAll.add(dy);
            }
        }

        countDownLatch.countDown();

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

    /**
     * 中级的计算
     * @param professorList
     * @param titleA
     * @param auditTitleType
     * @param userAccount
     * @param auditdynamicList
     */
    private String getNumberA(List<DcaBSciencepublish> professorList, String titleA, String auditTitleType, String userAccount, List<DcaBAuditdynamic> auditdynamicList) {
        double nameF_d = professorList.stream().filter(p -> p.getAuditQkjb().equals(titleA))
                .mapToDouble(p -> Convert.toDouble(p.getJxzcsl() == null ? 0 : p.getJxzcsl(), 0D)).sum();
        if(nameF_d==0){
            return  "";
        }
        String nameF = String.format("%.2f", nameF_d);
        if (nameF.contains(".99")) {
            nameF_d = NumberUtil.add(nameF_d, 0.01D);
        }
        return String.format("%.2f", nameF_d)+titleA;
    }

    private String getNumberA_lc(List<DcaBSciencepublish> professorList, String titleA, String auditTitleType, String userAccount, List<DcaBAuditdynamic> auditdynamicList) {
        double nameF_d = professorList.stream().filter(p -> p.getAuditQkjb().equals(titleA))
                .mapToDouble(p -> Convert.toDouble(p.getLczcsl() == null ? 0 : p.getLczcsl(), 0D)).sum();
        if(nameF_d==0){
            return  "";
        }
        String nameF = String.format("%.2f", nameF_d);

        if (nameF.contains(".99")) {
            nameF_d = NumberUtil.add(nameF_d, 0.01D);
        }
        return String.format("%.2f", nameF_d)+titleA;
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
            dcaBReport.setSchoolDate(DateUtil.format(user.getSchoolDate(), "yyyyMM"));
        }
        dcaBReport.setNpqk(user.getAuditManName()); //内聘情况
        dcaBReport.setZygwDate(user.getZygwDate());
        dcaBReport.setTelephone(user.getTelephone());
        dcaBReport.setBaomingIndex(user.getPatentRanknum());//报名序号
        if (user.getApplyState() == 2) {
            if(dcaBReport.getNtyy()!=null &&!dcaBReport.getNtyy().equals("")) {
                dcaBReport.setNtyy("中途退回");
            }
            if(dcaBReport.getClshjg()!=null &&!dcaBReport.getClshjg().equals("")) {
                dcaBReport.setClshjg("拟退");
            }
        }
        dcaBReport.setApplyState(user.getApplyState());
        //人员类别
        dcaBReport.setYuangongzu(user.getYuangongzu());
        //岗前培训情况
        dcaBReport.setGqpxqk(user.getGqpxqk());
        //规范化医师培训情况
        dcaBReport.setGfhyspxqk(user.getGfhyspxqk());
        //中级水平能力测试情况
        dcaBReport.setZjspnlceqk(user.getZjspnlceqk());
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
        if (value.trim().equals("")) {
            return "0";
        }
        if (!isNumeric(value)) {
            return "0";
        }
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
            //这是在查询时候 传递 岗位等级

            if (!StrUtil.hasBlank(dcaBUser.getKs())) {
                dcaBUser.setGwdjList(dcaBUser.getKs().split(","));
            }

            IPage<DcaBUser> listResult = this.baseMapper.getAllShowUserInfo(page, dcaBUser);

            List<DcaBReport> dcaBReportList = new ArrayList<>();

            if (listResult.getRecords().size() > 0) {
                log.info("数据取到了");
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
        List<DcaBAssitant> assitantList = this.baseMapper.getAssitant();
        List<DcaBPatent> patentList = this.baseMapper.getPatentInfo();
        List<DcaBEducationexperice> dcaBEducationexpericeList= this.baseMapper.getEduexperiennce();

        List<DcaBQualification> dcaBQualificationList=this.baseMapper.getQualification();


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

        /**
         * 法定资质
         */
        List<DcaBAuditdynamic> listfdzz_ys= auditdynamicAuditList.stream().filter(p -> p.getAuditTitletype().equals("sfyszgzs")||p.getAuditTitletype().equals("sfjyhlzgzs")).collect(Collectors.toList());


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

          //  DcaBReport dcaBReport = new DcaBReport();
            DcaBReport dcaBReport2 = new DcaBReport();//双报
            boolean isAddJs = true;
            boolean isAddLc = true;


            List<DcaBReport> dcaBReportList = saveUser.stream().filter(p -> p.getUserAccount().equals(userAccount) && p.getNpPositionName().equals(user.getNpPositionName()) && p.getYear().equals(user.getDcaYear())).collect(Collectors.toList());
            if (dcaBReportList.size() > 0) {
                dcaBReport2 = dcaBReportList.get(0);
                if (dcaBReportList.get(0).getState() > 0) {
                    continue;
                }
            } else {
                dcaBReport2.setId("");
            }
            dcaBReport2.setNpPositionName(user.getNpPositionName());
            dcaBReport2.setIfshuangbao(shuangbao);
            SetUserForReport(user, dcaBReport2);

            /**
             * 法定资质
             */
            Optional<DcaBAuditdynamic> listfdzz = listfdzz_ys.stream()
                    .filter(p -> p.getUserAccount().equals(userAccount)).findFirst();
            InsertDynamic(auditdynamicList, userAccount, String.valueOf(!listfdzz.isPresent() ? "" : GetNullStr(listfdzz.get().getAuditResult())), "fdzz");


            /**
             * 取得湖北省相应专业技术职务资格及时间
             */
            List<DcaBQualification> listquli= dcaBQualificationList.stream()
                    .filter(p -> p.getUserAccount().equals(userAccount) &&p.getQualificationName()!=null &&p.getAuditGrade()!=null&& p.getQualificationName().equals("湖北省专业技术职务资格证书")&&p.getAuditGrade()!=null&&p.getAuditGrade().equals("中级")).collect(Collectors.toList());
            if(listquli.size()<=0){
                listquli= dcaBQualificationList.stream()
                        .filter(p -> p.getUserAccount().equals(userAccount) &&p.getQualificationName()!=null &&p.getAuditGrade()!=null&& p.getQualificationName().equals("湖北省专业技术职务资格证书")&&p.getAuditGrade().contains("初级")).collect(Collectors.toList());
            }
            if(listquli.size()>0) {
                InsertDynamic(auditdynamicList, userAccount, GetNullStr(listquli.get(0).getAuditGrade()), "zyjszwzg");
                InsertDynamic(auditdynamicList, userAccount,  GetNullStr(DateUtil.format(listquli.get(0).getQualificationDate(), "yyyyMM")), "zyjszwzgsj");
            }
            else{
                InsertDynamic(auditdynamicList, userAccount, "", "zyjszwzg");
                InsertDynamic(auditdynamicList, userAccount,  "", "zyjszwzgsj");
            }
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
             * 学历 中专、专科、本科
             */
            Optional<DcaBEducationexperice> zzbysj = dcaBEducationexpericeList.stream()
                    .filter(p -> p.getUserAccount().equals(userAccount) && p.getExpPosition().equals("中专")).findFirst();
            InsertDynamic(auditdynamicList, userAccount, String.valueOf(!zzbysj.isPresent() ? "" : GetNullStr(DateUtil.format(zzbysj.get().getExpEndTime(), "yyyyMM"))), "zzbysj");

            Optional<DcaBEducationexperice> dzbysj = dcaBEducationexpericeList.stream()
                    .filter(p -> p.getUserAccount().equals(userAccount) && p.getExpPosition().equals("大专")).findFirst();
            InsertDynamic(auditdynamicList, userAccount, String.valueOf(!dzbysj.isPresent() ? "" : GetNullStr(DateUtil.format(dzbysj.get().getExpEndTime(), "yyyyMM"))), "dzbysj");

            Optional<DcaBEducationexperice> bkbysj = dcaBEducationexpericeList.stream()
                    .filter(p -> p.getUserAccount().equals(userAccount) && p.getExpPosition().equals("本科")).findFirst();
            InsertDynamic(auditdynamicList, userAccount, String.valueOf(!bkbysj.isPresent() ? "" : GetNullStr(DateUtil.format(bkbysj.get().getExpEndTime(), "yyyyMM"))), "bkbysj");

            Optional<DcaBEducationexperice> ssbysj = dcaBEducationexpericeList.stream()
                    .filter(p -> p.getUserAccount().equals(userAccount) && p.getExpPosition().equals("硕士")).findFirst();
            InsertDynamic(auditdynamicList, userAccount, String.valueOf(!ssbysj.isPresent() ? "" : GetNullStr(DateUtil.format(ssbysj.get().getExpEndTime(), "yyyyMM"))), "ssbysj");

            Optional<DcaBEducationexperice> bsbysj = dcaBEducationexpericeList.stream()
                    .filter(p -> p.getUserAccount().equals(userAccount) && p.getExpPosition().equals("博士")).findFirst();
            InsertDynamic(auditdynamicList, userAccount, String.valueOf(!bsbysj.isPresent() ? "" : GetNullStr(DateUtil.format(bsbysj.get().getExpEndTime(), "yyyyMM"))), "bsbysj");

            /**
             * 入职前最高学历
             */

                 if(user.getSchoolDate()!=null){
                     Optional<DcaBEducationexperice> rzzg = dcaBEducationexpericeList.stream()
                             .filter(p -> p.getUserAccount().equals(userAccount)&&p.getExpEndTime()!=null&&p.getExpEndTime().before(user.getSchoolDate())).sorted(new Comparator<DcaBEducationexperice>() {
                                 @Override
                                 public int compare(DcaBEducationexperice o1, DcaBEducationexperice o2) {
                                     return o2.getExpEndTime().compareTo(o1.getExpEndTime());
                                 }
                             }).findFirst();
                     InsertDynamic(auditdynamicList, userAccount, String.valueOf(!rzzg.isPresent() ? "" : rzzg.get().getExpPosition()), "rzqedu");
                 }

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


            if("中级,初级".contains(user.getGwdj())){
               String Ap= getNumberA(professorList, "A", "publishA", userAccount, auditdynamicList_jiaoshou);
                String Bp= getNumberA(professorList, "B", "publishB", userAccount, auditdynamicList_jiaoshou);
                String Cp= getNumberA(professorList, "C", "publishB", userAccount, auditdynamicList_jiaoshou);
                InsertDynamic(auditdynamicList_jiaoshou, userAccount,Ap+Bp+Cp , "publishA");
                SetABCDEF_Jxzcsl(professorList, "D", "publishD", userAccount, auditdynamicList_jiaoshou);
                SetABCDEF_Jxzcsl(professorList, "E", "publishE", userAccount, auditdynamicList_jiaoshou);
                SetABCDEF_Jxzcsl(professorList, "F", "publishF", userAccount, auditdynamicList_jiaoshou);
            }
            else {
                SetABCDEF_Jxzcsl(professorList, "A", "publishA", userAccount, auditdynamicList_jiaoshou);
                SetABCDEF_Jxzcsl(professorList, "B", "publishB", userAccount, auditdynamicList_jiaoshou);
                SetABCDEF_Jxzcsl(professorList, "C", "publishC", userAccount, auditdynamicList_jiaoshou);
                SetABCDEF_Jxzcsl(professorList, "D", "publishD", userAccount, auditdynamicList_jiaoshou);
                SetABCDEF_Jxzcsl(professorList, "E", "publishE", userAccount, auditdynamicList_jiaoshou);
                SetABCDEF_Jxzcsl(professorList, "F", "publishF", userAccount, auditdynamicList_jiaoshou);
            }
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

            if("中级,初级".contains(user.getGwdj())) {

                String lAp=getNumberA_lc(professorList2, "A", "publishA", userAccount, auditdynamicList_zhurenyishi);
                String lBp=getNumberA_lc(professorList2, "B", "publishB", userAccount, auditdynamicList_zhurenyishi);
                String lCp=getNumberA_lc(professorList2, "C", "publishC", userAccount, auditdynamicList_zhurenyishi);

                InsertDynamic(auditdynamicList_zhurenyishi, userAccount, lAp+lBp+lCp, "publishA");
                SetABCDEF_Lczcsl(professorList2, "D", "publishD", userAccount, auditdynamicList_zhurenyishi);
                SetABCDEF_Lczcsl(professorList2, "E", "publishE", userAccount, auditdynamicList_zhurenyishi);
                SetABCDEF_Lczcsl(professorList2, "F", "publishF", userAccount, auditdynamicList_zhurenyishi);
            }
            else {
                SetABCDEF_Lczcsl(professorList2, "A", "publishA", userAccount, auditdynamicList_zhurenyishi);
                SetABCDEF_Lczcsl(professorList2, "B", "publishB", userAccount, auditdynamicList_zhurenyishi);
                SetABCDEF_Lczcsl(professorList2, "C", "publishC", userAccount, auditdynamicList_zhurenyishi);
                SetABCDEF_Lczcsl(professorList2, "D", "publishD", userAccount, auditdynamicList_zhurenyishi);
                SetABCDEF_Lczcsl(professorList2, "E", "publishE", userAccount, auditdynamicList_zhurenyishi);
                SetABCDEF_Lczcsl(professorList2, "F", "publishF", userAccount, auditdynamicList_zhurenyishi);
            }
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
                    patentStrList.add("专利" + index);
                }
                String patent_name = patentStrList.stream().collect(Collectors.joining("#", "", ""));
                InsertDynamic(auditdynamicList, userAccount, patent_name, "patentNum");
                String patentFund = patentUserList.stream().map(p -> p.getPatentGood() == null ? "" : p.getPatentGood()).collect(Collectors.joining("#", "", ""));
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
                InsertDynamic(auditdynamicList, userAccount, pL + "著", "publicarticle1");
            }
            InsertDynamic(auditdynamicList, userAccount, String.format("%.2f", pC), "publicarticle2");
            //endregion

            //region 教学质量奖与成果奖
            List<DcaBSchoolprize> schoolprizeUserList = schoolprizeList.stream()
                    .filter(p -> p.getUserAccount().equals(userAccount)).collect(Collectors.toList());
            String sp_name = schoolprizeUserList.stream().map(p -> GetNullStr(p.getPrizeName())).collect(Collectors.joining("#", "", ""));
            String sp_denngji = schoolprizeUserList.stream().map(p -> GetNullStr(p.getPrizeGrade())).collect(Collectors.joining("#", "", ""));
            String sp_date = schoolprizeUserList.stream().map(p -> GetNullStr(DateUtil.format(p.getPrizeDate(), "yyyyMM"))).collect(Collectors.joining("#", "", ""));
            String sp_ranknum = schoolprizeUserList.stream().map(p -> p.getRanknum() == null ? "" : String.valueOf(p.getRanknum())).collect(Collectors.joining("#", "", ""));
            InsertDynamic(auditdynamicList, userAccount, sp_name, "schoolprizeName");
            InsertDynamic(auditdynamicList, userAccount, sp_denngji, "schoolprizeDengji");
            InsertDynamic(auditdynamicList, userAccount, sp_date, "schoolprizeDate");
            InsertDynamic(auditdynamicList, userAccount, sp_ranknum, "schoolprizeRanknum");
            //endregion

            //region 精品课程
            List<DcaBCourseclass> courseUserList = courseclassList.stream()
                    .filter(p -> p.getUserAccount().equals(userAccount)).collect(Collectors.toList());
            String cc_name = courseUserList.stream().map(p -> GetNullStr(p.getCourse())).collect(Collectors.joining("#", "", ""));
            String cc_denngji = courseUserList.stream().map(p -> GetNullStr(p.getGrade())).collect(Collectors.joining("#", "", ""));
            String cc_date = courseUserList.stream().map(p -> GetNullStr(DateUtil.format(p.getCoruseDate(), "yyyyMM"))).collect(Collectors.joining("#", "", ""));
            String cc_ranknum = courseUserList.stream().map(p -> p.getRanknum() == null ? "" : String.valueOf(p.getRanknum())).collect(Collectors.joining("#", "", ""));
            InsertDynamic(auditdynamicList, userAccount, cc_name, "courseName");
            InsertDynamic(auditdynamicList, userAccount, cc_denngji, "courseDengji");
            InsertDynamic(auditdynamicList, userAccount, cc_date, "courseDate");
            InsertDynamic(auditdynamicList, userAccount, cc_ranknum, "courseRanknum");
            //endregion

            //region 教学竞赛获奖
            List<DcaBYoungprize> youngprizeUserList = youngprizeList.stream()
                    .filter(p -> p.getUserAccount().equals(userAccount)).collect(Collectors.toList());
            String yp_name = youngprizeUserList.stream().map(p -> GetNullStr(p.getPrizeJb())).collect(Collectors.joining("#", "", ""));
            String yp_denngji = youngprizeUserList.stream().map(p -> GetNullStr(p.getPrizeGrade())).collect(Collectors.joining("#", "", ""));
            String yp_date = youngprizeUserList.stream().map(p -> GetNullStr(DateUtil.format(p.getPrizeDate(), "yyyyMM"))).collect(Collectors.joining("#", "", ""));
            String yp_ranknum = youngprizeUserList.stream().map(p -> p.getRanknum() == null ? "" : String.valueOf(p.getRanknum())).collect(Collectors.joining("#", "", ""));
            InsertDynamic(auditdynamicList, userAccount, yp_name, "youngName");
            InsertDynamic(auditdynamicList, userAccount, yp_denngji, "youngDengji");
            InsertDynamic(auditdynamicList, userAccount, yp_date, "youngDate");
            InsertDynamic(auditdynamicList, userAccount, yp_ranknum, "youngRanknum");
            //endregion

            //region 科研项目教改项目
            List<DcaBSciencesearch> sciencesearchUserList = listScienceSearch.stream()
                    .filter(p -> p.getUserAccount().equals(userAccount)).collect(Collectors.toList());
            String ssu_lb = sciencesearchUserList.stream().filter(p -> p.getAuditTypetp()!=null  && p.getAuditTypetp().equals("按等级")).map(p -> p.getAuditLb() == null ? "" : String.valueOf(p.getAuditLb())).collect(Collectors.joining("#", "", ""));
            String ssu_fund = sciencesearchUserList.stream().filter(p -> p.getAuditTypetp()!=null  && p.getAuditTypetp().equals("按等级")).map(p -> p.getAuditFund() == null ? "" : String.valueOf(p.getAuditFund())).collect(Collectors.joining("#", "", ""));
            String ssu_ranknum = sciencesearchUserList.stream().filter(p ->p.getAuditTypetp()!=null  && p.getAuditTypetp().equals("按等级")).map(p -> p.getAuditRank() == null ? "" : String.valueOf(p.getAuditRank())).collect(Collectors.joining("#", "", ""));


            InsertDynamic(auditdynamicList_zhurenyishi, userAccount, ssu_lb, "sciDjlb");
            InsertDynamic(auditdynamicList_zhurenyishi, userAccount, ssu_fund, "sciDjfund");
            InsertDynamic(auditdynamicList_zhurenyishi, userAccount, ssu_ranknum, "sciDjranknum");

            String ssu_lb_jx = sciencesearchUserList.stream().filter(p -> p.getAuditTypetpjx()!=null  && p.getAuditTypetpjx().equals("按等级")).map(p -> p.getAuditLb() == null ? "" : String.valueOf(p.getAuditLb())).collect(Collectors.joining("#", "", ""));
            String ssu_fund_jx = sciencesearchUserList.stream().filter(p -> p.getAuditTypetpjx()!=null  && p.getAuditTypetpjx().equals("按等级")).map(p -> p.getAuditFund() == null ? "" : String.valueOf(p.getAuditFund())).collect(Collectors.joining("#", "", ""));
            String ssu_ranknum_jx = sciencesearchUserList.stream().filter(p ->p.getAuditTypetpjx()!=null  && p.getAuditTypetpjx().equals("按等级")).map(p -> p.getAuditRank() == null ? "" : String.valueOf(p.getAuditRank())).collect(Collectors.joining("#", "", ""));


            InsertDynamic(auditdynamicList_jiaoshou, userAccount, ssu_lb_jx, "sciDjlb");
            InsertDynamic(auditdynamicList_jiaoshou, userAccount, ssu_fund_jx, "sciDjfund");
            InsertDynamic(auditdynamicList_jiaoshou, userAccount, ssu_ranknum_jx, "sciDjranknum");

            String ssu_lb2 = sciencesearchUserList.stream().filter(p -> p.getAuditTypetp() != null&&  p.getAuditTypetp().equals("按经费")).map(p -> p.getAuditLb() == null ? "" : String.valueOf(p.getAuditLb())).collect(Collectors.joining("#", "", ""));
            String ssu_fund2 = sciencesearchUserList.stream().filter(p -> p.getAuditTypetp() != null&& p.getAuditTypetp().equals("按经费")).map(p -> p.getAuditFund() == null ? "" : String.valueOf(p.getAuditFund())).collect(Collectors.joining("#", "", ""));
            String ssu_ranknum2 = sciencesearchUserList.stream().filter(p -> p.getAuditTypetp() != null&& p.getAuditTypetp().equals("按经费")).map(p -> p.getAuditRank() == null ? "" : String.valueOf(p.getAuditRank())).collect(Collectors.joining("#", "", ""));
            InsertDynamic(auditdynamicList_zhurenyishi, userAccount, ssu_lb2, "sciJflb");
            InsertDynamic(auditdynamicList_zhurenyishi, userAccount, ssu_fund2, "sciJffund");
            InsertDynamic(auditdynamicList_zhurenyishi, userAccount, ssu_ranknum2, "sciJfranknum");

            String ssu_lb2_jx = sciencesearchUserList.stream().filter(p -> p.getAuditTypetpjx() != null&&  p.getAuditTypetpjx().equals("按经费")).map(p -> p.getAuditLb() == null ? "" : String.valueOf(p.getAuditLb())).collect(Collectors.joining("#", "", ""));
            String ssu_fund2_jx = sciencesearchUserList.stream().filter(p -> p.getAuditTypetpjx() != null && p.getAuditTypetpjx().equals("按经费")).map(p -> p.getAuditFund() == null ? "" : String.valueOf(p.getAuditFund())).collect(Collectors.joining("#", "", ""));
            String ssu_ranknum2_jx = sciencesearchUserList.stream().filter(p -> p.getAuditTypetpjx() != null && p.getAuditTypetpjx().equals("按经费")).map(p -> p.getAuditRank() == null ? "" : String.valueOf(p.getAuditRank())).collect(Collectors.joining("#", "", ""));
            InsertDynamic(auditdynamicList_jiaoshou, userAccount, ssu_lb2_jx, "sciJflb");
            InsertDynamic(auditdynamicList_jiaoshou, userAccount, ssu_fund2_jx, "sciJffund");
            InsertDynamic(auditdynamicList_jiaoshou, userAccount, ssu_ranknum2_jx, "sciJfranknum");
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

            //region 援助情况
            List<DcaBAssitant> helpUserList = assitantList.stream()
                    .filter(p -> p.getUserAccount().equals(userAccount)).collect(Collectors.toList());

            String help = helpUserList.stream().map(p -> p.getPzdd()).collect(Collectors.joining("#", "", ""));
            InsertDynamic(auditdynamicList, userAccount, help, "help");
            //endregion

            List<DcaBAuditdynamic> auditdynamicList2 = new ArrayList<>();
            auditdynamicList2.addAll(auditdynamicList);
            if (user.getNpPositionName().equals("教授") || user.getNpPositionName().equals("副教授") || user.getNpPositionName().equals("研究员") || user.getNpPositionName().equals("副研究员")) {
                auditdynamicList2.addAll(auditdynamicList_jiaoshou);
            } else {
                auditdynamicList2.addAll(auditdynamicList_zhurenyishi);
            }
            dcaBReport2.setDcaBAuditdynamicList(auditdynamicList2);
            allData.add(dcaBReport2);


        }

        return allData;
    }


    @Override
    @Transactional
    public List<DcaBUser> findPerson(String userAccount) {
        LambdaQueryWrapper<DcaBUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBUser::getIsDeletemark, 1);//1是未删 0是已删

        if (StringUtils.isNotBlank(userAccount)) {
            queryWrapper.eq(DcaBUser::getUserAccount, userAccount);

        }
        queryWrapper.eq(DcaBUser::getIsDeletemark, 1);
        return this.baseMapper.selectList(queryWrapper);

    }
}