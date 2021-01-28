package cc.mrbird.febs.check.service.impl;

import cc.mrbird.febs.check.dao.CheckBAuditresultMapper;
import cc.mrbird.febs.check.entity.CheckBAuditresult;
import cc.mrbird.febs.check.entity.CheckShowTitle;
import cc.mrbird.febs.check.entity.TotalResultNum;
import cc.mrbird.febs.check.service.ICheckBAuditresultService;
import cc.mrbird.febs.check.service.ICheckBSettingService;
import cc.mrbird.febs.check.service.ICheckDReportService;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.check.entity.CheckBUser;
import cc.mrbird.febs.check.dao.CheckBUserMapper;
import cc.mrbird.febs.check.service.ICheckBUserService;
import cn.hutool.core.util.NumberUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
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

import java.util.*;
import java.time.LocalDate;
import java.util.stream.Collectors;

/**
 * <p>
 * 待审核用户 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-01-22
 */
@Slf4j
@Service("ICheckBUserService")
@DS("slave")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class CheckBUserServiceImpl extends ServiceImpl<CheckBUserMapper, CheckBUser> implements ICheckBUserService {

    @Autowired
    private ICheckBAuditresultService iCheckBAuditresultService;
    @Autowired
    private ICheckDReportService iCheckDReportService;
    @Autowired
    private ICheckBSettingService iCheckBSettingService;

    @Override
    @DS("slave")
    public IPage<CheckBUser> findCheckBUsers(QueryRequest request, CheckBUser checkBUser) {
        try {
            LambdaQueryWrapper<CheckBUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(CheckBUser::getIsDeletemark, 1);//1是未删 0是已删
            CheckBAuditresult checkBAuditresult = new CheckBAuditresult();
            if (StringUtils.isNotBlank(checkBUser.getUserAccount())) {
                queryWrapper.and(wrap -> wrap.eq(CheckBUser::getUserAccount, checkBUser.getUserAccount()).or()
                        .like(CheckBUser::getUserAccountName, checkBUser.getUserAccount()));
                checkBAuditresult.setUserAccount(checkBUser.getUserAccount());

            }
            if (StringUtils.isNotBlank(checkBUser.getDcaYear())) {
                queryWrapper.like(CheckBUser::getDcaYear, checkBUser.getDcaYear());
                checkBAuditresult.setDcaYear(checkBUser.getDcaYear());
            }

            Page<CheckBUser> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            IPage<CheckBUser> pageResult = this.page(page, queryWrapper);

            List<TotalResultNum> checkBAuditresultTotalList = this.iCheckBAuditresultService.findTotalResult(checkBAuditresult);

            List<CheckShowTitle> listAlltitles= this.iCheckBSettingService.findAllTitle();
            List<CheckBAuditresult> auditresultList =this.iCheckBAuditresultService.list();
            if (pageResult.getTotal() > 0L) {
                for (CheckBUser checkBUser1 : pageResult.getRecords()
                ) {
                    double f = checkBAuditresultTotalList.stream().filter(p->p.getUserAccount().equals(checkBUser1.getUserAccount())&&p.getDcaYear().equals(checkBUser1.getDcaYear())).mapToDouble(p -> p.getAuditResult()).sum();
                    checkBUser1.setTotalNum(NumberUtil.roundStr(f, 2));

                    List<CheckBAuditresult> checkBAuditresultList =new ArrayList<>();
                    for (CheckShowTitle checkShowTitle:listAlltitles
                         ) {
                        CheckBAuditresult checkBAuditresult1=new CheckBAuditresult();
                        if(StringUtils.isNotBlank(checkShowTitle.getKs())){
                            if(checkShowTitle.getLb().contains(checkBUser1.getZjlb())){

                             List<CheckBAuditresult> auditresultList1=  auditresultList.stream().filter(p->p.getDcaYear().equals(checkBUser1.getDcaYear())&&
                                                p.getUserAccount().equals(checkBUser1.getUserAccount())
                                     &&p.getCheckUserId().equals(checkShowTitle.getUserAccount())
                                                        &&p.getAuditTitletype().equals(checkShowTitle.getAuditTitletype())).collect(Collectors.toList());
                             if(auditresultList1.size()>0) {
                                 checkBAuditresult1.setAuditTitle(checkShowTitle.getFiledTitle());
                                 checkBAuditresult1.setAuditTitletype(checkShowTitle.getFiledName());
                                 checkBAuditresult1.setAuditResult(auditresultList1.get(0).getAuditResult());
                             }
                             else{
                                 if("A016,A019".contains(checkShowTitle.getAuditTitletype())){
                                     checkBAuditresult1.setAuditTitle(checkShowTitle.getFiledTitle());
                                     checkBAuditresult1.setAuditTitletype(checkShowTitle.getFiledName());
                                     checkBAuditresult1.setAuditResult("");
                                 }
                                 else{
                                     checkBAuditresult1.setAuditTitle(checkShowTitle.getFiledTitle());
                                     checkBAuditresult1.setAuditTitletype(checkShowTitle.getFiledName());
                                     checkBAuditresult1.setAuditResult("未完成");
                                 }
                             }
                            }
                            else{
                                checkBAuditresult1.setAuditTitle(checkShowTitle.getFiledTitle());
                                checkBAuditresult1.setAuditTitletype(checkShowTitle.getFiledName());
                                checkBAuditresult1.setAuditResult("不审核");
                            }
                        }
                        else{
                            List<CheckBAuditresult> auditresultList1=  auditresultList.stream().filter(p->p.getDcaYear().equals(checkBUser1.getDcaYear())&&
                                    p.getUserAccount().equals(checkBUser1.getUserAccount())
                                    &&(p.getCheckUserId().equals(checkBUser1.getKsLeaderPernr()) ||p.getCheckUserId().equals(checkBUser1.getZgLeaderPernr()))
                                    &&p.getAuditTitletype().equals(checkShowTitle.getFiledName())).collect(Collectors.toList());
                            if(auditresultList1.size()>0) {
                                checkBAuditresult1.setAuditTitle(checkShowTitle.getFiledTitle());
                                checkBAuditresult1.setAuditTitletype(checkShowTitle.getFiledName());
                                checkBAuditresult1.setAuditResult(auditresultList1.get(0).getAuditResult());
                            }
                            else{
                                checkBAuditresult1.setAuditTitle(checkShowTitle.getFiledTitle());
                                checkBAuditresult1.setAuditTitletype(checkShowTitle.getFiledName());
                                checkBAuditresult1.setAuditResult("未完成");
                            }
                        }
                        checkBAuditresultList.add(checkBAuditresult1);
                    }
                    checkBUser1.setCheckBAuditresultList(checkBAuditresultList);
                }
            }
            return  pageResult;
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    @DS("slave")
    public IPage<CheckBUser> findCheckBUserList(QueryRequest request, CheckBUser checkBUser) {
        try {
            Page<CheckBUser> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findCheckBUser(page, checkBUser);
        } catch (Exception e) {
            log.error("获取待审核用户失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    @DS("slave")
    public void createCheckBUser(CheckBUser checkBUser) {
        checkBUser.setId(UUID.randomUUID().toString());
        checkBUser.setCreateTime(new Date());
        checkBUser.setIsDeletemark(1);
        this.save(checkBUser);
    }

    @Override
    @Transactional
    @DS("slave")
    public void updateCheckBUser(CheckBUser checkBUser) {
        checkBUser.setModifyTime(new Date());
        this.baseMapper.updateCheckBUser(checkBUser);
    }

    @Override
    @Transactional
    @DS("slave")
    public void deleteCheckBUsers(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }

    @Override
    @Transactional
    @DS("slave")
    public List<CheckBUser> getAll(String userAccount, String dcaYear) {
        LambdaQueryWrapper<CheckBUser> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
            queryWrapper.eq(CheckBUser::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
            queryWrapper.eq(CheckBUser::getDcaYear, dcaYear);
        }
        return this.baseMapper.selectList(queryWrapper);
    }


    @Override
    @Transactional
    @DS("slave")
    public IPage<CheckBUser> findDcaBUsersAudit(QueryRequest request, String userAccount, CheckBUser dcaBUser, int state) {
        try {
            LambdaQueryWrapper<CheckBUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(CheckBUser::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(dcaBUser.getUserAccount())) {
                queryWrapper.and(wrap -> wrap.eq(CheckBUser::getUserAccount, dcaBUser.getUserAccount()).or()
                        .like(CheckBUser::getUserAccountName, dcaBUser.getUserAccount()));

            }
            if (StringUtils.isNotBlank(dcaBUser.getDcaYear())) {
                queryWrapper.eq(CheckBUser::getDcaYear, dcaBUser.getDcaYear());
            }
            queryWrapper.apply("(EXISTS (\n" +
                    "\t\tSELECT\n" +
                    "\t\t\tid\n" +
                    "\t\tFROM\n" +
                    "\t\t\tcheck_b_setting\n" +
                    "\t\tWHERE\n" +
                    "\t\t\tuser_account = '" + userAccount + "' AND LOCATE(check_b_user.zjlb, lb) > 0\n" +
                    "\t)\n" +
                    "OR check_b_user.ks_leader_pernr = '" + userAccount + "'\n" +
                    "OR check_b_user.zg_leader_pernr = '" + userAccount + "')");
            if (state == 3) {
                queryWrapper.apply("EXISTS (\n" +
                        "SELECT\n" +
                        "\tc.id \n" +
                        "FROM\n" +
                        "\tcheck_b_auditresult c\n" +
                        "\tINNER JOIN check_b_setting d ON c.check_user_id=d.user_account \n" +
                        "\tAND c.dca_year = d.dca_year \n" +
                        "\tAND c.audit_titletype = d.audit_titletype \n" +
                        "\tAND d.user_account = '" + userAccount + "' \n" +
                        "\t\n" +
                        "WHERE\n" +
                        "\tc.dca_year = check_b_user.dca_year \n" +
                        "\tAND check_b_user.user_account = c.user_account \n" +
                        "\tAND LOCATE( check_b_user.zjlb, d.lb ) > 0 \n" +

                        "UNION ALL\n" +
                        "\t\t\tSELECT\n" +
                        "\t\t\t\tc.id\n" +
                        "\t\t\tFROM\n" +
                        "\t\t\t\tcheck_b_auditresult c\n" +
                        "\t\t\tINNER JOIN check_d_title d  ON c.check_user_id = '" + userAccount + "'\n" +
                        "\t\t\tAND c.audit_titletype = d.filed_name\n" +
                        "\t\t\tWHERE\n" +
                        "\t\t\t\tc.dca_year = check_b_user.dca_year\n" +
                        "\t\t\tAND check_b_user.user_account = c.user_account\n" +
                        "\t\t\tAND d.check_person IN ('2', '3'))");
            }
            if (state == 1) {
                queryWrapper.apply("NOT EXISTS (\n" +
                        "SELECT\n" +
                        "\tc.id \n" +
                        "FROM\n" +
                        "\tcheck_b_auditresult c\n" +
                        "\tINNER JOIN check_b_setting d ON c.check_user_id=d.user_account \n" +
                        "\tAND c.dca_year = d.dca_year \n" +
                        "\tAND c.audit_titletype = d.audit_titletype \n" +
                        "\tAND d.user_account = '" + userAccount + "' \n" +
                        "\t\n" +
                        "WHERE\n" +
                        "\tc.dca_year = check_b_user.dca_year \n" +
                        "\tAND check_b_user.user_account = c.user_account \n" +
                        "\tAND LOCATE( check_b_user.zjlb, d.lb ) > 0 \n" +
                        "UNION ALL\n" +
                        "\t\t\tSELECT\n" +
                        "\t\t\t\tc.id\n" +
                        "\t\t\tFROM\n" +
                        "\t\t\t\tcheck_b_auditresult c\n" +
                        "\t\t\tINNER JOIN check_d_title d  ON c.check_user_id = '" + userAccount + "'\n" +
                        "\t\t\tAND c.audit_titletype = d.filed_name\n" +
                        "\t\t\tWHERE\n" +
                        "\t\t\t\tc.dca_year = check_b_user.dca_year\n" +
                        "\t\t\tAND check_b_user.user_account = c.user_account\n" +
                        "\t\t\tAND d.check_person IN ('2', '3'))");
            }
            Page<CheckBUser> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            IPage<CheckBUser> listResult = this.page(page, queryWrapper);

            if (state == 3) {
                if (listResult.getRecords().size() > 0) {
                    List<CheckBAuditresult> auditdynamicList = this.iCheckBAuditresultService.getAll(userAccount, "");
                    for (CheckBUser user : listResult.getRecords()
                    ) {
                        List<CheckBAuditresult> listDy = auditdynamicList.stream().filter(p -> p.getUserAccount().equals(user.getUserAccount()) && p.getDcaYear().equals(user.getDcaYear())).collect(Collectors.toList());
                        user.setCheckBAuditresultList(listDy);
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
    @DS("slave")
    public IPage<CheckBUser> findDcaBUsersAuditCustom(QueryRequest request, String userAccount, CheckBUser dcaBUser, int state) {
        try {
            LambdaQueryWrapper<CheckBUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(CheckBUser::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(dcaBUser.getUserAccount())) {
                queryWrapper.and(wrap -> wrap.eq(CheckBUser::getUserAccount, dcaBUser.getUserAccount()).or()
                        .like(CheckBUser::getUserAccountName, dcaBUser.getUserAccount()));

            }
            if (StringUtils.isNotBlank(dcaBUser.getDcaYear())) {
                queryWrapper.eq(CheckBUser::getDcaYear, dcaBUser.getDcaYear());
            }
            queryWrapper.apply("(EXISTS (\n" +
                    "\t\tSELECT\n" +
                    "\t\t\tid\n" +
                    "\t\tFROM\n" +
                    "\t\t\tcheck_b_setting\n" +
                    "\t\tWHERE\n" +
                    "\t\t\tuser_account = '" + userAccount + "' AND LOCATE(check_b_user.zjlb, lb) > 0\n" +

                    "\t)\n" +
                    "OR check_b_user.ks_leader_pernr = '" + userAccount + "'\n" +
                    "OR check_b_user.zg_leader_pernr = '" + userAccount + "')");
            if (state == 3) {
                queryWrapper.apply("EXISTS (\n" +
                        "SELECT\n" +
                        "\tc.id \n" +
                        "FROM\n" +
                        "\tcheck_b_auditresult c\n" +
                        "\tINNER JOIN check_b_setting d ON c.check_user_id=d.user_account \n" +
                        "\tAND c.dca_year = d.dca_year \n" +
                        "\tAND c.audit_titletype = d.audit_titletype \n" +
                        "\tAND d.user_account = '" + userAccount + "' \n" +
                        "\t\n" +
                        "WHERE\n" +
                        "\tc.dca_year = check_b_user.dca_year \n" +
                        "\tAND check_b_user.user_account = c.user_account \n" +
                        "\tAND LOCATE( check_b_user.zjlb, d.lb ) > 0 \n" +
                        "UNION ALL\n" +
                        "\t\t\tSELECT\n" +
                        "\t\t\t\tc.id\n" +
                        "\t\t\tFROM\n" +
                        "\t\t\t\tcheck_b_auditresult c\n" +
                        "\t\t\tINNER JOIN check_d_title d  ON c.check_user_id = '" + userAccount + "'\n" +
                        "\t\t\tAND c.audit_titletype = d.filed_name\n" +
                        "\t\t\tWHERE\n" +
                        "\t\t\t\tc.dca_year = check_b_user.dca_year\n" +
                        "\t\t\tAND check_b_user.user_account = c.user_account\n" +
                        "\t\t\tAND d.check_person IN ('2', '3'))");
            }
            if (state == 1) {
                queryWrapper.apply("NOT EXISTS (\n" +
                        "SELECT\n" +
                        "\tc.id \n" +
                        "FROM\n" +
                        "\tcheck_b_auditresult c\n" +
                        "\tINNER JOIN check_b_setting d ON c.check_user_id=d.user_account \n" +
                        "\tAND c.dca_year = d.dca_year \n" +
                        "\tAND c.audit_titletype = d.audit_titletype \n" +
                        "\tAND d.user_account = '" + userAccount + "' \n" +
                        "\t\n" +
                        "WHERE\n" +
                        "\tc.dca_year = check_b_user.dca_year \n" +
                        "\tAND check_b_user.user_account = c.user_account \n" +
                        "\tAND LOCATE( check_b_user.zjlb, d.lb ) > 0 \n" +
                        "UNION ALL\n" +
                        "\t\t\tSELECT\n" +
                        "\t\t\t\tc.id\n" +
                        "\t\t\tFROM\n" +
                        "\t\t\t\tcheck_b_auditresult c\n" +
                        "\t\t\tINNER JOIN check_d_title d  ON c.check_user_id = '" + userAccount + "'\n" +
                        "\t\t\tAND c.audit_titletype = d.filed_name\n" +
                        "\t\t\tWHERE\n" +
                        "\t\t\t\tc.dca_year = check_b_user.dca_year\n" +
                        "\t\t\tAND check_b_user.user_account = c.user_account\n" +
                        "\t\t\tAND d.check_person IN ('2', '3'))");
            }
            Page<CheckBUser> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            IPage<CheckBUser> listResult = this.page(page, queryWrapper);

            return listResult;
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }
}