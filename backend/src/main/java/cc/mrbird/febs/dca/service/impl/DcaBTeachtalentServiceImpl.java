package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.dao.DcaBSciencepublishMapper;
import cc.mrbird.febs.dca.entity.DcaBTeachtalent;
import cc.mrbird.febs.dca.dao.DcaBTeachtalentMapper;
import cc.mrbird.febs.dca.entity.userXuhao;
import cc.mrbird.febs.dca.service.IDcaBTeachtalentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import cc.mrbird.febs.dca.service.IDcaBUserapplyService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.time.LocalDate;
import java.util.stream.Collectors;

/**
 * <p>
 * 任现职以来完成教学、人才培养情况 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-09-27
 */
@Slf4j
@Service("IDcaBTeachtalentService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBTeachtalentServiceImpl extends ServiceImpl<DcaBTeachtalentMapper, DcaBTeachtalent> implements IDcaBTeachtalentService {

    @Autowired
    IDcaBUserapplyService iDcaBUserapplyService;
    @Autowired
    private DcaBSciencepublishMapper dcaBSciencepublishMapper;

    @Override
    public IPage<DcaBTeachtalent> findDcaBTeachtalents(QueryRequest request, DcaBTeachtalent dcaBTeachtalent) {
        try {
            LambdaQueryWrapper<DcaBTeachtalent> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DcaBTeachtalent::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(dcaBTeachtalent.getUserAccount())) {
                queryWrapper.and(wrap -> wrap.eq(DcaBTeachtalent::getUserAccount, dcaBTeachtalent.getUserAccount()).or()
                        .like(DcaBTeachtalent::getUserAccountName, dcaBTeachtalent.getUserAccount()));

            }
            if (StringUtils.isNotBlank(dcaBTeachtalent.getAuditManName())) {// 年度 和高级、中级、初级
                List<String> userAccountsList = this.iDcaBUserapplyService.getApplyAccount(dcaBTeachtalent.getAuditMan(), dcaBTeachtalent.getAuditManName());
                if (userAccountsList.size() == 0) {
                    userAccountsList.add("qiuc09");
                }
                queryWrapper.in(DcaBTeachtalent::getUserAccount, userAccountsList);
            }
            if (dcaBTeachtalent.getState() != null) {
                queryWrapper.eq(DcaBTeachtalent::getState, dcaBTeachtalent.getState());
            }
            if (StringUtils.isNotBlank(dcaBTeachtalent.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBTeachtalent.getCreateTimeTo())) {
                queryWrapper
                        .ge(DcaBTeachtalent::getCreateTime, dcaBTeachtalent.getCreateTimeFrom())
                        .le(DcaBTeachtalent::getCreateTime, dcaBTeachtalent.getCreateTimeTo());
            }
            if (StringUtils.isNotBlank(dcaBTeachtalent.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaBTeachtalent.getModifyTimeTo())) {
                queryWrapper
                        .ge(DcaBTeachtalent::getModifyTime, dcaBTeachtalent.getModifyTimeFrom())
                        .le(DcaBTeachtalent::getModifyTime, dcaBTeachtalent.getModifyTimeTo());
            }
            if (dcaBTeachtalent.getAuditXuhaoS() != null && (dcaBTeachtalent.getAuditXuhaoS() > 0)) {
                if (dcaBTeachtalent.getAuditXuhaoE() == null || dcaBTeachtalent.getAuditXuhaoE().equals(0)) {
                    dcaBTeachtalent.setAuditXuhaoE(dcaBTeachtalent.getAuditXuhaoS());
                }
                queryWrapper.apply(" dca_b_teachtalent.user_account in " +
                        "(select user_account from dca_b_user where patent_ranknum" +
                        " between " + dcaBTeachtalent.getAuditXuhaoS() + " and" +
                        " " + dcaBTeachtalent.getAuditXuhaoE() + ")");
            }

            Page<DcaBTeachtalent> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            List<userXuhao> xuhaoList = this.dcaBSciencepublishMapper.getXuhao();
            IPage<DcaBTeachtalent> result = this.page(page, queryWrapper);
            for (DcaBTeachtalent item : result.getRecords()
            ) {
                List<userXuhao> list2 = xuhaoList.stream().filter(p -> p.getUserAccount().equals(item.getUserAccount())).collect(Collectors.toList());
                if (list2.size() > 0) {
                    item.setAuditXuhao(list2.get(0).getPatentRanknum());
                }
            }
            return  result;
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<DcaBTeachtalent> findDcaBTeachtalentList(QueryRequest request, DcaBTeachtalent dcaBTeachtalent) {
        try {
            Page<DcaBTeachtalent> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findDcaBTeachtalent(page, dcaBTeachtalent);
        } catch (Exception e) {
            log.error("获取任现职以来完成教学、人才培养情况失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createDcaBTeachtalent(DcaBTeachtalent dcaBTeachtalent) {
        dcaBTeachtalent.setId(UUID.randomUUID().toString());
        dcaBTeachtalent.setCreateTime(new Date());
        dcaBTeachtalent.setIsDeletemark(1);
        this.save(dcaBTeachtalent);
    }

    @Override
    @Transactional
    public void updateDcaBTeachtalent(DcaBTeachtalent dcaBTeachtalent) {
        dcaBTeachtalent.setModifyTime(new Date());
        this.baseMapper.updateDcaBTeachtalent(dcaBTeachtalent);
    }

    @Override
    @Transactional
    public void deleteDcaBTeachtalents(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }

    @Override
    @Transactional
    public void deleteByuseraccount(String userAccount) {
        this.baseMapper.deleteByAccount(userAccount);
    }

}